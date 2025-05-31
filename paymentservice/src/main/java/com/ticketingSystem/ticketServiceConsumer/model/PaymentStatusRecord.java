package com.ticketingSystem.ticketServiceConsumer.model;

import java.util.ArrayList;

public record PaymentStatusRecord(String amount, Boolean paymentStatus, ArrayList<String> paymentMethodTypes, String confirmationMethod) {
}
