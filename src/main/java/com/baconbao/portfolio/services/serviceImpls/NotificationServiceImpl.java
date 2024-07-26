package com.baconbao.portfolio.services.serviceImpls;

import com.baconbao.portfolio.dto.NotificationDTO;
import com.baconbao.portfolio.model.Notification;
import com.baconbao.portfolio.repository.NotificationRepository;
import com.baconbao.portfolio.services.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private ModelMapper modelMapper;

    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        // Use most significant bits and ensure it's within the integer range
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }

    private Notification convertToModel(NotificationDTO notificationDTO){
        return modelMapper.map(notificationDTO, Notification.class);
    }

    private NotificationDTO convertToDTO(Notification notification){
        return modelMapper.map(notification, NotificationDTO.class);
    }

    private Notification save(NotificationDTO notificationDTO){
        Notification notification = Notification.builder()
                .id(getGenerationId())
                .message(notificationDTO.getMessage())
                .createAt(notificationDTO.getCreateAt())
                .isRead(notificationDTO.isRead())
                .build();
        return notificationRepository.save(notification);
    }

    @Override
    public NotificationDTO saveNotification(NotificationDTO notificationDTO) {
        log.info("Save notification");
        return convertToDTO(save(notificationDTO));
    }

    @Override
    public NotificationDTO update(NotificationDTO notificationDTO) {
        log.info("Update notification");
        return convertToDTO(notificationRepository.save(convertToModel(notificationDTO)));
    }

    @Override
    public NotificationDTO findById(Integer id) {
        log.info("Find notification by id: {}", id);
        return convertToDTO(notificationRepository.findById(id)
                .orElseThrow());
    }


}
