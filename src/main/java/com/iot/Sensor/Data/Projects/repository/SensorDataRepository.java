package com.iot.Sensor.Data.Projects.repository;

import com.iot.Sensor.Data.Projects.entity.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorDataRepository extends JpaRepository<SensorData, Long> {
}
