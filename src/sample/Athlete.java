package sample;

import java.util.ArrayList;

public class Athlete {
    private int id;
    private String name;
    private ArrayList<Integer> ageList = new ArrayList<>();
    private String sex;
    private ArrayList<Integer> heightList = new ArrayList<>();
    private ArrayList<Float> weightList = new ArrayList<>();
    private ArrayList<Appearance> appearanceList = new ArrayList<>();

    public Athlete(int id, String name, int age, String sex, int height, float weight, Appearance appearance) {
        this.id = id;
        this.name = name;
        this.ageList.add(age);
        this.sex = sex;
        this.heightList.add(height);
        this.weightList.add(weight);
        this.appearanceList.add(appearance);
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

    public void addAppearance(Appearance appearance) {
        appearanceList.add(appearance);
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

    public ArrayList<Appearance> getAppearanceList() {
        return appearanceList;
    }

    public void setAppearanceList(ArrayList<Appearance> appearanceList) {
        this.appearanceList = appearanceList;
    }
}
