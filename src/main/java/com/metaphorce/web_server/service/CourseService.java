package com.metaphorce.web_server.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.metaphorce.dto.CourseDTO;
import com.metaphorce.web_server.model.Course;
import com.metaphorce.web_server.model.User;
import com.metaphorce.web_server.repository.CourseRepository;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<CourseDTO> getAllCourses() {
    return courseRepository.findAll().stream()
        .map(this::convertToDTO)
        .toList();
    }

    public CourseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Course not found"));
        return convertToDTO(course);
    }

    public CourseDTO createCourse(CourseDTO dto) {
        Course course = convertToEntity(dto);
        Course saved = courseRepository.save(course);
        return convertToDTO(saved);
    }

    public CourseDTO updateCourse(Long id, CourseDTO dto) {
        Course course = courseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Course not found"));
        course.setTitle(dto.getTitle());
        course.setTopic(dto.getTopic());
        Course updated = courseRepository.save(course);
        return convertToDTO(updated);
    }

    public void deleteCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new RuntimeException("Course not found"));

        // Romper relaciones
        for (User user : course.getUsers()) {
            user.getCourses().remove(course);
        }

        course.getUsers().clear(); // opcional por seguridad

        courseRepository.delete(course);
    }

    public CourseDTO convertToDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setTopic(course.getTopic());
        return dto;
    }

    public Course convertToEntity(CourseDTO dto) {
        Course course = new Course();

        if (dto.getId() != null) {
            course.setId(dto.getId());
        }
        
        course.setTitle(dto.getTitle());
        course.setTopic(dto.getTopic());
        return course;
    }
}
