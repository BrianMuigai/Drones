package com.brianmuigai.thedrone.CustomExceptions;

import com.brianmuigai.thedrone.entities.Drone;

public class BatteryLowException extends RuntimeException{

    public BatteryLowException(Drone drone) {
        super("Battery Low! at "+drone.getBatteryCapacity() + "%");
    }
}
