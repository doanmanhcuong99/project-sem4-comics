package com.project_sem4.admin.service.Impl;

import com.project_sem4.admin.entity.UploadFile;
import com.project_sem4.admin.repository.UploadFileRepository;
import com.project_sem4.admin.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class UploadFileServiceImpl implements UploadFileService {
    @Autowired
    UploadFileRepository uploadFileRepository;

    @Override
    public List<UploadFile> GetAll() {
        return uploadFileRepository.findAll();
    }

    @Override
    public Page<UploadFile> GetPage(int page, int limit) {
        return uploadFileRepository.findAll(PageRequest.of(page - 1, limit));
    }

    @Override
    public UploadFile GetDetail(long id) {
        return uploadFileRepository.findById(id).orElse(null);
    }

    @Override
    public UploadFile Create(UploadFile uploadFile) {
        uploadFile.setCreatedAt(Calendar.getInstance().getTimeInMillis());
        uploadFile.setUpdatedAt(Calendar.getInstance().getTimeInMillis());
        uploadFile.setStatus(1);
        return uploadFileRepository.save(uploadFile);
    }

    @Override
    public UploadFile Update(long id, UploadFile updateUploadFile) {
        Optional<UploadFile> optionalUploadFile = uploadFileRepository.findById(id);
        if (optionalUploadFile.isPresent()) {
            UploadFile existUploadFile = optionalUploadFile.get();
            existUploadFile.setLink(updateUploadFile.getLink());
            existUploadFile.setUpdatedAt(Calendar.getInstance().getTimeInMillis());
            return uploadFileRepository.save(updateUploadFile);
        }
        return null;
    }

    @Override
    public boolean Delete(long id) {
        UploadFile existUploadFile = uploadFileRepository.findById(id).orElse(null);
        if (existUploadFile == null) {
            return false;
        }
        uploadFileRepository.delete(existUploadFile);
        return true;
    }

    @Override
    public List<UploadFile> getAllFileByChapter(Long chapterId) {
        return uploadFileRepository.findAllByChapterId(chapterId);
    }


}
