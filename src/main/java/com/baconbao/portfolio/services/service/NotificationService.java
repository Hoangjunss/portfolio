package com.baconbao.portfolio.services.service;

import com.baconbao.portfolio.dto.NotificationDTO;
import org.springframework.stereotype.Service;

@Service

public interface NotificationService {
    NotificationDTO saveNotification(NotificationDTO notificationDTO);
    NotificationDTO update(NotificationDTO notificationDTO);
    NotificationDTO findById(Integer id);
}
