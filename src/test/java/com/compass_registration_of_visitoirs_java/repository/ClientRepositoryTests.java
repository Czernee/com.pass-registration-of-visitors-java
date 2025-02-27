package com.compass_registration_of_visitoirs_java.repository;

import com.compass_registration_of_visitoirs_java.dto.ClientDto;
import com.compass_registration_of_visitoirs_java.models.Client;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.assertj.core.api.Assertions;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClientRepositoryTests {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void ClientRepository_SaveAll_ReturnSavedClient() {
        Client client = Client.builder()
                .fullName("Ivan Ivanov")
                .passport("1234 567890")
                .phone("88005551234")
                .room("A-302")
                .arrival(LocalDateTime.now())
                .departure(LocalDateTime.now().plusDays(1))
                .build();

        Client savedClient = clientRepository.save(client);

        Assertions.assertThat(savedClient).isNotNull();
        Assertions.assertThat(savedClient.getId()).isGreaterThan(0);
    }

    @Test
    public void ClientRepository_GetAll_ReturnMoreThanOneClient() {
        Client client = Client.builder()
                .fullName("Ivan Ivanov")
                .passport("1234 567890")
                .phone("88005551234")
                .room("A-302")
                .arrival(LocalDateTime.now())
                .departure(LocalDateTime.now().plusDays(1))
                .build();

        Client client2 = Client.builder()
                .fullName("Ivan Ivanov")
                .passport("1234 567890")
                .phone("88005551234")
                .room("A-302")
                .arrival(LocalDateTime.now())
                .departure(LocalDateTime.now().plusDays(1))
                .build();

        clientRepository.save(client);
        clientRepository.save(client2);

        List<Client> clientList = clientRepository.findAll();

        Assertions.assertThat(clientList).isNotNull();
        Assertions.assertThat(clientList.size()).isEqualTo(2);
    }

    @Test
    public void ClientRepository_FindById_ReturnClient() {
        Client client = Client.builder()
                .fullName("Ivan Ivanov")
                .passport("1234 567890")
                .phone("88005551234")
                .room("A-302")
                .arrival(LocalDateTime.now())
                .departure(LocalDateTime.now().plusDays(1))
                .build();

        clientRepository.save(client);

        Client client1 = clientRepository.findById(client.getId()).get();

        Assertions.assertThat(client1).isNotNull();
    }

    @Test
    public void ClientRepository_FindByPassport_ReturnClientNotNull() {
        Client client = Client.builder()
                .fullName("Ivan Ivanov")
                .passport("1234 567890")
                .phone("88005551234")
                .room("A-302")
                .arrival(LocalDateTime.now())
                .departure(LocalDateTime.now().plusDays(1))
                .build();

        clientRepository.save(client);

        Client client1 = clientRepository.findByPassport(client.getPassport()).get();

        Assertions.assertThat(client1).isNotNull();
    }

    @Test
    public void ClientRepository_UpdateClient_ReturnClientNotNull() {
        Client client = Client.builder()
                .fullName("Ivan Ivanov")
                .passport("1234 567890")
                .phone("88005551234")
                .room("A-302")
                .arrival(LocalDateTime.now())
                .departure(LocalDateTime.now().plusDays(1))
                .build();

        clientRepository.save(client);

        Client client1 = clientRepository.findById(client.getId()).get();
        client1.setFullName("Sergey Sergeev");
        client1.setPassport("1324 658709");
        client1.setPhone("89885551234");
        client1.setRoom("B-212");
        client1.setArrival(LocalDateTime.now().plusDays(1));
        client1.setDeparture(LocalDateTime.now().plusDays(2));

        Client updatedClient = clientRepository.save(client1);

        Assertions.assertThat(updatedClient.getFullName()).isNotNull();
        Assertions.assertThat(updatedClient.getPassport()).isNotNull();
        Assertions.assertThat(updatedClient.getPhone()).isNotNull();
        Assertions.assertThat(updatedClient.getRoom()).isNotNull();
        Assertions.assertThat(updatedClient.getArrival()).isNotNull();
        Assertions.assertThat(updatedClient.getDeparture()).isNotNull();
    }

    @Test
    public void ClientRepository_DeleteClientById_ReturnsClientIsEmpty() {
        Client client = Client.builder()
                .fullName("Ivan Ivanov")
                .passport("1234 567890")
                .phone("88005551234")
                .room("A-302")
                .arrival(LocalDateTime.now())
                .departure(LocalDateTime.now().plusDays(1))
                .build();

        clientRepository.save(client);

        clientRepository.deleteById(client.getId());
        Optional<Client> clientReturn = clientRepository.findById(client.getId());

        Assertions.assertThat(clientReturn).isEmpty();
    }
}
