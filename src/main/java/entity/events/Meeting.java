package entity.events;

import entity.Event;
import enums.EventType;
import lombok.Getter;
import lombok.Setter;
import pojo.Invitees;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Meeting extends Event {

    private List<Invitees> invitees;

    public Meeting(List<Invitees> invitees) {
        super(EventType.MEETING);
        this.invitees = invitees;
    }
}
