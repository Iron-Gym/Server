package com.kbm.Iron.Gym.dto;

import java.time.LocalDate;
import java.time.Month;

public class IncomeStats {

    private LocalDate month;
    private double totalIncome;

    public IncomeStats(LocalDate month, double totalIncome) {
        this.month = month;
        this.totalIncome = totalIncome;
    }

    public LocalDate getMonth() {
        return month;
    }

    public void setMonth(LocalDate month) {
        this.month = month;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }
}
