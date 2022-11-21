package com.brianmuigai.thedrone.entity_assemblers;

import com.brianmuigai.thedrone.controllers.FileController;
import com.brianmuigai.thedrone.entities.FileModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FileAssembler implements RepresentationModelAssembler<FileModel, EntityModel<FileModel>> {
    @Override
    public EntityModel<FileModel> toModel(FileModel entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(FileController.class).downloadFile(entity.getFileName())).withSelfRel());
    }
}
