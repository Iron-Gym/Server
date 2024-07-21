package com.kbm.Iron.Gym.repository;

import com.kbm.Iron.Gym.entity.Client;
import com.kbm.Iron.Gym.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Integer> {

    @Query("SELECT p FROM Payment p WHERE p.paymentDate BETWEEN :startDate AND :endDate")
    List<Payment> findPaymentsByMonth(LocalDate startDate, LocalDate endDate);

    List<Payment> findPaymentsByClient(Client client);
}
