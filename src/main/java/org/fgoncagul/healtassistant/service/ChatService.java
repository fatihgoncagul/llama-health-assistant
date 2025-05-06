package org.fgoncagul.healtassistant.service;

import org.fgoncagul.healtassistant.dto.ChatRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ChatService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public ChatService(ChatClient.Builder chatClientBuilder,VectorStore vectorStore) {
        this.chatClient = chatClientBuilder.defaultSystem("Sen verilere göre tıbbi değerlendirme yapan ve öneride bulunan bir tıbbi asistansın.Aşadağıdaki verilere göre hastanın ismini belirterek kısa ve öz genel değerlendirme yap ve kısa ve öz öneride bulun.Basit ve anlaşılır cümleler kur.").build();

        this.vectorStore = vectorStore;
    }

    public String generateResponse(ChatRequest request) throws IOException {
        String context = String.format(
                "İsmi %s olan hastanın ateşi %s, nabzı %s, tansiyonu %s. Not olarak: \"%s\" belirtilmiş.",
                request.getIsim(), request.getAtes(), request.getNabiz(), request.getTansiyon(), request.getNot()
        );



        return chatClient.prompt()
                .advisors(new QuestionAnswerAdvisor(vectorStore, SearchRequest.builder().build()))
                .user(context)
                .call()
                .content();
    }
}
