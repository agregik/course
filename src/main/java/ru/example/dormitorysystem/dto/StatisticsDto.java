package ru.example.dormitorysystem.dto;

public class StatisticsDto {
    private long totalStudents;
    private long accommodatedStudents;
    private long unaccommodatedStudents;
    private long totalRooms;
    private long occupiedRooms;
    private long freeRooms;
    private long totalCapacity;
    private long occupiedCapacity;
    private long freeCapacity;
    private double occupancyRate;

    // Конструкторы
    public StatisticsDto() {}

    public StatisticsDto(long totalStudents, long accommodatedStudents,
                        long totalRooms, long occupiedRooms,
                        long totalCapacity, long occupiedCapacity) {
        this.totalStudents = totalStudents;
        this.accommodatedStudents = accommodatedStudents;
        this.unaccommodatedStudents = totalStudents - accommodatedStudents;
        this.totalRooms = totalRooms;
        this.occupiedRooms = occupiedRooms;
        this.freeRooms = totalRooms - occupiedRooms;
        this.totalCapacity = totalCapacity;
        this.occupiedCapacity = occupiedCapacity;
        this.freeCapacity = totalCapacity - occupiedCapacity;
        this.occupancyRate = totalCapacity > 0 ? (double) occupiedCapacity / totalCapacity * 100 : 0;
    }

    // Геттеры и сеттеры
    public long getTotalStudents() { return totalStudents; }
    public void setTotalStudents(long totalStudents) { this.totalStudents = totalStudents; }

    public long getAccommodatedStudents() { return accommodatedStudents; }
    public void setAccommodatedStudents(long accommodatedStudents) { this.accommodatedStudents = accommodatedStudents; }

    public long getUnaccommodatedStudents() { return unaccommodatedStudents; }
    public void setUnaccommodatedStudents(long unaccommodatedStudents) { this.unaccommodatedStudents = unaccommodatedStudents; }

    public long getTotalRooms() { return totalRooms; }
    public void setTotalRooms(long totalRooms) { this.totalRooms = totalRooms; }

    public long getOccupiedRooms() { return occupiedRooms; }
    public void setOccupiedRooms(long occupiedRooms) { this.occupiedRooms = occupiedRooms; }

    public long getFreeRooms() { return freeRooms; }
    public void setFreeRooms(long freeRooms) { this.freeRooms = freeRooms; }

    public long getTotalCapacity() { return totalCapacity; }
    public void setTotalCapacity(long totalCapacity) { this.totalCapacity = totalCapacity; }

    public long getOccupiedCapacity() { return occupiedCapacity; }
    public void setOccupiedCapacity(long occupiedCapacity) { this.occupiedCapacity = occupiedCapacity; }

    public long getFreeCapacity() { return freeCapacity; }
    public void setFreeCapacity(long freeCapacity) { this.freeCapacity = freeCapacity; }

    public double getOccupancyRate() { return occupancyRate; }
    public void setOccupancyRate(double occupancyRate) { this.occupancyRate = occupancyRate; }
}