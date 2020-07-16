package sample.databaseLists;

import sample.database.Event;

import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class EventList {
    private final TreeMap<String, Event> EventMap = new TreeMap<>();

    public EventList() {
    }

    public void addOrUpdate(String event, String sport, String olympicGame) {
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

    public TreeMap<String, Event> searchEvent(String term, TreeMap<String, Event> searchMap) {
        final String upperCaseTerm = term.toUpperCase();
        long startTime = System.nanoTime();

        TreeMap<String, Event> results = new TreeMap<>();
        searchMap.forEach((id, event) -> {
            if (event.getEvent().toUpperCase().startsWith(upperCaseTerm)) {
                results.put(id, event);
            }
        });

        long endTime = System.nanoTime() - startTime;
        System.out.printf("Found %d result(s) in %d ms\n", results.size(), TimeUnit.MILLISECONDS.convert(endTime, TimeUnit.NANOSECONDS));
        return results;
    }

    public Event getEvent(String key) {
        return EventMap.get(key);
    }

    public TreeMap<String, Event> getEventMap() {
        return EventMap;
    }
}
