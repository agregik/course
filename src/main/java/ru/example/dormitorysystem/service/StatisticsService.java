package ru.example.dormitorysystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.example.dormitorysystem.dto.StatisticsDto;
import ru.example.dormitorysystem.model.Room;

import java.util.List;

@Service
public class StatisticsService {

@Autowired
private StudentService studentService;

@Autowired
private RoomService roomService;

@Autowired
private AccommodationService accommodationService;

public StatisticsDto getStatistics() {
    // Получаем общее количество студентов
    long totalStudents = studentService.findAll().size();

    // Получаем количество заселенных студентов (уникальных)
    long accommodatedStudents = accommodationService.findAll().stream()
            .map(accommodation -> accommodation.getStudent().getId())
            .distinct()
            .count();

    // Получаем общее количество комнат
    List<Room> allRooms = roomService.findAll();
    long totalRooms = allRooms.size();

    // Получаем количество занятых комнат (уникальных)
    long occupiedRooms = accommodationService.findAll().stream()
            .map(accommodation -> accommodation.getRoom().getId())
            .distinct()
            .count();

    // Вычисляем общую вместимость всех комнат
    long totalCapacity = allRooms.stream()
            .mapToLong(room -> room.getCapacity() != null ? room.getCapacity() : 0)
            .sum();

    // Вычисляем занятую вместимость (количество активных заселений)
    long occupiedCapacity = accommodationService.findAll().size();

    return new StatisticsDto(
            totalStudents,
            accommodatedStudents,
            totalRooms,
            occupiedRooms,
            totalCapacity,
            occupiedCapacity
    );
}
}