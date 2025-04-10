package com.techproed.repository.business;

import com.techproed.entity.concretes.business.StudentFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentFeedbackRepository extends JpaRepository<StudentFeedback, Long> {
}