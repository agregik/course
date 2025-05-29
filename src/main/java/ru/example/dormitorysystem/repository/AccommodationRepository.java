package ru.example.dormitorysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.example.dormitorysystem.model.Accommodation;

import java.util.List;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    // Поиск размещения по ID студента
    Accommodation findByStudentId(Long studentId);

    // Удаление всех размещений студента
    @Modifying
    @Transactional
    @Query("DELETE FROM Accommodation a WHERE a.student.id = :studentId")
    void deleteByStudentId(@Param("studentId") Long studentId);

    // Получение всех размещений студента
    List<Accommodation> findAllByStudentId(Long studentId);
}
