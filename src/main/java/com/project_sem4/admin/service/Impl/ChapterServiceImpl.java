package com.project_sem4.admin.service.Impl;


import com.project_sem4.admin.entity.Chapter;
import com.project_sem4.admin.entity.Story;
import com.project_sem4.admin.pagination.PageModel;
import com.project_sem4.admin.repository.ChapterRepository;
import com.project_sem4.admin.service.ChapterService;
import com.project_sem4.admin.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterRepository chapterRepository;
    @Autowired
    StoryService storyService;
    @Autowired
    PageModel pageModel;

    @Override
    public Page<Chapter> getAllChapter() {
        pageModel.setSIZE(8);
        pageModel.initPageAndSize();
        return chapterRepository.findAll(PageRequest.of(pageModel.getPAGE(), pageModel.getSIZE()));
    }


    @Override
    public Page<Chapter> getAllChapterByStory(Long storyId) {
        pageModel.setSIZE(8);
        pageModel.initPageAndSize();
        return chapterRepository.findAllByStoryId(storyId, PageRequest.of(pageModel.getPAGE(), pageModel.getSIZE()));
    }


    @Override
    public Chapter findById(Long chapId) {
        Optional<Chapter> chapterOptional = chapterRepository.findById(chapId);
        if (!chapterOptional.isPresent()) {
            throw new RuntimeException("Chapter Not Found!");
        }
        return chapterOptional.get();
    }

    @Override
    public Long create(Long storyId, Chapter chapterDetails) {
        Story story = storyService.findById(storyId);
        chapterDetails.setStatus(1);
        chapterDetails.setStory(story);
        chapterRepository.save(chapterDetails);
        return chapterDetails.getId();
    }

    @Override
    public void update(Long chapId, Chapter chapterDetails) {
        Chapter currentChapter = findById(chapId);
        currentChapter.setTitle(chapterDetails.getTitle());
        currentChapter.setContent(chapterDetails.getContent());
        chapterRepository.save(currentChapter);
    }

    @Override
    public void delete(Long chapId) {
        chapterRepository.deleteById(chapId);
    }


}
