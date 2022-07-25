package com.project.kafkastream.domain.enums;

public enum SignalType {
    CurrentSpeed("currentSpeed", ""),
    Odometer("odometer", ""),
    Uptime("uptime", "Duration in ms."),
    IsCharging("isCharging", "1 for true, 0 for false");

    private String name;
    private String description;

    SignalType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
