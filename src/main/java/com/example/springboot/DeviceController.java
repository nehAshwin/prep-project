/*
Controller to get List<Device>
*/
package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeviceController {

    @GetMapping("/devices")
    public List<Device> getDevices() {
        
        // Java objects -> Jackson -> JSON response body
        return List.of(
            new Device(1L, "Router-A", "router"),
            new Device(2L, "Sensor-B", "sensor"),
            new Device(3L, "Camera-C", "camera")
        );
    }
}