package sample;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String noc;
    private String team;
    private ArrayList<String> athleteList = new ArrayList<>();
    private ArrayList<String> olympicGameList = new ArrayList<>();


    public Team(String team, String noc, String olympicGame, String athlete) {
        this.noc = noc;
        this.team = team;
        this.olympicGameList.add(olympicGame);
        this.athleteList.add(athlete);
    }

    public ArrayList<String> getAthleteList() {
        return athleteList;
    }

    public void setAthleteList(ArrayList<String> athleteList) {
        this.athleteList = athleteList;
    }

    public void addOlympicGame(String olympicGame) {
        olympicGameList.add(olympicGame);
    }

    public void addAthlete(String athlete) {
        athleteList.add(athlete);
    }

    public List<String> getOlympicGameList() {
        return olympicGameList;
    }

    public void setOlympicGameList(ArrayList<String> olympicGameList) {
        this.olympicGameList = olympicGameList;
    }

    public String getNoc() {
        return noc;
    }

    public void setNoc(String noc) {
        this.noc = noc;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}
