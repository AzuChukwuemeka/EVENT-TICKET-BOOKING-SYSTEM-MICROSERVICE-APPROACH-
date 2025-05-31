package com.ticketingSystem.ticketServiceConsumer.DTO;

import java.net.URI;
import java.util.ArrayList;
import java.util.Map;

public record PaymentIntentRecord(String amount, String client_id, ArrayList<String> paymentMethodTypes, String confirmationMethod, Map<String, Object> extraDetails, URI backend_webhook_successful, URI backend_webhook_failure) {
}
