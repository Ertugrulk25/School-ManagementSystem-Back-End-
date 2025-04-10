package com.techproed.controller.user;

import com.techproed.payload.request.business.AddLessonProgramForStudent;
import com.techproed.payload.request.user.StudentRequest;
import com.techproed.payload.request.user.StudentUpdateRequest;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.payload.response.user.StudentResponse;
import com.techproed.service.user.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PreAuthorize("hasAnyAuthority('Admin')")
    @PostMapping("/save")
    public ResponseMessage<StudentResponse> save(@RequestBody @Valid StudentRequest studentRequest) {
        return studentService.save(studentRequest);
    }

    @PreAuthorize("hasAnyAuthority('Student')")
    @PutMapping("/update")
    public ResponseEntity<String> updateStudent(
            HttpServletRequest httpServletRequest,
            @RequestBody @Valid StudentUpdateRequest studentUpdateRequest) {
        return ResponseEntity.ok(studentService.updateStudent(httpServletRequest, studentUpdateRequest));
    }

    // TODO furkan fix this bug.
    // when we update teacher by manager, fathername and mother name saved always NULL in db.
    @PreAuthorize("hasAnyAuthority('Admin')")
    @PutMapping("/updateByAdmin/{studentId}")
    public ResponseMessage<StudentResponse>updateStudentByManager(
            @PathVariable Long studentId,
            @RequestBody @Valid StudentRequest studentRequest) {
        return studentService.updateStudentByManager(studentId,studentRequest);
    }

    //TODO Ertugrul
    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @GetMapping("/changeStatus")
    public ResponseMessage changeStatus(@RequestParam Long id,
                                        @RequestParam boolean status){
        return studentService.changeStatus(id,status);

    }

    @PreAuthorize("hasAnyAuthority('Student')")
    @GetMapping("/addLessonProgram")
    public ResponseMessage<StudentResponse>addLessonProgram(
            HttpServletRequest httpServletRequest,
            @RequestBody @Valid AddLessonProgramForStudent addLessonProgramForStudent){
        return studentService.addLessonProgram(httpServletRequest,addLessonProgramForStudent);
    }







}