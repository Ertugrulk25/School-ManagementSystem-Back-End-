package com.techproed.service.user;

import com.techproed.entity.concretes.business.LessonProgram;
import com.techproed.entity.concretes.user.User;
import com.techproed.entity.enums.RoleType;
import com.techproed.payload.mappers.UserMapper;
import com.techproed.payload.messages.SuccessMessages;
import com.techproed.payload.request.business.AddLessonProgram;
import com.techproed.payload.request.user.TeacherRequest;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.payload.response.user.StudentResponse;
import com.techproed.payload.response.user.UserResponse;
import com.techproed.repository.user.UserRepository;
import com.techproed.service.businnes.LessonProgramService;
import com.techproed.service.helper.MethodHelper;
import com.techproed.service.validator.UniquePropertyValidator;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeacherService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final MethodHelper methodHelper;
  private final UniquePropertyValidator uniquePropertyValidator;
  private final LessonProgramService lessonProgramService;


  public ResponseMessage<UserResponse> saveTeacher(TeacherRequest teacherRequest) {
    List<LessonProgram>lessonProgramList =
            lessonProgramService.getLessonProgramById(teacherRequest.getLessonProgramList());
    //validate unique property
    uniquePropertyValidator.checkDuplication(
            teacherRequest.getUsername(),
            teacherRequest.getSsn(),
            teacherRequest.getPhoneNumber(),
            teacherRequest.getEmail());
    User teacher = userMapper.mapUserRequestToUser(teacherRequest, RoleType.TEACHER.getName());
    //set additional props.
    teacher.setIsAdvisor(teacherRequest.getIsAdvisoryTeacher());
    teacher.setLessonProgramList(lessonProgramList);
    User savedTeacher = userRepository.save(teacher);
    return ResponseMessage.<UserResponse>
                    builder()
            .message(SuccessMessages.TEACHER_SAVE)
            .httpStatus(HttpStatus.CREATED)
            .returnBody(userMapper.mapUserToUserResponse(savedTeacher))
            .build();
  }

  public ResponseMessage<UserResponse> updateTeacherById(@Valid TeacherRequest teacherRequest,
                                                         long userId) {
    //validate if teacher exist
    User teacher = methodHelper.isUserExist(userId);
    //validate if user is a teacher
    methodHelper.checkUserRole(teacher, RoleType.TEACHER);
    //validate unique props.
    uniquePropertyValidator.checkUniqueProperty(teacher,teacherRequest);
    List<LessonProgram>lessonPrograms = lessonProgramService.getLessonProgramById(teacherRequest.getLessonProgramList());
    User theacherToUpdate = userMapper.mapUserRequestToUser(teacherRequest, RoleType.TEACHER.getName());
    //map missing props.
    theacherToUpdate.setId(userId);
    theacherToUpdate.setLessonProgramList(lessonPrograms);
    theacherToUpdate.setIsAdvisor(teacherRequest.getIsAdvisoryTeacher());
    User savedTeacher = userRepository.save(theacherToUpdate);
    return ResponseMessage.<UserResponse>builder()
            .message(SuccessMessages.TEACHER_UPDATE)
            .returnBody(userMapper.mapUserToUserResponse(savedTeacher))
            .httpStatus(HttpStatus.OK)
            .build();
  }

  public List<StudentResponse> getAllStudentByAdvisorTeacher(
          HttpServletRequest httpServletRequest) {
    String username = (String) httpServletRequest.getAttribute("username");
    User teacher = methodHelper.loadByUsername(username);
    methodHelper.checkIsAdvisor(teacher);
    return userRepository.findByAdvisorTeacherId(teacher.getId())
            .stream()
            .map(userMapper::mapUserToStudentResponse)
            .collect(Collectors.toList());
  }

  public ResponseMessage<UserResponse> addLessonProgram(@Valid AddLessonProgram lessonProgram) {
    User teacher = methodHelper.isUserExist(lessonProgram.getTeacherId());
    methodHelper.checkUserRole(teacher, RoleType.TEACHER);
    List<LessonProgram>lessonPrograms = lessonProgramService.getLessonProgramById(lessonProgram.getLessonProgramId());
    // 1,2,3 -> 3,4,5
    // 1,2,3,3,4,5
    // TODO prevent duplication of lesson programs here
    // KERIM
    teacher.getLessonProgramList().addAll(lessonPrograms);
    //update with new lesson program list
    User savedTeacher = userRepository.save(teacher);
    return ResponseMessage.<UserResponse>builder()
            .message(SuccessMessages.LESSON_PROGRAM_ADD_TO_TEACHER)
            .returnBody(userMapper.mapUserToUserResponse(savedTeacher))
            .build();
  }

  @Transactional
  public ResponseMessage<UserResponse> deleteTeacherById(Long teacherId) {
    User teacher = methodHelper.isUserExist(teacherId);
    methodHelper.checkUserRole(teacher,RoleType.TEACHER);

    userRepository.removeAdvisorFromStudents(teacherId);
    userRepository.delete(teacher);

    return ResponseMessage.<UserResponse>builder()
            .message(SuccessMessages.ADVISOR_TEACHER_DELETE)
            .httpStatus(HttpStatus.OK)
            .build();
  }
}
