package com.example.journalApp.controller;

import com.example.journalApp.Entity.JournalEntry;
import com.example.journalApp.Entity.User;
import com.example.journalApp.services.JournalEntryService;
import com.example.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

     
@Autowired
private JournalEntryService journalService;
@Autowired
private UserService userService;

    @GetMapping
    public ResponseEntity<?>  getAllJournalEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
       User user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getEntries();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping
    public  ResponseEntity<?> create(@RequestBody JournalEntry contentEntry){

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            contentEntry.setDate(LocalDateTime.now());
            journalService.saveEntry(contentEntry,userName);
            return new ResponseEntity<>(contentEntry, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }



    }

    @GetMapping("{objId}")
    public ResponseEntity<?> getUserById(@PathVariable ObjectId objId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        if (user!=null){
            JournalEntry journalEntry = user.getEntries().stream().filter(x -> x.getId().equals(objId)).findFirst().orElse(null);
            if (journalEntry != null){
                return new ResponseEntity<>(journalEntry, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

   @DeleteMapping("/{objId}")
   public ResponseEntity<?> deleteUserById(@PathVariable ObjectId objId){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean removed = journalService.deleteById(objId,userName);
        if (removed){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }
    @PutMapping("/{objId}")
    public  ResponseEntity<?> updateJournalById(@PathVariable ObjectId objId,@RequestBody JournalEntry newEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        if (user!=null){
            JournalEntry journalEntry = user.getEntries().stream().filter(x -> x.getId().equals(objId)).findFirst().orElse(null);
            if (journalEntry != null){
                    journalEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle(): journalEntry.getTitle());
                    journalEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("")? newEntry.getContent(): journalEntry.getContent());
                    journalService.saveEntry(journalEntry);
                    return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}
