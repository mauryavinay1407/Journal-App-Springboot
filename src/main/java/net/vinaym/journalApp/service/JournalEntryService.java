package net.vinaym.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.vinaym.journalApp.entity.JournalEntry;
import net.vinaym.journalApp.entity.User;
import net.vinaym.journalApp.repository.JournalEntryRepository;
import net.vinaym.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry myEntry,String userName){
        try {
            Optional<User> user = userService.findByUserName(userName);
            myEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(myEntry);
            if(user.isPresent()){
                user.get().getJournalEntries().add(saved);
                userService.saveEntry(user.get());
            }
        } catch (Exception e) {
            log.error("Exception ", e);
            throw new RuntimeException("An error occured while saving the entry.",e);
        }
    }

    public void saveEntry(JournalEntry myEntry){
        journalEntryRepository.save(myEntry);
    }

    public List<JournalEntry> getAllEntries(String userName){
        Optional<User> user = userService.findByUserName(userName);
        return user.map(u -> u.getJournalEntries()).orElse(Collections.emptyList());
    }

    public Optional<JournalEntry> getEntryById(ObjectId myId){
        return  journalEntryRepository.findById(myId);
    }

    public void deleteEntryById(ObjectId myId,String userName){
        Optional<User> user = userService.findByUserName(userName);
        if(user.isPresent()){
            user.get().getJournalEntries().removeIf(u->u.getId().equals(myId));
            userService.saveEntry(user.get());
        }
        journalEntryRepository.deleteById(myId);
    }


}
