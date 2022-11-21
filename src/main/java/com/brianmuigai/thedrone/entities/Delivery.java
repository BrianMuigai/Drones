package com.brianmuigai.thedrone.entities;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class Delivery {

    @GeneratedValue
    private @Id Long id;
    @ManyToOne
    @JoinColumn(name = "drone_serial_number")
    private Drone drone;
    @ManyToMany
    @JoinTable(name = "delivery_medications",
            joinColumns = @JoinColumn(name = "delivery_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "medications_code", referencedColumnName = "code"))
    private List<Medication> medications = new java.util.ArrayList<>();
    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdAt;

    public Delivery(){
        createdAt = OffsetDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Drone getDrone() {
        return drone;
    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

    public void setMedication(Medication medication) {
        medications.add(medication);
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Delivery)) return false;
        Delivery delivery = (Delivery) o;
        return Objects.equals(id, delivery.id) && Objects.equals(createdAt, delivery.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt);
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", drone=" + drone +
                ", medications=" + medications +
                ", createdAt=" + createdAt +
                '}';
    }
}
