package net.vinaym.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.vinaym.journalApp.entity.JournalEntry;
import net.vinaym.journalApp.entity.User;
import net.vinaym.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveEntry(User myEntry){
        try {
            userRepository.save(myEntry);
        } catch (Exception e) {
            log.error("Exception ", e);
        }
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
