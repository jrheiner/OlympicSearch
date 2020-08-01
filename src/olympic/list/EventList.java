package olympic.list;

import olympic.database.Event;

import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

/**
 * TreeMap of all events and methods to add, update and search
 */
public class EventList {
    private final TreeMap<String, Event> EventMap = new TreeMap<>();

    EventList() {
    }

    /**
     * Adds new or updates existing event in the internal database.
     *
     * @param event       Event name
     * @param sport       Event discipline
     * @param olympicGame Name of the olympic game
     */
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

    /**
     * Searches for events by name or partial name
     *
     * @param term Event name or partial name to search for
     * @return Map containing all results matching the search
     */
    public TreeMap<String, Event> searchEvent(String term) {
        final String upperCaseTerm = term.toUpperCase();
        long startTime = System.nanoTime();

        TreeMap<String, Event> results = new TreeMap<>();
        EventMap.forEach((id, event) -> {
            if (event.getEvent().toUpperCase().contains(upperCaseTerm)) {
                results.put(id, event);
            }
        });

        long endTime = System.nanoTime() - startTime;
        System.out.printf("Found %d result(s) in %d ms\n", results.size(), TimeUnit.MILLISECONDS.convert(endTime, TimeUnit.NANOSECONDS));
        return results;
    }

    /**
     * Gets a set of all unique disciplines in the event map
     *
     * @return Set containing disciplines
     */
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
