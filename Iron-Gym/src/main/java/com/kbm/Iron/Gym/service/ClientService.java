package com.kbm.Iron.Gym.service;

import com.kbm.Iron.Gym.entity.Client;
import com.kbm.Iron.Gym.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }


    public Optional<Client> getClient(Integer id) {
        return clientRepository.findById(id);
    }

    public Client updateClient(Client client, Integer clientId) {
        Optional<Client> tempClient = clientRepository.findById(clientId);
        Client updatedClient = null;
        if (tempClient.isPresent()){
            updatedClient = tempClient.get();

            updatedClient.setfName(client.getfName());
            updatedClient.setlName(client.getlName());
            updatedClient.setEmail(client.getEmail());
            updatedClient.setPhone(client.getPhone());
            updatedClient.setRegistrationDate(client.getRegistrationDate());
            updatedClient.setStatus(client.getStatus());

            updatedClient = clientRepository.save(updatedClient);
        }
        return updatedClient;
    }
}
