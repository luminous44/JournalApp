package com.example.journalApp.services;

import com.example.journalApp.Entity.User;
import com.example.journalApp.Repository.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomeUserDetailsService implements UserDetailsService {

    @Autowired
    private userRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userD = userRepo.findByuserName(username);

        if(userD != null){
            UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(userD.getUserName())
                    .password(userD.getPassword())
                    .roles(userD.getRoles().toArray(new String[0]))
                    .build();
            return  userDetails;
        }

        throw  new UsernameNotFoundException("User not found with username: "+username);
    }
}
