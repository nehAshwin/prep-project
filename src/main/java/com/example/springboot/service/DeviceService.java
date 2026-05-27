package com.example.springboot.service;

import com.example.springboot.Device;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class DeviceService {
    private final List<Device> devices = new ArrayList<>();

    public DeviceService() {
        devices.add(new Device(1L, "Router-A", "router"));
        devices.add(new Device(2L, "Sensor-B", "sensor"));
        devices.add(new Device(3L, "Camera-C", "camera"));
    }

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

    public Device createDevice(Device device) {
        devices.add(device);
        return device;
    }

    public Device updateDevice(Long id, Device updatedDevice) {
        for (int i = 0; i < devices.size(); i++) {
            Device currentDevice = devices.get(i);
            if (currentDevice.getId().equals(id)) {

                devices.set(i, updatedDevice);
                return updatedDevice
            }
        }
        return null;
    }

    public boolean deleteDevice(Long id) {
        for (Device device : devices) {
            if device.getId().equals(id) {
                devices.remove(device);
                return true;
            }
        }
        return false;
    }
}