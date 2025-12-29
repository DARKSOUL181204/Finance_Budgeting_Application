package com.Finance.demo.Controller;

import com.Finance.demo.Repository.UserRepository;
import com.Finance.demo.DTO.RegisterDto;
import com.Finance.demo.Model.User;
import com.Finance.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {

        // 1. Check if email already exists
        if (userRepository.findByEmail(registerDto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email is already in use!");
        }

        // 2. Create the new user object
        User newUser = new User();
        newUser.setFirstName(registerDto.getFirstName());
        newUser.setLastName(registerDto.getLastName());
        newUser.setEmail(registerDto.getEmail());

        // 3. IMPORTANT: Hash the password using BCrypt!
        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());
        newUser.setPassword(encodedPassword);

        // 4. Save to Database
        userRepository.save(newUser);

        return ResponseEntity.ok("User registered successfully!");
    }
}