package com.baconbao.portfolio.services.serviceImpls;

import com.baconbao.portfolio.model.Image;
import com.baconbao.portfolio.repository.ImageRepository;
import com.baconbao.portfolio.services.CloudinaryService;
import com.baconbao.portfolio.services.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    public Image saveImage(MultipartFile imageFile)  {

        Map<String, Object> resultMap = cloudinaryService.upload(imageFile);
        String imageUrl = (String) resultMap.get("url");
        Image image= Image.builder()
                .url(imageUrl)
                .id(getGenerationId())
                .build();
        return imageRepository.save(image);

    }
    public Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        // Use most significant bits and ensure it's within the integer range
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }

}
