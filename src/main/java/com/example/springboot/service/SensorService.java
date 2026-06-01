// Sensor Service handles sensor business logic for managing sensors and linking them to devices
package com.example.springboot.service;

import com.example.springboot.Device;
import com.example.springboot.Sensor;
import com.example.springboot.repository.DeviceRepository;
import com.example.springboot.repository.SensorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorService {
    private final SensorRepository sensorRepository;
    private final DeviceRepository deviceRepository;

    public SensorService(SensorRepository sensorRepository, DeviceRepository deviceRepository) {
        this.sensorRepository = sensorRepository;
        this.deviceRepository = deviceRepository;
    }

    // attaches newly created sensor to existing device
    public Sensor createSensorForDevice(Long deviceId, Sensor sensor) {
        // check device exists & get device
        Device device = deviceRepository.findById(deviceId).orElse(null);

        if (device == null) {
            return null;
        }

        // set sensor device to deviceId and save
        sensor.setDevice(device);
        return sensorRepository.save(sensor);
    }

    // get all sensors attached to deviceId
    public List<Sensor> getSensorsForDevice(Long deviceId) {
        return sensorRepository.findByDeviceId(deviceId);
    }
}