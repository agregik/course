package ru.example.dormitorysystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.example.dormitorysystem.model.Room;
import ru.example.dormitorysystem.service.RoomService;

import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public String list(@RequestParam(value = "floor", required = false) Integer floor, Model model) {
        try {
            List<Room> rooms;
            if (floor != null) {
                rooms = roomService.findByFloor(floor);
                System.out.println("Фильтр по этажу " + floor + ": найдено " + rooms.size() + " комнат");
            } else {
                rooms = roomService.findAll();
                System.out.println("Загружено всех комнат: " + rooms.size());
            }

            // Получаем список всех этажей для фильтра
            List<Integer> floors = roomService.getAllFloors();

            model.addAttribute("rooms", rooms);
            model.addAttribute("floors", floors);
            model.addAttribute("selectedFloor", floor);

            return "rooms";
        } catch (Exception e) {
            System.err.println("Ошибка при загрузке комнат: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Ошибка загрузки комнат: " + e.getMessage());
            model.addAttribute("rooms", List.of());
            model.addAttribute("floors", List.of());
            return "rooms";
        }
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        try {
            Room room = new Room();
            room.setFloor(1);
            room.setCapacity(1);
            model.addAttribute("room", room);
            return "room_form";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Ошибка создания формы: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping
    public String save(@ModelAttribute Room room, BindingResult result, Model model) {
        try {
            // Проверяем и устанавливаем значения по умолчанию
            if (room.getFloor() == null) {
                room.setFloor(1);
            }
            if (room.getCapacity() == null) {
                room.setCapacity(1);
            }

            if (result.hasErrors()) {
                return "room_form";
            }
            roomService.save(room);
            return "redirect:/rooms";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Ошибка сохранения: " + e.getMessage());
            model.addAttribute("room", room);
            return "room_form";
        }
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        try {
            Room room = roomService.findById(id);
            if (room == null) {
                model.addAttribute("error", "Комната не найдена");
                return "error";
            }
            // Проверяем и устанавливаем значения по умолчанию
            if (room.getFloor() == null) {
                room.setFloor(1);
            }
            if (room.getCapacity() == null) {
                room.setCapacity(1);
            }
            model.addAttribute("room", room);
            return "room_form";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Ошибка загрузки комнаты: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        try {
            roomService.delete(id);
            return "redirect:/rooms";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Ошибка удаления: " + e.getMessage());
            return "error";
        }
    }
}