package com.example.springboot.service;

import com.example.springboot.Device;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {
    private final List<Device> devices = List.of(
        new Device(1L, "Router-A", "router"),
        new Device(2L, "Sensor-B", "sensor"),
        new Device(3L, "Camera-C", "camera")
    );

    public List<Device> getAllDevices() {
        return devices;
    }

    public Device getDeviceById(Long id) {
        for (Device device : devices) {
            if (device.getId().equals(id)) {
                return device;
            }
        }

        return null;
    }
}