package sample;

public class DatabaseConnector {
    private final String filename;

    public DatabaseConnector(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}