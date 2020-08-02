package olympic.maps;

import olympic.database.OlympicGame;

import java.util.TreeMap;

/**
 * TreeMap of all olympic games and methods to add, update and search
 */
public class OlympicGameMap {

    private final TreeMap<String, OlympicGame> OlympicGameMap = new TreeMap<>();

    OlympicGameMap() {
    }

    public TreeMap<String, OlympicGame> getOlympicGameMap() {
        return OlympicGameMap;
    }

    /**
     * Adds a new Olympic game or updates data of an existing one
     *
     * @param olympicGame Game name
     * @param city        Game city
     * @param year        Game year
     * @param season      Game season
     * @param event       Event name
     */
    public void addOrUpdate(String olympicGame, String city, Integer year, String season, String event) {
        if (OlympicGameMap.containsKey(olympicGame)) {
            OlympicGame currentOlympicGame = OlympicGameMap.get(olympicGame);
            if (!currentOlympicGame.getEventList().contains(event)) {
                currentOlympicGame.addEvent(event);
            }
        } else {
            OlympicGame newOlympicGame = new OlympicGame(city, year, season, event);
            OlympicGameMap.put(year + " " + season, newOlympicGame);
        }

    }

    /**
     * Searches for an Olympic game by game name
     *
     * @param term Name to search for
     * @return Map containing all results matching the search
     */
    public TreeMap<String, OlympicGame> searchOlympicGame(String term) {
        final String upperCaseTerm = term.toUpperCase();

        TreeMap<String, OlympicGame> results = new TreeMap<>();
        OlympicGameMap.forEach((id, olympicGame) -> {
            if (olympicGame.getGame().toUpperCase().startsWith(upperCaseTerm)) {
                results.put(id, olympicGame);
            }
        });

        return results;
    }

    public OlympicGame getOlympicGame(String key) {
        return OlympicGameMap.get(key);
    }
}
