package com.example.demoBus.controller;

import com.example.demoBus.model.Booking;
import com.example.demoBus.model.BusRoute;
import com.example.demoBus.model.User;
import com.example.demoBus.repository.BookingRepository;
import com.example.demoBus.repository.BusRouteRepository;
import com.example.demoBus.repository.UserRepository;
import com.example.demoBus.service.BookingService;
import com.example.demoBus.service.BusRouteService;
import com.example.demoBus.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BusRouteRepository busRouteRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserService userService;

    // Existing method: Show booking form for a selected bus route.
    @GetMapping("/{routeId}")
    public String showBookingForm(@PathVariable("routeId") Long routeId, Model model) {
        BusRoute route = busRouteRepository.findById(routeId).orElse(null);
        if (route == null) {
            model.addAttribute("error", "Bus route not found");
            return "error";
        }
        Booking booking = new Booking();
        booking.setOrigin(route.getOrigin());
        booking.setDestination(route.getDestination());
        booking.setDepartureTime(route.getDepartureTime());
        booking.setArrivalTime(route.getArrivalTime());
        booking.setPrice(route.getPrice());

        model.addAttribute("booking", booking);
        return "bookingForm";
    }

    // Existing method: Process new booking.
    @PostMapping("")
    public String processBooking(@ModelAttribute("booking") Booking booking, Model model, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        booking.setUser(user);
        bookingRepository.save(booking);
        model.addAttribute("booking", booking);
        return "bookingConfirmation";
    }

    // NEW: Display the edit form for a booking.
    @GetMapping("/edit/{bookingId}")
    public String editBookingForm(@PathVariable("bookingId") Long bookingId, Model model, Principal principal) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null) {
            model.addAttribute("error", "Booking not found.");
            return "error";
        }
        User user = userService.getUserByEmail(principal.getName());
        if (!booking.getUser().getId().equals(user.getId())) {
            model.addAttribute("error", "You are not authorized to edit this booking.");
            return "error";
        }
        model.addAttribute("booking", booking);
        return "editBooking";
    }

    // NEW: Process booking update.
    @PostMapping("/edit")
    public String updateBooking(@ModelAttribute("booking") Booking booking, Model model, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        Booking existingBooking = bookingRepository.findById(booking.getId()).orElse(null);
        if (existingBooking == null) {
            model.addAttribute("error", "Booking not found.");
            return "error";
        }
        if (!existingBooking.getUser().getId().equals(user.getId())) {
            model.addAttribute("error", "You are not authorized to edit this booking.");
            return "error";
        }
        // Update only the editable fields (for example, passengerName and seatPreference)
        existingBooking.setPassengerName(booking.getPassengerName());
        existingBooking.setSeatPreference(booking.getSeatPreference());
        bookingRepository.save(existingBooking);
        return "redirect:/profile";
    }

    // NEW: Delete a booking.
    @GetMapping("/delete/{bookingId}")
    public String deleteBooking(@PathVariable("bookingId") Long bookingId, Model model, Principal principal) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null) {
            model.addAttribute("error", "Booking not found.");
            return "error";
        }
        User user = userService.getUserByEmail(principal.getName());
        if (!booking.getUser().getId().equals(user.getId())) {
            model.addAttribute("error", "You are not authorized to delete this booking.");
            return "error";
        }
        bookingRepository.delete(booking);
        return "redirect:/bookingHistory";
    }
    // NEW: Download ticket endpoint
    @GetMapping("/download/{bookingId}")
    public void downloadTicket(@PathVariable("bookingId") Long bookingId, Principal principal, HttpServletResponse response) throws IOException {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Booking not found.");
            return;
        }
        User user = userService.getUserByEmail(principal.getName());
        if (!booking.getUser().getId().equals(user.getId())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Not authorized to download this ticket.");
            return;
        }

        // Generate ticket content (you can modify this format as needed)
        StringBuilder ticketContent = new StringBuilder();
        ticketContent.append("Ticket for Booking ID: " + booking.getId() + "\n");
        ticketContent.append("Route: " + booking.getOrigin() + " to " + booking.getDestination() + "\n");
        ticketContent.append("Departure: " + booking.getDepartureTime() + "\n");
        ticketContent.append("Arrival: " + booking.getArrivalTime() + "\n");
        ticketContent.append("Passenger: " + booking.getPassengerName() + "\n");
        ticketContent.append("Seat Preference: " + booking.getSeatPreference() + "\n");
        ticketContent.append("Number of Seats: " + booking.getNumberOfSeats() + "\n");
        ticketContent.append("Total Amount: " + booking.getTotalAmount() + "\n");
        ticketContent.append("Co-Passenger(s): " + booking.getCoPassengerNames() + "\n");

        // Set the response headers and content type
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment; filename=ticket_" + booking.getId() + ".txt");
        try (OutputStream out = response.getOutputStream()) {
            out.write(ticketContent.toString().getBytes(StandardCharsets.UTF_8));
            out.flush();
        }
    }
}