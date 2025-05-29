package ru.example.dormitorysystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.dormitorysystem.model.Student;
import ru.example.dormitorysystem.model.User;
import ru.example.dormitorysystem.repository.StudentRepository;
import ru.example.dormitorysystem.repository.UserRepository;

import java.util.List;

@Service
@Transactional
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccommodationService accommodationService;

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student findById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student save(Student student) {
        // Очищаем данные от запятых перед сохранением
        cleanStudentData(student);
        return studentRepository.save(student);
    }

    @Transactional
    public void delete(Long id) {
        try {
            if (!studentRepository.existsById(id)) {
                throw new RuntimeException("Студент с ID " + id + " не найден");
            }

            System.out.println("Начинаем удаление студента с ID: " + id);

            // Сначала удаляем все связанные записи размещения
            accommodationService.deleteByStudentId(id);
            System.out.println("Удалены записи размещения для студента с ID: " + id);

            // Затем удаляем самого студента
            studentRepository.deleteById(id);
            System.out.println("Студент с ID " + id + " успешно удален");

        } catch (Exception e) {
            System.err.println("Ошибка при удалении студента с ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Ошибка при удалении студента: " + e.getMessage());
        }
    }

    // Новые методы для фильтрации и поиска
    public List<Student> findByFaculty(String faculty) {
        if (faculty == null || faculty.trim().isEmpty()) {
            return findAll();
        }
        return studentRepository.findByFaculty(faculty);
    }

    public List<Student> findByNameContaining(String search) {
        if (search == null || search.trim().isEmpty()) {
            return findAll();
        }
        return studentRepository.findByNameContaining(search.trim());
    }

    public List<Student> findByFacultyAndNameContaining(String faculty, String search) {
        if ((faculty == null || faculty.trim().isEmpty()) &&
                (search == null || search.trim().isEmpty())) {
            return findAll();
        }
        if (faculty == null || faculty.trim().isEmpty()) {
            return findByNameContaining(search);
        }
        if (search == null || search.trim().isEmpty()) {
            return findByFaculty(faculty);
        }
        return studentRepository.findByFacultyAndNameContaining(faculty, search.trim());
    }

    public List<String> getAllFaculties() {
        return studentRepository.findDistinctFaculties();
    }

    // Добавляем метод для поиска студента по username
    public Student findByUsername(String username) {
        return studentRepository.findByUser_Username(username);
    }

    // Метод для связывания студента с пользователем
    public void linkStudentToUser(Long studentId, String username) {
        Student student = findById(studentId);
        User user = userRepository.findByUsername(username);

        if (student != null && user != null) {
            student.setUser(user);
            save(student);
            System.out.println("Студент " + student.getFullName() + " связан с пользователем " + username);
        }
    }

    // Автоматическое связывание по имени
    public boolean autoLinkStudentByName(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) return false;

        // Ищем студента по похожему имени
        List<Student> allStudents = findAll();
        for (Student student : allStudents) {
            if (student.getUser() == null) { // только если еще не связан
                String fullName = student.getFullName().toLowerCase();
                if (fullName.contains(username.toLowerCase()) ||
                        username.toLowerCase().contains(student.getFirstName().toLowerCase())) {
                    student.setUser(user);
                    save(student);
                    System.out.println("Автоматически связан студент " + student.getFullName() + " с пользователем " + username);
                    return true;
                }
            }
        }
        return false;
    }

    private void cleanStudentData(Student student) {
        if (student.getFirstName() != null) {
            student.setFirstName(student.getFirstName().replaceAll(",$", "").trim());
        }
        if (student.getLastName() != null) {
            student.setLastName(student.getLastName().replaceAll(",$", "").trim());
        }
        if (student.getPhone() != null) {
            student.setPhone(student.getPhone().replaceAll(",$", "").trim());
        }
        if (student.getFaculty() != null) {
            student.setFaculty(student.getFaculty().replaceAll(",$", "").trim());
        }
        if (student.getSnils() != null) {
            student.setSnils(student.getSnils().replaceAll(",$", "").trim());
        }
        if (student.getPassport() != null) {
            student.setPassport(student.getPassport().replaceAll(",$", "").trim());
        }
    }
}
