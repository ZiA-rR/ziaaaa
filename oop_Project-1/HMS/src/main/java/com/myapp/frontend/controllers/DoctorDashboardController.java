package com.myapp.frontend.controllers;

import com.myapp.backend.model.*;
import com.myapp.backend.services.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DoctorDashboardController implements Initializable {

    private Doctor currentDoctor;
    private ChatService chatService;
    private VideoCallService videoCallService;
    private EmergencyAlertService emergencyAlertService;

    @FXML private Label lblDoctorName;
    @FXML private TableView<Patient> tblPatients;
    @FXML private TableColumn<Patient, String> colPatientId;
    @FXML private TableColumn<Patient, String> colPatientName;
    @FXML private TableColumn<Patient, String> colPatientEmail;
    
    @FXML private TableView<Appointment> tblAppointments;
    @FXML private TableColumn<Appointment, String> colAppointmentId;
    @FXML private TableColumn<Appointment, String> colAppointmentPatient;
    @FXML private TableColumn<Appointment, LocalDateTime> colAppointmentDate;
    @FXML private TableColumn<Appointment, Boolean> colAppointmentStatus;
    
    @FXML private TableView<EmergencyAlert> tblEmergencyAlerts;
    @FXML private TableColumn<EmergencyAlert, String> colAlertPatient;
    @FXML private TableColumn<EmergencyAlert, LocalDateTime> colAlertTime;
    @FXML private TableColumn<EmergencyAlert, Boolean> colAlertStatus;
    
    @FXML private TextArea txtPatientVitals;
    @FXML private TextArea txtFeedbackComments;
    @FXML private TextField txtMedication;

    private ObservableList<Patient> patientsList = FXCollections.observableArrayList();
    private ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();
    private ObservableList<EmergencyAlert> emergencyAlertsList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize table columns
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPatientName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPatientEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        colAppointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        colAppointmentPatient.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colAppointmentDate.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        colAppointmentStatus.setCellValueFactory(new PropertyValueFactory<>("approved"));
        
        colAlertPatient.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        colAlertTime.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        colAlertStatus.setCellValueFactory(new PropertyValueFactory<>("resolved"));
        
        // Add listeners for selection changes
        tblPatients.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> showPatientDetails(newSelection));
            
        tblEmergencyAlerts.getSelectionModel().selectedItem