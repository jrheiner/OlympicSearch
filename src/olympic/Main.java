package olympic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import olympic.filehandle.Reader;
import olympic.filehandle.Writer;
import olympic.list.ListReference;
import olympic.ui.MainController;

import java.util.concurrent.TimeUnit;


public class Main extends Application {

    static final ListReference listReference = new ListReference();
    static final String filename = "data/test.db";
    static Reader fileReader;
    static Writer fileWriter;
    MainController mainController;

    public static void main(String[] args) {

        fileReader = new Reader(Main.class.getResourceAsStream(filename));
        fileReader.setListReference(listReference);

        long startTime = System.nanoTime();

        fileReader.readDatabase();

        long endTime = System.nanoTime() - startTime;
        System.out.printf("Reading data took %d ms\n", TimeUnit.MILLISECONDS.convert(endTime, TimeUnit.NANOSECONDS));

        fileWriter = new Writer(Main.class.getResourceAsStream(filename));


        launch(args);

    }

    @Override
    public void start(Stage mainStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent root = loader.load();
        mainController = loader.getController();
        mainController.setListReference(listReference);
        mainController.setFileWriter(fileWriter);
        mainController.initializeUI();
        mainStage.setTitle("Olympic Search");
        mainStage.setMinHeight(450);
        mainStage.setMinWidth(800);
        mainStage.setScene(new Scene(root, mainStage.getMinWidth(), mainStage.getMinHeight()));
        mainStage.show();
    }
}
