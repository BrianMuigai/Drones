package com.brianmuigai.thedrone.controllers;

import com.brianmuigai.thedrone.CustomExceptions.MedicationExistsException;
import com.brianmuigai.thedrone.CustomExceptions.MedicationNotFoundException;
import com.brianmuigai.thedrone.dao.MedicationRepository;
import com.brianmuigai.thedrone.entities.Medication;
import com.brianmuigai.thedrone.entity_assemblers.MedicationAssembler;
import net.bytebuddy.utility.RandomString;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class MedicationController {

    private final MedicationRepository medicationRepository;
    private final MedicationAssembler medicationAssembler;

    public MedicationController(MedicationRepository medicationRepository, MedicationAssembler medicationAssembler) {
        this.medicationRepository = medicationRepository;
        this.medicationAssembler = medicationAssembler;
    }

    @GetMapping("/get-medication/{medicationCode}")
    public EntityModel<Medication> getMedication(@PathVariable String medicationCode) {
        Medication medication = medicationRepository.findById(medicationCode)
                .orElseThrow(() -> new MedicationNotFoundException(medicationCode));
        return medicationAssembler.toModel(medication);
    }

    @GetMapping("/get-medications")
    public CollectionModel<EntityModel<Medication>> getMedications() {
        List<EntityModel<Medication>> medications = medicationRepository.findAll()
                .stream().map(medicationAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(medications, linkTo(methodOn(MedicationController.class).getMedications()).withSelfRel());
    }

    @PostMapping("/register-medication")
    public EntityModel<Medication> registerMedication(@RequestBody Medication medication) {
        if (medication.getCode() == null) medication.setCode(RandomString.make().toUpperCase());
        if (medicationRepository.findById(medication.getCode()).isPresent())
            throw new MedicationExistsException(medication.getCode());
        medication = medicationRepository.save(medication);
        return medicationAssembler.toModel(medication);
    }

    @PostMapping("/update-medication")
    public EntityModel<Medication> updateMedication(@RequestBody Medication newMedication) {
        Medication m = medicationRepository.findById(newMedication.getCode())
                .map(medication -> {
                    medication.setImage(newMedication.getImage());
                    medication.setName(newMedication.getName());
                    medication.setWeight(newMedication.getWeight());
                    return medicationRepository.save(medication);
                })
                .orElseGet(() -> medicationRepository.save(newMedication));
        return medicationAssembler.toModel(m);
    }
}
