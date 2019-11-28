package com.project_sem4.admin.repository;

import com.project_sem4.admin.entity.Chapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    Optional<Chapter> findById(Long chapId);

    Page<Chapter> findAllByStoryId(Long storyId, Pageable pageable);


}
