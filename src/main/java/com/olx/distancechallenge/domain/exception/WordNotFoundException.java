package com.olx.distancechallenge.domain.exception;

public class WordNotFoundException extends RuntimeException {

    public WordNotFoundException(String message) {
        super(message);
    }

}