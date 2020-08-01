package olympic.database;

import java.util.ArrayList;

/**
 * Team data structure
 */
public class Team {
    private final String noc;
    private final String team;
    private final ArrayList<String> athleteList = new ArrayList<>();
    private final ArrayList<String> olympicGameList = new ArrayList<>();

    /**
     * Creates a new team
     *
     * @param team        Team name
     * @param noc         Team national committee
     * @param olympicGame Game the team competed in
     * @param athlete     Athlete that is part of the team
     */
    public Team(String team, String noc, String olympicGame, String athlete) {
        this.noc = noc;
        this.team = team;
        this.olympicGameList.add(olympicGame);
        this.athleteList.add(athlete);
    }

    public ArrayList<String> getAthleteList() {
        return athleteList;
    }

    /**
     * Add olympic game to the game list of a team
     *
     * @param olympicGame Olympic Game object
     */
    public void addOlympicGame(String olympicGame) {
        olympicGameList.add(olympicGame);
    }

    /**
     * Add athlete to the athlete list of a team
     *
     * @param athlete Athlete object
     */
    public void addAthlete(String athlete) {
        athleteList.add(athlete);
    }

    public ArrayList<String> getOlympicGameList() {
        return olympicGameList;
    }

    public String getNoc() {
        return noc;
    }

    public String getTeam() {
        return team;
    }
}
