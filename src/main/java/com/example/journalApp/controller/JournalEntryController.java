package com.example.journalApp.controller;

import com.example.journalApp.Entity.JournalEntry;
import com.example.journalApp.Entity.User;
import com.example.journalApp.services.JournalEntryService;
import com.example.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{userName}")
    public ResponseEntity<?>  getAllJournalEntriesOfUser(@PathVariable String userName){
       User user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getEntries();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/{userName}")
    public  ResponseEntity<?> create(@RequestBody JournalEntry contentEntry, @PathVariable String userName){

        try {
            contentEntry.setDate(LocalDateTime.now());
            journalService.saveEntry(contentEntry,userName);
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

   @DeleteMapping("id/{userName}/{objId}")
   public ResponseEntity<?> deleteUserById(@PathVariable String userName,@PathVariable ObjectId objId){
        journalService.deleteById(objId,userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }
    @PutMapping("id/{userName}/{objId}")
    public  ResponseEntity<?> updateJournalById(@PathVariable String userName,@PathVariable ObjectId objId,@RequestBody JournalEntry newEntry){
      JournalEntry old = journalService.getById(objId).orElse(null);
      if (old != null){
          old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle(): old.getTitle());
          old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("")? newEntry.getContent(): old.getContent());
          journalService.saveEntry(old);
          return new ResponseEntity<>(HttpStatus.OK);
      }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
