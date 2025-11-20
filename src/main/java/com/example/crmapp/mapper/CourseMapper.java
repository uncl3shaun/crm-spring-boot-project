package com.example.crmapp.mapper;

import com.example.crmapp.dto.CourseCategoryDto;
import com.example.crmapp.dto.CourseDto;
import com.example.crmapp.dto.TagDto;
import com.example.crmapp.model.CourseCategory;
import com.example.crmapp.model.Courses;
import com.example.crmapp.model.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseDto toDto(Courses course);
    CourseCategoryDto toDto(CourseCategory category);
    TagDto toDto(Tag tag);

    Courses toEntity(CourseDto courseDto);

    CourseCategory toEntity(CourseCategoryDto categoryDto);

    Tag toEntity(TagDto tagDto);
}