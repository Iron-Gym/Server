package com.kbm.Iron.Gym.controller;

import com.kbm.Iron.Gym.entity.Mail;
import com.kbm.Iron.Gym.service.NotificationService;
import com.kbm.Iron.Gym.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vi/notifications")
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    //send custom email to a client
    @PostMapping("/custom-mail/{clientId}")
    public ResponseEntity<Mail> customMail(@RequestBody Mail mail, @PathVariable Integer clientId){
        Mail customMail = notificationService.sendCustomEmail(clientId,mail);
        return ResponseEntity.ok(customMail);
    }
}
