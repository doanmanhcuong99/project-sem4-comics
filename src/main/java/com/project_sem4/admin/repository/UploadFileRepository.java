package com.project_sem4.admin.repository;

import com.project_sem4.admin.entity.UploadFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {
    List<UploadFile> findAllByChapterId(Long chapterId);
}
