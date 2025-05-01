package com.myapp.backend.services;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class NotificationService {
    private final String username = "your-email@gmail.com"; // Replace with your email
    private final String password = "your-app-password";    // Replace with your app password
    private final Properties prop;

    public NotificationService() {
        prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
    }

    public void sendEmailAlert(String recipient, String subject, String message) {
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(username));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);

            Transport.send(mimeMessage);
            System.out.println("Email alert sent successfully to: " + recipient);
        } catch (MessagingException e) {
            System.out.println("Failed to send email alert: " + e.getMessage());
        }
    }

    public void sendSMSAlert(String phoneNumber, String message) {
        // SMS functionality would be implemented here
        // For now, we'll just log the message
        System.out.println("SMS would be sent to " + phoneNumber + ": " + message);
    }
    
    public void sendAppointmentReminder(String recipient, String appointmentDetails) {
        String subject = "Appointment Reminder";
        String message = "You have an upcoming appointment: " + appointmentDetails;
        sendEmailAlert(recipient, subject, message);
    }

    public void sendMedicationReminder(String recipient, String medicationDetails) {
        String subject = "Medication Reminder";
        String message = "It's time to take your medication: " + medicationDetails;
        sendEmailAlert(recipient, subject, message);
    }
}