package com.myapp.backend.services;

import com.myapp.backend.model.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class VideoCallService {
    private Map<String, VideoCallSession> activeCalls;
    private NotificationService notificationService;
    
    public VideoCallService() {
        activeCalls = new HashMap<>();
        notificationService = new NotificationService();
    }
    
    public String initiateVideoCall(User caller, User recipient) {
        // Generate a unique call ID
        String callId = generateCallId(caller.getId(), recipient.getId());
        
        // Create video call link (in a real system, this would link to a video platform)
        String videoLink = "https://meet.google.com/generated-link-" + callId;
        
        // Create a new video call session
        VideoCallSession session = new VideoCallSession(
            callId,
            caller.getId(),
            recipient.getId(),
            videoLink,
            LocalDateTime.now()
        );
        
        // Store the active call
        activeCalls.put(callId, session);
        
        // Notify the recipient
        notificationService.sendEmailAlert(
            recipient.getEmail(),
            "Incoming Video Call from " + caller.getName(),
            "You have a video call invitation from " + caller.getName() + ".\n\n" +
            "Join using this link: " + videoLink
        );
        
        return videoLink;
    }
    
    public VideoCallSession getActiveCall(String callId) {
        return activeCalls.get(callId);
    }
    
    public void endCall(String callId) {
        if (activeCalls.containsKey(callId)) {
            VideoCallSession session = activeCalls.get(callId);
            session.setEndTime(LocalDateTime.now());
            activeCalls.remove(callId);
        }
    }
    
    private String generateCallId(String callerId, String recipientId) {
        // Create a unique ID for the call
        return callerId + "-" + recipientId + "-" + System.currentTimeMillis();
    }
}