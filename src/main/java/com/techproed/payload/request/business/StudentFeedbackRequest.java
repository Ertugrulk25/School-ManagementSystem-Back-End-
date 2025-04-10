package com.techproed.payload.request.business;

import lombok.Data;

@Data
public class StudentFeedbackRequest {

    private String feedbackText;
    private Long studentId; // Geri bildirimi veren öğrencinin ID'si (şimdilik böyle, ileride güvenlik bağlamından alabiliriz)
}