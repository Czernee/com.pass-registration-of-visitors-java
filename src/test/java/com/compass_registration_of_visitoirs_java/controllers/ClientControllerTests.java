package com.compass_registration_of_visitoirs_java.controllers;

import com.compass_registration_of_visitoirs_java.dto.ClientDto;
import com.compass_registration_of_visitoirs_java.dto.ClientResponse;
import com.compass_registration_of_visitoirs_java.models.Client;
import com.compass_registration_of_visitoirs_java.services.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.xml.transform.Result;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = ClientController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ClientControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Autowired
    private ObjectMapper objectMapper;

    private Client client;
    private ClientDto clientDto;

    @BeforeEach
    public void init() {
        client = Client.builder()
                .fullName("Ivan Ivanov")
                .passport("1234 567890")
                .phone("88005551234")
                .room("A-302")
                .arrival(LocalDateTime.now())
                .departure(LocalDateTime.now().plusDays(1))
                .build();

        clientDto = ClientDto.builder()
                .fullName("Ivan Ivanov")
                .passport("1234 567890")
                .phone("88005551234")
                .room("A-302")
                .arrival(LocalDateTime.now())
                .departure(LocalDateTime.now().plusDays(1))
                .build();
    }

    @Test
    public void ClientController_CreateClient_ReturnCreated() throws Exception {
        given(clientService.createClient(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/api/clients/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName", CoreMatchers.is(clientDto.getFullName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.passport", CoreMatchers.is(clientDto.getPassport())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone", CoreMatchers.is(clientDto.getPhone())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.room", CoreMatchers.is(clientDto.getRoom())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void ClientController_GetAllClient_ReturnResponseDto() throws Exception {
        ClientResponse clientResponse = ClientResponse.builder().pageSize(10).last(true).pageNo(1).content(Arrays.asList(clientDto)).build();
        when(clientService.getAllClients(1, 10)).thenReturn(clientResponse);

        ResultActions response = mockMvc.perform(get("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo", "1")
                .param("pageSize", "10"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(clientResponse.getContent().size())));
    }

    @Test
    public void ClientController_Client_ReturnClientDto() throws Exception {
        int clientId = 1;
        when(clientService.getClientById(clientId)).thenReturn(clientDto);

        ResultActions response = mockMvc.perform(get("/api/clients/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName", CoreMatchers.is(clientDto.getFullName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.passport", CoreMatchers.is(clientDto.getPassport())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone", CoreMatchers.is(clientDto.getPhone())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.room", CoreMatchers.is(clientDto.getRoom())));
    }

    @Test
    public void ClientController_UpdateClientById_ReturnClientDto() throws Exception {
        int clientId = 1;
        when(clientService.updateClient(clientId, clientDto)).thenReturn(clientDto);

        ResultActions response = mockMvc.perform(put("/api/clients/1/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName", CoreMatchers.is(clientDto.getFullName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.passport", CoreMatchers.is(clientDto.getPassport())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone", CoreMatchers.is(clientDto.getPhone())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.room", CoreMatchers.is(clientDto.getRoom())));
    }

    @Test
    public void ClientController_DeleteClient_ReturnsString() throws Exception {
        int clientId = 1;
        doNothing().when(clientService).deleteClientById(clientId);

        ResultActions response = mockMvc.perform(delete("/api/clients/1/delete")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
