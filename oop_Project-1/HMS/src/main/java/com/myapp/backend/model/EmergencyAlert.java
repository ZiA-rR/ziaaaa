package com.myapp.backend.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class EmergencyAlert {
    private String patientId;
    private String patientName;
    private String alertMessage;
    private LocalDateTime timestamp;
    private ArrayList<String> criticalIssues;
    private boolean isResolved;
    
    public EmergencyAlert(String patientId, String patientName, String alertMessage, 
                        LocalDateTime timestamp, ArrayList<String> criticalIssues) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.alertMessage = alertMessage;
        this.timestamp = timestamp;
        this.criticalIssues = criticalIssues;
        this.isResolved = false;
    }
    
    public String getPatientId() {
        return patientId;
    }
    
    public String getPatientName() {
        return patientName;
    }
    
    public String getAlertMessage() {
        return alertMessage;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public ArrayList<String> getCriticalIssues() {
        return criticalIssues;
    }
    
    public boolean isResolved() {
        return isResolved;
    }
    
    public void setResolved(boolean resolved) {
        this.isResolved = resolved;
    }
    
    @Override
    public String toString() {
        return "Emergency Alert - Patient: " + patientName + 
               " (" + patientId + ") - Time: " + timestamp + 
               " - Status: " + (isResolved ? "Resolved" : "Active");
    }
}