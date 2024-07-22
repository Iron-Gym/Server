package com.kbm.Iron.Gym.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

@AllArgsConstructor
@Entity
@NoArgsConstructor
@Data
public class Income {
    @Id
    private int incomeId;
    private YearMonth month;
    private double income;
}
