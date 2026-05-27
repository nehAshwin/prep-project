/*
Internet of Things (IoT) Device
*/

package com.example.springboot;

public class Device {
    // private fields; access via getters (encapsulation)
    private Long id;
    private String name;
    private String type;

    public Device() {}

    public Device(Long id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    // Jackson uses public getters to serialize fields to JSON
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    // add setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }
}