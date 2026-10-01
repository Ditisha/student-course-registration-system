package com.ditisha.scrs.repository;

import com.ditisha.scrs.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

    // Search by title or code, with pagination and sorting supplied by Pageable
    Page<Course> findByTitleContainingIgnoreCaseOrCodeContainingIgnoreCase(String title, String code, Pageable pageable);

    boolean existsByCode(String code);
}
