package com.bank.customerservice.exception;

public class CustomerProfileAlreadyExistsException  extends RuntimeException{
    public CustomerProfileAlreadyExistsException(String message) {
        super(message);
    }
}
