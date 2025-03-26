package net.vinaym.journalApp.controller;

import net.vinaym.journalApp.entity.User;
import net.vinaym.journalApp.repository.UserRepository;
import net.vinaym.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Optional<User> userInDB = userService.findByUserName((userName));
        if(userInDB.isPresent()){
            userInDB.get().setUserName(user.getUserName());
            userInDB.get().setPassword(user.getPassword());
            userService.saveNewUser(userInDB.get());
        }
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String userName = authentication.getName();
       userRepository.deleteByUserName(userName);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}