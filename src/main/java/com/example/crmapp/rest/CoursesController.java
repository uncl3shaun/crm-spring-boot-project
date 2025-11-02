package com.example.crmapp.rest;

import com.example.crmapp.model.Courses;
import com.example.crmapp.service.CoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CoursesController {

    @Autowired
    private CoursesService coursesService;


    @GetMapping
    public List<Courses> getAllCourses() {
        return coursesService.getAllCourses();
    }


    @PostMapping
    public ResponseEntity<Courses> addCourse(@RequestBody Courses course) {
        Courses newCourse = coursesService.addCourse(course);
        return new ResponseEntity<>(newCourse, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
        coursesService.deleteCourse(id);
        return ResponseEntity.ok("Course with id " + id + " was deleted");
    }
}