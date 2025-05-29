package ru.example.dormitorysystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.example.dormitorysystem.dto.StatisticsDto;
import ru.example.dormitorysystem.service.StatisticsService;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping
    public String statistics(Model model) {
        try {
            StatisticsDto statistics = statisticsService.getStatistics();
            model.addAttribute("statistics", statistics);
            return "statistics";
        } catch (Exception e) {
            System.err.println("Ошибка при загрузке статистики: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Ошибка загрузки статистики: " + e.getMessage());
            return "error";
        }
    }
}