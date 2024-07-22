package com.kbm.Iron.Gym.service;

import com.kbm.Iron.Gym.entity.Client;
import com.kbm.Iron.Gym.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getAllClients() {
        try {
            return clientRepository.findAll();
        } catch (Exception e) {
            logger.error("Error retrieving all clients", e);
            return List.of();
        }
    }

    public Client saveClient(Client client) {
        try {
            return clientRepository.save(client);
        } catch (Exception e) {
            logger.error("Error saving client: " + client, e);
            return null;
        }
    }

    public Optional<Client> getClient(Integer id) {
        try {
            return clientRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error retrieving client with id " + id, e);
            return Optional.empty();
        }
    }

    public Client updateClient(Client client, Integer clientId) {
        try {
            Optional<Client> tempClient = clientRepository.findById(clientId);
            if (tempClient.isPresent()) {
                Client updatedClient = tempClient.get();

                updatedClient.setfName(client.getfName());
                updatedClient.setlName(client.getlName());
                updatedClient.setAge(client.getAge());
                updatedClient.setEmail(client.getEmail());
                updatedClient.setPhone(client.getPhone());
                updatedClient.setRegistrationDate(client.getRegistrationDate());
                updatedClient.setStatus(client.getStatus());

                return clientRepository.save(updatedClient);
            } else {
                logger.warn("Client with id " + clientId + " not found");
                return null;
            }
        } catch (Exception e) {
            logger.error("Error updating client with id " + clientId, e);
            return null;
        }
    }

    public List<Client> getAllActiveClients() {
        try {
            return clientRepository.getAllActiveClients();
        } catch (Exception e) {
            logger.error("Error retrieving all active clients", e);
            return List.of();
        }
    }
}
