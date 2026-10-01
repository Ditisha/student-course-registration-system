package com.ditisha.scrs.controller;

import com.ditisha.scrs.dto.Dtos.EnrollmentDto;
import com.ditisha.scrs.service.EnrollmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService service;

    public EnrollmentController(EnrollmentService service) {
        this.service = service;
    }

    @GetMapping("/my")
    public List<EnrollmentDto> mine(Authentication auth) {
        return service.myEnrollments(auth.getName());
    }

    @PostMapping("/{courseId}")
    public ResponseEntity<Void> enroll(@PathVariable Long courseId, Authentication auth) {
        service.enroll(auth.getName(), courseId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> drop(@PathVariable Long courseId, Authentication auth) {
        service.drop(auth.getName(), courseId);
        return ResponseEntity.noContent().build();
    }
}
