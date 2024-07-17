package com.kbm.Iron.Gym.service;

import com.kbm.Iron.Gym.entity.*;
import com.kbm.Iron.Gym.mailConfig.MailConfig;
import com.kbm.Iron.Gym.repository.ClientRepository;
import com.kbm.Iron.Gym.repository.NotificationRepository;
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
public class NotificationService {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    private JavaMailSender mailSender;

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Scheduled(cron = "0 0 0 * * ?") // Runs every day at midnight
    public void sendFeeReminders() {
        LocalDate today = LocalDate.now();
        List<Client> clients = clientRepository.findAll();

        for (Client client : clients) {
            LocalDate startDate = client.getRegistrationDate();
            if (startDate != null && today.isAfter(startDate) && today.minusDays(30).equals(startDate)) {
                sendReminderEmail(client);
            } else if(startDate != null && today.isAfter(startDate) && today.minusDays(45).equals(startDate)) {
                sendLateFeeReminderMail(client);
            }
        }
    }

    @Transactional
    private void sendLateFeeReminderMail(Client client) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(client.getEmail());
        message.setSubject("Late Fee Reminder");
        message.setText(MailConfig.lateFeeMsg);

        try {
            mailSender.send(message);
            saveEmail(client.getEmail(),"Late Fee Reminder", MailConfig.monthlyFeeMsg,"SENT","EMAIL",client.getPhone());
            logger.info("Reminder email sent successfully to {}", client.getEmail());
        } catch (MailException e) {
            logger.error("Error while sending email to {}: {}", client.getEmail(), e.getMessage());
            saveEmail(client.getEmail(),"Fee Reminder", MailConfig.monthlyFeeMsg,"FAILED","EMAIL",client.getPhone());
        }
    }

    @Transactional
    private void sendReminderEmail(Client client) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(client.getEmail());
        message.setSubject("Fee Reminder");
        message.setText(MailConfig.monthlyFeeMsg);

        try {
            mailSender.send(message);
            saveEmail(client.getEmail(),"Fee Reminder", MailConfig.monthlyFeeMsg,"SENT","EMAIL",client.getPhone());
            logger.info("Reminder email sent successfully to {}", client.getEmail());
        } catch (MailException e) {
            logger.error("Error while sending email to {}: {}", client.getEmail(), e.getMessage());
            saveEmail(client.getEmail(),"Fee Reminder", MailConfig.monthlyFeeMsg,"FAILED","EMAIL",client.getPhone());
        }
    }

    @Transactional
    public Mail sendCustomEmail(Integer clientId, Mail mail) {
        Optional<Client> getClient = clientRepository.findById(clientId);
        Client client = null;
        if (getClient.isPresent()) {
            client = getClient.get();
        }
        SimpleMailMessage message = new SimpleMailMessage();
        if (client != null) {
            message.setTo(client.getEmail());
            message.setSubject(mail.getSubject());
            message.setText(mail.getMessage());
        }
        try {
            mailSender.send(message);
            saveEmail(client.getEmail(), "Custom Mail", mail.getMessage(), "SENT", "EMAIL", client.getPhone());
            logger.info("Reminder email sent successfully to {}", client.getEmail());
        } catch (MailException e) {
            logger.error("Error while sending email to {}: {}", client.getEmail(), e.getMessage());
            saveEmail(client.getEmail(), "Custom Mail", mail.getMessage(), "FAILED", "EMAIL", client.getPhone());
        }
        return mail;
    }

    private void saveEmail(String to,String subject,String body, String status,String type, String phone){
        Notification notification = new Notification();
        notification.setRecipientEmail(to);
        notification.setSubject(subject);
        notification.setMessage(body);
        notification.setType(Type.valueOf(type));
        notification.setSentDate(LocalDate.now());
        notification.setRecipientPhone(phone);
        notification.setStatus(MsgStatus.valueOf(status));
        notificationRepository.save(notification);
    }
}
