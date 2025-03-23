package com.masprogtech.repositories;

import com.masprogtech.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    long countByRole(String Role);



}
