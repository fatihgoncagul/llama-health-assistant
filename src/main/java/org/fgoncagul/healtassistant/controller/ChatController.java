package org.fgoncagul.healtassistant.controller;

import org.fgoncagul.healtassistant.dto.ChatRequest;
import org.fgoncagul.healtassistant.dto.ChatResponse;
import org.fgoncagul.healtassistant.service.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        try {
            String response = chatService.generateResponse(request);
            return ResponseEntity.ok(new ChatResponse(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ChatResponse("⚠️ Bir hata oluştu: " + e.getMessage()));
        }
    }
}
