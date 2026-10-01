package com.ditisha.scrs.controller;

import com.ditisha.scrs.dto.Dtos.StudentDto;
import com.ditisha.scrs.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public Page<StudentDto> list(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "5") int size,
                                 @RequestParam(defaultValue = "fullName") String sortBy,
                                 @RequestParam(defaultValue = "asc") String direction) {
        return service.list(page, size, sortBy, direction);
    }
}
