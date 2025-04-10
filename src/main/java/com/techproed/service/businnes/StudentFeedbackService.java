package com.techproed.service.businnes;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.techproed.entity.concretes.business.StudentFeedback;
import com.techproed.payload.request.business.StudentFeedbackRequest;
import com.techproed.payload.response.business.StudentFeedbackResponse;
import com.techproed.repository.business.StudentFeedbackRepository;
import com.techproed.repository.user.UserRepository;
import com.techproed.entity.concretes.user.User;

@Service
public class StudentFeedbackService {

    private final StudentFeedbackRepository studentFeedbackRepository;
    private final UserRepository userRepository;
    private final OllamaClient ollamaClient;
    private static final Logger logger = LoggerFactory.getLogger(StudentFeedbackService.class);

    public StudentFeedbackService(StudentFeedbackRepository studentFeedbackRepository, UserRepository userRepository, OllamaClient ollamaClient) {
        this.studentFeedbackRepository = studentFeedbackRepository;
        this.userRepository = userRepository;
        this.ollamaClient = ollamaClient;
    }

    public StudentFeedbackResponse saveStudentFeedback(StudentFeedbackRequest studentFeedbackRequest) {
        User student = userRepository.findById(studentFeedbackRequest.getStudentId()).orElseThrow(() -> new RuntimeException("Öğrenci bulunamadı"));
        String feedbackText = studentFeedbackRequest.getFeedbackText();
        String sentiment = null;

        try {
            String sentimentResponse = ollamaClient.getSentiment(feedbackText);
            logger.info("Ollama'dan Gelen Yanıt: {}", sentimentResponse);
            sentiment = sentimentResponse; // Doğrudan metni alıyoruz
        } catch (HttpClientErrorException ex) {
            System.err.println("Ollama'dan Hata: " + ex.getResponseBodyAsString());
            sentiment = "Duygu analizi yapılamadı (Ollama hatası).";
        } catch (RestClientException ex) {
            System.err.println("Ollama'ya bağlanırken bir hata oluştu: " + ex.getMessage());
            sentiment = "Duygu analizi yapılamadı (Bağlantı hatası).";
        }

        StudentFeedback studentFeedback = StudentFeedback.builder()
                .feedbackText(feedbackText)
                .student(student)
                .sentiment(sentiment)
                .build();

        StudentFeedback savedFeedback = studentFeedbackRepository.save(studentFeedback);

        StudentFeedbackResponse response = new StudentFeedbackResponse();
        response.setId(savedFeedback.getId());
        response.setMessage("Geri bildirim başarıyla kaydedildi.");
        response.setStudentId(student.getId());
        response.setFeedbackText(savedFeedback.getFeedbackText());
        response.setSentiment(sentiment); // Response'a da ekleyelim

        return response;
    }
}