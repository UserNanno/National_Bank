package com.nationalbank.nationalbankperu.exception;

public class InactiveBankAccountException extends RuntimeException {
    public InactiveBankAccountException(String message) {
        super(message);
    }
}
