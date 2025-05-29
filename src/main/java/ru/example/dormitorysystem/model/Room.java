package ru.example.dormitorysystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private Integer capacity = 1;

    @Column(nullable = false)
    private Integer floor = 1;

    // Конструкторы
    public Room() {}

    public Room(String number, Integer capacity, Integer floor) {
        this.number = number;
        this.capacity = capacity != null ? capacity : 1;
        this.floor = floor != null ? floor : 1;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity != null ? capacity : 1;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor != null ? floor : 1;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", capacity=" + capacity +
                ", floor=" + floor +
                '}';
    }
}