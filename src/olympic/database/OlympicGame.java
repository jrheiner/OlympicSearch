package olympic.database;

import java.util.ArrayList;

/**
 * Olympic game data structure
 */
public class OlympicGame {
    private final String game;
    private final String city;
    private final int year;
    private final String season;
    private final ArrayList<String> eventList = new ArrayList<>();

    /**
     * Creates new olympic game
     *
     * @param game   Game name
     * @param city   Game city
     * @param year   Game year
     * @param season Game season
     * @param event  Game event
     */
    public OlympicGame(String game, String city, int year, String season, String event) {
        this.game = game;
        this.city = city;
        this.year = year;
        this.season = season;
        this.eventList.add(event);
    }

    public ArrayList<String> getEventList() {
        return eventList;
    }

    /**
     * Add new event to the event list of an olympic game
     *
     * @param event Event object
     */
    public void addEvent(String event) {
        eventList.add(event);
    }

    public String getCity() {
        return city;
    }

    public String getGame() {
        return game;
    }
}
