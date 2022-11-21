package com.brianmuigai.thedrone.controllers;

import com.brianmuigai.thedrone.CustomExceptions.DroneExistsException;
import com.brianmuigai.thedrone.CustomExceptions.DroneNotFoundException;
import com.brianmuigai.thedrone.dao.DroneRepository;
import com.brianmuigai.thedrone.entities.Drone;
import com.brianmuigai.thedrone.entity_assemblers.DroneAssembler;
import net.bytebuddy.utility.RandomString;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class DroneController {
    private final DroneAssembler droneAssembler;
    private final DroneRepository droneRepository;

    public DroneController(DroneRepository droneRepository, DroneAssembler droneAssembler) {
        this.droneAssembler = droneAssembler;
        this.droneRepository = droneRepository;
    }


    @GetMapping("/get-drones")
    public CollectionModel<EntityModel<Drone>> getDrones() {
        List<EntityModel<Drone>> drones = droneRepository.findAll().stream() //
                .map(droneAssembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(drones, linkTo(methodOn(DroneController.class).getDrones()).withSelfRel());
    }

    @GetMapping("/get-drone/{serialNumber}")
    public EntityModel<Drone> getDrone(@PathVariable String serialNumber) {
        Drone drone = droneRepository.findById(serialNumber).orElseThrow(() ->
                new DroneNotFoundException(serialNumber)
        );
        return droneAssembler.toModel(drone);
    }

    @PostMapping("/register-drone")
    public EntityModel<Drone> registerDrone(@RequestBody Drone drone) {
        if (drone.getSerialNumber() == null) drone.setSerialNumber(RandomString.make(100));
        if (droneRepository.findById(drone.getSerialNumber()).isPresent())
            throw new DroneExistsException(drone.getSerialNumber());
        drone = droneRepository.save(drone);
        return droneAssembler.toModel(drone);
    }

    @PostMapping("/update-drone")
    public EntityModel<Drone> updateDrone(@RequestBody Drone newDrone) {
        Drone d = droneRepository.findById(newDrone.getSerialNumber())
                .map(drone -> {
                    drone.setModel(newDrone.getModel());
                    drone.setBatteryCapacity(newDrone.getBatteryCapacity());
                    drone.setState(newDrone.getState());
                    drone.setSerialNumber(newDrone.getSerialNumber());
                    drone.setWeightLimit(newDrone.getWeightLimit());
                    return droneRepository.save(drone);
                })
                .orElseGet(() -> droneRepository.save(newDrone));
        return droneAssembler.toModel(d);
    }

}
