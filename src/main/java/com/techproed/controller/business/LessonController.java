package com.techproed.controller.business;

import com.techproed.payload.request.business.LessonRequest;
import com.techproed.payload.response.business.LessonResponse;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.service.businnes.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/lesson")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @PostMapping("/save")
    public ResponseMessage<LessonResponse>saveLesson(
            @RequestBody @Valid LessonRequest lessonRequest) {
        return lessonService.saveLesson(lessonRequest);
    }

    //TODO edip
    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @DeleteMapping("/delete/{lessonId}")
    public ResponseMessage deleteLesson(@PathVariable Long lessonId) {
        //return lessonService.deleteLesson(lessonId);
        return null;
    }

    //TODO nesli
    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @GetMapping("/getLessonByName")
    public ResponseMessage<LessonResponse>getLessonByName(
            @RequestParam String lessonName){
        //return lessonService.findLessonByName(lessonName);
        return null;
    }


    //TODO ertugrul
    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @GetMapping("/getLessonByPage")
    public Page<LessonResponse> findLessonByPage(
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "10") int size,
            @RequestParam(value = "sort",defaultValue = "lessonName") String sort,
            @RequestParam(value = "type",defaultValue = "desc") String type
    ){
        return lessonService.getLessonByPage(page,size,sort,type);

    }

}
