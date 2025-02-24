package com.compass_registration_of_visitoirs_java.controllers;

import com.compass_registration_of_visitoirs_java.dto.ClientDto;
import com.compass_registration_of_visitoirs_java.dto.ClientResponse;
import com.compass_registration_of_visitoirs_java.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class ClientController {

    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("clients/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ClientDto> createClient(@RequestBody ClientDto clientDto) {
        return new ResponseEntity<>(clientService.createClient(clientDto), HttpStatus.CREATED);
    }

    @GetMapping("clients")
    public ResponseEntity<ClientResponse> getClients(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
            ) {
        return new ResponseEntity<>(clientService.getAllClients(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("clients/{id}")
    public ResponseEntity<ClientDto> clientDetail(@PathVariable int id) {
        return new ResponseEntity<>(clientService.clientDetail(id), HttpStatus.OK);
    }

    @PutMapping("clients/{id}/update")
    public ResponseEntity<ClientDto> updateClient(@PathVariable("id") int clientId, @RequestBody ClientDto clientDto) {
        ClientDto response = clientService.updateClient(clientId, clientDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("clients/{id}/delete")
    public ResponseEntity<String> deleteClient(@PathVariable("id") int clientId) {
        clientService.deleteClient(clientId);
        return new ResponseEntity<>("Client deleted", HttpStatus.OK);
    }

    @GetMapping("clients/staying")
    public ResponseEntity<ClientResponse> getStayingClients(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
            ) {
        return new ResponseEntity<>(clientService.getStayingClients(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("clients/{id}/staying")
    public ResponseEntity<Boolean> isClientStaying(@PathVariable("id") int clientId) {
        boolean response = clientService.ifClientStaying(clientId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
