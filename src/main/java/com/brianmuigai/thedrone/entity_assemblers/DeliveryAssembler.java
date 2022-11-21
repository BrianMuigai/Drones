package com.brianmuigai.thedrone.entity_assemblers;

import com.brianmuigai.thedrone.controllers.DispatchController;
import com.brianmuigai.thedrone.entities.Delivery;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DeliveryAssembler implements RepresentationModelAssembler<Delivery, EntityModel<Delivery>> {
    @Override
    public EntityModel<Delivery> toModel(Delivery entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(DispatchController.class).getDelivery(entity.getId())).withSelfRel());
    }
}
