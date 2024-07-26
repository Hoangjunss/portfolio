package com.baconbao.portfolio.repository;

import com.baconbao.portfolio.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    Optional<Notification> findById(Integer integer);
}
