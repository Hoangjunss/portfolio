package com.baconbao.portfolio.services.service;

import com.baconbao.portfolio.dto.ImageDTO;
import com.baconbao.portfolio.model.Image;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ImageService {

    Image saveImage(MultipartFile imageFile);
}
