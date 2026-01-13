package com.rishika.journalSpringBootProject.Service;

import com.rishika.journalSpringBootProject.Entity.JournalEntry;
import com.rishika.journalSpringBootProject.Repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService extends JournalEntry{
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntry journalEntity) {
        journalEntryRepository.save(journalEntity);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId myId){
        return journalEntryRepository.findById(myId);
    }

    public void deleteById(ObjectId myId){
         journalEntryRepository.deleteById(myId);
    }

}