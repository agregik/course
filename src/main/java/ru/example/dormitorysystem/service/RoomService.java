package ru.example.dormitorysystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.dormitorysystem.model.Room;
import ru.example.dormitorysystem.repository.RoomRepository;

import java.util.List;

@Service
@Transactional
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public Room findById(Long id) {
        return roomRepository.findById(id).orElse(null);
    }

    public Room save(Room room) {
        return roomRepository.save(room);
    }

    public void delete(Long id) {
        try {
            if (roomRepository.existsById(id)) {
                roomRepository.deleteById(id);
            } else {
                throw new RuntimeException("Комната с ID " + id + " не найдена");
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при удалении комнаты: " + e.getMessage());
        }
    }

    // Новые методы для фильтрации
    public List<Room> findByFloor(Integer floor) {
        if (floor == null) {
            return findAll();
        }
        return roomRepository.findByFloor(floor);
    }

    public List<Integer> getAllFloors() {
        return roomRepository.findDistinctFloors();
    }
}