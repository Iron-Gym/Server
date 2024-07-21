package com.kbm.Iron.Gym.controller;

import com.kbm.Iron.Gym.dto.PaymentStatusDTO;
import com.kbm.Iron.Gym.entity.Client;
import com.kbm.Iron.Gym.entity.Payment;
import com.kbm.Iron.Gym.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;


    //save payment to a specific user
    @PostMapping("/{clientId}")
    public ResponseEntity<Payment> savePayment(@RequestBody Payment payment, @PathVariable Integer clientId){
        Payment savedPayment = paymentService.savePayment(payment,clientId);
        return ResponseEntity.ok(savedPayment);
    }

    //get all payments of given month
    @GetMapping("/this-month")
    public ResponseEntity<List<Payment>> thisMonthPayments(
            @RequestParam("month") @DateTimeFormat(pattern = "yyyy-MM") YearMonth month){
        return ResponseEntity.ok(paymentService.getAllPaymentsByMonth(month));
    }

    //get list of paid and unpaid user list for a given month
    @GetMapping("/list-of-paid-unpaid-users")
    public ResponseEntity<PaymentStatusDTO> getClientPaymentStatusForMonth(
            @RequestParam("month") @DateTimeFormat(pattern = "yyyy-MM") YearMonth month) {
        PaymentStatusDTO paymentStatusDTO= paymentService.getClientPaymentStatusForMonth(month);
        return ResponseEntity.ok(paymentStatusDTO);
    }

    //get all payments of a give user
    @GetMapping("/client")
    public ResponseEntity<List<Payment>> getPaymentsByClient(
            @RequestParam Integer clientId){
        Optional<List<Payment>> paymentList = paymentService.getPaymentsByClient(clientId);
        return paymentList.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //get income of a month
    @GetMapping("/income")
    public ResponseEntity<Double> thisMonthIncome(
            @RequestParam("month") @DateTimeFormat(pattern = "yyyy-MM") YearMonth month){
        return ResponseEntity.ok(paymentService.getIncomeOfaMonth(month));
    }

}
