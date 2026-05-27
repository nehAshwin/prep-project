/*
Controller to get List<Device>
*/
package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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

    @PostMapping("/devices")
                            // | deserialization JSON from HTTP Request Body -> Java object
    public Device createDevice(@RequestBody Device device) {
        return deviceService.createDevice(device);
    }

    @PutMapping("/devices/{id}")
    public Device updateDevice(
        @PathVariable Long id, 
        @RequestBody Device device
    ) {
        return deviceService.updateDevice(id, device);
    }

    @DeleteMapping("/devices/{id}")
    public String deleteDevice(@PathVariable Long id) {
        boolean deleted = deviceService.deleteDevice(id);
        if deleted {
            return "Device deleted successfully."
        }
        return "Error: Could not delete device."
    }
}