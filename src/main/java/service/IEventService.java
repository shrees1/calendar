package service;

import entity.Event;
import entity.Room;
import pojo.Invitees;

import java.time.LocalDateTime;
import java.util.List;

public interface IEventService {
    boolean createEvent();
    List<Event> getEventsForUser(String userId, LocalDateTime start, LocalDateTime end);
    List<Invitees> getInviteesForEvent(String eventId);
    List<Room> getAvailableRoomsForSlot(String buildingName, LocalDateTime start, LocalDateTime end);
}
