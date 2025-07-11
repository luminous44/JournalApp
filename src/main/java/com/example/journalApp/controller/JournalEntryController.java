package com.example.journalApp.controller;

import com.example.journalApp.Entity.JournalEntry;
import com.example.journalApp.services.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
        return journalService.getAll();
    }
    @PostMapping
    public JournalEntry create(@RequestBody JournalEntry contentEntry){
        contentEntry.setDate(LocalDateTime.now());
        journalService.saveEntry(contentEntry);
        return contentEntry;

    }

    @GetMapping("id/{myId}")
    public JournalEntry getUserById(@PathVariable ObjectId myId){
        return journalService.getById(myId).orElse(null);
    }

   @DeleteMapping("id/{myId}")
   public boolean deleteUserById(@PathVariable ObjectId myId){
        journalService.deleteById(myId);
        return true;
   }
    @PutMapping("/uid/{userId}")
    public JournalEntry updateJournalById(@PathVariable ObjectId id,@RequestBody JournalEntry newEntry){
      JournalEntry old = journalService.getById(id).orElse(null);
      if (old != null){
          old.setTitle(newEntry.getTitle() != null && newEntry.getTitle().equals("") ? newEntry.getTitle(): old.getTitle());
          old.setContent(newEntry.getContent() != null && newEntry.getContent().equals("")? newEntry.getContent(): old.getContent());
      }
      journalService.saveEntry(old);
      return old;
    }

}
