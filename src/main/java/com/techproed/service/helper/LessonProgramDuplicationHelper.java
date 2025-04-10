package com.techproed.service.helper;

import com.techproed.entity.concretes.business.LessonProgram;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LessonProgramDuplicationHelper {

    public List<LessonProgram> removeDuplicates(List<LessonProgram> existingLessonPrograms,
                                                List<LessonProgram> newLessonPrograms) {
        List<Long> existingLessonProgramIds = existingLessonPrograms.stream()
                .map(LessonProgram::getId)
                .collect(Collectors.toList());

        return newLessonPrograms.stream()
                .filter(lessonProgram -> !existingLessonProgramIds.contains(lessonProgram.getId()))
                .collect(Collectors.toList());
    }

}
