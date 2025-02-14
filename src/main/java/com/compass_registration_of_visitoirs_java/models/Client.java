package com.compass_registration_of_visitoirs_java.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="clients")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String full_name;

    private String passport;

    private String phone;

    private String room;

    private LocalDateTime arrival;

    private LocalDateTime departure;
}