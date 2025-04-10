package com.techproed.controller.business;
import com.techproed.payload.request.business.StudentFeedbackRequest;
import com.techproed.payload.response.business.ResponseMessage; // Eğer genel bir ResponseMessage sınıfınız varsa
import com.techproed.payload.response.business.StudentFeedbackResponse;
import com.techproed.service.businnes.StudentFeedbackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/studentFeedback")
public class StudentFeedbackController {

    private final StudentFeedbackService studentFeedbackService;

    public StudentFeedbackController(StudentFeedbackService studentFeedbackService) {
        this.studentFeedbackService = studentFeedbackService;
    }
    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @PostMapping("/save")
    public ResponseEntity<ResponseMessage<StudentFeedbackResponse>> saveStudentFeedback(@RequestBody StudentFeedbackRequest studentFeedbackRequest) {
        StudentFeedbackResponse response = studentFeedbackService.saveStudentFeedback(studentFeedbackRequest);
        return ResponseEntity.ok(ResponseMessage.<StudentFeedbackResponse>builder()
                .message("Geri bildiriminiz başarıyla kaydedildi.")
                .returnBody(response)
                .build());
    }
}