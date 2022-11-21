package com.brianmuigai.thedrone.CustomExceptions;

public class DroneExistsException extends RuntimeException{

    public DroneExistsException(String serialNumber) {
        super("Drone with serial number "+serialNumber+"exists!");
    }
}
