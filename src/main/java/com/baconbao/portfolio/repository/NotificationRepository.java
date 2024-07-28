package com.baconbao.portfolio.repository;

import com.baconbao.portfolio.dto.NotificationDTO;
import com.baconbao.portfolio.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    Optional<Notification> findById(Integer integer);
    List<Notification> findByUserId(Integer idUser);
    @Query("SELECT n FROM Notification n WHERE n.userSend.id = :userId")
    List<NotificationDTO> getNotificationByUser(@Param("userId") Integer userId);
}
