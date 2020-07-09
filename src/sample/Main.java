package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Map;
import java.util.TreeMap;

public class Main extends Application {

    public static void main(String[] args) {

        DatabaseConnector DbC = new DatabaseConnector("src/data/test.db");

        long startTime = System.nanoTime();

        DbC.readDatabase();


        long endTime = System.nanoTime() - startTime;
        System.out.println(endTime);
        System.out.println(getReadableTime(endTime));

        launch(args);


        /*

        https://www.kaggle.com/heesoo37/120-years-of-olympic-history-athletes-and-results


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

    public static Integer searchInMap(String term, TreeMap<Integer, Athlete> map) {
        Integer matchedKey = null;
        for (Map.Entry<Integer, Athlete> entry : map.entrySet()) // descendingMap()
            if (entry.getValue().getName().contains(term)) {
                matchedKey = entry.getKey();
                System.out.println("found key for " + term + " => " + matchedKey);
            }
        return matchedKey;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("resources/MainUI.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 700, 800));
        primaryStage.show();
    }
}
