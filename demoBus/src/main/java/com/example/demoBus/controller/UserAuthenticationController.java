package com.example.demoBus.controller;


import com.example.demoBus.model.User;
import com.example.demoBus.security.JwtUtil;
import com.example.demoBus.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;




@Controller
public class UserAuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    // Display the login form.
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    // Process login form submission.
    @PostMapping("/login")
    public String loginUser(@RequestParam("email") String email,
                            @RequestParam("password") String password,
                            Model model,
                            HttpServletResponse response) {
        User user = userService.authenticateUser(email, password);
        if (user == null) {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
        // Generate the JWT token.
        String token = jwtUtil.generateToken(user.getEmail());

        // Set the JWT token in an HttpOnly cookie.
        Cookie cookie = new Cookie("JWT_TOKEN", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(86400); // 1 day (in seconds)
        response.addCookie(cookie);

        // Pass user and token to the dashboard view.
        model.addAttribute("user", user);
        model.addAttribute("token", token);
        return "dashboard";
    }
}

//@Controller
//public class UserAuthenticationController {
//
//    @Autowired
//    private UserService userService;
//
//    // Display the login form
//    @GetMapping("/login")
//    public String showLoginForm() {
//        return "login";
//    }
//
//    // Process login form submission
//    @PostMapping("/login")
//    public String loginUser(@RequestParam("email") String email,
//                            @RequestParam("password") String password,
//                            Model model) {
//        User user = userService.authenticateUser(email, password);
//        if (user == null) {
//            model.addAttribute("error", "Invalid email or password");
//            return "login";
//        }
//        // Generate JWT token for the authenticated user
//        String token = JwtUtil.generateToken(user.getEmail());
//        model.addAttribute("token", token);
//        model.addAttribute("user", user);
//        return "dashboard";
//    }
//}
