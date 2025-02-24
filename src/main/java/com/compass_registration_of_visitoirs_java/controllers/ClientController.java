package com.compass_registration_of_visitoirs_java.controllers;

import com.compass_registration_of_visitoirs_java.models.Client;
import com.compass_registration_of_visitoirs_java.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/getAllClients")
    public ResponseEntity<List<Client>> getAllClients() {
        try {
            List<Client> clients = clientRepository.findAll();

            if (clients.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(clients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getClientById/{id}")
    public ResponseEntity<Client> getOneClient(@PathVariable Long id) {
        try {
            Optional<Client> client = clientRepository.findById(id);

            return client.isPresent() ? new ResponseEntity<>(client.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addClient")
    public ResponseEntity<Client> addClient(@RequestBody Client clientData) {
        try {
            Client newClient = clientRepository.save(clientData);
            return new ResponseEntity<>(newClient, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateClient/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client clientData) {
        try {
            if (!clientRepository.existsById(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            clientData.setId(id);
            clientRepository.save(clientData);
            return new ResponseEntity<>(clientData, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

   @GetMapping("/getStayingClients")
    public ResponseEntity<List<Client>> getStayingClients() {
        try {
            Date currentDate = new Date();
            List<Client> clients = clientRepository.findAllStayingClients(currentDate);
            return new ResponseEntity<>(clients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
