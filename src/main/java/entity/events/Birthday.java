package entity.events;

import entity.Event;
import enums.EventType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Birthday extends Event {
    public Birthday() {
        super(EventType.BIRTHDAY);
    }
}
