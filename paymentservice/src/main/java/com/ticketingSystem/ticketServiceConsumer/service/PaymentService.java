package com.ticketingSystem.ticketServiceConsumer.service;

import com.ticketingSystem.ticketServiceConsumer.DTO.PaymentIntentRecord;
import com.ticketingSystem.ticketServiceConsumer.exceptions.PaymentIntentInvalidException;
import com.ticketingSystem.ticketServiceConsumer.model.PaymentStatusRecord;
import com.ticketingSystem.ticketServiceConsumer.utils.JwtServiceI;
import com.ticketingSystem.ticketServiceConsumer.utils.PaymentWalletServiceI;
import com.ticketingSystem.ticketServiceConsumer.utils.WebHookCallbackManagerDefaultImpl;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final WebHookCallbackManagerDefaultImpl webHookCallbackManager;
    private final JwtServiceI jwtService;
    private final PaymentWalletServiceI atmPayment;
    public PaymentService(WebHookCallbackManagerDefaultImpl webHookCallbackManager, JwtServiceI jwtService, PaymentWalletServiceI payment) {
        this.webHookCallbackManager = webHookCallbackManager;
        this.jwtService = jwtService;
        atmPayment = payment;
    }
    public PaymentStatusRecord validatePayment(PaymentIntentRecord paymentIntentDTO, String jwtToken) {
        //Simulating Payment Service succeeding or failing
        if(paymentIntentIsNotValid(paymentIntentDTO)){
            throw new PaymentIntentInvalidException();
        }
        try{
            atmPayment.pay(paymentIntentDTO.amount(), "random");
            return webHookCallbackManager.paymentSuccessful(paymentIntentDTO, jwtToken);
        }catch(Exception e){
            atmPayment.reversePay(paymentIntentDTO.amount(), "random");
            return webHookCallbackManager.paymentFailure(paymentIntentDTO,  jwtToken);
        }
    }
    private boolean paymentIntentIsNotValid(PaymentIntentRecord paymentIntentDTO) {
        return paymentIntentDTO.backend_webhook_failure() == null || paymentIntentDTO.backend_webhook_successful() == null;
    }
    private String getUsername(String jwtToken) {
        return jwtService.getDecodedJwt(jwtToken).getSubject();
    }
}
