package com.project_sem4.admin.controller;

import com.project_sem4.admin.entity.Account;
import com.project_sem4.admin.entity.Category;
import com.project_sem4.admin.entity.Story;
import com.project_sem4.admin.service.AccountService;
import com.project_sem4.admin.service.CategoryService;
import com.project_sem4.admin.service.ChapterService;
import com.project_sem4.admin.service.StoryService;
import com.project_sem4.admin.specification.SearchCriteria;
import com.project_sem4.admin.specification.StorySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.HashSet;
import java.util.Optional;

@Controller
public class StoryController {
    @Autowired
    private StoryService storyService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private AccountService accountService;

    //List Story
    @GetMapping(value = "/stories")
    public String list(
            @RequestParam(name = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "5") int limit,
            Model model) {
        Specification specification = Specification.where(null);
        if (categoryId != null && categoryId > 0) {
            specification = specification
                    .and(new StorySpecification(new SearchCriteria("categoryId", "joinCategory", categoryId)));
            model.addAttribute("categoryId", categoryId);
        }
        if (keyword != null && keyword.length() > 0) {
            specification = specification
                    .and(new StorySpecification(new SearchCriteria("keyword", "join", keyword)));
            model.addAttribute("keyword", keyword);
        }
        Page<Story> storyPage = storyService.findAllActive(specification, PageRequest.of(page - 1, limit));
        model.addAttribute("keyword", keyword);
        model.addAttribute("stories", storyPage.getContent());
        model.addAttribute("currentPage", storyPage.getPageable().getPageNumber() + 1);
        model.addAttribute("limit", storyPage.getPageable().getPageSize());
        model.addAttribute("totalPage", storyPage.getTotalPages());
        model.addAttribute("categories", categoryService.getAll());
        /* model.addAttribute("stories", storyService.findAllByOrderByCreatedAtDesc());*/
        return "stories/list";
    }

    //New Story
    @RequestMapping(value = "/story/create")
    public String newStoryForm(Model model) {
        model.addAttribute("story", new Story());
        Page<Category> categories = categoryService.getAll();
        model.addAttribute("categories", categories);
        return "stories/new";
    }

    @RequestMapping(value = "/story", method = RequestMethod.POST)
    public String saveNewStory(Principal principal, Story story) {
        Optional<Account> account = accountService.findByEmail(principal.getName());
        story.setAccount(account.get());
        storyService.create(story);
        return "redirect:/stories";
    }

    //Detail Story
    @RequestMapping(value = "/story/show/{id}")
    public String showSingleStory(@PathVariable long id, Model model) {
        Story story = storyService.findById(id);
        model.addAttribute("story", story);
        return "stories/showById";
    }

    //New ChapterByStory
    @GetMapping("/story/chapters/{id}")
    public String getChaptersByStory(@PathVariable("id") Long storyId, Model model) {
        Optional<Story> stories = storyService.findForId(storyId);
        model.addAttribute("story", stories.get());
        model.addAttribute("chapters", chapterService.getAllChapterByStory(storyId));
        return "stories/showByIdStories";
    }

}
