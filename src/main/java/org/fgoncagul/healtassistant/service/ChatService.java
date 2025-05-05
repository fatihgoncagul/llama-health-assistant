package org.fgoncagul.healtassistant.service;

import org.fgoncagul.healtassistant.dto.ChatRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultSystem("Sen verilere göre tıbbi değerlendirme yapan ve öneride bulunan bir asistansın.")
                .build();
    }

    public String generateResponse(ChatRequest request) {
        String context = String.format(
                "İsmi %s olan hastanın ateşi %s, nabzı %s, tansiyonu %s. Not olarak: \"%s\" belirtilmiş.",
                request.getIsim(), request.getAtes(), request.getNabiz(), request.getTansiyon(), request.getNot()
        );

        return chatClient.prompt()
                .user(context)
                .call()
                .content();
    }
}

