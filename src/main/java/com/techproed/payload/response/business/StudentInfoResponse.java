package com.techproed.payload.response.business;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.techproed.entity.enums.Note;
import com.techproed.entity.enums.Term;
import com.techproed.payload.response.user.StudentResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentInfoResponse {

  private Long id;
  private Double midtermExam;
  private Double finalExam;
  private Integer absentee;
  private String infoNote;
  private String lessonName;
  private int creditScore;
  private boolean isCompulsory;
  private Term educationTerm;
  private Double average;
  private Note note;
  private StudentResponse studentResponse;
  private Double predictedFinalExamScore;


}
