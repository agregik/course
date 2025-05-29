package ru.example.dormitorysystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.example.dormitorysystem.model.Accommodation;
import ru.example.dormitorysystem.service.AccommodationService;
import ru.example.dormitorysystem.service.RoomService;
import ru.example.dormitorysystem.service.StudentService;

@Controller
@RequestMapping("/accommodations")
public class AccommodationController {
    @Autowired
    private AccommodationService accommodationService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private RoomService roomService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("accommodations", accommodationService.findAll());
        return "accommodations";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("accommodation", new Accommodation());
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("rooms", roomService.findAll());
        return "accommodation_form";
    }

    @PostMapping
    public String save(@ModelAttribute Accommodation accommodation) {
        accommodationService.save(accommodation);
        return "redirect:/accommodations";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Accommodation accommodation = accommodationService.findById(id);
        if (accommodation == null) return "error";
        model.addAttribute("accommodation", accommodation);
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("rooms", roomService.findAll());
        return "accommodation_form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        accommodationService.delete(id);
        return "redirect:/accommodations";
    }
}
