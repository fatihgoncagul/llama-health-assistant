# 💡 Tıbbi Asistan – LLaMA 3.1 ile Tıbbi Değerlendirme API'si

Bu proje, kullanıcıdan gelen sağlık verilerini doğal dil ile değerlendiren bir **tıbbi asistan** içerir. Spring AI ve Ollama kullanılarak geliştirilmiş, arkada **LLaMA 3.1 8B** modeli çalışmaktadır.

### 1. Model Kurulumu

İlk olarak [Ollama](https://ollama.com/download)'nın resmi web sitesinden işletim sisteminize uygun sürümü indirip kurun.

> Uygun modeli seçin: [llama3.1](https://ollama.com/library/llama3.1)

```
ollama run llama3.1
```
### 2. Spring Boot API
**Projede kullanılan spring-ai-ollama bağımlılığı sayesinde, yerel olarak çalışan LLaMA 3.1 modeli ile etkileşim kurulabilir.**

**API'ın Genel Akışı:**
- POST / isteği ```http://localhost:8080/api/v1/chat``` yapıldığında sistem aşağıdaki gibi çalışır:

- [ChatController](https://github.com/fatihgoncagul/llama-health-assistant/blob/3a58de6b30f14ec35dfbb9d13f5f5337839b8124/src/main/java/org/fgoncagul/healtassistant/controller/ChatController.java) gelen istek gövdesini (ChatRequest) alır.

- Bu veri [ChatService](https://github.com/fatihgoncagul/llama-health-assistant/blob/main/src/main/java/org/fgoncagul/healtassistant/service/ChatService.java) sınıfına iletilir.

- ChatService, hasta bilgilerini anlamlı bir prompt formatına çevirir.

- Bu prompt, [ChatClient](https://github.com/fatihgoncagul/llama-health-assistant/blob/3a58de6b30f14ec35dfbb9d13f5f5337839b8124/src/main/java/org/fgoncagul/healtassistant/service/ChatService.java#L23) aracılığıyla LLaMA 3.1 modeline gönderilir.

- Modelin yanıtı ChatResponse olarak kullanıcıya geri döner.


> **ChatClient**, ChatService içerisinde aşağıdaki gibi tanımlanır, modelin rolünü belirler.
```
this.chatClient = chatClientBuilder
    .defaultSystem("Sen tıbbi değerlendirme yapan ve öneride bulunan bir asistansın.")
    .build();
```

Ollama hazır olduktan sonra API'yı başlatalım.
```bash
./mvnw clean install
./mvnw spring-boot:run
```
#### Örnek istek ve yanıt
![PostmanTest](https://drive.google.com/uc?export=view&id=14f7YippNAHCc3Di9iLdQT-JBBgQpe14S)
## Teknolojiler

* Java 21
* Spring Boot
* Spring AI
* Ollama + LLaMA 3.1 (8B)
