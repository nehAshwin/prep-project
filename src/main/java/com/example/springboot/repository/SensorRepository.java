// Sensor Repository handles Sensor data access through JPA -> SQL Queries
package com.example.springboot.repository;

import com.example.springboot.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SensorRepository
    extends JpaRepository<Sensor, Long> {
        // spring reads and automatically generates SQL Query
        List<Sensor> findByDeviceId(Long deviceId);
    }