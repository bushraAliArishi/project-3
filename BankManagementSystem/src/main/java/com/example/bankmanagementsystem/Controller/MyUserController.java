package com.example.bankmanagementsystem.Controller;

import com.example.bankmanagementsystem.Api.ApiResponse;
import com.example.bankmanagementsystem.Model.MyUser;
import com.example.bankmanagementsystem.Service.MyUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class MyUserController {

    private final MyUserService myUserService;


    @PostMapping("/login")
    public ResponseEntity login() {
        return ResponseEntity.status(200).body("Logged in successfully");
    }
    // Admin
    @GetMapping("/all-users")
    public ResponseEntity getAllUsers() {
        return ResponseEntity.status(200).body(myUserService.getAllUsers());
    }

    // Admin
    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(myUserService.getUser(id));
    }

    // All
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid  MyUser newUser) {
        myUserService.registerNewUser(newUser);
        return ResponseEntity.status(201).body(new ApiResponse("User Registered"));
    }

    // User
    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable Integer id, @RequestBody MyUser updatedUser) {
        myUserService.updateUser(updatedUser, id);
        return ResponseEntity.status(200).body(new ApiResponse("User Updated"));
    }

    // Admin
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id) {
        myUserService.deleteUser(id);
        return ResponseEntity.status(200).body(new ApiResponse("User Deleted"));
    }
}
