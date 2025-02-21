package com.iot.Sensor.Data.Projects.controller;


import com.iot.Sensor.Data.Projects.entity.SensorData;
import com.iot.Sensor.Data.Projects.repository.SensorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensor-data")
public class SensorDataController {

    private final SensorDataRepository repository;

    @Autowired
    public SensorDataController(SensorDataRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<SensorData> getAllData() {
        return repository.findAll();
    }
}
