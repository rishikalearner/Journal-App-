package com.rishika.journalSpringBootProject.Repository;
import com.rishika.journalSpringBootProject.Entity.JournalEntry;
import com.rishika.journalSpringBootProject.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId>{
    User findByUserName(String userName);

}
