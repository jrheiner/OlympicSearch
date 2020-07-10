package sample;

public class Appearance {
    private OlympicGame olympicGame;
    private Team team;
    private Event event;
    private String medal;
    public Appearance(OlympicGame olympicGame, Team team, Event event, String medal) {
        this.olympicGame = olympicGame;
        this.team = team;
        this.event = event;
        this.medal = medal;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public OlympicGame getOlympicGame() {
        return olympicGame;
    }

    public void setOlympicGame(OlympicGame olympicGame) {
        this.olympicGame = olympicGame;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getMedal() {
        return medal;
    }

    public void setMedal(String medal) {
        this.medal = medal;
    }
}