package com.example.journalApp.services;

import com.example.journalApp.Entity.JournalEntry;
import com.example.journalApp.Entity.User;
import com.example.journalApp.Repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepo journalRepo;
    @Autowired
    private  UserService userService;

    public void saveEntry(JournalEntry journalEntry,String userName){
        User user = userService.findByUserName(userName);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saveEntry = journalRepo.save(journalEntry);
        user.getEntries().add(saveEntry);
        userService.saveUser(user);

    }
    public void saveEntry(JournalEntry journalEntry){
        journalRepo.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalRepo.findAll();
    }
    public Optional<JournalEntry> getById(ObjectId id){
        return journalRepo.findById(String.valueOf(id));
    }

    public void deleteById(ObjectId id,String userName){
        User user = userService.findByUserName(userName);
        if (user == null) {
            throw new RuntimeException("User not found with username: " + userName);
        }
        user.getEntries().removeIf(x-> x.getId().equals(id));
        userService.saveUser(user);
        journalRepo.deleteById(String.valueOf(id));
    }
}
