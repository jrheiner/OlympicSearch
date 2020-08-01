package olympic.utility;

import olympic.list.ListReference;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

/**
 * Utility class for interaction with the internal database and its values
 */
public class ListUtility {
    /**
     * Parse arraylist to string format ready to be displayed on the ui
     *
     * @param arrayList Arraylist containing numeric athlete data
     * @return Single string of array values
     */
    public static String arrayToStringDisplay(ArrayList<?> arrayList) {
        String arrayString = arrayList.toString();
        return arrayString.substring(1, arrayString.length() - 1).replaceAll("-1.0|-1", "Not available");
    }

    /**
     * Search in a set by key or partial key
     *
     * @param key     Key or partial key to search for
     * @param baseSet Set to search in
     * @return Set containing all values matching the search
     */
    public static TreeSet<String> searchInSet(String key, Set<String> baseSet) {
        TreeSet<String> resultSet = new TreeSet<>();
        baseSet.forEach(entry -> {
            if (entry.toUpperCase().contains(key.toUpperCase())) {
                resultSet.add(entry);
            }
        });
        return resultSet;
    }

    /**
     * Pass values to internal database to add or update data.
     *
     * @param id            Athlete id
     * @param name          Athlete name
     * @param sex           Athlete sex
     * @param age           Athlete age
     * @param height        Athlete height
     * @param weight        Athlete weight
     * @param team          Athletes team
     * @param noc           Teams national olympic committee
     * @param olympicGame   Game name
     * @param year          Game year
     * @param season        Game season
     * @param city          Game city
     * @param sport         Event discipline
     * @param event         Event name
     * @param medal         Athletes medal
     * @param listReference Reference to internal database
     */
    public static void addLineToDatabase(int id, String name, String sex, int age, int height, float weight, String team, String noc, String olympicGame, int year, String season, String city, String sport, String event, String medal, ListReference listReference) {
        listReference.getTeamList().addOrUpdate(team, noc, olympicGame, name);
        listReference.getEventList().addOrUpdate(event, sport, olympicGame);
        listReference.getOlympicGameList().addOrUpdate(olympicGame, city, year, season, event);

        listReference.getAthleteList().addOrUpdate(id, name, age, sex, height, weight,
                listReference.getAthleteList().createParticipation(
                        listReference.getOlympicGameList().getOlympicGame(olympicGame),
                        listReference.getTeamList().getTeam(team),
                        listReference.getEventList().getEvent(event),
                        medal));
    }
}
