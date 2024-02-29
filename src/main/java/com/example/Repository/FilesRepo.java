package com.example.Repository;

import com.example.Entity.FilesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilesRepo extends JpaRepository<FilesEntity, Long> {
}
