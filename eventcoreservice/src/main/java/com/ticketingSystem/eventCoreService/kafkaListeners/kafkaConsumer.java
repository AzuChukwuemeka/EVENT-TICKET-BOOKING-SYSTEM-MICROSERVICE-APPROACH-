package com.ticketingSystem.eventCoreService.kafkaListeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketingSystem.eventCoreService.model.SeatGeneratedStatus;
import com.ticketingSystem.eventCoreService.repository.EventRepositoryI;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

//@Configuration
public class kafkaConsumer {
    private final ObjectMapper objectMapper;
    private final EventRepositoryI eventRepository;
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    public kafkaConsumer(ObjectMapper objectMapper, EventRepositoryI eventRepository) {
        this.objectMapper = objectMapper;
        this.eventRepository = eventRepository;
    }
    @KafkaListener(topics = "event.public.tbl_seat_generated_outbox")
    public void kafkaListener(ConsumerRecord<String, String> consumerRecord, Acknowledgment acknowledgment) throws JsonProcessingException {
        JsonNode rootNode = objectMapper.readTree(consumerRecord.value());
        JsonNode payload = rootNode.path("payload");
        JsonNode after = payload.path("after");
        if(jsonValid(after)){
            SeatGeneratedStatus seatGeneratedStatus = objectMapper.readValue(after.toString(), SeatGeneratedStatus.class);
            eventRepository.updateEventToSuccessfullyGenerated(seatGeneratedStatus.event_id());
            log.info("Set event with eventID: ".concat(seatGeneratedStatus.event_id().toString()).concat("to successfully generated"));
            acknowledgment.acknowledge();
        }else {
            log.info("Error Processing Message with id: ".concat("add later"));
        }
    }

    private boolean jsonValid(JsonNode after) {
        return !after.isMissingNode() && !after.isNull();
    }
}
