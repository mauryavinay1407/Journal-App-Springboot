package net.vinaym.journalApp.controller;

import net.vinaym.journalApp.entity.JournalEntry;
import net.vinaym.journalApp.entity.User;
import net.vinaym.journalApp.service.JournalEntryService;
import net.vinaym.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

   @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User newUser){
       try{
           userService.saveEntry(newUser);
           return new ResponseEntity<>(true,HttpStatus.CREATED);
       } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
   }

   @GetMapping
    public ResponseEntity<?> getAll(){
        List<User> AllUsers = userService.getAllUsers();
        if(AllUsers != null && !AllUsers.isEmpty()){
            return new ResponseEntity<>(AllUsers,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user,@PathVariable String userName){
       Optional<User> existingUser = userService.findByUserName(userName);
       if(existingUser.isPresent()){
           existingUser.get().setUserName(user.getUserName());
           existingUser.get().setPassword(user.getPassword());
           userService.saveEntry(existingUser.get());
       }
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
