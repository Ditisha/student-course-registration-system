package com.ditisha.scrs.service;

import com.ditisha.scrs.dto.Dtos.CourseDto;
import com.ditisha.scrs.dto.Dtos.CourseRequest;
import com.ditisha.scrs.exception.ResourceNotFoundException;
import com.ditisha.scrs.model.Course;
import com.ditisha.scrs.repository.CourseRepository;
import com.ditisha.scrs.repository.EnrollmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CourseService {

    // Only these fields may be used for sorting (protects against bad input)
    private static final Set<String> SORT_FIELDS = Set.of("id", "code", "title", "credits", "capacity");

    private final CourseRepository courses;
    private final EnrollmentRepository enrollments;

    public CourseService(CourseRepository courses, EnrollmentRepository enrollments) {
        this.courses = courses;
        this.enrollments = enrollments;
    }

    public Page<CourseDto> list(String q, int page, int size, String sortBy, String direction) {
        String field = SORT_FIELDS.contains(sortBy) ? sortBy : "code";
        Sort.Direction dir = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(Math.max(page, 0), Math.min(Math.max(size, 1), 50), Sort.by(dir, field));
        String term = q == null ? "" : q.trim();
        return courses.findByTitleContainingIgnoreCaseOrCodeContainingIgnoreCase(term, term, pageable)
                .map(this::toDto);
    }

    public CourseDto create(CourseRequest r) {
        if (courses.existsByCode(r.code().trim())) {
            throw new IllegalStateException("A course with this code already exists");
        }
        return toDto(courses.save(apply(new Course(), r)));
    }

    public CourseDto update(Long id, CourseRequest r) {
        Course c = find(id);
        if (!c.getCode().equalsIgnoreCase(r.code().trim()) && courses.existsByCode(r.code().trim())) {
            throw new IllegalStateException("A course with this code already exists");
        }
        if (r.capacity() < enrollments.countByCourseId(id)) {
            throw new IllegalStateException("Capacity cannot be lower than the students already registered");
        }
        return toDto(courses.save(apply(c, r)));
    }

    public void delete(Long id) {
        Course c = find(id);
        if (enrollments.countByCourseId(id) > 0) {
            throw new IllegalStateException("Cannot delete a course that has registered students");
        }
        courses.delete(c);
    }

    private Course find(Long id) {
        return courses.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + id));
    }

    private Course apply(Course c, CourseRequest r) {
        c.setCode(r.code().trim());
        c.setTitle(r.title().trim());
        c.setCredits(r.credits());
        c.setCapacity(r.capacity());
        return c;
    }

    private CourseDto toDto(Course c) {
        return new CourseDto(c.getId(), c.getCode(), c.getTitle(), c.getCredits(), c.getCapacity(),
                enrollments.countByCourseId(c.getId()));
    }
}
