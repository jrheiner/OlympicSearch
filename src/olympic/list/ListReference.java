package olympic.list;

/**
 * Utility class to easily pass reference of internal database
 */
public class ListReference {
    private final AthleteList athleteList = new AthleteList();
    private final TeamList teamList = new TeamList();
    private final OlympicGameList olympicGameList = new OlympicGameList();
    private final EventList eventList = new EventList();

    public ListReference() {
    }

    public AthleteList getAthleteList() {
        return athleteList;
    }

    public TeamList getTeamList() {
        return teamList;
    }

    public OlympicGameList getOlympicGameList() {
        return olympicGameList;
    }

    public EventList getEventList() {
        return eventList;
    }
}
