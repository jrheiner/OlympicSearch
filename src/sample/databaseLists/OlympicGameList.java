package sample.databaseLists;

import sample.database.OlympicGame;

import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

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

    public TreeMap<String, OlympicGame> searchOlympicGame(String term, TreeMap<String, OlympicGame> searchMap) {
        final String upperCaseTerm = term.toUpperCase();
        long startTime = System.nanoTime();

        TreeMap<String, OlympicGame> results = new TreeMap<>();
        searchMap.forEach((id, olympicGame) -> {
            if (olympicGame.getGame().toUpperCase().startsWith(upperCaseTerm)) {
                results.put(id, olympicGame);
            }
        });

        long endTime = System.nanoTime() - startTime;
        System.out.printf("Found %d result(s) in %d ms\n", results.size(), TimeUnit.MILLISECONDS.convert(endTime, TimeUnit.NANOSECONDS));
        return results;
    }

    public OlympicGame getOlympicGame(String key) {
        return OlympicGameMap.get(key);
    }
}
