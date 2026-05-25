package com.bank.customerservice.exception;

public class PanAlreadyExistsException extends RuntimeException {
    public PanAlreadyExistsException(String message) {
        super(message);
    }
}
