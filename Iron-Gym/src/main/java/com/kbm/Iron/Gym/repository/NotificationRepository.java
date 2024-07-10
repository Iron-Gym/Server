package com.kbm.Iron.Gym.repository;

import com.kbm.Iron.Gym.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Integer> {
}
