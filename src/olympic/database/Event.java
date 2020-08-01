package olympic.database;

import java.util.ArrayList;

/**
 * Event data structure
 */
public class Event {
    private final String discipline;
    private final ArrayList<String> olympicGameList = new ArrayList<>();
    private final String event;

    /**
     * Create new event
     *
     * @param event       Event name
     * @param sport       Event discipline
     * @param olympicGame Game
     */
    public Event(String event, String sport, String olympicGame) {
        this.event = event;
        this.discipline = sport;
        this.olympicGameList.add(olympicGame);
    }

    public String getDiscipline() {
        return discipline;
    }

    /**
     * Adds new Olympic game to the game list of an event
     *
     * @param olympicGame Olympic game object
     */
    public void addOlympicGame(String olympicGame) {
        olympicGameList.add(olympicGame);
    }

    public ArrayList<String> getOlympicGameList() {
        return olympicGameList;
    }

    public String getEvent() {
        return event;
    }
}

