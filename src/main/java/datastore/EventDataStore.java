package datastore;

import entity.Building;
import entity.Event;
import entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class EventDataStore {

    private static EventDataStore eventDataStoreInst;

    private ConcurrentHashMap<String, Building> buildingMap = new ConcurrentHashMap<>();    //buildingName to buildings map
    private ConcurrentHashMap<String, List<Event>> userEventMap = new ConcurrentHashMap<>();    //userId to events map
    private ConcurrentHashMap<String, Event> eventMap = new ConcurrentHashMap<>();              //eventId to events map
    private ConcurrentHashMap<String, User> userMap = new ConcurrentHashMap<>();                //userId to user map

    public static EventDataStore getInstance() {
        if (eventDataStoreInst == null) {
            synchronized (EventDataStore.class) {
                if (eventDataStoreInst == null) {
                    eventDataStoreInst = new EventDataStore();
                }
            }
        }
        return eventDataStoreInst;
    }
}
