package olympic.maps;

import olympic.database.Team;

import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * TreeMap of all teams and methods to add, update and search
 */
public class TeamMap {
    private final TreeMap<String, Team> teamMap = new TreeMap<>();

    TeamMap() {
    }

    /**
     * Searches for a team by name or partial name
     *
     * @param term Name or partial name to search for
     * @return Map containing all results matching the search
     */
    public TreeMap<String, Team> searchTeam(String term) {
        final String upperCaseTerm = term.toUpperCase();
        long startTime = System.nanoTime();

        TreeMap<String, Team> results = new TreeMap<>();
        teamMap.forEach((id, team) -> {
            if (team.getTeam().toUpperCase().contains(upperCaseTerm)) {
                results.put(id, team);
            }
        });

        long endTime = System.nanoTime() - startTime;
        System.out.printf("Found %d result(s) in %d ms\n", results.size(), TimeUnit.MILLISECONDS.convert(endTime, TimeUnit.NANOSECONDS));
        return results;
    }

    /**
     * Adds new team or updates data of an existing one
     *
     * @param team        Team name
     * @param noc         National olympic committee
     * @param olympicGame Game the team participated in
     * @param name        Athlete name
     */
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
