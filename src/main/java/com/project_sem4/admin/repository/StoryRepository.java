package com.project_sem4.admin.repository;


import com.project_sem4.admin.entity.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long>, JpaSpecificationExecutor<Story> {
    Optional<Story> findById(long storyId);

    Page<Story> findAll(Specification specification, Pageable pageable);
    @Transactional
    @Modifying
    @Query("update Story b set b.view = b.view+1 where b.id = ?1")
    int updateViews(Long storyId);
}
