package sample;

import java.util.ArrayList;
import java.util.HashMap;

public class Athlete {
    private int id;
    private String name;
    private ArrayList<Integer> ageList = new ArrayList<>();
    private String sex;
    private ArrayList<Integer> heightList = new ArrayList<>();
    private ArrayList<Float> weightList = new ArrayList<>();
    private HashMap<String, Integer> medals = new HashMap<>();
    private ArrayList<String> eventList = new ArrayList<>();


    public Athlete(int id, String name, int age, String sex, int height, float weight, String event) {
        this.id = id;
        this.name = name;
        this.ageList.add(age);
        this.sex = sex;
        this.heightList.add(height);
        this.weightList.add(weight);
        medals.put("Gold", 0);
        medals.put("Silver", 0);
        medals.put("Bronze", 0);
        medals.put("NA", 0);
        eventList.add(event);
    }

    public ArrayList<String> getEventList() {
        return eventList;
    }

    public void setEventList(ArrayList<String> eventList) {
        this.eventList = eventList;
    }

    public void addEvent(String event) {
        eventList.add(event);
    }

    public void addMedal(String type) {
        int count = medals.get(type);
        count++;
        medals.put(type, count);
    }

    public void addAge(int newAge) {
        ageList.add(newAge);
    }

    public void addHeight(int newHeight) {
        heightList.add(newHeight);
    }

    public void addWeight(float newWeight) {
        weightList.add(newWeight);
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

    public void setAgeList(ArrayList<Integer> ageList) {
        this.ageList = ageList;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public ArrayList<Integer> getHeightList() {
        return heightList;
    }

    public void setHeightList(ArrayList<Integer> heightList) {
        this.heightList = heightList;
    }

    public ArrayList<Float> getWeightList() {
        return weightList;
    }

    public void setWeightList(ArrayList<Float> weightList) {
        this.weightList = weightList;
    }

    public HashMap<String, Integer> getMedals() {
        return medals;
    }

    public void setMedals(HashMap<String, Integer> medals) {
        this.medals = medals;
    }
}
