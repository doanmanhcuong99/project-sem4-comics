package com.project_sem4.admin.controller;


import com.project_sem4.admin.entity.Category;
import com.project_sem4.admin.entity.Story;
import com.project_sem4.admin.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Set;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/category/create")
    public String newCatForm(Model model) {
        model.addAttribute("category", new Category());
        return "categories/new";
    }

    @RequestMapping(value = "/category", method = RequestMethod.POST)
    public String saveNewCategory(Category category) {
        long id = categoryService.create(category);
        return "redirect:/categories";
    }


    @GetMapping(value = "/category/stories/{id}")
    public String showStoryByCategory(@PathVariable("id") long category_id, Model model) {
        Category category = categoryService.findById(category_id);
        Set<Story> stories = category.getStories();
        model.addAttribute("category", category);
        model.addAttribute("stories", stories);
        return "categories/showById";
    }

    @GetMapping(value = "/categories")
    public String showAllCategories(Model model) {

        model.addAttribute("categories", categoryService.getAll());
        return "categories/list";
    }

    @GetMapping(value = "/category/{id}")
    public String editCategoryForm(@PathVariable("id") long id, Model model) {
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        return "categories/edit";
    }

    @RequestMapping(value = "/category/{id}", method = RequestMethod.POST)
    public String updateCategory(@PathVariable("id") long id, Category category) {
        categoryService.update(id, category);
        return "redirect:/categories";
    }

    @RequestMapping(value = "/category/delete/{id}", method = RequestMethod.GET)
    public String deleteCategory(@PathVariable("id") long id) {
        categoryService.delete(id);
        return "redirect:/categories";
    }
}
