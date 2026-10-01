package com.ditisha.scrs.service;

import com.ditisha.scrs.dto.Dtos.MeDto;
import com.ditisha.scrs.dto.Dtos.RegisterRequest;
import com.ditisha.scrs.model.AppUser;
import com.ditisha.scrs.model.Student;
import com.ditisha.scrs.repository.AppUserRepository;
import com.ditisha.scrs.repository.StudentRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final AppUserRepository users;
    private final StudentRepository students;
    private final PasswordEncoder encoder;

    public AuthService(AppUserRepository users, StudentRepository students, PasswordEncoder encoder) {
        this.users = users;
        this.students = students;
        this.encoder = encoder;
    }

    @Transactional
    public void register(RegisterRequest r) {
        if (users.existsByUsername(r.username())) {
            throw new IllegalStateException("Username is already taken");
        }
        if (students.existsByEmail(r.email())) {
            throw new IllegalStateException("Email is already registered");
        }
        AppUser user = users.save(new AppUser(r.username(), encoder.encode(r.password()), "STUDENT"));
        Student s = new Student();
        s.setFullName(r.fullName());
        s.setEmail(r.email());
        s.setUser(user);
        students.save(s);
    }

    public MeDto me(String username, String role) {
        String name = students.findByUserUsername(username).map(Student::getFullName).orElse(username);
        return new MeDto(username, role, name);
    }
}
