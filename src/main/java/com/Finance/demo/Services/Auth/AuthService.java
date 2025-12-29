package com.Finance.demo.Services.Auth;

import com.Finance.demo.Model.Role;
import com.Finance.demo.Model.User;
import com.Finance.demo.Repository.RolesRepository;
import com.Finance.demo.Repository.UserRepository;
import com.Finance.demo.Request.Auth.AuthenticationRequest;
import com.Finance.demo.Request.Auth.RegisterRequest;
import com.Finance.demo.Response.AuthenticationResponse;
import com.Finance.demo.Security.jwt.JwtUtils; // Import this!
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
    private final RolesRepository rolesRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already in use!");
        }
        Role userRole = rolesRepository.findByName("USER")
                .orElseGet(() -> rolesRepository.save(new Role("USER")));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        User user = new User();
        user.setFirstName(request.getFirstname());
        user.setLastName(request.getLastname());


        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(roles);

        userRepository.save(user);


        String jwtToken = jwtUtils.generateToken(user.getEmail());

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();


        String jwtToken = jwtUtils.generateToken(user.getEmail());

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}