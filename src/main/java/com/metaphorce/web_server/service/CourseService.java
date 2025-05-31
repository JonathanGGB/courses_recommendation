package com.metaphorce.web_server.service;

import org.springframework.stereotype.Service;

import com.metaphorce.web_server.model.Course;
import com.metaphorce.web_server.repository.CourseRepository;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }
}
