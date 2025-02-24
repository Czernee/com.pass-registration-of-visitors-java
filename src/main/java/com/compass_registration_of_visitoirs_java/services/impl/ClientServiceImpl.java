package com.compass_registration_of_visitoirs_java.services.impl;

import com.compass_registration_of_visitoirs_java.dto.ClientDto;
import com.compass_registration_of_visitoirs_java.dto.ClientResponse;
import com.compass_registration_of_visitoirs_java.exceptions.ClientNotFoundException;
import com.compass_registration_of_visitoirs_java.models.Client;
import com.compass_registration_of_visitoirs_java.repository.ClientRepository;
import com.compass_registration_of_visitoirs_java.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientDto createClient(ClientDto clientDto) {
        Client client = new Client();
        client.setFullName(clientDto.getFullName());
        client.setPassport(clientDto.getPassport());
        client.setPhone(clientDto.getPhone());
        client.setRoom(clientDto.getRoom());
        client.setArrival(clientDto.getArrival());
        client.setDeparture(clientDto.getDeparture());

        Client newClient = clientRepository.save(client);

        ClientDto clientResponse = new ClientDto();
        clientResponse.setId(newClient.getId());
        clientResponse.setFullName(newClient.getFullName());
        clientResponse.setPassport(newClient.getPassport());
        clientResponse.setPhone(newClient.getPhone());
        clientResponse.setRoom(newClient.getRoom());
        clientResponse.setArrival(newClient.getArrival());
        clientResponse.setDeparture(newClient.getDeparture());
        return clientResponse;
    }

    @Override
    public ClientResponse getAllClients(int pageNo, int pageSize) {
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
    public ClientDto clientDetail(long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new ClientNotFoundException("Client with this id can't be found"));
        return mapToDto(client);
    }


    @Override
    public ClientDto updateClient(long clientId, ClientDto clientDto) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new ClientNotFoundException("Client with this id can't be found"));

        client.setFullName(clientDto.getFullName());
        client.setPassport(clientDto.getPassport());
        client.setPhone(clientDto.getPhone());
        client.setRoom(clientDto.getRoom());
        client.setArrival(clientDto.getArrival());
        client.setDeparture(clientDto.getDeparture());

        Client updatedClient = clientRepository.save(client);
        return mapToDto(updatedClient);
    }

    @Override
    public void deleteClient(long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new ClientNotFoundException("Client with this id can't be found"));
        clientRepository.delete(client);
    }

    @Override
    public ClientResponse getStayingClients(int pageNo, int pageSize) {
        LocalDateTime now = LocalDateTime.now();
        int totalElements = 0;
        int totalPages = 0;

        List<Client> stayingClients = clientRepository.findAll().stream()
                .filter(client -> client.getArrival().isBefore(now) && client.getDeparture().isAfter(now))
                .toList();

        totalElements = stayingClients.size();
        totalPages = (int) Math.ceil((double) totalElements / pageSize);

        int start = pageNo * pageSize;
        int end = Math.min(start + pageSize, totalElements);
        List<Client> currentPage = stayingClients.subList(start, end);

        List<ClientDto> content = currentPage.stream().map(this::mapToDto).toList();

        ClientResponse response = new ClientResponse();
        response.setContent(content);
        response.setPageNo(pageNo);
        response.setPageSize(pageSize);
        response.setTotalElements(totalElements);
        response.setTotalPages(totalPages);
        response.setLast(pageNo == totalPages - 1);

        return response;
    }

    @Override
    public boolean ifClientStaying(long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new ClientNotFoundException("Client with this id can't be found"));

        LocalDateTime now = LocalDateTime.now();

        return (now.isAfter(client.getArrival()) && now.isBefore(client.getDeparture()));
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
