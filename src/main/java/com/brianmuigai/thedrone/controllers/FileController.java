package com.brianmuigai.thedrone.controllers;

import com.brianmuigai.thedrone.CustomExceptions.FileStorageException;
import com.brianmuigai.thedrone.dao.FileRepository;
import com.brianmuigai.thedrone.entity_assemblers.FileAssembler;
import com.brianmuigai.thedrone.entities.FileModel;
import config.FileStorageProperties;
import net.bytebuddy.utility.RandomString;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class FileController {
    private final FileAssembler fileAssembler;
    private final FileRepository fileRepository;
    private final Path fileStorageLocation;

    public FileController(FileAssembler fileAssembler, FileRepository fileRepository, FileStorageProperties fileStorageProperties) {
        this.fileAssembler = fileAssembler;
        this.fileRepository = fileRepository;

        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @PostMapping("/upload-file")
    public EntityModel<FileModel> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            //generate rand unique file name
            fileName = RandomString.make(10)+fileName;
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
        FileModel fileModel = new FileModel(fileName, file.getContentType(), file.getSize());
        fileRepository.save(fileModel);
        return fileAssembler.toModel(fileModel);
    }

    @PostMapping("/upload-multiple-files")
    public CollectionModel<EntityModel<FileModel>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        List<EntityModel<FileModel>> uploadedFiles = Arrays.asList(files)
                .stream()
                .map(this::uploadFile)
                .collect(Collectors.toList());

        return CollectionModel.of(uploadedFiles);
    }

    @GetMapping("/download-file/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        // Load file as Resource
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                FileModel fileModel = fileRepository.findByFileName(fileName).get(0);
                String contentType = fileModel.getFileType();
                if (contentType == null) contentType = "application/octet-stream";

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new FileStorageException("File not found " + fileName, new FileNotFoundException());
            }
        } catch (MalformedURLException ex) {
            throw new FileStorageException("File not found " + fileName, ex);
        }
    }

    @GetMapping("/list-files")
    public CollectionModel<EntityModel<FileModel>> listFiles() {
        List<EntityModel<FileModel>> uploadedFiles = fileRepository.findAll()
                .stream()
                .map(fileAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(uploadedFiles, linkTo(methodOn(FileController.class).listFiles()).withSelfRel());
    }
}
