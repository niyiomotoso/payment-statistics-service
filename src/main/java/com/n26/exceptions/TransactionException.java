package com.n26.exceptions;

public class TransactionException extends Exception{
    private static final long serialVersionUID = 1L;

    public TransactionException(String errorCode) {
        super(errorCode);
    }
}
