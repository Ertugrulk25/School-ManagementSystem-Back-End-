package com.techproed.payload.mappers;

import com.techproed.entity.concretes.business.EducationTerm;
import com.techproed.payload.request.business.EducationTermRequest;
import com.techproed.payload.request.business.EducationTermUpdateRequest;
import com.techproed.payload.response.business.EducationTermResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        //with this parameter, MapStruct will always check source properties if they have null value or not.
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        //If a source bean property equals null, the target bean property will be ignored and retain its existing value. So, we will be able to perform partial update.
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EducationTermMapper {

    EducationTerm mapEducationTermRequestToEducationTerm(EducationTermRequest educationTermRequest);

    EducationTerm updateEducationTermWithEducationTermUpdateRequest(EducationTermUpdateRequest educationTermRequest, @MappingTarget EducationTerm educationTerm);

    EducationTermResponse mapEducationTermToEducationTermResponse( EducationTerm educationTerm);
}
