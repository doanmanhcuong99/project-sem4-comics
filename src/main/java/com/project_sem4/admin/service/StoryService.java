package com.project_sem4.admin.service;

import com.project_sem4.admin.entity.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface StoryService {
    Page<Story> getAll();

    Story findById(Long storyId);

    Long create(Story storyDetails);

    void update(Long storyId, Story storyDetails);

    Page<Story> findAllActive(Specification specification, Pageable pageable);

    Optional<Story> findForId(Long storyId);
    /*    Set<Story> getTasksByStatus(Status status);*/
}
