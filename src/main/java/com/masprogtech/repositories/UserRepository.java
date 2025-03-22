package com.masprogtech.repositories;

import com.masprogtech.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    long countByRole(String Role);
}
