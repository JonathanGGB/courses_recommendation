package com.metaphorce.course_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.metaphorce.course_service.model.Course;

public interface CourseRepository extends MongoRepository<Course, String> {
}