package olympic.filehandle;

import java.io.InputStream;

public class Writer extends Connector {
    public Writer(InputStream fileStream) {
        super(fileStream);
    }

    public void save(int id, String name, String sex, int age, int height, float weight, String team, String noc, String olympicGame, int year, String season, String city, String sport, String event, String medal) {
        System.out.println("Write " + name + " to file");
    }
}
