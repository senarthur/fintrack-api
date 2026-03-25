package com.arthursena.fin_track.exception;

public class TransactionNotFoundException extends RuntimeException {

    public TransactionNotFoundException(String msg) {
        super(msg);
    }
    
}