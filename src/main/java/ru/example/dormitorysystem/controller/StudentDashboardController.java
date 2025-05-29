package ru.example.dormitorysystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.example.dormitorysystem.model.Student;
import ru.example.dormitorysystem.model.Accommodation;
import ru.example.dormitorysystem.model.Room;
import ru.example.dormitorysystem.service.StudentService;
import ru.example.dormitorysystem.service.AccommodationService;
import ru.example.dormitorysystem.service.UserService;

@Controller
@RequestMapping("/student")
public class StudentDashboardController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private AccommodationService accommodationService;

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String studentDashboard(Authentication authentication, Model model) {
        try {
            String username = authentication.getName();
            System.out.println("Ищем студента с username: " + username);

            // Проверяем, существует ли пользователь
            var user = userService.findByUsername(username);
            if (user == null) {
                System.out.println("Пользователь не найден: " + username);
                model.addAttribute("error", "Пользователь не найден в системе");
                return "error";
            }
            System.out.println("Найден пользователь: " + user.getUsername() + ", роль: " + user.getRole());

            // Получаем информацию о студенте
            Student student = studentService.findByUsername(username);

            // Если студент не найден, пытаемся автоматически связать
            if (student == null) {
                System.out.println("Студент не найден для username: " + username);
                System.out.println("Пытаемся автоматически связать студента...");

                boolean linked = studentService.autoLinkStudentByName(username);
                if (linked) {
                    student = studentService.findByUsername(username);
                    System.out.println("Успешно связан студент: " + student.getFullName());
                }
            }

            if (student == null) {
                System.out.println("Студент все еще не найден для username: " + username);

                // Создаем временного студента для демонстрации
                student = new Student();
                student.setFirstName("Неизвестный");
                student.setLastName("Студент");
                student.setSnils("Не указано");
                student.setPassport("Не указано");
                student.setPhone("Не указано");
                student.setFaculty("Не указано");

                model.addAttribute("student", student);
                model.addAttribute("hasAccommodation", false);
                model.addAttribute("warning", "Ваша учетная запись не связана с профилем студента. Обратитесь к администратору для связывания аккаунта.");

                return "student/dashboard";
            }

            System.out.println("Найден студент: " + student.getFullName());

            // Очищаем запятые из данных студента
            cleanStudentData(student);

            model.addAttribute("student", student);

            // Получаем информацию о размещении студента
            try {
                Accommodation accommodation = accommodationService.findByStudentId(student.getId());
                if (accommodation != null) {
                    Room room = accommodation.getRoom();
                    model.addAttribute("accommodation", accommodation);
                    model.addAttribute("room", room);
                    model.addAttribute("hasAccommodation", true);
                    System.out.println("Студент размещен в комнате: " + room.getNumber());
                } else {
                    model.addAttribute("hasAccommodation", false);
                    System.out.println("Студент не размещен");
                }
            } catch (Exception e) {
                System.err.println("Ошибка при получении размещения: " + e.getMessage());
                model.addAttribute("hasAccommodation", false);
            }

            return "student/dashboard";
        } catch (Exception e) {
            System.err.println("Ошибка в studentDashboard: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Системная ошибка: " + e.getMessage());
            return "error";
        }
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
