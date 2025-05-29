package ru.example.dormitorysystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.example.dormitorysystem.model.User;
import ru.example.dormitorysystem.model.Student;
import ru.example.dormitorysystem.model.Commandant;
import ru.example.dormitorysystem.service.UserService;
import ru.example.dormitorysystem.service.StudentService;
import ru.example.dormitorysystem.service.CommandantService;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CommandantService commandantService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("student", new Student());
        model.addAttribute("commandant", new Commandant());
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                          @RequestParam String password,
                          @RequestParam String role,
                          @RequestParam(required = false) String firstName,
                          @RequestParam(required = false) String lastName,
                          @RequestParam(required = false) String phone,
                          @RequestParam(required = false) String snils,
                          @RequestParam(required = false) String passport,
                          @RequestParam(required = false) String faculty,
                          Model model) {
        try {
            // Проверяем, что пользователь с таким именем не существует
            if (userService.findByUsername(username) != null) {
                model.addAttribute("error", "Пользователь с таким именем уже существует");
                return "register";
            }

            // Дополнительная защита: проверяем, что роль не ADMIN
            if ("ADMIN".equals(role)) {
                model.addAttribute("error", "Регистрация администраторов запрещена");
                return "register";
            }

            // Создаем пользователя
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(role);

            User savedUser = userService.save(user);

            // В зависимости от роли создаем соответствующую запись
            if ("STUDENT".equals(role)) {
                Student student = new Student();
                student.setFirstName(firstName);
                student.setLastName(lastName);
                student.setPhone(phone);
                student.setSnils(snils);
                student.setPassport(passport);
                student.setFaculty(faculty);
                student.setUser(savedUser);
                studentService.save(student);
            } else if ("COMMANDANT".equals(role)) {
                Commandant commandant = new Commandant();
                commandant.setFirstName(firstName);
                commandant.setLastName(lastName);
                commandant.setPhone(phone);
                commandant.setUser(savedUser);
                commandantService.save(commandant);
            }

            return "redirect:/login?registered";
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка регистрации: " + e.getMessage());
            return "register";
        }
    }
}
