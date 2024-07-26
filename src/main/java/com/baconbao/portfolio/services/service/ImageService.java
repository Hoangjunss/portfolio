package com.baconbao.portfolio.services.service;

import com.baconbao.portfolio.dto.ImageDTO;
import org.springframework.stereotype.Service;

@Service
public interface ImageService {
    ImageDTO findById(Integer id);
    ImageDTO saveImage(ImageDTO imageDTO);
}
