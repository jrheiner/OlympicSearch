package olympic.database;

import java.util.ArrayList;

public class Event {

    private String event;
    private String discipline;
    private ArrayList<String> olympicGameList = new ArrayList<>();

    public Event(String event, String sport, String olympicGame) {
        this.event = event;
        this.discipline = sport;
        this.olympicGameList.add(olympicGame);
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public void addOlympicGame(String olympicGame) {
        olympicGameList.add(olympicGame);
    }

    public ArrayList<String> getOlympicGameList() {
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


}

