package com.techproed.payload.request.user;

import com.techproed.payload.request.abstracts.BaseUserRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class TeacherRequest extends BaseUserRequest {

  @NotNull(message = "Please select lesson program")
  private List<Long> lessonProgramList;

  @NotNull(message = "Please select isAdvisory teacher")
  private Boolean isAdvisoryTeacher;

}
