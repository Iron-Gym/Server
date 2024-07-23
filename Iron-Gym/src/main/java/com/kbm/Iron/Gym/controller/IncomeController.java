package com.kbm.Iron.Gym.controller;

import com.kbm.Iron.Gym.dto.IncomeCount;
import com.kbm.Iron.Gym.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;

@RestController
@RequestMapping("/api/v1/income")
public class IncomeController {

    @Autowired
    IncomeService incomeService;

    //get income of a month
    @GetMapping("/income-given-month")
    public ResponseEntity<IncomeCount> thisMonthIncome(
            @RequestParam("month") @DateTimeFormat(pattern = "yyyy-MM") YearMonth month){
        return ResponseEntity.ok(incomeService.getIncomeOfaMonth(month));
    }

    @GetMapping("/income-current-month")
    public ResponseEntity<IncomeCount> currentMonthIncome(){
        return ResponseEntity.ok(incomeService.getCurrentMonthIncome());
    }
}
