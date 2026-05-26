/*
Controller to get List<Device>
*/
package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

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
    // similarity search for /devices/[something]
    @GetMapping("/devices/{id}")
                            // | take URL value and inject as method parameter
    public Device getDeviceById(@PathVariable Long id) {
        List<Device> devices = List.of(
            new Device(1L, "Router-A", "router"),
            new Device(2L, "Sensor-B", "sensor"),
            new Device(3L, "Camera-C", "camera")
        );

        for (Device device : devices) {
                            // | note: Long is an object and needs .equals()
            if (device.getId().equals(id)) {
                return device;
            }
        }

        // error handling
        return null;
    }
}