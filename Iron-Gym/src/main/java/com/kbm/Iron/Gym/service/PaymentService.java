package com.kbm.Iron.Gym.service;

import com.kbm.Iron.Gym.entity.Client;
import com.kbm.Iron.Gym.entity.Payment;
import com.kbm.Iron.Gym.repository.ClientRepository;
import com.kbm.Iron.Gym.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
