package net.vinaym.journalApp.controller;

import net.vinaym.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    private Map<Long,JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAll(){
        return new ArrayList<>(journalEntries.values());
    }
    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry myEntry){
        journalEntries.put(myEntry.getId(),myEntry);
        return true;
    }

    @GetMapping("id/{journalId}")
    public JournalEntry getJournalEntryById(@PathVariable Long journalId){
        return journalEntries.get(journalId);
    }

    @DeleteMapping("id/{journalId}")
    public JournalEntry deleteJournalEntryById(@PathVariable Long journalId){
        return  journalEntries.remove(journalId);
    }

    @PutMapping("id/{journalId}")
    public JournalEntry updateJournalEntryById(@PathVariable Long journalId,@RequestBody JournalEntry updatedEntry){
         journalEntries.put(journalId,updatedEntry);
        return journalEntries.get(journalId);
    }
}
