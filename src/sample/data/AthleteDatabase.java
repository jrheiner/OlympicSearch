package sample.data;

import sample.Athlete;

import java.util.Map;
import java.util.TreeMap;

public class AthleteDatabase {
    private Map<Integer, Athlete> AthletesMap;

    public AthleteDatabase() {
        this.AthletesMap = new TreeMap<>();
    }

    public Map<Integer, Athlete> getAthletesMap() {
        return AthletesMap;
    }

    public void setAthletesMap(Map<Integer, Athlete> athletesMap) {
        AthletesMap = athletesMap;
    }
}
