package entity.events;

import entity.Event;
import enums.EventType;
import lombok.*;

@Getter
@Setter
public class Reminder extends Event {
    public Reminder() {
        super(EventType.REMINDER);
    }
}
