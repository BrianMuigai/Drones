package com.brianmuigai.thedrone.controllers;

import com.brianmuigai.thedrone.CustomExceptions.*;
import com.brianmuigai.thedrone.dao.DeliveryRepository;
import com.brianmuigai.thedrone.dao.DroneRepository;
import com.brianmuigai.thedrone.dao.MedicationRepository;
import com.brianmuigai.thedrone.entities.Delivery;
import com.brianmuigai.thedrone.entities.Drone;
import com.brianmuigai.thedrone.entities.Medication;
import com.brianmuigai.thedrone.entity_assemblers.DeliveryAssembler;
import com.brianmuigai.thedrone.entity_assemblers.DroneAssembler;
import com.brianmuigai.thedrone.entity_assemblers.MedicationAssembler;
import com.brianmuigai.thedrone.enums.DroneState;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class DispatchController {
    private final DeliveryRepository deliveryRepository;
    private final DeliveryAssembler deliveryAssembler;
    private final MedicationAssembler medicationAssembler;
    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;
    private final DroneAssembler droneAssembler;

    public DispatchController(DeliveryRepository deliveryRepository,
                              DeliveryAssembler deliveryAssembler,
                              MedicationAssembler medicationAssembler,
                              DroneRepository droneRepository,
                              MedicationRepository medicationRepository,
                              DroneAssembler droneAssembler) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryAssembler = deliveryAssembler;
        this.medicationAssembler = medicationAssembler;
        this.droneRepository = droneRepository;
        this.medicationRepository = medicationRepository;
        this.droneAssembler = droneAssembler;
    }

    @GetMapping("/get-delivery/{id}")
    public EntityModel<Delivery> getDelivery(@PathVariable Long id) {
        Delivery delivery = deliveryRepository.findById(id).orElseThrow(() -> new DeliveryNotFoundException(id));
        return deliveryAssembler.toModel(delivery);
    }

    @GetMapping("/get-deliveries")
    public CollectionModel<EntityModel<Delivery>> getDeliveries() {
        List<EntityModel<Delivery>> deliveries = deliveryRepository.findAll().stream() //
                .map(deliveryAssembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(deliveries, linkTo(methodOn(DispatchController.class).getDeliveries()).withSelfRel());
    }

    @PostMapping("/load-medication/{medicationCode}/{droneSerialNumber}")
    public EntityModel<Delivery> loadMedication(@PathVariable String medicationCode,
                                                @PathVariable String droneSerialNumber) {
        Drone drone = droneRepository.findById(droneSerialNumber).orElseThrow(() ->
                new DroneNotFoundException(droneSerialNumber)
        );
        if (drone.getBatteryCapacity() < 25)
            throw new BatteryLowException(drone);
        //can only load a loaded drone if the weight is below limit, or an idle drone
        if (drone.getState() == DroneState.IDLE || drone.getState() == DroneState.LOADED) {
            drone.setState(DroneState.LOADING);
            methodOn(DroneController.class).updateDrone(drone);
            Medication medication = medicationRepository.findById(medicationCode)
                    .orElseThrow(() -> new MedicationNotFoundException(medicationCode));
            if (drone.getWeightLimit() < medication.getWeight()) throw new AboveDroneWeightLimitException(drone);
            //set drone status to LOADED
            drone.setState(DroneState.LOADED);
            methodOn(DroneController.class).updateDrone(drone);
            Delivery delivery = new Delivery();
            delivery.setMedication(medication);
            methodOn(DroneController.class).updateDrone(drone);
            delivery.setDrone(drone);
            return deliveryAssembler.toModel(deliveryRepository.save(delivery));
        } else {
            throw new DroneNotAvailableException(drone);
        }
    }

    @GetMapping("/check-loaded-medications/{serialNumber}")
    CollectionModel<EntityModel<Medication>> checkLoadedMedications(@PathVariable String serialNumber) {
        Drone drone = droneRepository.findById(serialNumber).orElseThrow(() ->
                new DroneNotFoundException(serialNumber)
        );
        if (drone.getState() == DroneState.IDLE || drone.getState() == DroneState.DELIVERED ||
                drone.getState() == DroneState.RETURNING) {
            throw new DroneNotAvailableException(drone);
        }
        Delivery delivery = deliveryRepository.findByDroneOrderByCreatedAtAsc(drone).get(0);
        List<EntityModel<Medication>> medications = delivery.getMedications().stream()
                .map(medicationAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(medications,
                linkTo(methodOn(DispatchController.class).getDelivery(delivery.getId())).withSelfRel());
    }

    @GetMapping("/get-available-drones/{weight}")
    CollectionModel<EntityModel<Drone>> getAvailableDrones(@PathVariable double weight) {
        List<Drone> drones = droneRepository.findAll();
        List<EntityModel<Drone>> available = new ArrayList<>();
        for (Drone drone : drones) {
            if (drone.getState() == DroneState.IDLE && drone.getWeightLimit() >= weight) {
                available.add(droneAssembler.toModel(drone));
            } else if (drone.getState() == DroneState.LOADED) {
                Delivery delivery = deliveryRepository.findByDroneOrderByCreatedAtAsc(drone).get(0);
                double loadedWeight = 0;
                for (Medication medication : delivery.getMedications()) {
                    loadedWeight += medication.getWeight();
                }
                if (drone.getWeightLimit() - weight >= loadedWeight) {
                    available.add(droneAssembler.toModel(drone));
                }
            }
        }

        return CollectionModel.of(available, linkTo(methodOn(DroneController.class).getDrones()).withSelfRel());
    }

    @GetMapping("/check-battery-level/{serialNumber}")
    public EntityModel<Map<String, Double>> getBatteryLevel(@PathVariable String serialNumber) {
        Drone drone = droneRepository.findById(serialNumber).orElseThrow(() ->
                new DroneNotFoundException(serialNumber)
        );
        Map<String, Double> battery = new HashMap<>();
        battery.put("battery_capacity", drone.getBatteryCapacity());
        return EntityModel.of(battery,
                linkTo(methodOn(DispatchController.class).getBatteryLevel(serialNumber)).withSelfRel());
    }

}
