package com.brianmuigai.thedrone.dao;

import com.brianmuigai.thedrone.entities.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneRepository extends JpaRepository<Drone, String> {

}
