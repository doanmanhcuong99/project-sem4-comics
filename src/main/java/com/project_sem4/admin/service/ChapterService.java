package com.project_sem4.admin.service;

;
import com.project_sem4.admin.entity.Chapter;
import com.project_sem4.admin.entity.Story;
import org.springframework.data.domain.Page;

public interface ChapterService {
    Page<Chapter> getAllChapter();

    Chapter findById(Long chapId);

    Long create(Long storyId, Chapter chapterDetails);

    void update(Long chapId, Chapter chapterDetails);

    void delete(Long chapId);


    Page<Chapter> getAllChapterByStory(Long storyId);
}
