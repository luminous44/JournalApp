package com.example.journalApp.controller;

import com.example.journalApp.Entity.User;
import com.example.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserDetailsController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUse(){
        return userService.getAll();
    }
    @PostMapping
    public boolean createUser(@RequestBody User user){
        userService.saveUser(user);
        return true;
    }
    @PutMapping("/{uerName}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable String uerName){

        User userDb = userService.findByUserName(uerName);
        if (userDb.getUserName() != null){
            userDb.setUserName(user.getUserName());
            userDb.setPassword(user.getPassword());
            userService.saveUser(userDb);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
