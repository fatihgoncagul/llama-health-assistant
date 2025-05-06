package org.fgoncagul.healtassistant.service;

import org.fgoncagul.healtassistant.config.RagConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class RagService {

    private static final Logger log = LoggerFactory.getLogger(RagService.class);
    private static final String CSV_PATH = "src/main/resources/docs/medquad.csv";
    private static final int MAX_LINES = 500;

    public List<Document> loadInitialDocuments() throws IOException {
        List<Document> documents = new ArrayList<>();
        Path csvPath = Paths.get(CSV_PATH);
        try (BufferedReader reader = Files.newBufferedReader(csvPath)) {
            reader.readLine(); // header'ı atla
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null && count < MAX_LINES) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    String content = "Soru: " + parts[0].trim() + "\nYanıt: " + parts[1].trim();
                    documents.add(new Document(content));
                    count++;
                }
            }
        }

        log.info("📄 İlk {} soru-cevap yüklendi.", documents.size());

        TextSplitter splitter = new TokenTextSplitter();
        List<Document> splitDocs = splitter.apply(documents);

        log.info("✂️ Toplam {} parçaya bölündü.", splitDocs.size());
        return splitDocs;
    }
}

