package com.example.crmapp.service;

import com.example.crmapp.dto.CourseDto;
import com.example.crmapp.exception.ResourceNotFoundException;
import com.example.crmapp.model.Courses;
import com.example.crmapp.repository.CoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoursesService {

    @Autowired
    private CoursesRepository coursesRepository;

    public List<CourseDto> getAllCourses() {
        return coursesRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public CourseDto getCourseById(Long id) {
        Courses course = coursesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
        return toDto(course);
    }

    public CourseDto addCourse(CourseDto courseDto) {
        Courses course = toEntity(courseDto);
        Courses savedCourse = coursesRepository.save(course);
        return toDto(savedCourse);
    }

    public CourseDto updateCourse(Long id, CourseDto courseDto) {
        Courses existingCourse = coursesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));

        existingCourse.setName(courseDto.getName());
        existingCourse.setDescription(courseDto.getDescription());
        existingCourse.setPrice(courseDto.getPrice());

        Courses updatedCourse = coursesRepository.save(existingCourse);
        return toDto(updatedCourse);
    }

    public void deleteCourse(Long id) {
        if (!coursesRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course not found with id: " + id);
        }
        coursesRepository.deleteById(id);
    }


    public CourseDto toDto(Courses course) {
        return new CourseDto(
                course.getId(),
                course.getName(),
                course.getDescription(),
                course.getPrice()
        );
    }

    public Courses toEntity(CourseDto dto) {
        Courses course = new Courses();
        course.setId(dto.getId());
        course.setName(dto.getName());
        course.setDescription(dto.getDescription());
        course.setPrice(dto.getPrice());
        return course;
    }
}