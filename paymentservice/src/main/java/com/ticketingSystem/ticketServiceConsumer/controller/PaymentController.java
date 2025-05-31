package com.ticketingSystem.ticketServiceConsumer.controller;

import com.ticketingSystem.ticketServiceConsumer.DTO.PaymentIntentRecord;
import com.ticketingSystem.ticketServiceConsumer.model.PaymentStatusRecord;
import com.ticketingSystem.ticketServiceConsumer.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/payment")
public class PaymentController {
    private final PaymentService paymentService;
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    @PostMapping("/pay")
    public PaymentStatusRecord handlePayment(@Valid @RequestBody PaymentIntentRecord paymentIntentDTO, @RequestHeader("Authorization") String jwtToken){
        //Other things to be done to handle payment just switching it here
        return paymentService.validatePayment(paymentIntentDTO, jwtToken);
    }
}
