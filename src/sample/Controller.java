package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class Controller implements Initializable {

    private final ObservableList<String> items = FXCollections.observableArrayList();
    private final ObservableList<String> items2 = FXCollections.observableArrayList();

    @FXML
    private ListView<String> testListView;
    @FXML
    private ListView<String> testListView2;
    @FXML
    private Button btn;
    @FXML
    private TextField search;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        testListView.setItems(items);
        testListView2.setItems(items2);
        Map<Integer, Athlete> map = DatabaseConnector.getAthletesMap();
        for (Map.Entry<Integer, Athlete> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "/" + entry.getValue().getName());
            items.add(entry.getKey() + "/" + entry.getValue().getName());
        }
        Map<String, Team> map2 = DatabaseConnector.getTeamsMap();
        for (Map.Entry<String, Team> entry : map2.entrySet()) {
            items2.add(entry.getValue().getTeam());
        }

        testListView.setOnMouseClicked(event -> System.out.println("clicked on " + testListView.getSelectionModel().getSelectedItem()));

        btn.setText("search");
        btn.setOnAction(event -> {
            System.out.println("clicked");
            Main.searchInMap(search.getText(), (TreeMap<Integer, Athlete>) map);
        });
    }

}
