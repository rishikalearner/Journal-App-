package com.rishika.journalSpringBootProject.Controller;

import com.rishika.journalSpringBootProject.Entity.JournalEntry;
import com.rishika.journalSpringBootProject.Entity.User;
import com.rishika.journalSpringBootProject.Service.UserService;
import jakarta.websocket.server.PathParam;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserEntryController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAll();
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<User> getUserById(@PathVariable ObjectId myId){

        Optional<User> user =  userService.findById(myId);
        if(user.isPresent()){
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public void createUser(@RequestBody User user){
        userService.saveEntry(user);
    }

//    @PutMapping("/{userName}")
//    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String userName){
//        User userInDB = userService.findByUserName(user.getUserName());
//        if(userInDB!=null){
//            userInDB.setUserName(user.getUserName());
//            userInDB.setPassword(userInDB.getPassword());
//            userService.saveEntry(userInDB);
//        }
//        return new ResponseEntity<>(HttpStatus.ACCEPTED);
//    }
// java
@PutMapping("/{userName}")
public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable String userName) {
    User userInDB = userService.findByUserName(userName);
    if (userInDB == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Apply updates only if provided
    if (user.getUserName() != null && !user.getUserName().isBlank()) {
        userInDB.setUserName(user.getUserName());
    }
    if (user.getPassword() != null && !user.getPassword().isBlank()) {
        userInDB.setPassword(user.getPassword());
    }

    userService.saveEntry(userInDB);
    return ResponseEntity.ok(userInDB);
}


}
