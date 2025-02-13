package com.example.demoBus;

import com.example.demoBus.controller.UserAuthenticationController;
import com.example.demoBus.model.User;
import com.example.demoBus.security.JwtUtil;
import com.example.demoBus.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserAuthenticationControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private Model model;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private UserAuthenticationController userAuthenticationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginUser_Success() {
        User user = new User();
        user.setEmail("user@example.com");

        when(userService.authenticateUser("user@example.com", "password")).thenReturn(user);
        when(jwtUtil.generateToken(user.getEmail())).thenReturn("mock-token");

        String viewName = userAuthenticationController.loginUser("user@example.com", "password", model, response);

        verify(model, times(1)).addAttribute("user", user);
        assertEquals("dashboard", viewName);
    }
}
