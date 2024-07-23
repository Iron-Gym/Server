package com.kbm.Iron.Gym.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeCount {
    private double expectedIncome;
    private double currentIncome;
    private double dueIncome;
}
