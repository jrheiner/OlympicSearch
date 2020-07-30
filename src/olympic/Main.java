package olympic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import olympic.filehandle.Reader;
import olympic.filehandle.Writer;
import olympic.list.ListReference;
import olympic.ui.MainController;

import java.util.concurrent.TimeUnit;


public class Main extends Application {
    private static final ListReference listReference = new ListReference();
    private static final String filename = "data/olympic.db";
    private static Writer fileWriter;

    public static void main(String[] args) {

        Reader fileReader = new Reader(Main.class.getResourceAsStream(filename));
        fileReader.setListReference(listReference);
        fileWriter = new Writer("resources/olympic/" + filename);

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
        mainController.setListReference(listReference);
        mainController.setFileWriter(fileWriter);
        mainController.initializeUI();

        mainStage.setTitle("Olympic Search");
        mainStage.getIcons().add(new Image(Main.class.getResourceAsStream("icon.png")));
        mainStage.setMinHeight(500);
        mainStage.setMinWidth(800);
        mainStage.setScene(new Scene(root, mainStage.getMinWidth(), mainStage.getMinHeight()));
        mainStage.show();
    }
}
