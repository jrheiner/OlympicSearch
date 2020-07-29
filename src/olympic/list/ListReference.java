package olympic.list;


public class ListReference {
    private AthleteList athleteList = new AthleteList();
    private TeamList teamList = new TeamList();
    private OlympicGameList olympicGameList = new OlympicGameList();
    private EventList eventList = new EventList();

    public ListReference() {
    }

    public AthleteList getAthleteList() {
        return athleteList;
    }

    public void setAthleteList(AthleteList athleteList) {
        this.athleteList = athleteList;
    }

    public TeamList getTeamList() {
        return teamList;
    }

    public void setTeamList(TeamList teamList) {
        this.teamList = teamList;
    }

    public OlympicGameList getOlympicGameList() {
        return olympicGameList;
    }

    public void setOlympicGameList(OlympicGameList olympicGameList) {
        this.olympicGameList = olympicGameList;
    }

    public EventList getEventList() {
        return eventList;
    }

    public void setEventList(EventList eventList) {
        this.eventList = eventList;
    }
}
