package com.techproed.service.businnes;

import com.techproed.entity.concretes.business.Lesson;
import com.techproed.entity.concretes.business.LessonProgram;
import com.techproed.exception.ConflictException;
import com.techproed.payload.mappers.LessonMapper;
import com.techproed.payload.messages.ErrorMessages;
import com.techproed.payload.messages.SuccessMessages;
import com.techproed.payload.request.business.LessonRequest;
import com.techproed.payload.response.business.LessonResponse;
import com.techproed.payload.response.business.ResponseMessage;
import com.techproed.repository.business.LessonRepository;
import com.techproed.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;
    private final PageableHelper pageableHelper;

    public ResponseMessage<LessonResponse> saveLesson(@Valid LessonRequest lessonRequest) {

        isLessonExistByName(lessonRequest.getLessonName());

        Lesson lesson = lessonMapper.mapLessonRequestToLesson(lessonRequest);

        Lesson saveLesson = lessonRepository.save(lesson);

        return ResponseMessage.<LessonResponse>builder()
                .returnBody(lessonMapper.mapLessonToLessonResponse(saveLesson))
                .httpStatus(HttpStatus.CREATED)
                .message(SuccessMessages.LESSON_SAVE)
                .build();




    }

    private void isLessonExistByName(String lessonName) {
if (lessonRepository.findByLessonNameEqualsIgnoreCase(lessonName).isPresent()){
    throw new ConflictException(String.format(ErrorMessages.ALREADY_CREATED_LESSON_MESSAGE,lessonName));
}
    }


    public Page<LessonResponse> getLessonByPage(int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageable(page, size, sort, type);

        Page<Lesson> lessons = lessonRepository.findAll(pageable);

        return lessons.map(lessonMapper::mapLessonToLessonResponse);



    }
}

