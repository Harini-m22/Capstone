package com.example.demoBus.controller;


import com.example.demoBus.model.Booking;
import com.example.demoBus.model.User;
import com.example.demoBus.repository.BookingRepository;
import com.example.demoBus.repository.UserRepository;
import com.example.demoBus.service.UserService;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/profile")
public class UserProfileController {

    @Autowired
    private UserService userService;

    // Display user profile
    @GetMapping("")
    public String viewProfile(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.getUserByEmail(email);
        model.addAttribute("user", user);

        return "profile"; // Now profile.html does not contain booking history
    }

    // Edit profile page
    @GetMapping("/edit")
    public String editProfile(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.getUserByEmail(email);
        model.addAttribute("user", user);
        return "editProfile";
    }

    @PostMapping("/edit")
    public String updateProfile(@ModelAttribute("user") User updatedUser, Principal principal, Model model) {
        String email = principal.getName();
        User user = userService.getUserByEmail(email);
        user.setName(updatedUser.getName());

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            user.setPassword(updatedUser.getPassword());
        }

        userService.updateUser(user);
        model.addAttribute("user", user);
        model.addAttribute("message", "Profile updated successfully!");
        return "profile";
    }
}
