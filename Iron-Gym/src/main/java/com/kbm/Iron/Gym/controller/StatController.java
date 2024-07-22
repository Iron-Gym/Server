package com.kbm.Iron.Gym.controller;

import com.kbm.Iron.Gym.dto.IncomeStats;
import com.kbm.Iron.Gym.repository.ClientRepository;
import com.kbm.Iron.Gym.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/stats")
public class StatController {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/new-members")
    public List<Object[]> getNewMembersStats(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return clientRepository.countNewMembersByMonthNative(startDate, endDate);
    }

    @GetMapping("/monthly-income")
    public List<IncomeStats> getMonthlyIncome(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate
    ) {
        return paymentService.getTotalIncomeByMonthInRange(startDate,endDate);
    }
}
