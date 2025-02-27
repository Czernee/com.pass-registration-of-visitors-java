package com.compass_registration_of_visitoirs_java.service;

import com.compass_registration_of_visitoirs_java.dto.ClientDto;
import com.compass_registration_of_visitoirs_java.dto.ClientResponse;
import com.compass_registration_of_visitoirs_java.models.Client;
import com.compass_registration_of_visitoirs_java.repository.ClientRepository;
import com.compass_registration_of_visitoirs_java.services.impl.ClientServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTests {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    public void ClientService_CreateClient_ReturnsClientDto() {
        Client client = Client.builder()
                .fullName("Ivan Ivanov")
                .passport("1234 567890")
                .phone("88005551234")
                .room("A-302")
                .arrival(LocalDateTime.now())
                .departure(LocalDateTime.now().plusDays(1))
                .build();

        ClientDto clientDto = ClientDto.builder()
                .fullName("Ivan Ivanov")
                .passport("1234 567890")
                .phone("88005551234")
                .room("A-302")
                .arrival(LocalDateTime.now())
                .departure(LocalDateTime.now().plusDays(1))
                .build();

        when(clientRepository.save(Mockito.any(Client.class))).thenReturn(client);

        ClientDto savedClient = clientService.createClient(clientDto);

        Assertions.assertThat(savedClient).isNotNull();
    }

    @Test
    public void ClientService_GetAllClients_ReturnsResponseDto() {
        Page<Client> clients = Mockito.mock(Page.class);

        when(clientRepository.findAll(Mockito.any(Pageable.class))).thenReturn(clients);

        ClientResponse savedClient = clientService.getAllClients(1, 10);

        Assertions.assertThat(savedClient).isNotNull();
    }

    @Test
    public void ClientService_GetClientById_ReturnsClientDto() {
        long clientId = 1;
        Client client = Client.builder()
                .fullName("Ivan Ivanov")
                .passport("1234 567890")
                .phone("88005551234")
                .room("A-302")
                .arrival(LocalDateTime.now())
                .departure(LocalDateTime.now().plusDays(1))
                .build();

        ClientDto clientDto = ClientDto.builder()
                .fullName("Ivan Ivanov")
                .passport("1234 567890")
                .phone("88005551234")
                .room("A-302")
                .arrival(LocalDateTime.now())
                .departure(LocalDateTime.now().plusDays(1))
                .build();

        when(clientRepository.findById(clientId)).thenReturn(Optional.ofNullable(client));

        ClientDto savedClient = clientService.getClientById(clientId);

        Assertions.assertThat(savedClient).isNotNull();
    }

    @Test
    public void ClientService_UpdateClientById_ReturnsClientDto() {
        Client client = Client.builder()
                .fullName("Ivan Ivanov")
                .passport("1234 567890")
                .phone("88005551234")
                .room("A-302")
                .arrival(LocalDateTime.now())
                .departure(LocalDateTime.now().plusDays(1))
                .build();

        ClientDto clientDto = ClientDto.builder()
                .fullName("Ivan Ivanov")
                .passport("1234 567890")
                .phone("88005551234")
                .room("A-302")
                .arrival(LocalDateTime.now())
                .departure(LocalDateTime.now().plusDays(1))
                .build();

        when(clientRepository.findById(1L)).thenReturn(Optional.ofNullable(client));
        when(clientRepository.save(Mockito.any(Client.class))).thenReturn(client);

        ClientDto savedClient = clientService.updateClient(1L, clientDto);

        Assertions.assertThat(savedClient).isNotNull();
    }

    @Test
    public void ClientService_DeleteClientById_ReturnsClientDto() {
        Client client = Client.builder()
                .fullName("Ivan Ivanov")
                .passport("1234 567890")
                .phone("88005551234")
                .room("A-302")
                .arrival(LocalDateTime.now())
                .departure(LocalDateTime.now().plusDays(1))
                .build();

        when(clientRepository.findById(1L)).thenReturn(Optional.ofNullable(client));

        assertAll(() -> clientService.deleteClientById(1L));
    }
}
