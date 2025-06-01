package com.example.journalApp.controller;

import com.example.journalApp.Entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JounalEntityController {

    HashMap<Long, JournalEntry> journalEntries = new HashMap<>();
    @GetMapping
    public List<JournalEntry> getAll(){
        return new ArrayList<>(journalEntries.values());
    }
    @PostMapping
    public boolean create(@RequestBody JournalEntry contentEntry){

        journalEntries.put(contentEntry.getId(), contentEntry);
        return true;

    }

    @GetMapping("id/{myId}")
    public JournalEntry getUserById(@PathVariable Long myId){
        return journalEntries.get(myId);
    }

    @PutMapping("/uid/{id}")
    public JournalEntry updateJournalById(@PathVariable Long id,@RequestBody JournalEntry entry){

        return journalEntries.put(id,entry);
    }

}
