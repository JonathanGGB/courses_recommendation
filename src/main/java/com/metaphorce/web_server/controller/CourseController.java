package com.metaphorce.web_server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metaphorce.web_server.model.Course;
import com.metaphorce.web_server.repository.CourseRepository;
import com.metaphorce.web_server.service.CourseService;

@RestController
@RequestMapping("/courses")
public class CourseController {
     @Autowired
    private CourseRepository courseRepository;

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseRepository.findAll();

        return ResponseEntity.ok().body(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCourseById(@PathVariable Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));

        return ResponseEntity.ok().body(course);
    }
    
    @PostMapping("/")
    public ResponseEntity<Object> createCourse(@RequestBody Course course) {
        Course createdCourse = courseService.createCourse(course);
        
        return ResponseEntity.status(HttpStatus.CREATED).body("Course created with id:" + createdCourse.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCourse(@PathVariable Long id) {
        courseRepository.deleteById(id);

        return ResponseEntity.ok().body("Course deleted sucessfully with ID:" + id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCourse(@PathVariable Long id, @RequestBody Course courseDetails) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));

        course.setTitle(courseDetails.getTitle());
        course.setTopic(courseDetails.getTopic());

        Course updatedCourse = courseRepository.save(course);

        return ResponseEntity.ok().body("Course updated sucessfully with ID:" + updatedCourse.getId());
    }
}