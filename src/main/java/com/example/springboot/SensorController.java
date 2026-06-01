package com.example.springboot;

import com.example.springboot.Sensor;
import com.example.springboot.service.SensorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices/{deviceId}/sensors")
public class SensorController {
    
    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @PostMapping
    public Sensor createSensor(
        @PathVariable Long deviceId,
        @RequestBody Sensor sensor
    ) {
        return sensorService.createSensorForDevice(deviceId, sensor);
    }

    @GetMapping
    public List<Sensor> getSensorsForDevice(@PathVariable Long deviceId) {
        return sensorService.getSensorsForDevice(deviceId);
    }
}