package com.project_sem4.admin.service;

import com.project_sem4.admin.entity.Account;
import com.project_sem4.admin.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface CategoryService {
    Page<Category> getAll();

    Category findById(Long catId);

    Long create(Category categoryDetails);

    void update(Long catId, Category categoryDetails);

    void delete(Long catId);

    Page<Category> findAllCategory(Specification specification, Pageable pageable);


}
