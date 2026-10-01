package com.ditisha.scrs.service;

import com.ditisha.scrs.dto.Dtos.EnrollmentDto;
import com.ditisha.scrs.exception.ResourceNotFoundException;
import com.ditisha.scrs.model.Course;
import com.ditisha.scrs.model.Enrollment;
import com.ditisha.scrs.model.Student;
import com.ditisha.scrs.repository.CourseRepository;
import com.ditisha.scrs.repository.EnrollmentRepository;
import com.ditisha.scrs.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollments;
    private final StudentRepository students;
    private final CourseRepository courses;

    public EnrollmentService(EnrollmentRepository enrollments, StudentRepository students, CourseRepository courses) {
        this.enrollments = enrollments;
        this.students = students;
        this.courses = courses;
    }

    @Transactional
    public void enroll(String username, Long courseId) {
        Student s = studentOf(username);
        Course c = courses.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + courseId));
        if (enrollments.existsByStudentIdAndCourseId(s.getId(), courseId)) {
            throw new IllegalStateException("You are already registered for this course");
        }
        if (enrollments.countByCourseId(courseId) >= c.getCapacity()) {
            throw new IllegalStateException("This course is full");
        }
        Enrollment e = new Enrollment();
        e.setStudent(s);
        e.setCourse(c);
        enrollments.save(e);
    }

    @Transactional
    public void drop(String username, Long courseId) {
        Student s = studentOf(username);
        Enrollment e = enrollments.findByStudentIdAndCourseId(s.getId(), courseId)
                .orElseThrow(() -> new ResourceNotFoundException("You are not registered for this course"));
        enrollments.delete(e);
    }

    public List<EnrollmentDto> myEnrollments(String username) {
        Student s = studentOf(username);
        return enrollments.findByStudentId(s.getId()).stream()
                .map(e -> new EnrollmentDto(e.getCourse().getId(), e.getCourse().getCode(),
                        e.getCourse().getTitle(), e.getCourse().getCredits(), e.getEnrolledOn()))
                .toList();
    }

    private Student studentOf(String username) {
        return students.findByUserUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("No student profile for this account"));
    }
}
