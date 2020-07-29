package olympic.utility;

import olympic.list.ListReference;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class ListUtility {

    public static String arrayToStringDisplay(ArrayList<?> arrayList) {
        String arrayString = arrayList.toString();
        return arrayString.substring(1, arrayString.length() - 1).replaceAll("-1.0|-1", "Not available");
    }

    public static TreeSet<String> searchInSet(String term, Set<String> baseSet) {
        TreeSet<String> resultSet = new TreeSet<>();
        baseSet.forEach(entry -> {
            if (entry.toUpperCase().contains(term.toUpperCase())) {
                resultSet.add(entry);
            }
        });
        return resultSet;
    }

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
