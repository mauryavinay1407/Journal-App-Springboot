package net.vinaym.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.vinaym.journalApp.entity.JournalEntry;
import net.vinaym.journalApp.entity.User;
import net.vinaym.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveNewUser(User myEntry){
        try {
            myEntry.setPassword(passwordEncoder.encode(myEntry.getPassword()));
            myEntry.setRoles(Arrays.asList("USER"));
            userRepository.save(myEntry);
        } catch (Exception e) {
            log.error("Exception ", e);
        }
    }

    public void saveEntry(User myEntry){
        userRepository.save(myEntry);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId myId){
        return userRepository.findById(myId);
    }

    public void deleteById(ObjectId myId){
        userRepository.deleteById(myId);
    }

    public Optional<User> findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }
}
