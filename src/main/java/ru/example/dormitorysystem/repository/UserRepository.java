package ru.example.dormitorysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.dormitorysystem.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}