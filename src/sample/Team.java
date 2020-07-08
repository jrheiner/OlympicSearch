package sample;

import java.util.ArrayList;

public class Team {
    private String noc;
    private String team;
    private ArrayList<Integer> athleteList = new ArrayList<>();
    private ArrayList<String> olympicGameList = new ArrayList<>();


    public Team(String team, String noc, String olympicGame, int athlete) {
        this.noc = noc;
        this.team = team;
        this.olympicGameList.add(olympicGame);
        this.athleteList.add(athlete);
    }

    public ArrayList<Integer> getAthleteList() {
        return athleteList;
    }

    public void setAthleteList(ArrayList<Integer> athleteList) {
        this.athleteList = athleteList;
    }

    public void addOlympicGame(String olympicGame) {
        olympicGameList.add(olympicGame);
    }

    public void addAthlete(int id) {
        athleteList.add(id);
    }

    public ArrayList<String> getOlympicGameList() {
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
