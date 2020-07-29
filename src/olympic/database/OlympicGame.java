package olympic.database;

import java.util.ArrayList;

public class OlympicGame {

    private final String game;
    private final String city;
    private final int year;
    private final String season;
    private final ArrayList<String> eventList = new ArrayList<>();

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
