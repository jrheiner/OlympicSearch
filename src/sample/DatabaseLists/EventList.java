package sample.DatabaseLists;

import sample.Database.Event;

import java.util.TreeMap;

public class EventList {
    private static final TreeMap<String, Event> EventMap = new TreeMap<>();

    public EventList() {
    }

    public static void addOrUpdate(String event, String sport, String olympicGame) {
        if (EventMap.containsKey(event)) {
            Event currentEvent = EventMap.get(event);
            if (!currentEvent.getOlympicGameList().contains(olympicGame)) {
                currentEvent.addOlympicGame(olympicGame);
            }
        } else {
            Event newEvent = new Event(event, sport, olympicGame);
            EventMap.put(event, newEvent);
        }
    }

    public static Event getEvent(String key) {
        return EventMap.get(key);
    }

    public TreeMap<String, Event> getEventMap() {
        return EventMap;
    }
}
