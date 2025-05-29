package ru.example.dormitorysystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "commandant")
public class Commandant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Конструкторы
    public Commandant() {}

    public Commandant(String firstName, String lastName, String phone, User user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.user = user;
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
