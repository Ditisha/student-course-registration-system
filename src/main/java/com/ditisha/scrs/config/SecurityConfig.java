package com.ditisha.scrs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CSRF is off to keep this demo simple; enable it for a production app
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login.html", "/register.html", "/css/**", "/api/auth/register",
                                "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/students").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/courses").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/courses/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/courses/**").hasRole("ADMIN")
                        .requestMatchers("/api/enrollments/**").hasRole("STUDENT")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login.html")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/index.html", true)
                        .failureUrl("/login.html?error=true")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login.html"))
                // API calls get a 401 instead of a redirect to the login page
                .exceptionHandling(ex -> ex.defaultAuthenticationEntryPointFor(
                        new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED), new AntPathRequestMatcher("/api/**")));
        return http.build();
    }
}
