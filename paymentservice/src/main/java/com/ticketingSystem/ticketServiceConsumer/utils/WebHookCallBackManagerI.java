package com.ticketingSystem.ticketServiceConsumer.utils;

import com.ticketingSystem.ticketServiceConsumer.DTO.PaymentIntentRecord;
import com.ticketingSystem.ticketServiceConsumer.model.PaymentStatusRecord;

public interface WebHookCallBackManagerI {
    PaymentStatusRecord paymentSuccessful(PaymentIntentRecord paymentIntentDTO, String jwtToken);
    PaymentStatusRecord paymentFailure(PaymentIntentRecord paymentIntentDTO, String jwtToken);
}
