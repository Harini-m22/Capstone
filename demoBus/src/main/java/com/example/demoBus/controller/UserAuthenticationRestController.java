package com.example.demoBus.controller;


import com.example.demoBus.model.User;
import com.example.demoBus.security.JwtUtil;
import com.example.demoBus.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserAuthenticationRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> loginUserApi(@RequestBody LoginRequest loginRequest,
                                          BindingResult bindingResult,
                                          HttpServletResponse response) {
        if(bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }
        User user = userService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
        if(user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
        String token = jwtUtil.generateToken(user.getEmail());

        // Set HttpOnly cookie with the token.
        Cookie cookie = new Cookie("JWT_TOKEN", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(86400);
        response.addCookie(cookie);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    // DTO classes for the login payload and JWT response.
    public static class LoginRequest {
        private String email;
        private String password;

        // Getters and Setters.
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class JwtResponse {
        private String token;
        public JwtResponse(String token) { this.token = token; }
        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
    }
}
//
//@RestController
//@RequestMapping("/api")
//public class UserAuthenticationRestController {
//
//    @Autowired
//    private UserService userService;
//
//    @PostMapping("/login")
//    public ResponseEntity<?> loginUserApi(@RequestBody LoginRequest loginRequest) {
//        User user = userService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
//        if (user == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
//        }
//        String token = JwtUtil.generateToken(user.getEmail());
//        return ResponseEntity.ok(new JwtResponse(token));
//    }
//
//    // Helper classes for the login payload and response
//    public static class LoginRequest {
//        private String email;
//        private String password;
//
//        // Getters and Setters
//        public String getEmail() { return email; }
//        public void setEmail(String email) { this.email = email; }
//        public String getPassword() { return password; }
//        public void setPassword(String password) { this.password = password; }
//    }
//
//    public static class JwtResponse {
//        private String token;
//
//        public JwtResponse(String token) {
//            this.token = token;
//        }
//        public String getToken() { return token; }
//        public void setToken(String token) { this.token = token; }
//    }
//}
