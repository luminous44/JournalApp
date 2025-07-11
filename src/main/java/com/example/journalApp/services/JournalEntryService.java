package com.example.journalApp.services;

import com.example.journalApp.Entity.JournalEntry;
import com.example.journalApp.Repository.JournalEntryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
}
