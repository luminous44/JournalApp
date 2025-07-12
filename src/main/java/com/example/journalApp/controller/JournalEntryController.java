package com.example.journalApp.controller;

import com.example.journalApp.Entity.JournalEntry;
import com.example.journalApp.services.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journalMap")
public class JournalEntryController {

     
@Autowired
private JournalEntryService journalService;

    @GetMapping
    public ResponseEntity<?>  getAll(){
        List<JournalEntry> all = journalService.getAll();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping
    public  ResponseEntity<?> create(@RequestBody JournalEntry contentEntry){

        try {
            contentEntry.setDate(LocalDateTime.now());
            journalService.saveEntry(contentEntry);
            return new ResponseEntity<>(contentEntry, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }



    }

    @GetMapping("id/{myId}")
    public ResponseEntity<?> getUserById(@PathVariable ObjectId myId){
        Optional<JournalEntry> jEntry = journalService.getById(myId);

        if (jEntry.isPresent()){
            return new ResponseEntity<>(jEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

   @DeleteMapping("id/{myId}")
   public ResponseEntity<?> deleteUserById(@PathVariable ObjectId myId){
        journalService.deleteById(myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }
    @PutMapping("uid/{userId}")
    public  ResponseEntity<?> updateJournalById(@PathVariable ObjectId userId,@RequestBody JournalEntry newEntry){
      JournalEntry old = journalService.getById(userId).orElse(null);
      if (old != null){
          old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle(): old.getTitle());
          old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("")? newEntry.getContent(): old.getContent());
          journalService.saveEntry(old);
          return new ResponseEntity<>(HttpStatus.OK);
      }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
