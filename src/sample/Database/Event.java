package sample.Database;

import java.util.ArrayList;
import java.util.List;

public class Event {

    private String event;
    private String sport;
    private List<String> olympicGameList = new ArrayList<>();

    public Event(String event, String sport, String olympicGame) {
        this.event = event;
        this.sport = sport;
        this.olympicGameList.add(olympicGame);
    }


    public void addOlympicGame(String olympicGame) {
        olympicGameList.add(olympicGame);
    }

    public List<String> getOlympicGameList() {
        return olympicGameList;
    }

    public void setOlympicGameList(ArrayList<String> olympicGameList) {
        this.olympicGameList = olympicGameList;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

}

