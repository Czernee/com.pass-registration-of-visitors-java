package com.compass_registration_of_visitoirs_java.repository;

import com.compass_registration_of_visitoirs_java.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
