package com.brianmuigai.thedrone.entity_assemblers;

import com.brianmuigai.thedrone.controllers.MedicationController;
import com.brianmuigai.thedrone.entities.Medication;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MedicationAssembler implements RepresentationModelAssembler<Medication, EntityModel<Medication>> {

    @Override
    public EntityModel<Medication> toModel(Medication entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(MedicationController.class).getMedication(entity.getCode())).withSelfRel());
    }
}
