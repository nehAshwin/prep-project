/*
Controller to get List<Device>
*/
package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.springboot.service.DeviceService;

import java.util.List;

@RestController
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/devices")
    public List<Device> getDevices() {
        // Java objects -> Jackson -> JSON response body
        return deviceService.getAllDevices();
    }

    // similarity search for /devices/[something]
    @GetMapping("/devices/{id}")
                            // | take URL value and inject as method parameter
    public Device getDeviceById(@PathVariable Long id) {
        return deviceService.getDeviceById(id);
    }
}