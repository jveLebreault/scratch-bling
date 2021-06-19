package com.scratchbling.controller;

import com.scratchbling.dto.TokenInfo;
import com.scratchbling.service.JwtTokenService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private JwtTokenService tokenService;

    @Autowired
    private UserDetailsService userDetailsService;

    private PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @ExceptionHandler
    public ResponseEntity handle(UsernameNotFoundException exception) {
        Map<String, String> error = Map.of("message", "Invalid Credentials");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @PostMapping("/auth")
    public ResponseEntity<?> authenticateUser(@RequestParam String user, @RequestParam String password) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(user);
        if (encoder.matches(password, userDetails.getPassword())) {
            TokenInfo token = tokenService.getTokenFor(userDetails);
            return ResponseEntity.ok().body(token);
        } else {
            Map<String, String> error = Map.of("message", "Invalid Credentials");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
        }
    }
}
