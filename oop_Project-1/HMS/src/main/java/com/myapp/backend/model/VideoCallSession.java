package com.myapp.backend.model;

import java.time.LocalDateTime;
import java.time.Duration;

public class VideoCallSession {
    private String callId;
    private String callerId;
    private String recipientId;
    private String videoLink;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
    public VideoCallSession(String callId, String callerId, String recipientId, 
                         String videoLink, LocalDateTime startTime) {
        this.callId = callId;
        this.callerId = callerId;
        this.recipientId = recipientId;
        this.videoLink = videoLink;
        this.startTime = startTime;
    }
    
    public String getCallId() {
        return callId;
    }
    
    public String getCallerId() {
        return callerId;
    }
    
    public String getRecipientId() {
        return recipientId;
    }
    
    public String getVideoLink() {
        return videoLink;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    
    public Duration getDuration() {
        if (endTime == null) {
            return Duration.between(startTime, LocalDateTime.now());
        } else {
            return Duration.between(startTime, endTime);
        }
    }
    
    public boolean isActive() {
        return endTime == null;
    }
}