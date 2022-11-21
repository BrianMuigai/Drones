package com.brianmuigai.thedrone.tasks;

import com.brianmuigai.thedrone.dao.DroneRepository;
import com.brianmuigai.thedrone.entities.Drone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DroneTasks {
    private static final Logger log = LoggerFactory.getLogger("Drone Task");
    private final DroneRepository droneRepository;

    public DroneTasks(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    @Scheduled(fixedDelay = 60000, initialDelay = 3000)
    public void checkBatteries() {
        List<Drone> droneList = droneRepository.findAll();
        for (Drone drone : droneList) {
            log.info(drone.toString());
        }

    }
}
