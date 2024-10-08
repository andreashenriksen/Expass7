package com.example.demo.controllers;

import com.example.demo.domains.PollManager;
import com.example.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private PollManager pollManager;

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = pollManager.addUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping
    public ResponseEntity<Collection<User>> getAllUsers() {
        return ResponseEntity.ok(pollManager.getAllUsers());
    }
}
