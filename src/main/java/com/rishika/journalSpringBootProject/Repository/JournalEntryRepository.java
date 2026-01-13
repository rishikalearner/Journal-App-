package com.rishika.journalSpringBootProject.Repository;
import com.rishika.journalSpringBootProject.Entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId>{

}
