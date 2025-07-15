package com.example.journalApp.controller;

import com.example.journalApp.Entity.User;
import com.example.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PublicController {
    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String check(){
        return "Okay";
    }


    @PostMapping("/userCreation")
    public boolean createUser(@RequestBody User user){
        userService.saveUser(user);
        return true;
    }
}
