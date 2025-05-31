package com.ticketingSystem.bookingService.DTO;

import java.net.URI;
import java.util.List;
import java.util.Map;

public class PaymentIntentDTO {
    String amount;
    String client_id;
    List<String> payment_method_types;
    String last_payment_error;
    String confirmation_method;
    Map<String, Object> extraDetails;
    URI backend_webhook_successful;
    URI backend_webhook_failure;
    public PaymentIntentDTO(String amount, String client_secret, List<String> payment_method_types, String last_payment_error, String confirmation_method) {
        this.client_id = client_secret;
        this.amount = amount;
        this.payment_method_types = payment_method_types;
        this.last_payment_error = last_payment_error;
        this.confirmation_method = confirmation_method;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public List<String> getPayment_method_types() {
        return payment_method_types;
    }

    public void setPayment_method_types(List<String> payment_method_types) {
        this.payment_method_types = payment_method_types;
    }

    public String getLast_payment_error() {
        return last_payment_error;
    }

    public void setLast_payment_error(String last_payment_error) {
        this.last_payment_error = last_payment_error;
    }

    public String getConfirmation_method() {
        return confirmation_method;
    }

    public void setConfirmation_method(String confirmation_method) {
        this.confirmation_method = confirmation_method;
    }

    public Map<String, Object> getExtraDetails() {
        return extraDetails;
    }

    public void setExtraDetails(Map<String, Object> extraDetails) {
        this.extraDetails = extraDetails;
    }

    public URI getBackend_webhook_successful() {
        return backend_webhook_successful;
    }

    public void setBackend_webhook_successful(URI backend_webhook_successful) {
        this.backend_webhook_successful = backend_webhook_successful;
    }

    public URI getBackend_webhook_failure() {
        return backend_webhook_failure;
    }

    public void setBackend_webhook_failure(URI backend_webhook_failure) {
        this.backend_webhook_failure = backend_webhook_failure;
    }

    @Override
    public String toString() {
        return "PaymentIntentDTO{" +
                "amount='" + amount + '\'' +
                ", client_id='" + client_id + '\'' +
                ", payment_method_types=" + payment_method_types +
                ", last_payment_error='" + last_payment_error + '\'' +
                ", confirmation_method='" + confirmation_method + '\'' +
                ", extraDetails=" + extraDetails +
                ", backend_webhook_successful=" + backend_webhook_successful +
                ", backend_webhook_failure=" + backend_webhook_failure +
                '}';
    }
}