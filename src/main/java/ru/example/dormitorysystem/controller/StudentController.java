package ru.example.dormitorysystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.example.dormitorysystem.model.Student;
import ru.example.dormitorysystem.service.StudentService;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping
    public String list(@RequestParam(value = "faculty", required = false) String faculty,
                       @RequestParam(value = "search", required = false) String search,
                       Model model) {
        try {
            List<Student> students;

            // Применяем фильтры
            if (faculty != null && !faculty.isEmpty() && search != null && !search.isEmpty()) {
                students = studentService.findByFacultyAndNameContaining(faculty, search);
                System.out.println("Фильтр по факультету '" + faculty + "' и поиск '" + search + "': найдено " + students.size() + " студентов");
            } else if (faculty != null && !faculty.isEmpty()) {
                students = studentService.findByFaculty(faculty);
                System.out.println("Фильтр по факультету '" + faculty + "': найдено " + students.size() + " студентов");
            } else if (search != null && !search.isEmpty()) {
                students = studentService.findByNameContaining(search);
                System.out.println("Поиск '" + search + "': найдено " + students.size() + " студентов");
            } else {
                students = studentService.findAll();
                System.out.println("Загружено всех студентов: " + students.size());
            }

            // Получаем список всех факультетов для фильтра
            List<String> faculties = studentService.getAllFaculties();

            model.addAttribute("students", students);
            model.addAttribute("faculties", faculties);
            model.addAttribute("selectedFaculty", faculty);
            model.addAttribute("searchQuery", search);

            return "students";
        } catch (Exception e) {
            System.err.println("Ошибка при загрузке студентов: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Ошибка загрузки студентов: " + e.getMessage());
            model.addAttribute("students", List.of());
            model.addAttribute("faculties", List.of());
            return "students";
        }
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("student", new Student());
        return "student_form";
    }

    @PostMapping
    public String save(@ModelAttribute Student student, RedirectAttributes redirectAttributes) {
        try {
            studentService.save(student);
            redirectAttributes.addFlashAttribute("success", "Студент успешно сохранен");
            return "redirect:/students";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при сохранении студента: " + e.getMessage());
            return "redirect:/students";
        }
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Student student = studentService.findById(id);
        if (student == null) {
            model.addAttribute("error", "Студент не найден");
            return "error";
        }
        model.addAttribute("student", student);
        return "student_form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Student student = studentService.findById(id);
            if (student == null) {
                redirectAttributes.addFlashAttribute("error", "Студент не найден");
                return "redirect:/students";
            }

            String studentName = student.getFullName();
            studentService.delete(id);

            redirectAttributes.addFlashAttribute("success", "Студент " + studentName + " успешно удален");
            System.out.println("Студент " + studentName + " (ID: " + id + ") успешно удален");

            return "redirect:/students";
        } catch (Exception e) {
            System.err.println("Ошибка при удалении студента с ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Ошибка при удалении студента: " + e.getMessage());
            return "redirect:/students";
        }
    }
}
