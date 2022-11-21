package com.brianmuigai.thedrone.utils;

import com.brianmuigai.thedrone.entities.Drone;
import com.brianmuigai.thedrone.enums.DroneModel;
import com.brianmuigai.thedrone.enums.DroneState;
import com.brianmuigai.thedrone.entities.Medication;
import com.brianmuigai.thedrone.dao.DroneRepository;
import com.brianmuigai.thedrone.dao.MedicationRepository;
import net.bytebuddy.utility.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PreloadDatabase {

    private static final Logger log = LoggerFactory.getLogger(PreloadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(DroneRepository droneRepository, MedicationRepository medicationRepository) {
        return args -> {
            log.info("Preloading "+ droneRepository.save(
                    new Drone(
                            RandomString.make(100),
                            DroneModel.Lightweight,
                            50,
                            65,
                            DroneState.IDLE)));
            log.info("Preloading "+ droneRepository.save(
                    new Drone(
                            RandomString.make(100),
                            DroneModel.Lightweight,
                            50,
                            65,
                            DroneState.IDLE)));
            log.info("Preloading "+ droneRepository.save(
                    new Drone(
                            RandomString.make(100),
                            DroneModel.Lightweight,
                            50,
                            65,
                            DroneState.IDLE)));
            log.info("Preloading medication:-> "+medicationRepository.save(
                    new Medication(
                            "CP200",
                            "TestMedication1",
                            20
                            )));
        };
    }
}
