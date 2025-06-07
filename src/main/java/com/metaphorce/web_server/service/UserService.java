package com.metaphorce.web_server.service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metaphorce.dto.UserDTO;
import com.metaphorce.dto.CourseDTO;
import com.metaphorce.web_server.model.Course;
import com.metaphorce.web_server.model.User;
import com.metaphorce.web_server.repository.CourseRepository;
import com.metaphorce.web_server.repository.UserRepository;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public UserService(UserRepository userRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDTO(user);
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setName(userDTO.getName());

        // Actualizar cursos solo si están presentes en el DTO
        if (userDTO.getCourses() != null) {
            Set<Course> updatedCourses = userDTO.getCourses().stream()
                    .map(courseDTO -> courseRepository.findById(courseDTO.getId())
                            .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseDTO.getId())))
                    .collect(Collectors.toSet());

            existingUser.setCourses(updatedCourses);
        }

        User updatedUser = userRepository.save(existingUser);
        return convertToDTO(updatedUser);
    }

    // Convert Entity to DTO
    public UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());

        Set<CourseDTO> courseDTOs = new HashSet<>();

        for (Course course : user.getCourses()) {
            courseDTOs.add(convertCourseToDTO(course));
        }

        userDTO.setCourses(courseDTOs);
        return userDTO;
    }

    // Convert DTO to Entity
    public User convertToEntity(UserDTO dto) {
        User user = new User();

        if (dto.getId() != null) {
            user.setId(dto.getId());
        }

        user.setName(dto.getName());

        if (dto.getCourses() != null) {
            Set<Course> courses = dto.getCourses().stream()
                    .map(courseDTO -> courseRepository.findById(courseDTO.getId())
                            .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseDTO.getId())))
                    .collect(Collectors.toSet());
            user.setCourses(courses);
        }

        return user;
    }

    public CourseDTO convertCourseToDTO(Course course) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setTitle(course.getTitle());
        courseDTO.setTopic(course.getTopic());
        return courseDTO;
    }

    @Transactional
    public UserDTO addCourseToUser(Long userId, Long courseId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new RuntimeException("Course not found"));

        user.getCourses().add(course);
        userRepository.save(user);

        return convertToDTO(user);
    }

    @Transactional
    public UserDTO removeCourseFromUser(Long userId, Long courseId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new RuntimeException("Course not found"));

        user.getCourses().remove(course);
        userRepository.save(user);

        return convertToDTO(user);
    }
}