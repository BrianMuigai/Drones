package com.brianmuigai.thedrone.entity_assemblers;

import com.brianmuigai.thedrone.controllers.DroneController;
import com.brianmuigai.thedrone.entities.Drone;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DroneAssembler implements RepresentationModelAssembler<Drone, EntityModel<Drone>> {
    @Override
    public EntityModel<Drone> toModel(Drone entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(DroneController.class).getDrone(entity.getSerialNumber())).withSelfRel());
    }

}
