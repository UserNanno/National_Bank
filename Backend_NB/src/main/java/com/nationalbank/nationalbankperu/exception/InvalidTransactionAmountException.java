package com.nationalbank.nationalbankperu.exception;

public class InvalidTransactionAmountException extends RuntimeException {
    public InvalidTransactionAmountException(String message) {
        super(message);
    }
}
