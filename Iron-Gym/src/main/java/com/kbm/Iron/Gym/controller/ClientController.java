package com.kbm.Iron.Gym.controller;

import com.kbm.Iron.Gym.entity.Client;
import com.kbm.Iron.Gym.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {
    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
    @Autowired
    ClientService clientService;

    //get all clients
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    //save a client
    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client savedClient = clientService.saveClient(client);
        return ResponseEntity.ok(savedClient);
    }

    //update a client
    @PutMapping("/{clientId}")
    public ResponseEntity<Client> updateClient(@RequestBody Client client, @PathVariable Integer clientId){
        Client updatedClient = clientService.updateClient(client,clientId);
        return ResponseEntity.ok(updatedClient);
    }

    //get a client
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClient(@PathVariable Integer id) {
        Optional<Client> client = clientService.getClient(id);
        return client.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
