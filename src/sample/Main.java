package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.databaseConnection.DatabaseReader;
import sample.databaseLists.ListReference;
import sample.ui.MainController;

import java.util.concurrent.TimeUnit;


public class Main extends Application {

    static ListReference listReference = new ListReference();
    MainController mainController;

    public static void main(String[] args) {

        DatabaseReader Reader = new DatabaseReader("src/olympic.db");
        Reader.setListReference(listReference);

        long startTime = System.nanoTime();

        Reader.readDatabase();

        long endTime = System.nanoTime() - startTime;
        System.out.printf("Reading data took %d ms\n", TimeUnit.MILLISECONDS.convert(endTime, TimeUnit.NANOSECONDS));

        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent root = loader.load();
        mainController = loader.getController();
        mainController.setListReference(listReference);
        mainController.initializeUI();
        primaryStage.setTitle("Olympic Search");
        primaryStage.setMinHeight(450);
        primaryStage.setMinWidth(700);
        primaryStage.setScene(new Scene(root, primaryStage.getMinWidth(), primaryStage.getMinHeight()));
        primaryStage.show();
    }
}
