package olympic.filehandle;

import java.io.InputStream;

public abstract class Connector {
    private final InputStream stream;

    public Connector(InputStream fileStream) {
        this.stream = fileStream;
    }

    public InputStream getStream() {
        return stream;
    }

}