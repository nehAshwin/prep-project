package com.example.springboot;

import jakarta.persistence.*;

@Entity
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // remember: generates uids for you
    private Long id;

    private String name;

    private String type;

    @ManyToOne  // many sensors can belong to one device
    @JoinColumn(name = "device_id")     // create a foreign key column named device_id in the sensor table
    private Device device;

    public Sensor() {}

    public Sensor(String name, String type, Device device) {
        this.name = name;
        this.type = type;
        this.device = device;
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public Device getDevice() {
        return device;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setDevice(Device device) {
        this.device = device;
    }
}