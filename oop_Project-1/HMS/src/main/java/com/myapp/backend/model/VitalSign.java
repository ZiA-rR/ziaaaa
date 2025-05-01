package com.myapp.backend.model;

import java.time.LocalDateTime;

public class VitalSign {
    private int heartRate;
    private int oxygenLevel;
    private String bloodPressure;
    private double temperature;
    private LocalDateTime timestamp;

    public VitalSign(int heartRate, int oxygenLevel, String bloodPressure, double temperature) {
        this.heartRate = heartRate;
        this.oxygenLevel = oxygenLevel;
        this.bloodPressure = bloodPressure;
        this.temperature = temperature;
        this.timestamp = LocalDateTime.now();
    }

    public int getHeartRate() {
        return heartRate;
    }

    public int getOxygenLevel() {
        return oxygenLevel;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public double getTemperature() {
        return temperature;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    @Override
    public String toString() {
        return "Time: " + timestamp + 
               " | Heart Rate: " + heartRate + " bpm" +
               " | Oxygen: " + oxygenLevel + "%" +
               " | BP: " + bloodPressure +
               " | Temp: " + temperature + "°F";
    }
}