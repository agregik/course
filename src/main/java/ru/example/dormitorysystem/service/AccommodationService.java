package ru.example.dormitorysystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.dormitorysystem.model.Accommodation;
import ru.example.dormitorysystem.repository.AccommodationRepository;

import java.util.List;

@Service
@Transactional
public class AccommodationService {
    @Autowired
    private AccommodationRepository accommodationRepository;

    public List<Accommodation> findAll() {
        return accommodationRepository.findAll();
    }

    public Accommodation findById(Long id) {
        return accommodationRepository.findById(id).orElse(null);
    }

    public Accommodation save(Accommodation accommodation) {
        return accommodationRepository.save(accommodation);
    }

    public void delete(Long id) {
        accommodationRepository.deleteById(id);
    }

    // Поиск размещения по ID студента
    public Accommodation findByStudentId(Long studentId) {
        return accommodationRepository.findByStudentId(studentId);
    }

    // Получение всех размещений студента
    public List<Accommodation> findAllByStudentId(Long studentId) {
        return accommodationRepository.findAllByStudentId(studentId);
    }

    // Удаление всех размещений студента
    @Transactional
    public void deleteByStudentId(Long studentId) {
        accommodationRepository.deleteByStudentId(studentId);
    }
}
