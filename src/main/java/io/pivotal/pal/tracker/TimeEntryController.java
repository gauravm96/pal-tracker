package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    @Autowired
    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(){

    }
    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository=timeEntryRepository;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry outputBody = timeEntryRepository.create(timeEntryToCreate);
        ResponseEntity<TimeEntry> createResponse = new ResponseEntity<>(outputBody, HttpStatus.CREATED);

        return createResponse;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable(value = "id")long timeEntryId) {
        ResponseEntity<TimeEntry> readResponse = null;
        TimeEntry outputBody = timeEntryRepository.find(timeEntryId);
        if(outputBody == null){
            readResponse = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return readResponse;
        }
        readResponse = new ResponseEntity<>(outputBody, HttpStatus.OK);

        return readResponse;
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> outputList = timeEntryRepository.list();
        ResponseEntity<List<TimeEntry>> listResponse = new ResponseEntity<>(outputList, HttpStatus.OK);

        return listResponse;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable("id")long timeEntryId, @RequestBody TimeEntry timeEntry) {
        TimeEntry updated = timeEntryRepository.update(timeEntryId,timeEntry);
        if( updated == null ){
            return new ResponseEntity<TimeEntry>(HttpStatus.NOT_FOUND);

        }
        ResponseEntity<TimeEntry> updateResponse = new ResponseEntity<>(updated,HttpStatus.OK);
        return updateResponse;
    }

    @DeleteMapping(value= "/{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable("id") long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
