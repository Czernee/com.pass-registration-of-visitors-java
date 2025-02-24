package com.compass_registration_of_visitoirs_java.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="clients")
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fullName;

    private String passport;

    private String phone;

    private String room;

    private LocalDateTime arrival;

    private LocalDateTime departure;
}