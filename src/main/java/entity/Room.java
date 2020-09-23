package entity;

import entity.events.Birthday;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@ToString
public class Room {

    private String roomId;
    private Set<Event> events;         //set of events in the room, this will contain non-overlapping events

    public Room(String roomId) {
        this.roomId = roomId;
        events = new TreeSet<>();
    }

    public boolean addEvent(Event event) {
        if (events.contains(event))
            return false;
        return events.add(event);
    }

    public boolean canAddEvent(LocalDateTime startTime, LocalDateTime endTime){
        Event dummyEvent = new Birthday();
        dummyEvent.setStartTime(startTime);
        dummyEvent.setEndTime(endTime);
        if(events.contains(dummyEvent))
            return false;
        return true;
    }

    public boolean removeEvent(String id) {
        return events.removeIf(event -> event.getEventId().equals(id));
    }
}
