package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.DatabaseConnection.DatabaseReader;
import sample.DatabaseLists.AthleteList;
import sample.UIController.MainUI;

import java.util.concurrent.TimeUnit;


public class Main extends Application {
    MainUI mainUI;
    AthleteList athleteList;

    public static void main(String[] args) {

        athleteList = new AthleteList();
        DatabaseReader Reader = new DatabaseReader("src/data/olympic.db");

        long startTime = System.nanoTime();

        Reader.readDatabase();

        long endTime = System.nanoTime() - startTime;
        System.out.printf("Reading data took %d ms\n", TimeUnit.MILLISECONDS.convert(endTime, TimeUnit.NANOSECONDS));

        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader FXMLLoader = new FXMLLoader();
        Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("MainUI.fxml"));
        mainUI = FXMLLoader.getController();
        mainUI.setAthleteList(athleteList);
        primaryStage.setTitle("Hello World");
        primaryStage.setMinHeight(450);
        primaryStage.setMinWidth(700);
        primaryStage.setScene(new Scene(root, primaryStage.getMinWidth(), primaryStage.getMinHeight()));
        primaryStage.show();
    }
}
