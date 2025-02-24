package com.compass_registration_of_visitoirs_java.repository;

import com.compass_registration_of_visitoirs_java.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("SELECT c FROM Client c WHERE c.arrival <= :currentDate AND c.departure >= :currentDate")
    List<Client> findAllStayingClients(@Param("currentDate") Date currentDate);
}
