package com.rishika.journalSpringBootProject.Controller;

import com.rishika.journalSpringBootProject.Entity.JournalEntry;
import com.rishika.journalSpringBootProject.Entity.User;
import com.rishika.journalSpringBootProject.Service.JournalEntryService;
import com.rishika.journalSpringBootProject.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping({"/journal"})
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @GetMapping({"{userName}"})
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName) {
        User user = this.userService.findByUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();
        return all != null && !all.isEmpty() ? new ResponseEntity(all, HttpStatus.OK) : new ResponseEntity(all, HttpStatus.NOT_FOUND);
    }

    @PostMapping({"{userName}"})
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry, @PathVariable String userName) {
        try {
            this.journalEntryService.saveEntry(journalEntry, userName);
            return new ResponseEntity(journalEntry, HttpStatus.CREATED);
        } catch (Exception var4) {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping({"id/{myId}"})
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
        Optional<JournalEntry> journalEntry = this.journalEntryService.findById(myId);
        return journalEntry.isPresent() ? new ResponseEntity((JournalEntry)journalEntry.get(), HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping({"id/{userName}/{myId}"})
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId, @PathVariable String userName) {
        this.journalEntryService.deleteById(myId, userName);
        return new ResponseEntity(HttpStatus.GONE);
    }
    // Start
    @PutMapping("id/{userName}/{id}")
    public ResponseEntity<?> updateJournalById(
            @PathVariable ObjectId id,
            @RequestBody JournalEntry newEntry,
            @PathVariable String userName
    ) {
        JournalEntry old = journalEntryService.findById(id).orElse(null);
        if (old != null) {
            old.setTitle(
                    newEntry.getTitle() != null && !newEntry.getTitle().equals("")
                            ? newEntry.getTitle()
                            : old.getTitle()
            );
            old.setContent(
                    newEntry.getContent() != null && !newEntry.getContent().equals("")
                            ? newEntry.getContent()
                            : old.getContent()
            );
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    //End
}