package com.myapp.backend.services;

import com.myapp.backend.model.*;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class EmergencyAlertService {
    private final ArrayList<Doctor> doctors;
    private final NotificationService notificationService;
    
    // Critical thresholds for vital signs
    private static final int MIN_HEART_RATE = 60;
    private static final int MAX_HEART_RATE = 100;
    private static final int MIN_OXYGEN_LEVEL = 90;
    private static final double MIN_TEMPERATURE = 97.0;
    private static final double MAX_TEMPERATURE = 99.5;
    private static final int MIN_SYSTOLIC = 90;
    private static final int MAX_SYSTOLIC = 140;
    private static final int MIN_DIASTOLIC = 60;
    private static final int MAX_DIASTOLIC = 90;

    public EmergencyAlertService(ArrayList<Doctor> doctors) {
        this.doctors = doctors;
        this.notificationService = new NotificationService();
    }

    public boolean checkVitals(VitalSign vital, Patient patient) {
        ArrayList<String> criticalMessages = new ArrayList<>();
        boolean alertTriggered = false;

        if (vital.getHeartRate() < MIN_HEART_RATE || vital.getHeartRate() > MAX_HEART_RATE) {
            criticalMessages.add("Abnormal heart rate: " + vital.getHeartRate() + " bpm");
            alertTriggered = true;
        }
        
        if (vital.getOxygenLevel() < MIN_OXYGEN_LEVEL) {
            criticalMessages.add("Low oxygen level: " + vital.getOxygenLevel() + "%");
            alertTriggered = true;
        }
        
        if (vital.getTemperature() < MIN_TEMPERATURE || vital.getTemperature() > MAX_TEMPERATURE) {
            criticalMessages.add("Abnormal temperature: " + vital.getTemperature() + "°F");
            alertTriggered = true;
        }
        
        // Parse blood pressure (assuming format "120/80")
        try {
            String[] bpValues = vital.getBloodPressure().split("/");
            int systolic = Integer.parseInt(bpValues[0].trim());
            int diastolic = Integer.parseInt(bpValues[1].trim());
            
            if (systolic < MIN_SYSTOLIC || systolic > MAX_SYSTOLIC || 
                diastolic < MIN_DIASTOLIC || diastolic > MAX_DIASTOLIC) {
                criticalMessages.add("Abnormal blood pressure: " + vital.getBloodPressure());
                alertTriggered = true;
            }
        } catch (Exception e) {
            System.out.println("Unable to parse blood pressure value: " + vital.getBloodPressure());
        }

        if (alertTriggered) {
            triggerEmergencyAlert(patient, criticalMessages);
        }
        
        return alertTriggered;
    }
    
    public void triggerEmergencyAlert(Patient patient, ArrayList<String> criticalMessages) {
        // Create alert message
        StringBuilder alertMessage = new StringBuilder();
        alertMessage.append("EMERGENCY ALERT for patient ").append(patient.getName())
                   .append(" (ID: ").append(patient.getId()).append(")\n");
        
        for (String message : criticalMessages) {
            alertMessage.append("- ").append(message).append("\n");
        }
        
        alertMessage.append("\nTimestamp: ").append(LocalDateTime.now());
        
        // Create emergency alert object
        EmergencyAlert alert = new EmergencyAlert(
            patient.getId(),
            patient.getName(),
            alertMessage.toString(),
            LocalDateTime.now(),
            criticalMessages
        );
        
        // Send alert to assigned doctor
        if (patient.getAssignedDoctor() != null) {
            Doctor assignedDoctor = patient.getAssignedDoctor();
            assignedDoctor.receiveEmergencyAlert(alert);
            notificationService.sendEmailAlert(assignedDoctor.getEmail(), 
                "EMERGENCY: Patient " + patient.getName(), 
                alertMessage.toString());
        } else {
            // If no assigned doctor, alert all doctors
            for (Doctor doctor : doctors) {
                doctor.receiveEmergencyAlert(alert);
                notificationService.sendEmailAlert(doctor.getEmail(), 
                    "EMERGENCY: Patient " + patient.getName(), 
                    alertMessage.toString());
            }
        }
        
        // Also notify patient's emergency contact
        if (patient.getEmergencyContact() != null && !patient.getEmergencyContact().isEmpty()) {
            notificationService.sendEmailAlert(
                patient.getEmergencyContact(),
                "MEDICAL EMERGENCY: " + patient.getName(),
                "The Remote Patient Monitoring System has detected critical vital signs for " +
                patient.getName() + ". Medical attention may be required. Please contact their healthcare provider immediately."
            );
        }
    }
    
    public void triggerManualEmergency(Patient patient, String message) {
        ArrayList<String> criticalMessages = new ArrayList<>();
        criticalMessages.add("MANUAL EMERGENCY: " + message);
        triggerEmergencyAlert(patient, criticalMessages);
    }
}