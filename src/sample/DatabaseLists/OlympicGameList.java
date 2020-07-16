package sample.DatabaseLists;

import sample.Database.OlympicGame;

import java.util.TreeMap;

public class OlympicGameList {

    private final TreeMap<String, OlympicGame> OlympicGameMap = new TreeMap<>();

    public OlympicGameList() {
    }

    public TreeMap<String, OlympicGame> getOlympicGameMap() {
        return OlympicGameMap;
    }

    public void addOrUpdate(String olympicGame, String city, Integer year, String season, String event) {
        if (OlympicGameMap.containsKey(olympicGame)) {
            OlympicGame currentOlympicGame = OlympicGameMap.get(olympicGame);
            if (!currentOlympicGame.getEventList().contains(event)) {
                currentOlympicGame.addEvent(event);
            }
        } else {
            OlympicGame newOlympicGame = new OlympicGame(olympicGame, city, year, season, event);
            OlympicGameMap.put(olympicGame, newOlympicGame);
        }

    }

    public OlympicGame getOlympicGame(String key) {
        return OlympicGameMap.get(key);
    }
}
