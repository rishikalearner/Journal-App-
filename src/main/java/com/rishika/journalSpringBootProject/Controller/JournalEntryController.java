package com.rishika.journalSpringBootProject.Controller;

import com.rishika.journalSpringBootProject.Entity.JournalEntry;
import com.rishika.journalSpringBootProject.Service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<JournalEntry> all = journalEntryService.getAll();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry){
        try{
            journalEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(journalEntry);
            return new ResponseEntity<>(journalEntry,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){

        Optional<JournalEntry> journalEntry =  journalEntryService.findById(myId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalENtryById(@PathVariable ObjectId myId){
        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
        if(journalEntry.isPresent()){
            journalEntryService.deleteById(myId);
            return new ResponseEntity<>(HttpStatus.GONE);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateEntrById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry){
        JournalEntry old = journalEntryService.findById(myId).orElse(null);
        if(old != null){
                old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
                old.setName(newEntry.getName() != null && !newEntry.getName().equals("") ? newEntry.getName() : old.getName());
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }


}
