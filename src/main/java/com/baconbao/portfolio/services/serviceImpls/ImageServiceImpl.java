package com.baconbao.portfolio.services.serviceImpls;

import com.baconbao.portfolio.exception.CustomException;
import com.baconbao.portfolio.exception.Error;
import com.baconbao.portfolio.model.Image;
import com.baconbao.portfolio.repository.ImageRepository;
import com.baconbao.portfolio.services.CloudinaryService;
import com.baconbao.portfolio.services.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
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
        try{
            log.info("Saving image");
            Map<String, Object> resultMap = cloudinaryService.upload(imageFile);
            String imageUrl = (String) resultMap.get("url");
            Image image= Image.builder()
                    .url(imageUrl)
                    .id(getGenerationId())
                    .build();
            return imageRepository.save(image);
        } catch (InvalidDataAccessResourceUsageException e){
            throw new CustomException(Error.IMAGE_UNABLE_TO_SAVE);
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }

    }
    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }

}
