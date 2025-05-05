package org.fgoncagul.healtassistant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatRequest {
    private String isim;
    private String ates;
    private String nabiz;
    private String tansiyon;
    private String not;
}
