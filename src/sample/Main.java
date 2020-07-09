package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
        DatabaseConnector DbC = new DatabaseConnector("C:\\Users\\Jonas\\Desktop\\2. Semester\\Programmieren Java\\Projekt\\Projektcode\\src\\sample\\olympic.db");

        long startTime = System.nanoTime();

        DbC.readDatabase();


        long endTime = System.nanoTime() - startTime;
        System.out.println(endTime);
        System.out.println(getReadableTime(endTime));

        /*
        read and parse
        3385 Hashmap
        3321 LinkedHashMap
        3681 TreeMap
         */

    }

    private static String getReadableTime(Long nanos) {
        long tempSec = nanos / (1000 * 1000 * 1000);
        long sec = tempSec % 60;
        long min = (tempSec / 60) % 60;
        long hour = (tempSec / (60 * 60)) % 24;
        long day = (tempSec / (24 * 60 * 60)) % 24;
        return String.format("%dd %dh %dm %ds", day, hour, min, sec);

    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }
}
