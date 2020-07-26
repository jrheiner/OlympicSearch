package olympic.list;


public class ListReference {
    AthleteList athleteList = new AthleteList();
    TeamList teamList = new TeamList();
    OlympicGameList olympicGameList = new OlympicGameList();
    EventList eventList = new EventList();

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
