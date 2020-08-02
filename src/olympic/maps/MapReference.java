package olympic.maps;

/**
 * Utility class to easily pass reference of internal database
 */
public class MapReference {
    private final AthleteMap athleteMap = new AthleteMap();
    private final TeamMap teamMap = new TeamMap();
    private final OlympicGameMap olympicGameMap = new OlympicGameMap();
    private final EventMap eventMap = new EventMap();

    /**
     * Creates new MapReference and initialises internal database
     */
    public MapReference() {
    }

    public AthleteMap getAthleteDB() {
        return athleteMap;
    }

    public TeamMap getTeamDB() {
        return teamMap;
    }

    public OlympicGameMap getOlympicGameDB() {
        return olympicGameMap;
    }

    public EventMap getEventDB() {
        return eventMap;
    }
}
