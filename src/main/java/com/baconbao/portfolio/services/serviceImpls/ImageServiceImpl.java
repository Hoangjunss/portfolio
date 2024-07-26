package com.baconbao.portfolio.services.serviceImpls;

import com.baconbao.portfolio.dto.ImageDTO;
import com.baconbao.portfolio.model.Image;
import com.baconbao.portfolio.repository.ImageRepository;
import com.baconbao.portfolio.services.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ModelMapper modelMapper;

    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }

    private Image convertToImage(ImageDTO imageDTO){
        return modelMapper.map(imageDTO, Image.class);
    }

    private ImageDTO convertToDTO(Image image){
        return modelMapper.map(image, ImageDTO.class);
    }

    private Image save(ImageDTO imageDTO){
        Image image = Image.builder()
                .id(getGenerationId())
                .url(imageDTO.getUrl())
                .build();
        return imageRepository.save(image);
    }

    @Override
    public ImageDTO findById(Integer id) {
        return convertToDTO(imageRepository.findById(id).orElseThrow());
    }

    @Override
    public ImageDTO saveImage(ImageDTO imageDTO) {
        return convertToDTO(save(imageDTO));
    }
}
