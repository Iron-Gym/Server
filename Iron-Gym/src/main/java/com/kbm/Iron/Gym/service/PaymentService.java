package com.kbm.Iron.Gym.service;

import com.kbm.Iron.Gym.dto.IncomeStats;
import com.kbm.Iron.Gym.dto.PaymentStatusDTO;
import com.kbm.Iron.Gym.entity.Client;
import com.kbm.Iron.Gym.entity.Payment;
import com.kbm.Iron.Gym.entity.Status;
import com.kbm.Iron.Gym.repository.ClientRepository;
import com.kbm.Iron.Gym.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    public Payment savePayment(Payment payment, Integer clientId) {
        try {
            Optional<Client> getClient = clientRepository.findById(clientId);
            if (getClient.isPresent()) {
                Client client = getClient.get();
                payment.setClient(client);
            } else {
                logger.warn("Client with id " + clientId + " not found");
            }
            return paymentRepository.save(payment);
        } catch (Exception e) {
            logger.error("Error saving payment for clientId: " + clientId, e);
            return null;
        }
    }

    public PaymentStatusDTO getClientPaymentStatusForMonth(YearMonth month) {
        try {
            LocalDate startDate = month.atDay(1);
            LocalDate endDate = month.atEndOfMonth();

            List<Payment> payments = paymentRepository.findPaymentsByMonth(startDate, endDate);
            List<Client> allClients = clientRepository.findAll();

            List<Client> activeClients = allClients.stream()
                    .filter(client -> client.getStatus().equals(Status.ACTIVE)).toList();

            List<Client> paidClients = payments.stream()
                    .map(Payment::getClient)
                    .distinct()
                    .toList();

            List<Client> unpaidClients = activeClients.stream()
                    .filter(client -> !paidClients.contains(client))
                    .toList();

            return new PaymentStatusDTO(paidClients, unpaidClients);
        } catch (Exception e) {
            logger.error("Error getting payment status for month: " + month, e);
            return new PaymentStatusDTO(List.of(), List.of()); // Return empty lists on error
        }
    }

    public Optional<List<Payment>> getPaymentsByClient(Integer clientId) {
        try {
            Optional<Client> client = clientRepository.findById(clientId);
            if (client.isPresent()) {
                return Optional.ofNullable(paymentRepository.findPaymentsByClient(client.get()));
            } else {
                logger.warn("Client with id " + clientId + " not found");
                return Optional.empty();
            }
        } catch (Exception e) {
            logger.error("Error getting payments for clientId: " + clientId, e);
            return Optional.empty();
        }
    }

    public List<Payment> getAllPaymentsByMonth(YearMonth month) {
        try {
            LocalDate startDate = month.atDay(1);
            LocalDate endDate = month.atEndOfMonth();
            return paymentRepository.findPaymentsByMonth(startDate, endDate);
        } catch (Exception e) {
            logger.error("Error getting payments for month: " + month, e);
            return List.of();
        }
    }

    public Double getIncomeOfaMonth(YearMonth month) {
        try {
            LocalDate startDate = month.atDay(1);
            LocalDate endDate = month.atEndOfMonth();
            double income = 0.0;
            List<Payment> payments = paymentRepository.findPaymentsByMonth(startDate, endDate);
            for (Payment p : payments) {
                income += p.getAmount();
            }
            return income;
        } catch (Exception e) {
            logger.error("Error calculating income for month: " + month, e);
            return 0.0;
        }
    }

    public List<IncomeStats> getTotalIncomeByMonthInRange(LocalDate startDate, LocalDate endDate) {
        try {
            return paymentRepository.findTotalIncomeByMonthInRange(startDate, endDate);
        } catch (Exception e) {
            logger.error("Error getting total income by month in range: " + startDate + " to " + endDate, e);
            return List.of();
        }
    }
}
