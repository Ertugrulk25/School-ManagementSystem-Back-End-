package com.techproed.entity.concretes.business;



import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lessonName;

    private Integer creditScore;

    private Boolean isCompulsory;

    @JsonIgnore
    @ManyToMany(mappedBy = "lessons")
    private List<LessonProgram> lessonPrograms;







}
