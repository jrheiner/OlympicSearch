package olympic.filehandle;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
    private String path;

    public Writer(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private void appendDatabase(String line) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path, true))) {
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            System.out.println("-END-");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("ERROR File not found!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(int id, String name, String sex, int age, int height, float weight, String team, String noc, String olympicGame, int year, String season, String city, String sport, String event, String medal) {
        System.out.println("Write " + id + " to file");
        appendDatabase(buildLineString(id, name, sex, age, height, weight, team, noc, olympicGame, year, season, city, sport, event, medal));
    }

    private String buildLineString(int id, String name, String sex, int age, int height, float weight, String team, String noc, String olympicGame, int year, String season, String city, String sport, String event, String medal) {
        return "\"" + id + "\"" + "," +
                inDoubleQuotes(name) + "," +
                inDoubleQuotes(sex) + "," +
                ((age < 0) ? "NA" : age) + "," +
                ((height < 0) ? "NA" : height) + "," +
                ((weight < 0) ? "NA" : weight) + "," +
                inDoubleQuotes(team) + "," +
                inDoubleQuotes(noc) + "," +
                inDoubleQuotes(olympicGame) + "," +
                year + "," +
                inDoubleQuotes(season) + "," +
                inDoubleQuotes(city) + "," +
                inDoubleQuotes(sport) + "," +
                inDoubleQuotes(event) + "," +
                (medal.equals("NA") ? medal : inDoubleQuotes(medal));
    }

    private String inDoubleQuotes(String string) {
        String stringInDoubleQuotes;
        stringInDoubleQuotes = "\"" + string + "\"";
        return stringInDoubleQuotes;

    }

}
