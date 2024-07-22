package com.kbm.Iron.Gym.repository;

import com.kbm.Iron.Gym.dto.IncomeStats;
import com.kbm.Iron.Gym.entity.Client;
import com.kbm.Iron.Gym.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Integer> {

    @Query("SELECT p FROM Payment p WHERE p.paymentDate BETWEEN :startDate AND :endDate")
    List<Payment> findPaymentsByMonth(LocalDate startDate, LocalDate endDate);

    List<Payment> findPaymentsByClient(Client client);

    @Query("SELECT new com.kbm.Iron.Gym.dto.IncomeStats(" +
            "date_trunc('month', p.paymentDate), SUM(p.amount)) " +
            "FROM Payment p " +
            "WHERE p.paymentDate BETWEEN :startDate AND :endDate " +
            "GROUP BY date_trunc('month', p.paymentDate)")
    List<IncomeStats> findTotalIncomeByMonthInRange(@Param("startDate") LocalDate startDate,
                                                    @Param("endDate") LocalDate endDate);

}
