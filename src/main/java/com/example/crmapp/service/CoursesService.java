package com.example.crmapp.service;

import com.example.crmapp.exception.ResourceNotFoundException;
import com.example.crmapp.model.Courses;
import com.example.crmapp.repository.CoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoursesService {

    @Autowired
    private CoursesRepository coursesRepository;

    public List<Courses> getAllCourses() {
        return coursesRepository.findAll();
    }


    public Courses addCourse(Courses course) {
        return coursesRepository.save(course);
    }

    public Courses getCourseById(Long id) {
        return coursesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
    }


    public void deleteCourse(Long id) {
        Courses course = getCourseById(id);
        coursesRepository.delete(course);
    }

}