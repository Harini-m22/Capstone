package com.example.demoBus;

import com.example.demoBus.controller.UserRegistrationController;
import com.example.demoBus.model.User;
import com.example.demoBus.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserRegistrationControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Model model;

    @InjectMocks
    private UserRegistrationController userRegistrationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        User user = new User();
        user.setEmail("newuser@example.com");

        when(userService.emailExists(user.getEmail())).thenReturn(false);
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = userRegistrationController.registerUser(user, bindingResult, model);

        verify(userService, times(1)).registerUser(user);
        assertEquals("redirect:/login", viewName);
    }

    @Test
    void testRegisterUser_Fail_EmailExists() {
        User user = new User();
        user.setEmail("existing@example.com");

        when(userService.emailExists(user.getEmail())).thenReturn(true);

        String viewName = userRegistrationController.registerUser(user, bindingResult, model);

        verify(bindingResult, times(1)).rejectValue("email", "error.user", "An account already exists for this email.");
        assertEquals("register", viewName);
    }
}
