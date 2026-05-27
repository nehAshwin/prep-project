package com.example.springboot.service;

import com.example.springboot.Device;
import com.example.springboot.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    // NEW: device repository that gives us access to the database
    private final DeviceRepository deviceRepository;

    // new DeviceService constructor is to connect to deviceRepository
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    // built in commands for SQL queries like findAll()
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public Device getDeviceById(Long id) {
        return deviceRepository.findById(id)
            .orElse(null);
    }

    public Device createDevice(Device device) {
        return deviceRepository.save(device);
    }

    // if device with id exists, set and save information with updatedDevice information
    public Device updateDevice(Long id, Device updatedDevice) {
        Device existingDevice = deviceRepository.findById(id)
            .orElse(null);

        if (existingDevice == null) {
            return null;
        }

        existingDevice.setName(updatedDevice.getName());
        existingDevice.setType(updatedDevice.getType());

        return deviceRepository.save(existingDevice);
    }


    public boolean deleteDevice(Long id) {
        if (!deviceRepository.existsById(id)) {
            return false;
        }
        deviceRepository.deleteById(id);

        return true;
    }
}