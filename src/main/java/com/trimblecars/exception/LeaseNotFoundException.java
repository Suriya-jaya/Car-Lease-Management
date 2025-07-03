package com.trimblecars.exception;

public class LeaseNotFoundException extends RuntimeException {
    public LeaseNotFoundException(Long id) {
        super("Lease not found with ID: " + id);
    }
}
