package com.bytetrio.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bytetrio.authentication.model.User;
import com.bytetrio.authentication.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService service;
    @Autowired
    private void setService(UserService service) {this.service = service;}

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody User user) {
        return service.signUp(user);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody User user) {
        return service.signIn(user);
    }

    @GetMapping("/get-user")
    public ResponseEntity<User> getUser(@RequestParam String username) {
        return service.getUser(username);
    }

}
