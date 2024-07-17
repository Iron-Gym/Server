package com.kbm.Iron.Gym.controller;

import com.kbm.Iron.Gym.entity.Payment;
import com.kbm.Iron.Gym.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;


//add payment to a specific user
    @PostMapping("/{clientId}")
    public ResponseEntity<Payment> savePayment(@RequestBody Payment payment, @PathVariable Integer clientId){
        Payment savedPayment = paymentService.savePayment(payment,clientId);
        return ResponseEntity.ok(savedPayment);
    }
}
