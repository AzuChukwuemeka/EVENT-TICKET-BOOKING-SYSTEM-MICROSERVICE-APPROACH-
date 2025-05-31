package com.ticketingSystem.ticketServiceConsumer.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class AtmPaymentImpl implements PaymentWalletServiceI {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //Just mocking a payment service a dummy version
    @Override
    public void pay(String amount, String accountDetails) {
        int random = getRandom();
//        if(random > 6){
//            logger.info("Amount: ".concat(amount).concat("paid to: ").concat(accountDetails));
//            return;
//        }
//        throw new PaymentProcessingException("Mocked Payment Failure");
    }
    @Override
    public void reversePay(String amount, String accountDetails) {
        logger.warn("Amount: ".concat(amount).concat(" from user: ").concat(accountDetails).concat(" could not be paid"));
        logger.warn("Amount : ".concat(amount).concat(" from user: ").concat(accountDetails).concat(" was reversed"));
    }
    private int getRandom(){
        return new SecureRandom().nextInt(1,10);
    }
}
