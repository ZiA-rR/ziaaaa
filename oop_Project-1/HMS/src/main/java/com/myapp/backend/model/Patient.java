package com.myapp.backend.model;

import java.util.ArrayList;
import java.time.LocalDateTime;

public class Patient extends User {
    private ArrayList<VitalSign> vitals;
    private ArrayList<Appointment> appointments;
    private ArrayList<Feedback> feedbackList;
    private String emergencyContact;
    private Doctor assignedDoctor;

    public Patient(String name, String id, String email, String password) {
        super(name, id, email, password);
        vitals = new ArrayList<>();
        appointments = new ArrayList<>();
        feedbackList = new ArrayList<>();
    }

    public void uploadVital(VitalSign vitalSign) {
        vitals.add(vitalSign);
    }

    @Override
    public void displayUserInfo() {
        System.out.printf("Patient Name: %s\nPatient ID: %s\nPatient Email: %s\n", 
                this.getName(), this.getId(), this.getEmail());
    }

    public void viewFeedback() {
        if (feedbackList.isEmpty()) {
            System.out.println("No feedback available");
        } else {
            for (Feedback feedback : feedbackList) {
                System.out.println("Doctor: " + feedback.getDoctorName());
                System.out.println("Feedback: " + feedback.getComment());
                System.out.println("Medication: " + feedback.getMedicationPrescribed());
                System.out.println("Date: " + feedback.getTimestamp());
                System.out.println();
            }
        }
    }

    public void scheduleAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public ArrayList<VitalSign> getVitals() {
        return vitals;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public ArrayList<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public void addFeedback(Feedback feedback) {
        feedbackList.add(feedback);
    }
    
    public String getEmergencyContact() {
        return emergencyContact;
    }
    
    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }
    
    public Doctor getAssignedDoctor() {
        return assignedDoctor;
    }
    
    public void setAssignedDoctor(Doctor doctor) {
        this.assignedDoctor = doctor;
    }
    
    public void requestAppointment(String appointmentId, Doctor doctor, LocalDateTime appointmentDate) {
        Appointment appointment = new Appointment(appointmentId, this.getId(), doctor.getId(), appointmentDate);
        this.appointments.add(appointment);
        doctor.getAppointments().add(appointment);
        System.out.println("Appointment request submitted successfully.");
    }
}