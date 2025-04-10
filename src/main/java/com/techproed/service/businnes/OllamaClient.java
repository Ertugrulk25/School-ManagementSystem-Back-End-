package com.techproed.service.businnes;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class OllamaClient {

    private static final Logger logger = LoggerFactory.getLogger(OllamaClient.class);
    private static final String OLLAMA_API_URL = "http://localhost:11434/api/generate";
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getSentiment(String feedbackText) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String modelName = "gemma3";
        String escapedFeedbackText = feedbackText.replace("\"", "\\\"");
        String prompt = "Bu öğrenci geri bildirimini değerlendir. 1 ile 10 arasında puan ver ,1-5 arası olumsuz , 5 normal, 5-10 arası olumlu.  ekstra hiç bir açıklama yapma. Örnek çıktı formatı : Puan:3 - Olumsuz .  puan ver ve olumlu, normal veya olumsuz diye yaz," + escapedFeedbackText;
        String requestBody = String.format("{\"prompt\":\"%s\", \"model\":\"%s\", \"stream\": true}", prompt, modelName);
        logger.info("Ollama'ya Gönderilen İstek Gövdesi: {}", requestBody);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        StringBuilder fullResponse = new StringBuilder();
        ResponseEntity<byte[]> responseEntity = restTemplate.postForEntity(OLLAMA_API_URL, requestEntity, byte[].class);

        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
            String responseBody = new String(responseEntity.getBody(), StandardCharsets.UTF_8);
            String[] responseLines = responseBody.split("\n");
            for (String line : responseLines) {
                if (!line.trim().isEmpty()) {
                    try {
                        JsonNode node = objectMapper.readTree(line);
                        String responsePart = node.get("response").asText("");
                        boolean done = node.get("done").asBoolean(false);
                        fullResponse.append(responsePart);
                        if (done) {
                            break;
                        }
                    } catch (IOException e) {
                        logger.error("JSON ayrıştırma hatası: {}", e.getMessage());
                        break;
                    }
                }
            }
        } else {
            logger.error("Ollama API'sinden hata: {}", responseEntity.getStatusCode());
        }

        return fullResponse.toString().trim();
    }
}