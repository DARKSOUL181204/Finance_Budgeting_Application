package com.Finance.demo.Services.Auth;

import com.Finance.demo.Model.Role;
import com.Finance.demo.Model.User;
import com.Finance.demo.Repository.RolesRepository;
import com.Finance.demo.Repository.UserRepository;
import com.Finance.demo.Request.Auth.AuthenticationRequest;
import com.Finance.demo.Request.Auth.RegisterRequest;
import com.Finance.demo.Response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RolesRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        // 1. Check if user already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already in use!");
        }

        // 2. Fetch the 'USER' role from the database
        // NOTE: Your database MUST have a role with name 'USER' inserted already.
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Error: Role 'USER' is not found."));

        // 3. Create the User object manually (No Builder)
        User user = new User();
        user.setFirstName(request.getFirstname());
        user.setLastName(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // 4. Set Roles (Initialize the Set)
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);

        // 5. Save User
        userRepository.save(user);

        // 6. Return success response
        return AuthenticationResponse.builder()
                .token("User registered successfully")
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // 1. Authenticate using Spring Security Manager
        // This line throws an exception if password is wrong
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // 2. If we get here, the user is valid. (Optional: Fetch user details if needed for JWT later)
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        // 3. Return success response
        return AuthenticationResponse.builder()
                .token("Login Successful")
                .build();
    }
}