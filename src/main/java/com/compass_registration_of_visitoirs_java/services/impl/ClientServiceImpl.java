package com.compass_registration_of_visitoirs_java.services.impl;

import com.compass_registration_of_visitoirs_java.dto.ClientDto;
import com.compass_registration_of_visitoirs_java.dto.ClientResponse;
import com.compass_registration_of_visitoirs_java.models.Client;
import com.compass_registration_of_visitoirs_java.repository.ClientRepository;
import com.compass_registration_of_visitoirs_java.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientResponse getClients(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Client> clients = clientRepository.findAll(pageable);
        List<Client> listOfClients = clients.getContent();
        List<ClientDto> content = listOfClients.stream().map(this::mapToDto).toList();

        ClientResponse clientResponse = new ClientResponse();
        clientResponse.setContent(content);
        clientResponse.setPageNo(clients.getNumber());
        clientResponse.setPageSize(clients.getTotalPages());
        clientResponse.setTotalElements(clients.getTotalElements());
        clientResponse.setTotalPages(clients.getTotalPages());
        clientResponse.setLast(clients.isLast());

        return clientResponse;
    }

    @Override
    public ClientDto getClientById(long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow();
        return mapToDto(client);
    }

    @Override
    public ClientDto createClient(ClientDto clientDto) {
        return null;
    }

    @Override
    public ClientDto updateClient(ClientDto clientDto) {
        return null;
    }

    @Override
    public void deleteClient(int clientId) {

    }

    @Override
    public List<ClientDto> getStayingClients() {
        return null;
    }

    @Override
    public boolean ifClientStaying(int clientId) {
        return false;
    }

    private Client mapToEntity(ClientDto clientDto) {
        Client client = new Client();
        client.setFullName(clientDto.getFullName());
        client.setPassport(clientDto.getPassport());
        client.setPhone(clientDto.getPhone());
        client.setRoom(clientDto.getRoom());
        client.setArrival(clientDto.getArrival());
        client.setDeparture(clientDto.getDeparture());
        return client;
    }

    private ClientDto mapToDto(Client client) {
        ClientDto clientDto = new ClientDto();
        clientDto.setId((client.getId()));
        clientDto.setFullName(client.getFullName());
        clientDto.setPassport(client.getPassport());
        clientDto.setPhone(client.getPhone());
        clientDto.setRoom(client.getRoom());
        clientDto.setArrival(client.getArrival());
        clientDto.setDeparture(client.getDeparture());
        return clientDto;
    }
}
