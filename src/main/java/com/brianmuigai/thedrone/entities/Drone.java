package com.brianmuigai.thedrone.entities;

import com.brianmuigai.thedrone.enums.DroneModel;
import com.brianmuigai.thedrone.enums.DroneState;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Drone {
    private @Id
    @Column(nullable = false, length = 100) String serialNumber;
    @Enumerated(EnumType.STRING)
    private DroneModel model;
    private @Range(min = 0, max = 500) double weightLimit;
    private @Range(min = 0, max = 100) double batteryCapacity;
    @Enumerated(EnumType.STRING)
    private DroneState state;

    public Drone() {
    }

    public Drone(String serialNumber, DroneModel model, double weightLimit, double batteryCapacity, DroneState state) {
        this.serialNumber = serialNumber;
        this.model = model;
        this.weightLimit = weightLimit;
        this.batteryCapacity = batteryCapacity;
        this.state = state;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public DroneModel getModel() {
        return model;
    }

    public void setModel(DroneModel model) {
        this.model = model;
    }

    public double getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(double weightLimit) {
        this.weightLimit = weightLimit;
    }

    public double getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(double batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public DroneState getState() {
        return state;
    }

    public void setState(DroneState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Drone{" +
                "serialNumber='" + serialNumber + '\'' +
                ", model=" + model +
                ", weightLimit=" + weightLimit +
                ", batteryCapacity=" + batteryCapacity +
                ", state=" + state +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Drone)) return false;
        Drone drone = (Drone) o;
        return model == drone.model &&
                Double.compare(drone.weightLimit, weightLimit) == 0 &&
                Double.compare(drone.batteryCapacity, batteryCapacity) == 0 &&
                state == drone.state && serialNumber.equals(drone.serialNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNumber, model, weightLimit, batteryCapacity, state);
    }
}
