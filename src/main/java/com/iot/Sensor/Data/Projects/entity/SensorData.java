package com.iot.Sensor.Data.Projects.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sensor_data")
public class SensorData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double temperature;
    private double humidity;
    private double vibration;

    // Default constructor (required by JPA)
    public SensorData() {}

    // Constructor with fields
    public SensorData(double temperature, double humidity, double vibration) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.vibration = vibration;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getVibration() {
        return vibration;
    }

    public void setVibration(double vibration) {
        this.vibration = vibration;
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "id=" + id +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", vibration=" + vibration +
                '}';
    }
}
