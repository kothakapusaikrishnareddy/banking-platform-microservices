package com.bank.transactionservice.util;

import com.bank.transactionservice.entity.TransactionType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component
public class TransactionReferenceGenerator {

    private static final String PREFIX = "TXN";

    public String generateTransactionReference(){
        String datePart =
                LocalDate.now()
                        .format(
                                DateTimeFormatter
                                        .ofPattern("yyyyMMdd")
                        );
        int randomNumber = 100000 + new Random().nextInt(900000);

        return PREFIX + datePart + randomNumber;

    }
}
