package com.techproed.payload.response.business;

import lombok.Data;

@Data
public class StudentFeedbackResponse {

    private Long id; // Kaydedilen geri bildirimin ID'si
    private String message; // Başarı mesajı
    private Long studentId; // Geri bildirimi veren öğrencinin ID'si
    private String feedbackText; // Kaydedilen geri bildirim metni (isteğe bağlı)
   private String sentiment;
    // İhtiyacınıza göre başka alanlar da ekleyebilirsiniz (örneğin, kayıt tarihi).
}
