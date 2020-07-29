package olympic.database;

import java.util.ArrayList;

public class Team {
    private final String noc;
    private final String team;
    private final ArrayList<String> athleteList = new ArrayList<>();
    private final ArrayList<String> olympicGameList = new ArrayList<>();

    public Team(String team, String noc, String olympicGame, String athlete) {
        this.noc = noc;
        this.team = team;
        this.olympicGameList.add(olympicGame);
        this.athleteList.add(athlete);
    }

    public ArrayList<String> getAthleteList() {
        return athleteList;
    }

    public void addOlympicGame(String olympicGame) {
        olympicGameList.add(olympicGame);
    }

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
