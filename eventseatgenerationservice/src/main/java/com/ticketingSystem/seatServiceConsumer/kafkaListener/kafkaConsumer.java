package com.ticketingSystem.seatServiceConsumer.kafkaListener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketingSystem.seatServiceConsumer.model.Event;
import com.ticketingSystem.seatServiceConsumer.model.EventOutboxEvent;
import com.ticketingSystem.seatServiceConsumer.service.SeatServiceI;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

@Configuration
public class kafkaConsumer {
    private final SeatServiceI seatService;
    private final ObjectMapper objectMapper;
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    public kafkaConsumer(SeatServiceI seatService, ObjectMapper objectMapper) {
        this.seatService = seatService;
        this.objectMapper = objectMapper;
    }
    @KafkaListener(topics = "event.public.tbl_event_outbox")
    public void kafkaListener(ConsumerRecord<String, String> consumerRecord, Acknowledgment acknowledgment) throws JsonProcessingException {
        JsonNode rootNode = objectMapper.readTree(consumerRecord.value());
        JsonNode payload = rootNode.path("payload");
        JsonNode after = payload.path("after");
        if(jsonValid(after)){
            EventOutboxEvent eventOutboxEvent = objectMapper.readValue(after.toString(), EventOutboxEvent.class);
            Event event = objectMapper.readValue(eventOutboxEvent.payload(), Event.class);
            seatService.generateSeats(event);
            acknowledgment.acknowledge();
        }else {
            log.info("Error Processing Message with id: Invalid JSON");
        }
    }

    private boolean jsonValid(JsonNode after) {
        return !after.isMissingNode() && !after.isNull();
    }
}
