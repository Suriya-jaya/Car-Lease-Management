package com.trimblecars.exception;

public class CarNotFoundException extends RuntimeException {
    public CarNotFoundException(Long id) {
        super("Car not found with ID: " + id);
    }
}
