package entity;

import enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pojo.EventLocation;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public abstract class Event implements Comparable<Event> {
    private String eventId;
    private String title;
    private EventLocation location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String ownerId;
    private EventType eventType;

    public Event(EventType eventType) {
        this.eventType = eventType;
    }

    @Override
    public int compareTo(Event event) {
        //if first events end time is less than seconds start then it is non-overlapping and it occurs first
        if (this.endTime.compareTo(event.startTime) < 0)
            return -1;
        //if first events start time is greater than seconds end then it is non-overlapping and it occurs after seconds
        else if (this.startTime.compareTo(event.endTime) > 0)
            return 1;
        return 0;                   //else overlapping
    }
}

