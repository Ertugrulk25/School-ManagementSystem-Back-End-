package com.techproed.service.helper;

import com.techproed.entity.concretes.business.StudentInfo;
import com.techproed.entity.enums.Note;
import com.techproed.exception.ConflictException;
import com.techproed.exception.ResourceNotFoundException;
import com.techproed.payload.messages.ErrorMessages;
import com.techproed.repository.business.StudentInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentInfoHelper {

  @Value("${final.exam.impact.percentage}")
  private Double finalExamPercentage;

  @Value("${midterm.exam.impact.percentage}")
  public Double midTermExamPercentage;

  private final StudentInfoRepository studentInfoRepository;

  public void validateLessonDuplication(Long studentId,String lessonName) {
    if(studentInfoRepository.isStudentInfoExistForLesson(studentId, lessonName)) {
      throw new ConflictException(String.format(ErrorMessages.ALREADY_CREATED_STUDENT_INFO_FOR_LESSON,lessonName));
    }
  }

  public Double calculateAverageScore(Double midtermExam,Double finalExam){
    return (midtermExam*midTermExamPercentage)+(finalExam*finalExamPercentage);
  }

  public Note checkLetterGrade(Double average){
    if(average<50.0) {
      return Note.FF;
    } else if (average<60) {
      return Note.DD;
    } else if (average<65) {
      return Note.CC;
    } else if (average<70) {
      return  Note.CB;
    } else if (average<75) {
      return  Note.BB;
    } else if (average<80) {
      return Note.BA;
    } else {
      return Note.AA;
    }
  }


  public StudentInfo isStudentInfoExistById(Long id){
    return studentInfoRepository.findById(id)
            .orElseThrow(()->new ResourceNotFoundException(String.format(ErrorMessages.STUDENT_INFO_NOT_FOUND,id)));
  }



}