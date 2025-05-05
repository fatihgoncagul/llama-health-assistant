package org.fgoncagul.healtassistant.controller;

import org.fgoncagul.healtassistant.dto.ChatRequest;
import org.fgoncagul.healtassistant.dto.ChatResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ChatController {
    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.defaultSystem("Sen verilere göre tıbbi değerlendirme yapan ve öneride bulunan bir tıbbi asistansın.Aşadağıdaki verilere göre hastanın ismini belirterek kısa ve öz genel değerlendirme yap ve kısa ve öz öneride bulun.").build();
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        try {
            String context = String.format(
                    "İsmi %s olan hastanın ateşi %s, nabzı %s, tansiyonu %s. Not olarak: \"%s\" belirtilmiş.",
                    request.getIsim(), request.getAtes(), request.getNabiz(), request.getTansiyon(), request.getNot()
            );

            String response = chatClient.prompt()
                    .user(context)
                    .call()
                    .content();

            return ResponseEntity.ok(new ChatResponse(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ChatResponse("Bir hata oluştu: " + e.getMessage()));
        }
    }
}
