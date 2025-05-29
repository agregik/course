package ru.example.dormitorysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.example.dormitorysystem.model.Room;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    // Поиск комнат по этажу
    List<Room> findByFloor(Integer floor);

    // Получение всех уникальных этажей
    @Query("SELECT DISTINCT r.floor FROM Room r ORDER BY r.floor")
    List<Integer> findDistinctFloors();
}