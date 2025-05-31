package com.ticketingSystem.bookingService.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ticketingSystem.bookingService.DTO.BookingDTORecord;
import com.ticketingSystem.bookingService.DTO.PaymentIntentDTO;
import com.ticketingSystem.bookingService.exception.CannotAcquireSeatLockException;
import com.ticketingSystem.bookingService.exception.SeatPurchasedException;
import com.ticketingSystem.bookingService.model.BookingOutbox;
import com.ticketingSystem.bookingService.model.BookingRecord;
import com.ticketingSystem.bookingService.repository.BookingRepositoryI;
import com.ticketingSystem.bookingService.utils.JwtServiceI;
import com.ticketingSystem.bookingService.utils.UUIDGeneratorI;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.CannotProceedException;
import java.net.URI;
import java.time.Duration;
import java.util.*;

@Service
public class BookingService {
    private final RedisTemplate<String, String> redisTemplate;
    private final UUIDGeneratorI uuidGenerator;
    private final BookingRepositoryI bookingRepository;
    private final JwtServiceI jwtService;
    public BookingService(RedisTemplate<String, String> redisTemplate, UUIDGeneratorI uuidGenerator, BookingRepositoryI bookingRepository, JwtServiceI jwtService) {
        this.redisTemplate = redisTemplate;
        this.uuidGenerator = uuidGenerator;
        this.bookingRepository = bookingRepository;
        this.jwtService = jwtService;
    }
    public List<BookingRecord> getBookingRecords(String jwt){
        return bookingRepository.getBooking(getUsername(jwt));
    }
     public PaymentIntentDTO bookSeat(BookingDTORecord bookingDTO, String jwtToken) throws CannotProceedException, JsonProcessingException {
         String username = getUsername(jwtToken);
         RedisLockDetails result = createRedisLockDetails(bookingDTO.seat_id(), username);
         Boolean lock = attemptSeatLock(result.redis_lock_key(), result.redis_user_value());
         if(Boolean.TRUE.equals(lock)){
             //CHECK IF BOOKING ALREADY EXISTS FOR THAT SEAT THROWS ERROR ON WEB LAYER IF BOOKING EXISTS
             bookingRepository.checkSeatHasBeenBooked(bookingDTO.seat_id());
             BookingRecord bookingEntry = createPendingBooking(bookingDTO, username);
             PaymentIntentDTO paymentIntentDTO = createPaymentIntent(bookingEntry, username);
             HashMap<String, Object> intentDetails = createPaymentIntentDetails(username, bookingEntry);
             paymentIntentDTO.setExtraDetails(intentDetails);
             bookingRepository.saveBookingToDatabase(bookingEntry);
             paymentIntentDTO.setBackend_webhook_successful(URI.create("http://localhost:9090/api/v1/booking/webhook/payment_successful"));
             paymentIntentDTO.setBackend_webhook_failure(URI.create("http://localhost:9090/api/v1/booking/webhook/payment_failed"));
             return paymentIntentDTO;
         }
         throw new CannotAcquireSeatLockException();
     }
    @Transactional
    //Using CDC to maintain consistency
    public void paymentSuccessful(String jwtToken, PaymentIntentDTO paymentIntentDTO) throws JsonProcessingException {
        String username = getUsername(jwtToken);
        PaymentIntentDetails paymentIntentDetails = getPaymentIntentDetails(paymentIntentDTO.getExtraDetails());
        //CHECK IF REDIS LOCK STILL BELONGS TO USER
        if(acquireLock(username, paymentIntentDetails.seat_id())){
            bookingRepository.updateBookingStatusToPaid(paymentIntentDetails.booking_id());
            BookingRecord bookingRecord = new BookingRecord(
                    paymentIntentDetails.booking_id,
                    paymentIntentDetails.seat_id,
                    username,
                    paymentIntentDTO.getAmount(),
                    false
            );
            BookingOutbox bookingOutboxTableModel = createBookingOutboxTableModel(bookingRecord);
            bookingRepository.saveBookingToBookingOutBoxTable(bookingOutboxTableModel);
            return;
        }
        throw new SeatPurchasedException();
    }
    public void paymentFailed(PaymentIntentDTO paymentIntentDTO){
        Map<String, Object> extraDetails = paymentIntentDTO.getExtraDetails();
        PaymentIntentDetails paymentIntentDetails = getPaymentIntentDetails(extraDetails);
        redisTemplate.delete("seat:".concat(paymentIntentDetails.seat_id.toString()));
        bookingRepository.deleteBooking(paymentIntentDetails.booking_id);
    }
    private String getUsername(String jwtToken) {
        System.out.println(jwtToken);
        return jwtService.isTokenValid(jwtToken).getSubject();
    }
    private boolean acquireLock(String username, UUID seat_id){
        RedisLockDetails redisLockDetails = createRedisLockDetails(seat_id, username);
        String redis_lock_owner = redisTemplate.opsForValue().get(redisLockDetails.redis_lock_key);
        return redis_lock_owner.equals(redisLockDetails.redis_user_value());
    }
    private Boolean attemptSeatLock(String redis_lock_key, String redis_user_value) {
        return redisTemplate.opsForValue().setIfAbsent(redis_lock_key, redis_user_value, Duration.ofMinutes(8));
    }
    private BookingOutbox createBookingOutboxTableModel(BookingRecord bookingRecord) {
        return new BookingOutbox(uuidGenerator.getUUID(),BookingRecord.class.getName(), bookingRecord);
    }
    private PaymentIntentDTO createPaymentIntent(BookingRecord savedBooking, String username) {
        return new PaymentIntentDTO(
                savedBooking.price(),
                username,
                List.of("card"),
                null,
                "automatic"
        );
    }
    private HashMap<String, Object> createPaymentIntentDetails(String username, BookingRecord savedBooking) {
        HashMap<String, Object> extraDetails = new HashMap<>();
        extraDetails.put("username", username);
        extraDetails.put("seat_id", savedBooking.seat_id());
        extraDetails.put("booking_id", savedBooking.bookingId());
        return extraDetails;
    }
    private RedisLockDetails createRedisLockDetails(UUID seat_id, String username) {
        String redis_lock_key = "seat:".concat(seat_id.toString());
        String redis_user_value = "user:".concat(username);
        return new RedisLockDetails(redis_lock_key, redis_user_value);
    }
    private BookingRecord createPendingBooking(BookingDTORecord bookingDTO, String username) {
        return new BookingRecord(uuidGenerator.getUUID(),bookingDTO.seat_id(),username, bookingDTO.price(),false);
    }
    private PaymentIntentDetails getPaymentIntentDetails(Map<String, Object> extraDetails) {
        UUID seat_id = UUID.fromString((String) extraDetails.get("seat_id"));
        UUID booking_id = UUID.fromString((String) extraDetails.get("booking_id"));
        return new PaymentIntentDetails(seat_id, booking_id);
    }
    private record PaymentIntentDetails(UUID seat_id, UUID booking_id) {
    }
    private record RedisLockDetails(String redis_lock_key, String redis_user_value) {
    }

}
