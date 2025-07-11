package com.example.journalApp.controller;

import com.example.journalApp.Entity.JournalEntry;
import com.example.journalApp.services.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/journalMap")
public class JournalEntryController {

     
@Autowired
private JournalEntryService journalService;

    @GetMapping
    public List<JournalEntry> getAll(){

        return null;
    }
    @PostMapping
    public boolean create(@RequestBody JournalEntry contentEntry){
        journalService.saveEntry(contentEntry);
        return true;

    }

    @GetMapping("id/{myId}")
    public JournalEntry getUserById(@PathVariable Long myId){
        return null;
    }

    @PutMapping("/uid/{userId}")
    public JournalEntry updateJournalById(@PathVariable Long userId,@RequestBody JournalEntry entry){

        return null;
    }

}
