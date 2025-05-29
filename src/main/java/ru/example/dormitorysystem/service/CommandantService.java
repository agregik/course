package ru.example.dormitorysystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.example.dormitorysystem.model.Commandant;
import ru.example.dormitorysystem.repository.CommandantRepository;

@Service
public class CommandantService {

    @Autowired
    private CommandantRepository commandantRepository;

    public Commandant save(Commandant commandant) {
        return commandantRepository.save(commandant);
    }

    public Commandant findByUserId(Long userId) {
        return commandantRepository.findByUserId(userId);
    }
}
