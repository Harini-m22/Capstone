package com.example.demoBus;

import com.example.demoBus.controller.BookingController;
import com.example.demoBus.model.Booking;
import com.example.demoBus.model.User;
import com.example.demoBus.repository.BookingRepository;
import com.example.demoBus.repository.BusRouteRepository;
import com.example.demoBus.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BookingControllerTest {

    @Mock
    private BusRouteRepository busRouteRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private Principal principal;

    @InjectMocks
    private BookingController bookingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessBooking() {
        User user = new User();
        user.setEmail("user@example.com");

        Booking booking = new Booking();
        when(principal.getName()).thenReturn(user.getEmail());
        when(userService.getUserByEmail(user.getEmail())).thenReturn(user);

        String viewName = bookingController.processBooking(booking, model, principal);

        verify(bookingRepository, times(1)).save(booking);
        assertEquals("bookingConfirmation", viewName);
    }

    @Test
    void testEditBookingForm_BookingNotFound() {
        when(bookingRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        String viewName = bookingController.editBookingForm(1L, model, principal);

        assertEquals("error", viewName);
    }

    @Test
    void testDeleteBooking() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setId(1L);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setId(1L);

        when(bookingRepository.findById(1L)).thenReturn(java.util.Optional.of(booking));
        when(principal.getName()).thenReturn(user.getEmail());
        when(userService.getUserByEmail(user.getEmail())).thenReturn(user);

        String viewName = bookingController.deleteBooking(1L, model, principal);

        verify(bookingRepository, times(1)).delete(booking);
        assertEquals("redirect:/profile", viewName);
    }
}
