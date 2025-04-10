package com.techproed.entity.concretes.business;

import com.techproed.entity.concretes.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String feedbackText;

    @OneToOne
    @JoinColumn(name = "student_id", unique = true) // t_user tablosundaki id alanına referans verir ve unique olmalı
    private User student; // İlişkili öğrenci

    private String sentiment; // Bu alanı ekleyin

}