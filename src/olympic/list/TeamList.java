package olympic.list;

import olympic.database.Team;

import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class TeamList {
    private final TreeMap<String, Team> teamMap = new TreeMap<>();

    TeamList() {
    }

    public TreeMap<String, Team> searchTeam(String term, TreeMap<String, Team> searchMap) {
        final String upperCaseTerm = term.toUpperCase();
        long startTime = System.nanoTime();

        TreeMap<String, Team> results = new TreeMap<>();
        searchMap.forEach((id, team) -> {
            if (team.getTeam().toUpperCase().contains(upperCaseTerm)) {
                results.put(id, team);
            }
        });

        long endTime = System.nanoTime() - startTime;
        System.out.printf("Found %d result(s) in %d ms\n", results.size(), TimeUnit.MILLISECONDS.convert(endTime, TimeUnit.NANOSECONDS));
        return results;
    }

    public void addOrUpdate(String team, String noc, String olympicGame, String name) {
        if (teamMap.containsKey(team)) {
            Team currentTeam = teamMap.get(team);
            if (!currentTeam.getAthleteList().contains(name)) {
                currentTeam.addAthlete(name);
            }
            if (!currentTeam.getOlympicGameList().contains(olympicGame)) {
                currentTeam.addOlympicGame(olympicGame);
            }
        } else {
            Team newTeam = new Team(team, noc, olympicGame, name);
            teamMap.put(team, newTeam);
        }
    }

    public Team getTeam(String key) {
        return teamMap.get(key);
    }

    public TreeMap<String, Team> getTeamMap() {
        return teamMap;
    }

}
