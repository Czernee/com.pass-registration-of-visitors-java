package com.compass_registration_of_visitoirs_java.services;

import com.compass_registration_of_visitoirs_java.dto.ClientDto;
import com.compass_registration_of_visitoirs_java.dto.ClientResponse;

import java.util.List;

public interface ClientService{
    ClientDto createClient(ClientDto clientDto);
    ClientResponse getAllClients(int pageNo, int pageSize);
    ClientDto getClientById(long clientId);
    ClientDto updateClient(long clientId, ClientDto clientDto);
    void deleteClient(long clientId);
    ClientResponse getStayingClients(int pageNo, int pageSize);
    boolean ifClientStaying(long clientId);
}
