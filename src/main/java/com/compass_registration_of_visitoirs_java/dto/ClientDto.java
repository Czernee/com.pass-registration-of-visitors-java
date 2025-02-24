package com.compass_registration_of_visitoirs_java.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    private long id;
    private String fullName;
    private String passport;
    private String phone;
    private String room;
    private LocalDateTime arrival;
    private LocalDateTime departure;
}
