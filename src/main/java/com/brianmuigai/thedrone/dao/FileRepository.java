package com.brianmuigai.thedrone.dao;

import com.brianmuigai.thedrone.entities.FileModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<FileModel, Long> {
    public List<FileModel> findByFileName(String name);
}
