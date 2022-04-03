package ru.geekbrains.gainanov.market.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.gainanov.market.api.AppError;
import ru.geekbrains.gainanov.market.api.JwtRequest;
import ru.geekbrains.gainanov.market.api.JwtResponse;
import ru.geekbrains.gainanov.market.api.SignInUserDto;
import ru.geekbrains.gainanov.market.auth.entities.User;
import ru.geekbrains.gainanov.market.auth.services.UserService;
import ru.geekbrains.gainanov.market.auth.utils.JwtTokenUtil;


@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Incorrect username or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> createAuthToken(@RequestBody SignInUserDto signInUserDto) {
        if (!signInUserDto.getPassword().equals(signInUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.BAD_REQUEST.value(), "Passwords doesn't match"), HttpStatus.BAD_REQUEST
            );
        }

        if (userService.findByUsername(signInUserDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.BAD_REQUEST.value(), "User with username: " + signInUserDto.getUsername() + " already exists"), HttpStatus.BAD_REQUEST
            );
        }
        User user = new User();
        user.setUsername(signInUserDto.getUsername());
        user.setEmail(signInUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(signInUserDto.getPassword()));
        userService.createUser(user);

        UserDetails userDetails = userService.loadUserByUsername(signInUserDto.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
