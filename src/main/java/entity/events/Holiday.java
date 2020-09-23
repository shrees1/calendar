package entity.events;

import entity.Event;
import enums.EventType;

public class Holiday extends Event {
    public Holiday() {
        super(EventType.HOLIDAY);
    }
}
