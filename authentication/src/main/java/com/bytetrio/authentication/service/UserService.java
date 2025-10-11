package com.bytetrio.authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bytetrio.authentication.config.TokenConfig;
import com.bytetrio.authentication.model.User;
import com.bytetrio.authentication.repository.UserRepository;

@Service
public class UserService {
    private UserRepository userRepo;
    @Autowired
    private void setService(UserRepository userRepo) {this.userRepo = userRepo;}

    private AuthenticationManager authenticationManager;
    @Autowired
    private void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    private TokenConfig config;
    @Autowired
    private void setConfig(TokenConfig config) {this.config = config;}

    public ResponseEntity<String> signUp(User user) {
        
        if (userRepo.existsById(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already present plz SignIn...");
        }
    
        user.setPassword(new BCryptPasswordEncoder(12).encode(user.getPassword()))
            .setRole("ROLE_" + user.getRole().toUpperCase());

        userRepo.save(user);

        return userRepo.existsById(user.getUsername())? 
            ResponseEntity.status(HttpStatus.ACCEPTED).body("Sign Up is success plz signIn") : 
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("SignUp is not possible...");

    }

    public ResponseEntity<String> signIn(User user) {
        String token = null;

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        if (authentication.isAuthenticated()) {
            token = config.generateToken(user.getUsername(), authentication.getAuthorities());
            return ResponseEntity.status(HttpStatus.OK).body(token);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(token);

    }

    public ResponseEntity<User> getUser(String username) {
        return ResponseEntity.status(HttpStatus.OK).body(userRepo.findById(username).orElse(null));
    }

}
