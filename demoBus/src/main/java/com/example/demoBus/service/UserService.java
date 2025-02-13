package com.example.demoBus.service;


import com.example.demoBus.model.User;
import com.example.demoBus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        // In production, encrypt the password before saving.
        return userRepository.save(user);
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    // Authenticate user by comparing plain-text passwords (for demonstration only)
    public User authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if(user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void updateUser(User user) {
    }

}

//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    public User registerUser(User user) {
//        // In production, encrypt the password before saving!
//        return userRepository.save(user);
//    }
//
//    public boolean emailExists(String email) {
//        return userRepository.findByEmail(email) != null;
//    }
//
//    // Authenticate user credentials (for demonstration only; use encryption in production)
//    public User authenticateUser(String email, String password) {
//        User user = userRepository.findByEmail(email);
//        if (user != null && user.getPassword().equals(password)) {
//            return user;
//        }
//        return null;
//    }
//
//    public User findByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }
//
//}

//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    public User registerUser(User user) {
//        // For production: encrypt the password using BCrypt or similar.
//        return userRepository.save(user);
//    }
//
//    public boolean emailExists(String email) {
//        return userRepository.findByEmail(email) != null;
//    }
//}

