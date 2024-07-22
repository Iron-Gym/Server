package com.kbm.Iron.Gym.repository;

import com.kbm.Iron.Gym.dto.NewMemberStats;
import com.kbm.Iron.Gym.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client,Integer> {
    List<Client> findByRegistrationDate(LocalDate registrationDate);

    @Query(value = "SELECT EXTRACT(YEAR FROM registration_date) AS year, EXTRACT(MONTH FROM registration_date) AS month, COUNT(*) AS count " +
            "FROM client " +
            "WHERE registration_date BETWEEN :startDate AND :endDate " +
            "GROUP BY EXTRACT(YEAR FROM registration_date), EXTRACT(MONTH FROM registration_date)",
            nativeQuery = true)
    List<Object[]> countNewMembersByMonthNative(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = "SELECT * FROM client c WHERE c.status = 'ACTIVE'",nativeQuery = true)
    List<Client> getAllActiveClients();
}
