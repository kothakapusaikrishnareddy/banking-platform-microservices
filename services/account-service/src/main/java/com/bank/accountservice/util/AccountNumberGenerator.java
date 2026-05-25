package com.bank.accountservice.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component
public class AccountNumberGenerator {

    private static final String PREFIX = "ACC";

    public String generateAccountNumber() {

        // Current date
        String datePart =
                LocalDate.now()
                        .format(
                                DateTimeFormatter
                                        .ofPattern("yyyyMMdd")
                        );

        // Random 6-digit number
        int randomNumber =
                100000 + new Random().nextInt(900000);

        return PREFIX + datePart + randomNumber;
    }
}