import datastore.EventDataStore;
import entity.Building;
import entity.Event;
import entity.Room;
import entity.User;
import entity.events.Birthday;
import entity.events.Meeting;
import enums.InviteeStatus;
import org.junit.Before;
import org.junit.Test;
import pojo.EventLocation;
import pojo.Invitees;
import service.IEventService;
import service.impl.EventService;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class CalendarTestService {

    private IEventService eventService;
    private EventDataStore eventDataStore;

    @Before
    public void setUp() {
        eventService = new EventService();
        User user1 = User.builder()
                .userId("1")
                .firstName("Test1")
                .build();
        User user2 = User.builder()
                .userId("2")
                .firstName("Test2").build();
        User user3 = User.builder()
                .userId("3")
                .firstName("Test3").build();
        User user4 = User.builder()
                .userId("4")
                .firstName("Test4").build();
        List<Invitees> invitees1 = Arrays.asList(new Invitees(user1, InviteeStatus.MAYBE),
                new Invitees(user2, InviteeStatus.ACCEPT),
                new Invitees(user3, InviteeStatus.DECLINE));

        List<Invitees> invitees2 = Arrays.asList(new Invitees(user1, InviteeStatus.MAYBE),
                new Invitees(user4, InviteeStatus.ACCEPT),
                new Invitees(user3, InviteeStatus.DECLINE));

        String buildingName1 = "building1", roomName1 = "room1", roomName2 = "room2", roomName3 = "room3";
        Building building1 = new Building(buildingName1);
        building1.getRoomList().addAll(Arrays.asList(new Room(roomName1), new Room(roomName2), new Room(roomName3)));

        Event event1 = new Meeting(invitees1);
        event1.setEventId("1");
        event1.setLocation(EventLocation.builder()
                .buildingName(buildingName1)
                .roomId(roomName1).build());
        event1.setStartTime(LocalDateTime.of(2020, Month.JULY, 3, 11, 30));
        event1.setEndTime(LocalDateTime.of(2020, Month.JULY, 3, 12, 30));
        event1.setOwnerId(user4.getUserId());
        building1.getRoomList().get(0).addEvent(event1);    //adding event to room1

        Event event2 = new Meeting(invitees2);
        event2.setEventId("2");
        event2.setLocation(EventLocation.builder()
                .buildingName(buildingName1)
                .roomId(roomName2).build());
        event2.setStartTime(LocalDateTime.of(2020, Month.JULY, 3, 12, 31));
        event2.setEndTime(LocalDateTime.of(2020, Month.JULY, 3, 13, 30));
        event2.setOwnerId(user2.getUserId());
        building1.getRoomList().get(0).addEvent(event2);    //adding event to room1

        Event event3 = new Birthday();
        event3.setEventId("3");
        event3.setOwnerId(user1.getUserId());
        event3.setStartTime(LocalDateTime.of(2020, Month.JULY, 3, 9, 0));
        event3.setEndTime(LocalDateTime.of(2020, Month.JULY, 3, 21, 0));
        event3.setLocation(EventLocation.builder()
                .buildingName(buildingName1)
                .roomId(roomName3).build());
        building1.getRoomList().get(1).addEvent(event3);    ////adding event to room2

        ConcurrentHashMap<String, Event> eventMap = new ConcurrentHashMap<>();
        eventMap.put(event1.getEventId(), event1);
        eventMap.put(event2.getEventId(), event2);
        eventMap.put(event3.getEventId(), event3);

        ConcurrentHashMap<String, List<Event>> userListMap = new ConcurrentHashMap<>();
        userListMap.put(user1.getUserId(), Arrays.asList(event1, event2, event3));
        userListMap.put(user2.getUserId(), Arrays.asList(event1, event2));
        userListMap.put(user3.getUserId(), Arrays.asList(event1, event2));
        userListMap.put(user4.getUserId(), Arrays.asList(event1, event2));

        ConcurrentHashMap<String, Building> buildingMap = new ConcurrentHashMap<>();
        buildingMap.put(buildingName1, building1);

        eventDataStore = EventDataStore.getInstance();
        eventDataStore.setBuildingMap(buildingMap);
        eventDataStore.setEventMap(eventMap);
        eventDataStore.setUserEventMap(userListMap);
    }

    @Test
    public void testGetEventsForUser() {
        List<Event> eventList = eventService.getEventsForUser("4",
                LocalDateTime.of(2020, Month.JULY, 3, 9, 0),
                LocalDateTime.of(2020, Month.JULY, 3, 21, 31));
        if (null != eventList) {
            System.out.println(eventList);
            assert true;
        } else
            assert false;
    }

    @Test
    public void testInviteesForEvent() {
        List<Invitees> invitees = eventService.getInviteesForEvent("1");
        if (null != invitees) {
            System.out.println(invitees);
            assert true;
        } else
            assert false;
    }

    @Test
    public void testGetRoomsForTimeSlotInBuilding() {
        List<Room> rooms = eventService.getAvailableRoomsForSlot("building1",
                LocalDateTime.of(2020, Month.JULY, 3, 10, 0),
                LocalDateTime.of(2020, Month.JULY, 3, 12, 0));
        if (rooms == null || rooms.isEmpty()) {
            System.out.println("No rooms available");
            assert false;
        } else {
            System.out.println("Rooms available\n" + rooms);
            assert true;
        }
    }
}
