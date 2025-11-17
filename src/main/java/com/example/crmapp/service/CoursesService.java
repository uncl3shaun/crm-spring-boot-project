package com.example.crmapp.service;

import com.example.crmapp.dto.CourseDto;
import com.example.crmapp.exception.ResourceNotFoundException;
import com.example.crmapp.mapper.CourseMapper;
import com.example.crmapp.model.CourseCategory;
import com.example.crmapp.model.Courses;
import com.example.crmapp.model.Tag;
import com.example.crmapp.dto.TagDto;
import com.example.crmapp.repository.CourseCategoryRepository;
import com.example.crmapp.repository.CoursesRepository;
import com.example.crmapp.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoursesService {

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseCategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    public List<CourseDto> getAllCourses() {
        return coursesRepository.findAll().stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
    }

    public CourseDto getCourseById(Long id) {
        Courses course = coursesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
        return courseMapper.toDto(course);
    }

    public CourseDto addCourse(CourseDto courseDto) {
        Courses course = courseMapper.toEntity(courseDto);

        if (courseDto.getCategory() != null && courseDto.getCategory().getId() != null) {
            CourseCategory category = categoryRepository.findById(courseDto.getCategory().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + courseDto.getCategory().getId()));
            course.setCategory(category);
        }

        if (courseDto.getTags() != null && !courseDto.getTags().isEmpty()) {
            List<Long> tagIds = courseDto.getTags().stream().map(TagDto::getId).collect(Collectors.toList());
            List<Tag> tags = tagRepository.findAllById(tagIds);
            course.setTags(tags);
        }

        Courses savedCourse = coursesRepository.save(course);
        return courseMapper.toDto(savedCourse);
    }

    public CourseDto updateCourse(Long id, CourseDto courseDto) {
        Courses existingCourse = coursesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));

        existingCourse.setName(courseDto.getName());
        existingCourse.setDescription(courseDto.getDescription());
        existingCourse.setPrice(courseDto.getPrice());

        if (courseDto.getCategory() != null && courseDto.getCategory().getId() != null) {
            CourseCategory category = categoryRepository.findById(courseDto.getCategory().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + courseDto.getCategory().getId()));
            existingCourse.setCategory(category);
        } else {
            existingCourse.setCategory(null);
        }

        if (courseDto.getTags() != null && !courseDto.getTags().isEmpty()) {
            List<Long> tagIds = courseDto.getTags().stream().map(TagDto::getId).collect(Collectors.toList());
            List<Tag> tags = tagRepository.findAllById(tagIds);
            existingCourse.setTags(tags);
        } else {
            existingCourse.setTags(null);
        }

        Courses updatedCourse = coursesRepository.save(existingCourse);
        return courseMapper.toDto(updatedCourse);
    }

    public void deleteCourse(Long id) {
        if (!coursesRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course not found with id: " + id);
        }
        coursesRepository.deleteById(id);
    }
}