package com.ditisha.scrs.config;

import com.ditisha.scrs.model.AppUser;
import com.ditisha.scrs.model.Course;
import com.ditisha.scrs.repository.AppUserRepository;
import com.ditisha.scrs.repository.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// Creates a demo admin account and sample courses on first run
@Component
public class DataInitializer implements CommandLineRunner {

    private final AppUserRepository users;
    private final CourseRepository courses;
    private final PasswordEncoder encoder;

    public DataInitializer(AppUserRepository users, CourseRepository courses, PasswordEncoder encoder) {
        this.users = users;
        this.courses = courses;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        if (!users.existsByUsername("admin")) {
            users.save(new AppUser("admin", encoder.encode("Admin@123"), "ADMIN"));
        }
        if (courses.count() == 0) {
            add("CS101", "Introduction to Programming", 4, 40);
            add("CS201", "Data Structures", 4, 35);
            add("CS210", "Database Management Systems", 3, 30);
            add("CS220", "Object Oriented Programming with Java", 4, 30);
            add("CS305", "Web Technologies", 3, 25);
            add("CS310", "Operating Systems", 3, 30);
            add("MA101", "Discrete Mathematics", 3, 50);
        }
    }

    private void add(String code, String title, int credits, int capacity) {
        Course c = new Course();
        c.setCode(code);
        c.setTitle(title);
        c.setCredits(credits);
        c.setCapacity(capacity);
        courses.save(c);
    }
}
