package net.vinaym.journalApp.controller;

import net.vinaym.journalApp.entity.JournalEntry;
import net.vinaym.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry){
        try {
            journalEntryService.saveEntry(myEntry);
            return new ResponseEntity<>(true,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getEntries(){
        List<JournalEntry> AllEntry = journalEntryService.getAllEntries();
        if(AllEntry != null && !AllEntry.isEmpty()){
            return new ResponseEntity<>(AllEntry,HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getEntry(@PathVariable ObjectId myId){
        Optional<JournalEntry> journalEntry = journalEntryService.getEntryById(myId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myId){
         journalEntryService.deleteEntryById(myId);
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry){
        JournalEntry old = journalEntryService.getEntryById(myId).orElse(null);
        if(old != null){
            old.setTitle(newEntry.getTitle()!= null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent()!= null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
