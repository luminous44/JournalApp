package com.example.journalApp.services;

import com.example.journalApp.Entity.JournalEntry;
import com.example.journalApp.Repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepo journalRepo;

    public void saveEntry(JournalEntry journalEntry){
        journalRepo.save(journalEntry);

    }

    public List<JournalEntry> getAll(){
        return journalRepo.findAll();
    }
    public Optional<JournalEntry> getById(ObjectId id){
        return journalRepo.findById(String.valueOf(id));
    }

    public void deleteById(ObjectId id){
        journalRepo.deleteById(String.valueOf(id));
    }
}
