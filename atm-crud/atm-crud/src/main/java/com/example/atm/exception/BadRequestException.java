
package com.example.atm.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) { super(message); }
}
