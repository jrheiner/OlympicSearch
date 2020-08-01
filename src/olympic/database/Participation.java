package olympic.database;

/**
 * Utility class which contains participation information of an athlete
 */
public class Participation {
    private final OlympicGame olympicGame;
    private final Team team;
    private final Event event;
    private final String medal;

    /**
     * Create participation
     *
     * @param olympicGame Olympic game the athlete competed in
     * @param team        Team the athlete is part of
     * @param event       Event the athlete competed in
     * @param medal       Athletes medal in the event
     */
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