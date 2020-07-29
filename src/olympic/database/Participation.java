package olympic.database;

public class Participation {
    private final OlympicGame olympicGame;
    private final Team team;
    private final Event event;
    private final String medal;

    public Participation(OlympicGame olympicGame, Team team, Event event, String medal) {
        this.olympicGame = olympicGame;
        this.team = team;
        this.event = event;
        this.medal = medal;
    }

    public Team getTeam() {
        return team;
    }

    public OlympicGame getOlympicGame() {
        return olympicGame;
    }

    public Event getEvent() {
        return event;
    }

    public String getMedal() {
        return medal;
    }

}