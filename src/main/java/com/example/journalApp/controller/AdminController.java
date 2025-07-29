package com.example.journalApp.controller;

import com.example.journalApp.Entity.User;
import com.example.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @GetMapping("/allUsers")
    public ResponseEntity<?> getAllUser() {
        List<User> all = userService.getAll();
        if (!all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("createAdmin")
    public boolean createAdmin(@RequestBody User user){

        return  userService.saveAdmin(user);
    }

}
