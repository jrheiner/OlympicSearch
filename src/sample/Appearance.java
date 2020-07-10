package sample;

public class Appearance {
    private OlympicGame olympicGame;
    private Integer age;
    private Integer height;
    private Float weight;
    private Team team;
    private Event event;
    private String medal;

    public Appearance(OlympicGame olympicGame, Integer age, Integer height, Float weight, Team team, Event event, String medal) {
        this.olympicGame = olympicGame;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.team = team;
        this.event = event;
        this.medal = medal;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
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
