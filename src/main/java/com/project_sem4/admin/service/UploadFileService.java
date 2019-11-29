package com.project_sem4.admin.service;

import com.project_sem4.admin.entity.UploadFile;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UploadFileService {
    List<UploadFile> GetAll();

    Page<UploadFile> GetPage(int page, int limit);

    UploadFile GetDetail(long id);

    UploadFile Create(UploadFile uploadFile);

    UploadFile Update(long id, UploadFile updateUploadFile);

    boolean Delete(long id);

}
