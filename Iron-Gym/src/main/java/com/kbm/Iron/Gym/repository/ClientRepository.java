package com.kbm.Iron.Gym.repository;

import com.kbm.Iron.Gym.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client,Integer> {
    List<Client> findByRegistrationDate(LocalDate registrationDate);
}
