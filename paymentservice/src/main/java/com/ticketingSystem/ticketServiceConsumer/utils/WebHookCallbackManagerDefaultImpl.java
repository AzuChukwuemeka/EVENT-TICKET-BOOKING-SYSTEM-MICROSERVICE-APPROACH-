package com.ticketingSystem.ticketServiceConsumer.utils;

import com.ticketingSystem.ticketServiceConsumer.DTO.PaymentIntentRecord;
import com.ticketingSystem.ticketServiceConsumer.model.PaymentStatusRecord;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WebHookCallbackManagerDefaultImpl implements WebHookCallBackManagerI{
    private final RestTemplate restTemplate;
    public WebHookCallbackManagerDefaultImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Override
    public PaymentStatusRecord paymentSuccessful(PaymentIntentRecord paymentIntent, String jwtToken) {
            //Talk to rest client for success
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PaymentIntentRecord> entity = new HttpEntity<>(paymentIntent,headers);
        restTemplate.exchange(
                paymentIntent.backend_webhook_successful(),
                HttpMethod.POST,
                entity,
                Void.class
        );
        return new PaymentStatusRecord(
                paymentIntent.amount(),
                true,
                paymentIntent.paymentMethodTypes(),
                "email"
        );
    }
    @Override
    public PaymentStatusRecord paymentFailure(PaymentIntentRecord paymentIntentDTO, String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", jwtToken);
        HttpEntity<PaymentIntentRecord> entity = new HttpEntity<>(paymentIntentDTO,headers);
        restTemplate.exchange(
                paymentIntentDTO.backend_webhook_failure(),
                HttpMethod.POST,
                entity,
                Void.class
        );
        return new PaymentStatusRecord(
                paymentIntentDTO.amount(),
                false,
                paymentIntentDTO.paymentMethodTypes(),
                "email"
        );
    }
}