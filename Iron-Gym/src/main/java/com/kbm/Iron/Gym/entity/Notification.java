package com.kbm.Iron.Gym.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notificationId;
    private String recipientEmail;
    private String recipientPhone;
    private String subject;
    @Enumerated(EnumType.STRING)
    private Type type;
    private String message;
    private LocalDate sentDate;
    @Enumerated(EnumType.STRING)
    private MsgStatus status;
}
