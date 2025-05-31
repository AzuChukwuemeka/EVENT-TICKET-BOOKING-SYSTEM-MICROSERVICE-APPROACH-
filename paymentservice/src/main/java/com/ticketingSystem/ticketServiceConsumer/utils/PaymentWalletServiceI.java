package com.ticketingSystem.ticketServiceConsumer.utils;

public interface PaymentWalletServiceI {
    void pay(String amount, String accountDetails);
    void reversePay(String amount, String accountDetails);
}
