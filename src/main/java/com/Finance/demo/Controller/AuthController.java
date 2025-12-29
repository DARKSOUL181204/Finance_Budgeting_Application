package com.Finance.demo.Controller;

import com.Finance.demo.Request.Auth.AuthenticationRequest;
import com.Finance.demo.Request.Auth.RegisterRequest;
import com.Finance.demo.Response.AuthenticationResponse;
import com.Finance.demo.Services.Auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth") // Must match SecurityConfig Matchers!
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        // We delegate the logic to the Service
        // The Service will: Check email, Hash password, Save User, Generate Token
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}