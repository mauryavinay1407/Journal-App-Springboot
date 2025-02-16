package net.vinaym.journalApp.service;

import net.vinaym.journalApp.entity.JournalEntry;
import net.vinaym.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntry myEntry){
        myEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(myEntry);
    }

    public List<JournalEntry> getAllEntries(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getEntryById(ObjectId myId){
        return  journalEntryRepository.findById(myId);
    }

    public void deleteEntryById(ObjectId myId){
          journalEntryRepository.deleteById(myId);
    }


}
