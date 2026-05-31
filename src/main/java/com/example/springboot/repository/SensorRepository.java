package com.example.springboot.repository;

import com.example.springboot.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository
    extends JpaRepository<Sensor, Long> {
        
    }