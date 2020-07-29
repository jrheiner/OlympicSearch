package olympic.database;

import java.util.ArrayList;

public class Event {
    private final String discipline;
    private final ArrayList<String> olympicGameList = new ArrayList<>();
    private String event;

    public Event(String event, String sport, String olympicGame) {
        this.event = event;
        this.discipline = sport;
        this.olympicGameList.add(olympicGame);
    }

    public String getDiscipline() {
        return discipline;
    }

    public void addOlympicGame(String olympicGame) {
        olympicGameList.add(olympicGame);
    }

    public ArrayList<String> getOlympicGameList() {
        return olympicGameList;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}

