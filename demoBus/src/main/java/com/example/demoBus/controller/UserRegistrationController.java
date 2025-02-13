// src/main/java/com/example/demoBus/controller/UserRegistrationController.java
package com.example.demoBus.controller;

import com.example.demoBus.model.User;
import com.example.demoBus.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    // Display the registration page
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Handle registration form submission
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult bindingResult,
                               Model model) {
        // Server-side check for duplicate email
        if (userService.emailExists(user.getEmail())) {
            bindingResult.rejectValue("email", "error.user", "An account already exists for this email.");
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

        userService.registerUser(user);

        // Redirect to login page after successful registration
        return "redirect:/login";
    }

    // REST API endpoint for user registration
    @RestController
    @RequestMapping("/api")
    public static class UserRegistrationRestController {

        @Autowired
        private UserService userService;

        @PostMapping("/register")
        public ResponseEntity<?> registerUserApi(@Valid @RequestBody User user, BindingResult bindingResult) {
            if (userService.emailExists(user.getEmail())) {
                bindingResult.rejectValue("email", "error.user", "An account already exists for this email.");
            }

            if (bindingResult.hasErrors()) {
                // Return validation errors in the response
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
            }

            userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        }
    }
}
