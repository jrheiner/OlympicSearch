package olympic.maps;

import olympic.database.*;

import java.util.TreeMap;

/**
 * TreeMap of all athletes and methods to add, update and search
 */
public class AthleteMap {

    private final TreeMap<Integer, Athlete> athleteMap = new TreeMap<>();

    AthleteMap() {
    }

    /**
     * Return an athlete from the database by id. <p>
     *
     * @param id Athlete id
     * @return Athlete object for the specific id or null (if database does not contain id)
     */
    public Athlete getAthleteById(int id) {
        return athleteMap.getOrDefault(id, null);
    }

    /**
     * Adds a new or updates an existing athlete object to the internal database.
     *
     * @param id            Athlete id
     * @param name          Athlete name
     * @param age           Athlete age
     * @param sex           Athlete sex
     * @param height        Athlete height
     * @param weight        Athlete weight
     * @param participation Athlete participation (e.g. Game and event information)
     */
    public void addOrUpdate(Integer id, String name, Integer age, String sex, Integer height, Float weight, Participation participation) {
        if (athleteMap.containsKey(id)) {
            Athlete currentAthlete = athleteMap.get(id);
            if (!currentAthlete.getAgeList().contains(age) && (age > 0)) {
                currentAthlete.addAge(age);
            }
            if (!currentAthlete.getHeightList().contains(height) && (age > 0)) {
                currentAthlete.addHeight(height);
            }
            if (!currentAthlete.getWeightList().contains(weight) && (age > 0)) {
                currentAthlete.addWeight(weight);
            }
            if (!currentAthlete.getParticipationList().contains(participation)) {
                currentAthlete.addParticipation(participation);
            }
        } else {
            Athlete newAthlete = new Athlete(id, name, age, sex, height, weight, participation);
            athleteMap.put(id, newAthlete);
        }
    }

    /**
     * Searches for athletes by name (or partial name)
     *
     * @param term Name or partial name to search for
     * @return Map containing all results matching the search
     */
    public TreeMap<Integer, Athlete> searchAthlete(String term) {
        final String upperCaseTerm = term.toUpperCase();

        TreeMap<Integer, Athlete> results = new TreeMap<>();
        athleteMap.forEach((id, athlete) -> {
            if (athlete.getName().toUpperCase().contains(upperCaseTerm)) {
                results.put(id, athlete);
            }
        });

        return results;
    }

    public TreeMap<Integer, Athlete> getAthleteMap() {
        return athleteMap;
    }

    /**
     * Creates new Participation object
     *
     * @param olympicGame Game the athlete competed in
     * @param team        Team the athlete is part of
     * @param event       Event the athlete competed in
     * @param medal       Medal the athlete achieved
     * @return Participation object
     */
    public Participation createParticipation(OlympicGame olympicGame, Team team, Event event, String medal) {
        return new Participation(olympicGame, team, event, medal);
    }
}
