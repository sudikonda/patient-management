package com.pm.authservice.service;

import com.pm.authservice.dto.LoginRequestDTO;
import com.pm.authservice.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }


    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO) {
        LOGGER.info("Authenticating user {}", loginRequestDTO.getEmail());

        Optional<String> token = userService
                .findByEmail(loginRequestDTO.getEmail())
                .filter(userEntity -> passwordEncoder.matches(loginRequestDTO.getPassword(), userEntity.getPassword()))
                .map(u -> jwtUtil.generateToken(u.getEmail(), u.getRole()));

        LOGGER.info("Authenticated user {}", loginRequestDTO.getEmail());
        return token;

    }

    public boolean validateToken(String token) {
        LOGGER.info("Validating token {}", token);
        try {
            jwtUtil.validateToken(token);
            return true;
        } catch (Exception e) {
            LOGGER.error("Token validation error", e);
        }
        return false;
    }
}
