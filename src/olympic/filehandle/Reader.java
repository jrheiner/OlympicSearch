package olympic.filehandle;

import olympic.list.ListReference;
import olympic.utility.ListUtility;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Reader extends Connector {
    private ListReference listReference;

    public Reader(InputStream fileStream) {
        super(fileStream);
    }

    public ListReference getListReference() {
        return listReference;
    }

    public void setListReference(ListReference listReference) {
        this.listReference = listReference;
    }

    public void readDatabase() {
        String line;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getStream()))) {
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                parseSplitLine(splitLine(line));
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
        lineSplit.remove(0);
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

        ListUtility.addLineToDatabase(id, name, sex, age, height, weight, team, noc, olympicGame, year, season, city, sport, event, medal, listReference);
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
