package com.kbm.Iron.Gym.service;

import com.kbm.Iron.Gym.dto.PaymentStatusDTO;
import com.kbm.Iron.Gym.entity.*;
import com.kbm.Iron.Gym.mailConfig.MailConfig;
import com.kbm.Iron.Gym.repository.ClientRepository;
import com.kbm.Iron.Gym.repository.NotificationRepository;
import com.kbm.Iron.Gym.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    ClientRepository clientRepository;



    //save a payment of a particular user
    @Transactional
    public Payment savePayment(Payment payment, Integer clientId) {
        Optional<Client> getClient = clientRepository.findById(clientId);
        if (getClient.isPresent()){
            Client client = getClient.get();
            payment.setClient(client);
        }
        return paymentRepository.save(payment);
    }


    //get list of paid and unpaid clients of a given month
    public PaymentStatusDTO getClientPaymentStatusForMonth(YearMonth month){
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
    }


    //get all payments for a specific client
    public Optional<List<Payment>> getPaymentsByClient(Integer clientId) {
        Optional<Client> client = clientRepository.findById(clientId);
        Client getClient = null;
        if (client.isPresent()){
            getClient = client.get();
        }
        return Optional.ofNullable(paymentRepository.findPaymentsByClient(getClient));
    }

    //get all payments of a month
    public List<Payment> getAllPaymentsByMonth(YearMonth month) {
        LocalDate startDate = month.atDay(1);
        LocalDate endDate = month.atEndOfMonth();
        return paymentRepository.findPaymentsByMonth(startDate,endDate);
    }

    public Double getIncomeOfaMonth(YearMonth month){
        LocalDate startDate = month.atDay(1);
        LocalDate endDate = month.atEndOfMonth();
        double income = 0.0;
        List<Payment> payments = paymentRepository.findPaymentsByMonth(startDate,endDate);
        for (Payment p : payments ){
            income = income + p.getAmount();
        }
        return income;
    }
}
