package io.pivotal.pal.tracker;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTimeEntryRepository implements TimeEntryRepository{

    long timeEntityId = 0L;
    Map<Long, TimeEntry> repo = new HashMap<>();

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        timeEntry.setId(++timeEntityId);
        repo.put(new Long(timeEntityId), timeEntry );
        return timeEntry;
    }

    @Override
    public TimeEntry find(long id) {
        return repo.get(new Long(id));
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {

        if(find(id) == null){
            return null;
        }
        repo.remove(new Long(id));
        timeEntry.setId(id);
        repo.put(new Long(id),timeEntry);
        return  timeEntry;
    }

    @Override
    public List<TimeEntry> list() {
        List<TimeEntry> list = new ArrayList();
        for(Long key : repo.keySet()){
            list.add(repo.get(key));
        }
        return list;
    }

    @Override
    public void delete(long id) {
        repo.remove(new Long(id));
    }
}
