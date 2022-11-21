package com.brianmuigai.thedrone.CustomExceptions;

import com.brianmuigai.thedrone.entities.Drone;

public class DroneNotAvailableException extends RuntimeException {
    public DroneNotAvailableException(Drone drone) {
        super(drone.getSerialNumber()+" is "+drone.getState());
    }
}
