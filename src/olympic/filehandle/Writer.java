package olympic.filehandle;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Writes new data to the database file
 */
public class Writer {
    private final String path;

    /**
     * Creates new writer
     *
     * @param path Location of the database file to write to
     */
    public Writer(String path) {
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

    /**
     * Parses single values of the database to a single line and appends it the database file
     *
     * @param id          Athlete id
     * @param name        Athlete name
     * @param sex         Athlete sex
     * @param age         Athlete age
     * @param height      Athlete height
     * @param weight      Athlete weight
     * @param team        An athletes team
     * @param noc         Teams national olympic committee
     * @param olympicGame The game the athlete participated in
     * @param year        Game year
     * @param season      Game season
     * @param city        Game city
     * @param sport       Event discipline
     * @param event       Event name
     * @param medal       Medal the athlete achieved
     */
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
