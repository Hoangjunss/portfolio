package com.baconbao.portfolio.services.serviceImpls;

import com.baconbao.portfolio.dto.NotificationDTO;
import com.baconbao.portfolio.dto.ProjectDTO;
import com.baconbao.portfolio.model.Notification;
import com.baconbao.portfolio.model.Project;
import com.baconbao.portfolio.model.User;
import com.baconbao.portfolio.repository.NotificationRepository;
import com.baconbao.portfolio.services.service.NotificationService;
import com.baconbao.portfolio.services.service.ProfileService;
import com.baconbao.portfolio.services.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;

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
        User user=userService.convertToModel(userService.findById(notificationDTO.getIdUser()));
        Notification notification = Notification.builder()
                .id(getGenerationId())
                .message(notificationDTO.getMessage())
                .createAt(notificationDTO.getCreateAt())
                .isRead(notificationDTO.isRead())
                .userSend(user)
                .build();
        return notificationRepository.save(notification);
    }

    @Override
    public NotificationDTO saveNotification(NotificationDTO notificationDTO) {
        Notification notification = save(notificationDTO);
        return convertToDTO(notification);
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

    @Override
    public NotificationDTO seenNotification(Integer id) {
        Notification notification =notificationRepository.findById(id).orElseThrow();
        notification.setRead(true);
        return convertToDTO(notificationRepository.save(notification));
    }
    public List<NotificationDTO> convertToDTOList(List<Notification> notifications) {
        return notifications.stream()
                .map(notification -> modelMapper.map(notification, NotificationDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationDTO> getNotificationByUser(Integer idUser) {
        return convertToDTOList(notificationRepository.findByUserId(idUser));
    }


}
