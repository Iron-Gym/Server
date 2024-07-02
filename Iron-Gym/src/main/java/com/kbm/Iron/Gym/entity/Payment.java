package com.kbm.Iron.Gym.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;
    private double amount;
    private Date paymentDate;
    private Date dueDate;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
