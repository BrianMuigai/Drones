package com.brianmuigai.thedrone.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FileModel {
    @GeneratedValue
    private @Id Long id;
    private String fileName;
    private String fileType;
    private long size;

    public FileModel() {
    }

    public FileModel(String fileName, String fileType, long size) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
