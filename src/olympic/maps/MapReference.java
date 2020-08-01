package olympic.maps;

/**
 * Utility class to easily pass reference of internal database
 */
public class MapReference {
    private final AthleteMap athleteMap = new AthleteMap();
    private final TeamMap teamMap = new TeamMap();
    private final OlympicGameMap olympicGameMap = new OlympicGameMap();
    private final EventMap eventMap = new EventMap();

    public MapReference() {
    }

    public AthleteMap getAthleteList() {
        return athleteMap;
    }

    public TeamMap getTeamList() {
        return teamMap;
    }

    public OlympicGameMap getOlympicGameList() {
        return olympicGameMap;
    }

    public EventMap getEventList() {
        return eventMap;
    }
}
