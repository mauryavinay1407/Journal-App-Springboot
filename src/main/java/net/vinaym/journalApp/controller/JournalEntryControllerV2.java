package net.vinaym.journalApp.controller;

import net.vinaym.journalApp.entity.JournalEntry;
import net.vinaym.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry myEntry){
        journalEntryService.saveEntry(myEntry);
        return true;
    }

    @GetMapping
    public List<JournalEntry> getEntries(){
        return journalEntryService.getAllEntries();
    }

    @GetMapping("id/{myId}")
    public  JournalEntry getEntry(@PathVariable ObjectId myId){
        return journalEntryService.getEntryById(myId).orElse(null);
    }
    @DeleteMapping("id/{myId}")
    public boolean deleteEntry(@PathVariable ObjectId myId){
         journalEntryService.deleteEntryById(myId);
         return true;
    }

    @PutMapping("id/{myId}")
    public JournalEntry updateEntry(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry){
        JournalEntry old = journalEntryService.getEntryById(myId).orElse(null);
        if(old != null){
            old.setTitle(newEntry.getTitle()!= null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent()!= null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
        }
        journalEntryService.saveEntry(old);
        return old;
    }
}
