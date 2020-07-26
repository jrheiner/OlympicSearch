package olympic.list;

import olympic.database.Event;

import java.util.TreeMap;
import java.util.TreeSet;
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
            if (event.getEvent().toUpperCase().contains(upperCaseTerm)) {
                results.put(id, event);
            }
        });

        long endTime = System.nanoTime() - startTime;
        System.out.printf("Found %d result(s) in %d ms\n", results.size(), TimeUnit.MILLISECONDS.convert(endTime, TimeUnit.NANOSECONDS));
        return results;
    }

    public TreeSet<String> getDisciplines() {
        TreeSet<String> disciplineSet = new TreeSet<>();
        EventMap.forEach((s, event) -> disciplineSet.add(event.getDiscipline()));
        return disciplineSet;
    }

    public Event getEvent(String key) {
        return EventMap.get(key);
    }

    public TreeMap<String, Event> getEventMap() {
        return EventMap;
    }
}
