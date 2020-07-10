package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.TreeMap;
import java.util.concurrent.TimeUnit;


public class Main extends Application {

    public static void main(String[] args) {

        DatabaseConnector DbC = new DatabaseConnector("src/data/test.db");

        long startTime = System.nanoTime();

        DbC.readDatabase();


        long endTime = System.nanoTime() - startTime;
        System.out.printf("Reading data took %d ms\n", TimeUnit.MILLISECONDS.convert(endTime, TimeUnit.NANOSECONDS));

        launch(args);


        /*

        https://www.kaggle.com/heesoo37/120-years-of-olympic-history-athletes-and-results


        read and parse
        3385 Hashmap
        3321 LinkedHashMap
        3681 TreeMap
         */

    }

    // https://stackoverflow.com/questions/86780/how-to-check-if-a-string-contains-another-string-in-a-case-insensitive-manner-in/25379180#25379180

    public static TreeMap<Integer, Athlete> searchInMap(String term, TreeMap<Integer, Athlete> searchMap) {
        final String upperCaseTerm = term.toUpperCase();
        long startTime = System.nanoTime();

        TreeMap<Integer, Athlete> results = new TreeMap<>();
        searchMap.forEach((id, athlete) -> {
            if (athlete.getName().toUpperCase().startsWith(upperCaseTerm)) {
                results.put(id, athlete);
            }
        });

/*
        TreeMap<Integer, Athlete> results = new TreeMap<>();
        for (Map.Entry<Integer, Athlete> entry : map.entrySet()) // descendingMap()
            if (entry.getValue().getName().startsWith(term)) {
                results.put(entry.getKey(), entry.getValue());
            }

        TreeMap<Integer, Athlete> results = new TreeMap<>();
        results = map.entrySet()
                .stream()
                .filter(e -> e.getValue().getName().startsWith(term))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o1, o2) -> (o1), TreeMap::new));
*/

        long endTime = System.nanoTime() - startTime;


        System.out.printf("Found %d result(s) in %d ms\n", results.size(), TimeUnit.MILLISECONDS.convert(endTime, TimeUnit.NANOSECONDS));
        return results;
    }

    public static TreeMap<Integer, Athlete> getAthleteMap() {
        return DatabaseConnector.getAthletesMap();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("resources/MainUI.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 700, 800));
        primaryStage.setMinHeight(450);
        primaryStage.setMinWidth(600);
        primaryStage.show();
    }
}
