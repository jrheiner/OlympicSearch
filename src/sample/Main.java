package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.DatabaseConnection.DatabaseReader;
import sample.DatabaseLists.ListReference;
import sample.UIController.MainUI;

import java.util.concurrent.TimeUnit;


public class Main extends Application {

    static ListReference listReference = new ListReference();
    MainUI mainUI;

    public static void main(String[] args) {

        DatabaseReader Reader = new DatabaseReader("src/data/olympic.db");
        Reader.setListReference(listReference);

        long startTime = System.nanoTime();

        Reader.readDatabase();

        long endTime = System.nanoTime() - startTime;
        System.out.printf("Reading data took %d ms\n", TimeUnit.MILLISECONDS.convert(endTime, TimeUnit.NANOSECONDS));

        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainUI.fxml"));
        Parent root = loader.load();
        mainUI = loader.getController();
        mainUI.setListReference(listReference);
        mainUI.initializeUI();
        primaryStage.setTitle("Hello World");
        primaryStage.setMinHeight(450);
        primaryStage.setMinWidth(700);
        primaryStage.setScene(new Scene(root, primaryStage.getMinWidth(), primaryStage.getMinHeight()));
        primaryStage.show();
    }
}
