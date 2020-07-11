package sample;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

public class DatabaseConnector {
    private static final TreeMap<Integer, Athlete> AthletesMap = new TreeMap<>();
    private static final TreeMap<String, Team> TeamsMap = new TreeMap<>();
    private final String filename;
    private final TreeMap<String, OlympicGame> OlympicGamesMap = new TreeMap<>();
    private final TreeMap<String, Event> EventsMap = new TreeMap<>();

    public DatabaseConnector(String filename) {
        this.filename = filename;
    }

    public static TreeMap<Integer, Athlete> getAthleteMap() {
        return AthletesMap;
    }

    public static TreeMap<String, Team> getTeamMap() {
        return TeamsMap;
    }

    public String getFilename() {
        return filename;
    }

    public void readDatabase() {
        String line;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                parseSplitLine(splitLine(line));
                //System.out.println(line);
            }
            System.out.println("-END-");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("ERROR File not found!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> splitLine(String line) {
        ArrayList<String> lineSplit = new ArrayList<>();
        String[] data = line.split("(^\")|(\",\")|(\",)|(,\")|(\"$)");
        for (int i = 0; i < data.length; i++) {
            if (i == 4) {
                Collections.addAll(lineSplit, data[i].split(","));
            } else {
                lineSplit.add(data[i]);
            }
        }
        lineSplit.remove(0); // remove "empty" element at the beginning
        return lineSplit;
    }

    private void parseSplitLine(ArrayList<String> splitLine) {

        // [ID, Name, Sex, Age, Height, Weight, Team, NOC, Games, Year, Season, City, Sport, Event, Medal]
        int id = Integer.parseInt(splitLine.get(0));
        String name = splitLine.get(1);
        String sex = String.valueOf(splitLine.get(2));
        int age = dataToInt(splitLine.get(3));
        int height = dataToInt(splitLine.get(4));
        float weight = dataToFloat(splitLine.get(5));
        String team = splitLine.get(6);
        String noc = splitLine.get(7);
        String olympicGame = splitLine.get(8);
        int year = dataToInt(splitLine.get(9));
        String season = splitLine.get(10);
        String city = splitLine.get(11);
        String sport = splitLine.get(12);
        String event = splitLine.get(13);
        String medal = splitLine.get(14);


        if (TeamsMap.containsKey(team)) {
            Team currentTeam = TeamsMap.get(team);
            if (!currentTeam.getAthleteList().contains(name)) {
                currentTeam.addAthlete(name);
            }
            if (!currentTeam.getOlympicGameList().contains(olympicGame)) {
                currentTeam.addOlympicGame(olympicGame);
            }

        } else {
            Team newTeam = new Team(team, noc, olympicGame, name);
            TeamsMap.put(team, newTeam);
        }


        if (EventsMap.containsKey(event)) {
            Event currentEvent = EventsMap.get(event);
            if (!currentEvent.getOlympicGameList().contains(olympicGame)) {
                currentEvent.addOlympicGame(olympicGame);
            }
        } else {
            Event newEvent = new Event(event, sport, olympicGame);
            EventsMap.put(event, newEvent);
        }


        if (OlympicGamesMap.containsKey(olympicGame)) {
            OlympicGame currentOlympicGame = OlympicGamesMap.get(olympicGame);
            if (!currentOlympicGame.getEventList().contains(event)) {
                currentOlympicGame.addEvent(event);
            }
        } else {
            OlympicGame newOlympicGame = new OlympicGame(olympicGame, city, year, season, event);
            OlympicGamesMap.put(olympicGame, newOlympicGame);
        }


        Appearance appearance = new Appearance(OlympicGamesMap.get(olympicGame), TeamsMap.get(team), EventsMap.get(event), medal);
        if (AthletesMap.containsKey(id)) {
            Athlete currentAthlete = AthletesMap.get(id);
            if (!currentAthlete.getAgeList().contains(age)) {
                currentAthlete.addAge(age);
            }
            if (!currentAthlete.getHeightList().contains(height)) {
                currentAthlete.addHeight(height);
            }
            if (!currentAthlete.getWeightList().contains(weight)) {
                currentAthlete.addWeight(weight);
            }
            if (!currentAthlete.getAppearanceList().contains(appearance)) {
                currentAthlete.addAppearance(appearance);
            }
        } else {
            Athlete newAthlete = new Athlete(id, name, age, sex, height, weight, appearance);
            AthletesMap.put(id, newAthlete);
        }
    }

    private int dataToInt(String data) {
        int dataInt;
        if (data.equals("NA")) {
            dataInt = -1;
        } else {
            dataInt = Integer.parseInt(data);
        }
        return dataInt;
    }

    private float dataToFloat(String data) {
        float dataFloat;
        if (data.equals("NA")) {
            dataFloat = -1;
        } else {
            dataFloat = Float.parseFloat(data);
        }
        return dataFloat;
    }
}