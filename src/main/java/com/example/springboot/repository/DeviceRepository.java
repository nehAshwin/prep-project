package com.example.springboot.repository;

import com.example.springboot.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository 
    extends JpaRepository<Device, Long> {
        
    }