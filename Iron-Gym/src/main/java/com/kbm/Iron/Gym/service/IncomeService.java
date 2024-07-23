package com.kbm.Iron.Gym.service;

import com.kbm.Iron.Gym.configurations.IncomeConfig;
import com.kbm.Iron.Gym.dto.IncomeCount;
import com.kbm.Iron.Gym.entity.Client;
import com.kbm.Iron.Gym.entity.Payment;
import com.kbm.Iron.Gym.entity.Status;
import com.kbm.Iron.Gym.repository.ClientRepository;
import com.kbm.Iron.Gym.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class IncomeService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ClientRepository clientRepository;

    public IncomeCount getIncomeOfaMonth(YearMonth month) {
        try {
            LocalDate startDate = month.atDay(1);
            LocalDate endDate = month.atEndOfMonth();
            List<Client> allClients = clientRepository.findAll();
            List<Client> activeClients = allClients.stream()
                    .filter(client -> client.getStatus().equals(Status.ACTIVE)).toList();
            double exIncome = activeClients.size()* IncomeConfig.monthlyGymFee;
            double income = 0.0;
            List<Payment> payments = paymentRepository.findPaymentsByMonth(startDate, endDate);
            for (Payment p : payments) {
                income += p.getAmount();
            }
            double dueIncome = exIncome-income;
            IncomeCount incomeCount = new IncomeCount();
            incomeCount.setCurrentIncome(income);
            incomeCount.setDueIncome(dueIncome);
            incomeCount.setExpectedIncome(exIncome);
            return incomeCount;
        } catch (Exception e) {
            logger.error("Error calculating income for month: " + month, e);
            return null;
        }
    }

    public IncomeCount getCurrentMonthIncome() {
        try {
            YearMonth currentMonth = YearMonth.now();
            LocalDate startDate = currentMonth.atDay(1);
            LocalDate endDate = currentMonth.atEndOfMonth();

            List<Client> allClients = clientRepository.findAll();
            List<Client> activeClients = allClients.stream()
                    .filter(client -> client.getStatus().equals(Status.ACTIVE))
                    .toList();

            double expectedIncome = activeClients.size() * IncomeConfig.monthlyGymFee;
            double income = 0.0;

            List<Payment> payments = paymentRepository.findPaymentsByMonth(startDate, endDate);
            for (Payment p : payments) {
                income += p.getAmount();
            }

            double dueIncome = expectedIncome - income;

            IncomeCount incomeCount = new IncomeCount();
            incomeCount.setCurrentIncome(income);
            incomeCount.setDueIncome(dueIncome);
            incomeCount.setExpectedIncome(expectedIncome);

            return incomeCount;
        } catch (Exception e) {
            logger.error("Error calculating income for the current month", e);
            return null;
        }
    }

}
