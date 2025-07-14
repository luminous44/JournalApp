package com.example.journalApp.controller;

import com.example.journalApp.Entity.User;
import com.example.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class userController {
    @Autowired
    private UserService userService;
    @GetMapping
    public List<User> getAllUse(){
        return userService.getAll();
    }
    @PutMapping
    public boolean createUser(@RequestBody User user){
        userService.saveUser(user);
        return true;
    }
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){

        User userDb = userService.findByUserName(user.getUserName());
        if (userDb.getUserName() != null){
            userDb.setUserName(user.getUserName());
            userDb.setPassword(user.getPassword());
            userService.saveUser(userDb);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


}
