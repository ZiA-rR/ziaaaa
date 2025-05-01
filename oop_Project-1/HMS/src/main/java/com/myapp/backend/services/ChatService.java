package com.myapp.backend.services;

import com.myapp.backend.model.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatService {
    private Map<String, ArrayList<ChatMessage>> chatHistory;
    
    public ChatService() {
        chatHistory = new HashMap<>();
    }
    
    public void sendMessage(String senderId, String receiverId, String message) {
        ChatMessage chatMessage = new ChatMessage(senderId, receiverId, message, LocalDateTime.now());
        
        // Create a unique chat ID using both sender and receiver IDs
        String chatId = getChatId(senderId, receiverId);
        
        // Add message to chat history
        if (!chatHistory.containsKey(chatId)) {
            chatHistory.put(chatId, new ArrayList<>());
        }
        chatHistory.get(chatId).add(chatMessage);
    }
    
    public ArrayList<ChatMessage> getChatHistory(String userId1, String userId2) {
        String chatId = getChatId(userId1, userId2);
        return chatHistory.getOrDefault(chatId, new ArrayList<>());
    }
    
    private String getChatId(String userId1, String userId2) {
        // Ensure the chat ID is consistent regardless of who is sender or receiver
        if (userId1.compareTo(userId2) < 0) {
            return userId1 + "_" + userId2;
        } else {
            return userId2 + "_" + userId1;
        }
    }
    
    public void clearChatHistory(String userId1, String userId2) {
        String chatId = getChatId(userId1, userId2);
        chatHistory.remove(chatId);
    }
}