package com.project_sem4.admin.controller;


import com.project_sem4.admin.entity.Chapter;
import com.project_sem4.admin.service.ChapterService;
import com.project_sem4.admin.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class ChapterController {
    @Autowired
    private StoryService storyService;
    @Autowired
    ChapterService chapterService;

    @GetMapping("/chapters")
    public String getAllChapter(Model model) {
        model.addAttribute("chapters", chapterService.getAllChapter());
        return "chapters/list";
    }


    @GetMapping("/chapters/create/{storyId}")
    public String createChapter(Model model, @PathVariable("storyId") Long storyId) {
        Chapter chapter = new Chapter();
        model.addAttribute("chapter", chapter);
        model.addAttribute("storyIds", storyId);
        return "chapters/new";
    }

    @PostMapping("/chapters/create/{storyId}")
    public String saveChapter(@PathVariable("storyId") Long storyId, Chapter chapter) {
        List<Chapter> chapters = new ArrayList<>();
        chapters.add(chapter);
        chapterService.create(storyId, chapter);
        return "redirect:/story/chapters/{storyId}";
    }

    @RequestMapping(value = "/chapter/show/{id}")
    public String showSingleStory(@PathVariable("id") long id, Model model) {
        Chapter chapter = chapterService.findById(id);
        model.addAttribute("chapter", chapter);
        return "chapters/showById";
    }

}
