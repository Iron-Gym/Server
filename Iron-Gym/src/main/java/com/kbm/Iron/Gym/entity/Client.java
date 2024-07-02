package com.kbm.Iron.Gym.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private int client_id;
    private String fName;
    private String lName;
    private String email;
    private String phone;
    private Date registrationDate;
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne
    @Column(name = "scheduleId")
    private Schedule schedule;
}
