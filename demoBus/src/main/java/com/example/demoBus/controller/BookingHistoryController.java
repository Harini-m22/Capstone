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
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bookingHistory")
public class BookingHistoryController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookingRepository bookingRepository;

    // Display booking history
    @GetMapping("")
    public String viewBookingHistory(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.getUserByEmail(email);

        List<Booking> bookingHistory = bookingRepository.findByUser(user);
        model.addAttribute("bookingHistory", bookingHistory);

        return "bookingHistory"; // Redirects to a separate booking history page
    }
}
