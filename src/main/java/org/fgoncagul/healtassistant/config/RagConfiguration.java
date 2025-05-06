package org.fgoncagul.healtassistant.config;

import org.fgoncagul.healtassistant.service.RagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

@Configuration
public class RagConfiguration {

    private static final Logger log = LoggerFactory.getLogger(RagConfiguration.class);
    private static final String VECTOR_FILE_PATH = "src/main/resources/data/vectorstore.json";
    private final RagService ragService;

    public RagConfiguration(RagService ragService) {
        this.ragService = ragService;
    }

    @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel) throws IOException {
        File vectorFile = new File(VECTOR_FILE_PATH);
        SimpleVectorStore vectorStore = SimpleVectorStore.builder(embeddingModel).build();

        if (vectorFile.exists()) {
            log.info("🧠 Vector store zaten mevcut, dosyadan yükleniyor...");
            vectorStore.load(vectorFile);
        } else {
            log.info("📄 Vector store bulunamadı, CSV'den oluşturuluyor...");
            vectorStore.add(ragService.loadInitialDocuments());
            vectorStore.save(vectorFile);
            log.info("✅ Vector store başarıyla oluşturuldu ve kaydedildi.");
        }

        return vectorStore;
    }
}
