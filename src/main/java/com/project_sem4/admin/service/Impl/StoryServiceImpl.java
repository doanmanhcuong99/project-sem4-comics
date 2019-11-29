package com.project_sem4.admin.service.Impl;

import com.project_sem4.admin.entity.Account;
import com.project_sem4.admin.entity.Story;
import com.project_sem4.admin.pagination.PageModel;
import com.project_sem4.admin.repository.StoryRepository;
import com.project_sem4.admin.service.StoryService;
import com.project_sem4.admin.specification.SearchCriteria;
import com.project_sem4.admin.specification.StorySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
public class StoryServiceImpl implements StoryService {
    @Autowired
    StoryRepository storyRepository;
    @Autowired
    PageModel pageModel;

    @Override
    public Page<Story> getAll() {
        pageModel.setSIZE(5);
        pageModel.initPageAndSize();
        return storyRepository.findAll(PageRequest.of(pageModel.getPAGE(), pageModel.getSIZE()));
    }

    @Override
    public Story findById(Long storyId) {
        Optional<Story> storyOptional = storyRepository.findById(storyId);
        storyRepository.updateViews(storyId);
        if (!storyOptional.isPresent()) {
            throw new RuntimeException("Story Not Found!");
        }

        return storyOptional.get();
    }


    @Override
    public Long create(Story storyDetails) {
        storyDetails.setCreatedAt(Calendar.getInstance().getTimeInMillis());
        storyDetails.setStatus(Story.Status.DELETED.getValue());
        storyDetails.setView(0);
        storyRepository.save(storyDetails);
        return storyDetails.getId();
    }


    @Override
    public Page<Story> findAllActive(Specification specification, Pageable pageable) {
        specification = specification
                .and(new StorySpecification(new SearchCriteria("status", "!=", Story.Status.DELETED.getValue())));
        return storyRepository.findAll(specification, pageable);
    }

    @Override
    public Page<Story> findByAccountOrderByCreatedAtDesc(Account account) {
        pageModel.setSIZE(5);
        pageModel.initPageAndSize();
        return storyRepository.findByAccountOrderByCreatedAtDesc(account, PageRequest.of(pageModel.getPAGE(), pageModel.getSIZE()));
    }

    @Override
    public void update(Long storyId, Story storyDetails) {
        Story currentStory = findById(storyId);
        currentStory.setTitle(storyDetails.getTitle());
        currentStory.setDirector(storyDetails.getDirector());
        currentStory.setCategories(storyDetails.getCategories());
        currentStory.setDescription(storyDetails.getDescription());
        currentStory.setUpdatedAt(Calendar.getInstance().getTimeInMillis());
        storyRepository.save(currentStory);
    }

    @Override
    public Optional<Story> findForId(Long storyId) {
        return storyRepository.findById(storyId);
    }


}
