package olympic.database;

import java.util.ArrayList;

public class Athlete {
    private final ArrayList<Integer> ageList = new ArrayList<>();
    private final String sex;
    private final ArrayList<Integer> heightList = new ArrayList<>();
    private final ArrayList<Float> weightList = new ArrayList<>();
    private final ArrayList<Participation> participationList = new ArrayList<>();
    private int id;
    private String name;

    public Athlete(int id, String name, int age, String sex, int height, float weight, Participation participation) {
        this.id = id;
        this.name = name;
        this.ageList.add(age);
        this.sex = sex;
        this.heightList.add(height);
        this.weightList.add(weight);
        this.participationList.add(participation);
    }

    public void addAge(int age) {
        ageList.add(age);
    }

    public void addHeight(int height) {
        heightList.add(height);
    }

    public void addWeight(float weight) {
        weightList.add(weight);
    }

    public void addAppearance(Participation participation) {
        participationList.add(participation);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getAgeList() {
        return ageList;
    }

    public String getSex() {
        return sex;
    }

    public ArrayList<Integer> getHeightList() {
        return heightList;
    }

    public ArrayList<Float> getWeightList() {
        return weightList;
    }

    public ArrayList<Participation> getParticipationList() {
        return participationList;
    }
}
