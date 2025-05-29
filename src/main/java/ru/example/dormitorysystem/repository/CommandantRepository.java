package ru.example.dormitorysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.dormitorysystem.model.Commandant;

@Repository
public interface CommandantRepository extends JpaRepository<Commandant, Long> {
    Commandant findByUserId(Long userId);
}
