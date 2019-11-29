package com.project_sem4.admin.controller;


import com.project_sem4.admin.entity.Chapter;
import com.project_sem4.admin.entity.Story;
import com.project_sem4.admin.entity.UploadFile;
import com.project_sem4.admin.service.ChapterService;
import com.project_sem4.admin.service.StoryService;
import com.project_sem4.admin.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class ChapterController {
    @Autowired
    private StoryService storyService;
    @Autowired
    ChapterService chapterService;
    @Autowired
    UploadFileService uploadFileService;

    @GetMapping("/chapters")
    public String getAllChapter(Model model) {
        model.addAttribute("chapters", chapterService.getAllChapter());
        return "chapters/list";
    }

    // truyen upload len
    @GetMapping("/chapters/create/{storyId}")
    public String createChapter(Model model, @PathVariable("storyId") Long storyId) {
        Chapter chapter = new Chapter();
        model.addAttribute("chapter", chapter);
        model.addAttribute("storyIds", storyId);
        return "chapters/new";
    }

    @PostMapping("/chapters/create/{storyId}")
    public String saveChapter(@PathVariable("storyId") Long storyId,
                              @RequestParam(name = "title", required = true) String title,
                              @RequestParam(name = "content", required = true) String content,
                              @RequestParam(name = "images", required = true) String[] images,
                              Model model
    ) {
        Story story = storyService.findById(storyId);
        if (story == null) {
            return "exception/error404";
        }
        Chapter chapter = new Chapter();
        chapter.setStory(story);
        chapter.setTitle(title);
        chapter.setContent(content);
        chapter.setCode(1);
        chapter.setEpisode(1);
        if (images != null && images.length > 0) {
            for (int i = 0; i < images.length; i++) {
                String link = images[i];
                chapter.addUploadFile(new UploadFile(link));
            }
        }
        chapterService.create(storyId, chapter);
        return "redirect:/story/chapters/{storyId}";
    }
    // end post

    @RequestMapping(value = "/chapter/show/{id}")
    public String showSingleStory(@PathVariable("id") long id, Model model) {
        Chapter chapter = chapterService.findById(id);
        model.addAttribute("chapter", chapter);
        model.addAttribute("allFile", uploadFileService.getAllFileByChapter(id));
        return "chapters/showById";
    }
}
