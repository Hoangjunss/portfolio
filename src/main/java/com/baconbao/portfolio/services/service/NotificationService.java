package com.baconbao.portfolio.services.service;

import com.baconbao.portfolio.dto.NotificationDTO;
import com.baconbao.portfolio.model.Notification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface NotificationService {
    NotificationDTO saveNotification(NotificationDTO notificationDTO);
    NotificationDTO update(NotificationDTO notificationDTO);
    NotificationDTO findById(Integer id);
    NotificationDTO seenNotification(Integer id);
    //getnotificationbyUser
    List<NotificationDTO> getNotificationByUser(Integer idUser);

}
