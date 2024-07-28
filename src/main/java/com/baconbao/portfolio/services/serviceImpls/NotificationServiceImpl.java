package com.baconbao.portfolio.services.serviceImpls;

import com.baconbao.portfolio.dto.NotificationDTO;
import com.baconbao.portfolio.dto.ProjectDTO;
import com.baconbao.portfolio.exception.CustomException;
import com.baconbao.portfolio.exception.Error;
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
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
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
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }

    private Notification convertToModel(NotificationDTO notificationDTO){
        return modelMapper.map(notificationDTO, Notification.class);
    }

    private NotificationDTO convertToDTO(Notification notification){
        return modelMapper.map(notification, NotificationDTO.class);
    }

    private Notification save(NotificationDTO notificationDTO){
        try{
            log.info("Saving notification");
            User user=userService.convertToModel(userService.findById(notificationDTO.getIdUser()));
            Notification notification = Notification.builder()
                    .id(getGenerationId())
                    .message(notificationDTO.getMessage())
                    .createAt(notificationDTO.getCreateAt())
                    .isRead(notificationDTO.isRead())
                    .userSend(user)
                    .build();
            return notificationRepository.save(notification);
        } catch (DataIntegrityViolationException e){
            throw new CustomException(Error.NOTIFICATION_UNABLE_TO_SAVE);
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public NotificationDTO saveNotification(NotificationDTO notificationDTO) {
        try {
            log.info("Save notification");
            Notification notification = save(notificationDTO);
            return convertToDTO(notification);
        } catch (DataIntegrityViolationException e){
            throw new CustomException(Error.NOTIFICATION_UNABLE_TO_SAVE);
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public NotificationDTO update(NotificationDTO notificationDTO) {
        try{
            log.info("Update notification by id: {}", notificationDTO.getId());
            return convertToDTO(notificationRepository.save(convertToModel(notificationDTO)));
        }  catch (DataIntegrityViolationException e){
            throw new CustomException(Error.NOTIFICATION_UNABLE_TO_UPDATE);
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public NotificationDTO findById(Integer id) {
        log.info("Find notification by id: {}", id);
        return convertToDTO(notificationRepository.findById(id)
                .orElseThrow(()-> new CustomException(Error.NOTIFICATION_NOT_FOUND)));
    }

    @Override
    public NotificationDTO seenNotification(Integer id) {
        try{
            log.info("Update seen notification by id: {}", id);
            Notification notification =convertToModel(findById(id));
            notification.setRead(true);
            return convertToDTO(notificationRepository.save(notification));
        }  catch (DataIntegrityViolationException e){
            throw new CustomException(Error.NOTIFICATION_UNABLE_TO_UPDATE);
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    public List<NotificationDTO> convertToDTOList(List<Notification> notifications) {
        return notifications.stream()
                .map(notification -> modelMapper.map(notification, NotificationDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationDTO> getNotificationByUser(Integer idUser) {
        try{
            log.info("Get notifications by user id: {}", idUser);
            return convertToDTOList(notificationRepository.findByUserId(idUser));
        } catch (InvalidDataAccessResourceUsageException e){
            log.error("Get notifications by user id failed: {}", e.getMessage());
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
        return null;
    }
}
