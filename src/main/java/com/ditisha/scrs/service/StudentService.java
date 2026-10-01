package com.ditisha.scrs.service;

import com.ditisha.scrs.dto.Dtos.StudentDto;
import com.ditisha.scrs.repository.EnrollmentRepository;
import com.ditisha.scrs.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class StudentService {

    private static final Set<String> SORT_FIELDS = Set.of("id", "fullName", "email");

    private final StudentRepository students;
    private final EnrollmentRepository enrollments;

    public StudentService(StudentRepository students, EnrollmentRepository enrollments) {
        this.students = students;
        this.enrollments = enrollments;
    }

    public Page<StudentDto> list(int page, int size, String sortBy, String direction) {
        String field = SORT_FIELDS.contains(sortBy) ? sortBy : "fullName";
        Sort.Direction dir = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        return students.findAll(PageRequest.of(Math.max(page, 0), Math.min(Math.max(size, 1), 50), Sort.by(dir, field)))
                .map(s -> {
                    List<String> codes = enrollments.findByStudentId(s.getId()).stream()
                            .map(e -> e.getCourse().getCode()).sorted().toList();
                    return new StudentDto(s.getId(), s.getFullName(), s.getEmail(), s.getUser().getUsername(),
                            codes.size(), codes);
                });
    }
}
