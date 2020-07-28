package olympic.filehandle;

import java.io.InputStream;

public abstract class Connector {
    private String filename = null;
    private InputStream inputStream = null;

    public Connector(InputStream fileStream) {
        this.inputStream = fileStream;
    }

    public Connector(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public InputStream getStream() {
        return inputStream;
    }

}