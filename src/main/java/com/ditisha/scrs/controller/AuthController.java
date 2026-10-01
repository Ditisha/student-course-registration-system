package com.ditisha.scrs.controller;

import com.ditisha.scrs.dto.Dtos.MeDto;
import com.ditisha.scrs.dto.Dtos.RegisterRequest;
import com.ditisha.scrs.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/api/auth/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest request) {
        service.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/api/me")
    public MeDto me(Authentication auth) {
        String role = auth.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
        return service.me(auth.getName(), role);
    }
}
