package com.brianmuigai.thedrone.dao;

import com.brianmuigai.thedrone.entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<Medication, String> {
}
