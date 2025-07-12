package com.example.journalApp.controller;

import com.example.journalApp.Entity.JournalEntry;
import com.example.journalApp.services.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<JournalEntry> getUserById(@PathVariable ObjectId myId){
         Optional<JournalEntry> entry = journalService.getById(myId);
         if (entry.isPresent()){
             return  new ResponseEntity<>(entry.get(), HttpStatus.OK);
         }

         return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);


    }

   @DeleteMapping("id/{myId}")
   public boolean deleteUserById(@PathVariable ObjectId myId){
        journalService.deleteById(myId);
        return true;
   }
    @PutMapping("uid/{userId}")
    public JournalEntry updateJournalById(@PathVariable ObjectId userId,@RequestBody JournalEntry newEntry){
      JournalEntry old = journalService.getById(userId).orElse(null);
      if (old != null){
          old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle(): old.getTitle());
          old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("")? newEntry.getContent(): old.getContent());
      }
      journalService.saveEntry(old);
      return old;
    }

}
