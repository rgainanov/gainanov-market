package ru.geekbrains.gainanov.market.core.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.gainanov.market.api.JwtRequest;
import ru.geekbrains.gainanov.market.api.JwtResponse;
import ru.geekbrains.gainanov.market.api.StringResponse;
import ru.geekbrains.gainanov.market.core.services.UserService;
import ru.geekbrains.gainanov.market.core.utils.JwtTokenUtil;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Incorrect username or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping("/secured")
    public String helloSecurity() {
        return "Hello";
    }

    @GetMapping("/auth_check")
    public StringResponse authCheck(Principal principal) {
        return new StringResponse(principal.getName());
    }
}
