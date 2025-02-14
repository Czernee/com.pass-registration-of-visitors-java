package com.compass_registration_of_visitoirs_java.services;

import com.compass_registration_of_visitoirs_java.repo.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.compass_registration_of_visitoirs_java.models.Client;

import java.util.Date;
import java.util.List;

@Service
public class ClientService  {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getClients() {
        return (List<Client>) clientRepository.findAll();
    }

    public Client getOneClient(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Клиент не найден"));
    }

    public Client createClient(Client clientData) {
        return clientRepository.save(clientData);
    }

    public Client updateClient(Long id, Client clientData) {
        if (!clientRepository.existsById(id)) {
            throw new RuntimeException("Клиент не найден");
        }

        clientData.setId(id);
        return clientRepository.save(clientData);
    }

    public List<Client> getStayingClients(Date currentDate) {
        return clientRepository.findAllStayingClients(currentDate);
    }
}
