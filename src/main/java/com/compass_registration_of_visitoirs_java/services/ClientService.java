package com.compass_registration_of_visitoirs_java.services;

import com.compass_registration_of_visitoirs_java.dto.ClientDto;
import com.compass_registration_of_visitoirs_java.dto.ClientResponse;

import java.util.List;

public interface ClientService{
    ClientResponse getClients(int pageNo, int pageSize);
    ClientDto getClientById(long clientId);
    ClientDto createClient(ClientDto clientDto);
    ClientDto updateClient(ClientDto clientDto);
    void deleteClient(int clientId);
    List<ClientDto> getStayingClients();
    boolean ifClientStaying(int clientId);
}
