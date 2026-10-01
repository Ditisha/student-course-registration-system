package com.ditisha.scrs.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public final class Dtos {

    private Dtos() {}

    public record CourseDto(Long id, String code, String title, int credits, int capacity, long enrolled) {}

    public record CourseRequest(
            @NotBlank(message = "Course code is required") String code,
            @NotBlank(message = "Title is required") String title,
            @Min(value = 1, message = "Credits must be at least 1") int credits,
            @Min(value = 1, message = "Capacity must be at least 1") int capacity) {}

    public record RegisterRequest(
            @NotBlank @Size(min = 3, max = 30, message = "Username must be 3-30 characters") String username,
            @NotBlank @Size(min = 6, message = "Password must be at least 6 characters") String password,
            @NotBlank(message = "Full name is required") String fullName,
            @NotBlank @Email(message = "Enter a valid email address") String email) {}

    public record EnrollmentDto(Long courseId, String code, String title, int credits, LocalDate enrolledOn) {}

    public record StudentDto(Long id, String fullName, String email, String username, long courses, List<String> courseCodes) {}

    public record MeDto(String username, String role, String fullName) {}
}
