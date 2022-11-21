package com.brianmuigai.thedrone.responses;

import com.brianmuigai.thedrone.entities.Delivery;
import com.brianmuigai.thedrone.entities.Drone;
import com.brianmuigai.thedrone.entities.Medication;

import java.util.List;

public class ListResponse {
    public List<Drone> droneList;
    public List<Medication> medicationList;
    public List<Delivery> deliveryList;
}
