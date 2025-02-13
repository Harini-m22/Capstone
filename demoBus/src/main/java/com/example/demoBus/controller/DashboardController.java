package com.example.demoBus.controller;



import com.example.demoBus.model.Booking;
import com.example.demoBus.model.User;
import com.example.demoBus.repository.BookingRepository;
import com.example.demoBus.service.UserService;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model, Principal principal) {
        // principal.getName() returns the user's email (from JWT)
        String email = principal.getName();
        User user = userService.getUserByEmail(email);
        model.addAttribute("user", user);
        return "dashboard";
    }
}
//
//@Controller
//public class DashboardController {
//
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/dashboard")
//    public String showDashboard(Model model, Principal principal) {
//        // principal.getName() should contain the user's email
//        String email = principal.getName();
//        User user = userService.getUserByEmail(email);
//        model.addAttribute("user", user);
//        // Optionally, if you want to include the JWT token,
//        // you can add it here if you stored it in the session or elsewhere.
//        return "dashboard";
//    }
//}
