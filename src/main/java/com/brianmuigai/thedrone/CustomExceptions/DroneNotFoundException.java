package com.brianmuigai.thedrone.CustomExceptions;

public class DroneNotFoundException extends RuntimeException{

    public DroneNotFoundException(String serialNumber) {
        super("Could not find drone "+serialNumber);
    }
}
