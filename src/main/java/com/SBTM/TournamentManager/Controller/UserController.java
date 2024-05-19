package com.SBTM.TournamentManager.Controller;

import com.SBTM.TournamentManager.DTO.UserDto;
import com.SBTM.TournamentManager.Model.User;
import com.SBTM.TournamentManager.Security.JwtUtil;
import com.SBTM.TournamentManager.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {

        if (userService.getUserByUsername(userDto.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        }
        String encodedPassword = new BCryptPasswordEncoder().encode(userDto.getPassword());
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(encodedPassword);
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto, HttpServletResponse response) {
        User user = userService.getUserByUsername(userDto.getUsername());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        if (!new BCryptPasswordEncoder().matches(userDto.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        String token = jwtUtil.generateToken(userDto.getUsername());

        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(3600);
        cookie.setPath("/");

        response.addCookie(cookie);

        return ResponseEntity.ok("Login successful");
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}