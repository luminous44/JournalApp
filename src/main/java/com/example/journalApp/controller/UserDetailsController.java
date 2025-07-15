package com.example.journalApp.controller;

import com.example.journalApp.Entity.User;
import com.example.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserDetailsController {
    @Autowired
    private UserService userService;

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uerName = authentication.getName();
        User userDb = userService.findByUserName(uerName);
        if (userDb.getUserName() != null) {
            userDb.setUserName(user.getUserName());
            userDb.setPassword(user.getPassword());
            userService.saveUser(userDb);
            return new ResponseEntity<>(userDb,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
