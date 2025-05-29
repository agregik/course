package ru.example.dormitorysystem.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.dormitorysystem.model.Room;
import ru.example.dormitorysystem.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomApiController {
    @Autowired
    private RoomService roomService;

    @GetMapping
    public List<Room> getAll() {
        return roomService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getById(@PathVariable Long id) {
        Room room = roomService.findById(id);
        return room != null ? ResponseEntity.ok(room) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Room create(@RequestBody Room room) {
        return roomService.save(room);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> update(@PathVariable Long id, @RequestBody Room room) {
        Room existing = roomService.findById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        room.setId(id);
        return ResponseEntity.ok(roomService.save(room));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Room room = roomService.findById(id);
        if (room == null) return ResponseEntity.notFound().build();
        roomService.delete(id);
        return ResponseEntity.ok().build();
    }
}