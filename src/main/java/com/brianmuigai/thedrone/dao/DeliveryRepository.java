package com.brianmuigai.thedrone.dao;

import com.brianmuigai.thedrone.entities.Delivery;
import com.brianmuigai.thedrone.entities.Drone;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    List<Delivery> findByDrone(Drone drone);

    List<Delivery> findByDrone(Drone drone, Sort sort);

    List<Delivery> findByDroneOrderByCreatedAtAsc(Drone drone);
}
