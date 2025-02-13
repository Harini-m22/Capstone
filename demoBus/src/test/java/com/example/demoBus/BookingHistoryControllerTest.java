package com.example.demoBus;

import com.example.demoBus.controller.BookingHistoryController;
import com.example.demoBus.model.Booking;
import com.example.demoBus.model.User;
import com.example.demoBus.repository.BookingRepository;
import com.example.demoBus.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BookingHistoryControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private Model model;

    @Mock
    private Principal principal;

    @InjectMocks
    private BookingHistoryController bookingHistoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testViewBookingHistory() {
        User user = new User();
        user.setEmail("user@example.com");

        List<Booking> bookingHistory = new ArrayList<>();
        when(principal.getName()).thenReturn(user.getEmail());
        when(userService.getUserByEmail(user.getEmail())).thenReturn(user);
        when(bookingRepository.findByUser(user)).thenReturn(bookingHistory);

        String viewName = bookingHistoryController.viewBookingHistory(model, principal);

        verify(model, times(1)).addAttribute("bookingHistory", bookingHistory);
        assertEquals("bookingHistory", viewName);
    }
}
