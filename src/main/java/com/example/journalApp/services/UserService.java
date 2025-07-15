package com.example.journalApp.services;
import com.example.journalApp.Entity.User;
import com.example.journalApp.Repository.userRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private userRepo userRepo;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public void saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("user"));
        userRepo.save(user);

    }
    public void saveNewUser(User user){
        userRepo.save(user);

    }

    public List<User> getAll(){
        return userRepo.findAll();
    }
    public Optional<User> getById(ObjectId id){
        return userRepo.findById(String.valueOf(id));
    }

    public void deleteById(ObjectId id){
        userRepo.deleteById(String.valueOf(id));
    }
    public  User findByUserName(String userName){
        return userRepo.findByuserName(userName);
    }
}
