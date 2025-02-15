package olympic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import olympic.filehandle.Reader;
import olympic.filehandle.Writer;
import olympic.maps.MapReference;
import olympic.ui.MainController;

import java.util.concurrent.TimeUnit;

/**
 * Main class, initialises primary user interface and file reader and writer
 */
public class Main extends Application {
    private static final MapReference MAP_REFERENCE = new MapReference();
    private static final String FILENAME = "data/olympic.db";
    private static Writer fileWriter;

    /**
     * Main method, starts the program
     *
     * @param args No required arguments
     */
    public static void main(String[] args) {

        Reader fileReader = new Reader(Main.class.getResourceAsStream(FILENAME));
        fileReader.setListReference(MAP_REFERENCE);

        fileWriter = new Writer("resources/olympic/" + FILENAME);

        long startTime = System.nanoTime();
        fileReader.readDatabase();
        long endTime = System.nanoTime() - startTime;
        System.out.printf("Reading data took %d ms\n", TimeUnit.MILLISECONDS.convert(endTime, TimeUnit.NANOSECONDS));

        launch(args);
    }

    @Override
    public void start(Stage mainStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent root = loader.load();

        MainController mainController = loader.getController();
        mainController.setListReference(MAP_REFERENCE);
        mainController.setFileWriter(fileWriter);
        mainController.initializeUI();

        mainStage.setTitle("Olympic Search - " + FILENAME.substring(FILENAME.lastIndexOf('/') + 1));
        mainStage.getIcons().add(new Image(Main.class.getResourceAsStream("icon.png")));
        mainStage.setMinHeight(500);
        mainStage.setMinWidth(800);
        mainStage.setScene(new Scene(root, mainStage.getMinWidth(), mainStage.getMinHeight()));
        mainStage.show();
    }
}
