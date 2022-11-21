package com.brianmuigai.thedrone.CustomExceptions;

import com.brianmuigai.thedrone.entities.Drone;

public class AboveDroneWeightLimitException extends RuntimeException{

    public AboveDroneWeightLimitException(Drone drone) {
        super("Drone with serial number "+drone.getSerialNumber()+" has a weight limit of "+drone.getWeightLimit());
    }
}
