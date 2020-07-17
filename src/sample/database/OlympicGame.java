package sample.database;

import java.util.ArrayList;

public class OlympicGame {

    private String game;
    private String city;
    private int year;
    private String season;
    private ArrayList<String> eventList = new ArrayList<>();

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

    public void setEventList(ArrayList<String> eventList) {
        this.eventList = eventList;
    }

    public void addEvent(String event) {
        eventList.add(event);
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }
}
