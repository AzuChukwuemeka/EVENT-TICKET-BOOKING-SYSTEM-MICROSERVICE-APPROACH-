package com.ticketingSystem.seatService.kafkaListeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketingSystem.seatService.model.BookingOutbox;
import com.ticketingSystem.seatService.model.BookingRecord;
import com.ticketingSystem.seatService.service.SeatService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

@Configuration
public class kafkaConsumer {
    private final ObjectMapper objectMapper;
    private final SeatService seatService;
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    public kafkaConsumer(ObjectMapper objectMapper, SeatService seatService) {
        this.objectMapper = objectMapper;
        this.seatService = seatService;
    }
    @KafkaListener(topics = "event.public.tbl_booking_outbox")
    public void kafkaListener(ConsumerRecord<String, String> consumerRecord, Acknowledgment acknowledgment) throws JsonProcessingException {
        JsonNode rootNode = objectMapper.readTree(consumerRecord.value());
        JsonNode payload = rootNode.path("payload");
        JsonNode after = payload.path("after");
        if(jsonValid(after)){
            BookingRecord bookingRecord = objectMapper.readValue(after.toString(), BookingRecord.class);
            seatService.updateSeatToBooked(bookingRecord.seatId());
            acknowledgment.acknowledge();
        }else {
            log.info("Error Processing Message with id: ".concat("add later"));
        }
    }

    private boolean jsonValid(JsonNode after) {
        return !after.isMissingNode() && !after.isNull();
    }
}
