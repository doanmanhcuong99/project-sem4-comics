package com.project_sem4.admin.service.Impl;


import com.project_sem4.admin.entity.Category;
import com.project_sem4.admin.entity.Story;
import com.project_sem4.admin.pagination.PageModel;
import com.project_sem4.admin.repository.CategoryRepository;
import com.project_sem4.admin.service.CategoryService;
import com.project_sem4.admin.specification.CategorySpecification;
import com.project_sem4.admin.specification.SearchCriteria;
import com.project_sem4.admin.specification.StorySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    PageModel pageModel;

    @Override
    public Page<Category> getAll() {
        pageModel.setSIZE(8);
        pageModel.initPageAndSize();
        return categoryRepository.findAll(PageRequest.of(pageModel.getPAGE(), pageModel.getSIZE()));
    }

    @Override
    public Category findById(Long catId) {
        Optional<Category> categoryOptional = categoryRepository.findById(catId);
        if (!categoryOptional.isPresent()) {
            throw new RuntimeException("Category Not Found!");
        }
        return categoryOptional.get();
    }


    @Override
    public Long create(Category categoryDetails) {
        /* categoryDetails.setDatecreated(Calendar.getInstance().getTimeInMillis());*/
        categoryRepository.save(categoryDetails);
        return categoryDetails.getId();
    }

    @Override
    public void update(Long catId, Category categoryDetails) {
        Category currentCat = findById(catId);
        currentCat.setName(categoryDetails.getName());
        currentCat.setDescription(categoryDetails.getDescription());
        categoryRepository.save(currentCat);
    }

    @Override
    public void delete(Long catId) {
        categoryRepository.deleteById(catId);
    }

    @Override
    public Page<Category> findAllCategory(Specification specification, Pageable pageable) {
        return categoryRepository.findAll(specification, pageable);
    }

}
