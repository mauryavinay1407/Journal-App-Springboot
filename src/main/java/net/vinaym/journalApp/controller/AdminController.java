package net.vinaym.journalApp.controller;

import net.vinaym.journalApp.entity.User;
import net.vinaym.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAll() {
        List<User> AllUsers = userService.getAllUsers();
        if (AllUsers != null && !AllUsers.isEmpty()) {
            return new ResponseEntity<>(AllUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public void createAdmin(@RequestBody User user){
        userService.saveNewAdmin(user);
    }
}
