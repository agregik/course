package ru.example.dormitorysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.example.dormitorysystem.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

     // Поиск по факультету
     List<Student> findByFaculty(String faculty);

     // Поиск по имени или фамилии (регистронезависимый)
     @Query("SELECT s FROM Student s WHERE " +
             "LOWER(s.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
             "LOWER(s.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
             "LOWER(CONCAT(s.firstName, ' ', s.lastName)) LIKE LOWER(CONCAT('%', :search, '%'))")
     List<Student> findByNameContaining(@Param("search") String search);

     // Поиск по факультету и имени/фамилии
     @Query("SELECT s FROM Student s WHERE s.faculty = :faculty AND " +
             "(LOWER(s.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
             "LOWER(s.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
             "LOWER(CONCAT(s.firstName, ' ', s.lastName)) LIKE LOWER(CONCAT('%', :search, '%')))")
     List<Student> findByFacultyAndNameContaining(@Param("faculty") String faculty, @Param("search") String search);

     // Получение всех уникальных факультетов
     @Query("SELECT DISTINCT s.faculty FROM Student s WHERE s.faculty IS NOT NULL ORDER BY s.faculty")
     List<String> findDistinctFaculties();

     // Поиск студента по username пользователя
     Student findByUser_Username(String username);
}
