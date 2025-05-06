## 💡 Tıbbi Asistan – LLaMA 3.1 ile Tıbbi Değerlendirme API'si

Bu proje, kullanıcıdan gelen sağlık verilerini Llama3.1 8B modeli ile değerlendiren RAG kullanarak desteklenmiş bir **tıbbi asistan** içerir. 

---

### 1. Model Kurulumu

İlk olarak [Ollama](https://ollama.com/download)'nın resmi web sitesinden işletim sisteminize uygun sürümü indirip kurun.

> Uygun modeli seçin ve çalıştırın. [llama3.1](https://ollama.com/library/llama3.1)

```bash
ollama run llama3.1
```

### 2. Spring Boot API

Ollama hazır olduktan sonra API'yı başlatalım.

```bash
./mvnw clean install
./mvnw spring-boot:run
```

Aşağıdaki gibi enpointe isteği iletebiliriz.

![PostmanTest](https://drive.google.com/uc?export=view&id=14f7YippNAHCc3Di9iLdQT-JBBgQpe14S)

## 🧰 Spring Boot Uygulama Akışı
- `ChatController`, HTTP isteklerini alır ve doğrudan `ChatService`’e yönlendirir.
- `ChatService`, kullanıcı girdisini karşılar ve uygun bir prompt oluşturur.
-  **Retrieval Augmented Generation**: Uygulama başlatıldığında `RagConfiguration` ve `RagService` ile sağlıkla ilgili sorular ve cevaplardan oluşan veri seti [MedQuAD](https://www.kaggle.com/datasets/pythonafroz/medquad-medical-question-answer-for-ai-research)'ın bir kısmı `nomic-embed-text:v1.5` modeliyle vektörlere dönüştürülmüştür.
- `ChatClient`'ın advisors desteğiyle kullanıcının verilerine benzer belgeleri vektör veritabanından arar.
- `ChatClient`, bu belgeleri  `LlaMA 3.1 (8B)` modeline göndererek daha gelişmiş yanıtlar alır.
-  Elde edilen yanıt, JSON formatında istemciye dönülür.


## Teknolojiler

* Java 21
* Spring Boot
* Spring AI
* Ollama + LLaMA 3.1 (8B)
* nomic-embed-text:v1.5
* SimpleVectorStore
