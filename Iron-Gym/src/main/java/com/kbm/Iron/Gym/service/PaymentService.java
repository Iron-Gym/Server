package com.kbm.Iron.Gym.service;

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
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    ClientRepository clientRepository;


    @Transactional
    public Payment savePayment(Payment payment, Integer clientId) {
        Optional<Client> getClient = clientRepository.findById(clientId);
        if (getClient.isPresent()){
            Client client = getClient.get();
            payment.setClient(client);
        }
        return paymentRepository.save(payment);
    }


}
