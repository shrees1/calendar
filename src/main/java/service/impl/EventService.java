package service.impl;

import datastore.EventDataStore;
import entity.Building;
import entity.Event;
import entity.Room;
import entity.events.Meeting;
import pojo.Invitees;
import service.IEventService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class EventService implements IEventService {

    private EventDataStore eventDataStore;

    public EventService() {
        this.eventDataStore = EventDataStore.getInstance();
    }

    @Override
    public boolean createEvent() {
        return false;
    }

    @Override
    public List<Event> getEventsForUser(String userId, LocalDateTime start, LocalDateTime end) {
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("userId is invalid");
        }

        if (start == null || end == null || start.isAfter(end)) {
            throw new IllegalArgumentException("start or end time are invalid");
        }

        ConcurrentHashMap<String, List<Event>> userEventMap = eventDataStore.getUserEventMap();
        if (userEventMap.isEmpty() || userEventMap.get(userId) == null) {
            throw new IllegalArgumentException("No events found for the user");
        }

        return userEventMap.get(userId).stream().filter(event ->
                event.getStartTime().isAfter(start) && event.getEndTime().isBefore(end))
                .collect(Collectors.toList());
    }

    @Override
    public List<Invitees> getInviteesForEvent(String eventId) {
        if (null == eventId || eventId.isEmpty()) {
            throw new IllegalArgumentException("Event id is invalid");
        }

        ConcurrentHashMap<String, Event> eventMap = eventDataStore.getEventMap();
        if (eventMap.isEmpty() || null == eventMap.get(eventId)) {
            throw new IllegalArgumentException("No Event is found for this id " + eventId);
        }
        Event event = eventMap.get(eventId);
        if (!(event instanceof Meeting)) {
            throw new IllegalArgumentException("Event type is not meeting, therefor no invitees");
        }
        return ((Meeting) event).getInvitees();
    }

    @Override
    public List<Room> getAvailableRoomsForSlot(String buildingName, LocalDateTime start, LocalDateTime end) {
        if (buildingName == null || buildingName.isEmpty()) {
            throw new IllegalArgumentException("building name is invalid");
        }

        if (start == null || end == null || start.isAfter(end)) {
            throw new IllegalArgumentException("start or end is invalid");
        }

        ConcurrentHashMap<String, Building> buildingMap = eventDataStore.getBuildingMap();
        if (buildingMap.isEmpty() || buildingMap.get(buildingName) == null) {
            throw new IllegalArgumentException("building name doesn't exists");
        }

        return buildingMap.get(buildingName).getRoomList().stream()
                .filter(room -> room.canAddEvent(start, end))
                .collect(Collectors.toList());
    }
}
