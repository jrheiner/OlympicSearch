package sample.DatabaseLists;

import sample.Database.*;

import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class AthleteList {

    private final TreeMap<Integer, Athlete> athleteMap = new TreeMap<>();

    public AthleteList() {
    }

    public void addOrUpdate(Integer id, String name, Integer age, String sex, Integer height, Float weight, Participation participation) {
        if (athleteMap.containsKey(id)) {
            Athlete currentAthlete = athleteMap.get(id);
            if (!currentAthlete.getAgeList().contains(age)) {
                currentAthlete.addAge(age);
            }
            if (!currentAthlete.getHeightList().contains(height)) {
                currentAthlete.addHeight(height);
            }
            if (!currentAthlete.getWeightList().contains(weight)) {
                currentAthlete.addWeight(weight);
            }
            if (!currentAthlete.getParticipationList().contains(participation)) {
                currentAthlete.addAppearance(participation);
            }
        } else {
            Athlete newAthlete = new Athlete(id, name, age, sex, height, weight, participation);
            athleteMap.put(id, newAthlete);
        }
    }

    public TreeMap<Integer, Athlete> searchAthlete(String term, TreeMap<Integer, Athlete> searchMap) {
        final String upperCaseTerm = term.toUpperCase();
        long startTime = System.nanoTime();

        TreeMap<Integer, Athlete> results = new TreeMap<>();
        searchMap.forEach((id, athlete) -> {
            if (athlete.getName().toUpperCase().startsWith(upperCaseTerm)) {
                results.put(id, athlete);
            }
        });

        long endTime = System.nanoTime() - startTime;
        System.out.printf("Found %d result(s) in %d ms\n", results.size(), TimeUnit.MILLISECONDS.convert(endTime, TimeUnit.NANOSECONDS));
        return results;
    }

    public TreeMap<Integer, Athlete> getAthleteMap() {
        return athleteMap;
    }

    public Participation createParticipation(OlympicGame olympicGame, Team team, Event event, String medal) {
        return new Participation(olympicGame, team, event, medal);
    }
}
