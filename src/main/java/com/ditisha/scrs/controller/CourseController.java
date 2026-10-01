package com.ditisha.scrs.controller;

import com.ditisha.scrs.dto.Dtos.CourseDto;
import com.ditisha.scrs.dto.Dtos.CourseRequest;
import com.ditisha.scrs.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    // GET /api/courses?q=java&page=0&size=5&sortBy=title&direction=desc
    @GetMapping
    public Page<CourseDto> list(@RequestParam(required = false) String q,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "5") int size,
                                @RequestParam(defaultValue = "code") String sortBy,
                                @RequestParam(defaultValue = "asc") String direction) {
        return service.list(q, page, size, sortBy, direction);
    }

    @PostMapping
    public ResponseEntity<CourseDto> create(@Valid @RequestBody CourseRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public CourseDto update(@PathVariable Long id, @Valid @RequestBody CourseRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
