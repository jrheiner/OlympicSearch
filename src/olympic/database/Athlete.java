package olympic.database;

import java.util.ArrayList;

/**
 * Athlete data structure
 */
public class Athlete {
    private final ArrayList<Integer> ageList = new ArrayList<>();
    private final String sex;
    private final ArrayList<Integer> heightList = new ArrayList<>();
    private final ArrayList<Float> weightList = new ArrayList<>();
    private final ArrayList<Participation> participationList = new ArrayList<>();
    private final int id;
    private final String name;

    /**
     * Creates new athlete
     *
     * @param id            Athlete id
     * @param name          Athlete name
     * @param age           Athlete age
     * @param sex           Athlete sex
     * @param height        Athlete height
     * @param weight        Athlete weight
     * @param participation Participation information
     */
    public Athlete(int id, String name, int age, String sex, int height, float weight, Participation participation) {
        this.id = id;
        this.name = name;
        this.ageList.add(age);
        this.sex = sex;
        this.heightList.add(height);
        this.weightList.add(weight);
        this.participationList.add(participation);
    }

    /**
     * Adds a new age to the age list of an athlete
     *
     * @param age Age in years
     */
    public void addAge(int age) {
        ageList.add(age);
    }

    /**
     * Adds a new height to the height list of an athlete
     *
     * @param height Height in centimeters
     */
    public void addHeight(int height) {
        heightList.add(height);
    }

    /**
     * Adds new weight to the weight list of an athlete
     *
     * @param weight Weight in kilograms
     */
    public void addWeight(float weight) {
        weightList.add(weight);
    }

    /**
     * Adds a new Participation object to the list of an athlete
     *
     * @param participation Participation object
     */
    public void addParticipation(Participation participation) {
        participationList.add(participation);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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
