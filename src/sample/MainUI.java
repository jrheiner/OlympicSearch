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

public class MainUI implements Initializable {

    private final ObservableList<String> athletes = FXCollections.observableArrayList();

    @FXML
    private Button athleteSearchButton;

    @FXML
    private TextField athleteSearchInput;

    @FXML
    private ListView<String> athleteResultsListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initAthleteListView();
        initSearchHandler();


    }

    public void initAthleteListView(TreeMap<Integer, Athlete> resultMap) {
        athleteResultsListView.setItems(athletes);
        for (Map.Entry<Integer, Athlete> entry : resultMap.entrySet()) {
            athletes.add("[" + entry.getKey() + "] " + entry.getValue().getName());
        }
    }

    public void initAthleteListView() {
        TreeMap<Integer, Athlete> athleteMap = Main.getAthleteMap();
        initAthleteListView(athleteMap);
    }

    public void initSearchHandler() {
        athleteSearchButton.setOnAction(event -> {
            athleteResultsListView.getItems().clear();
            initAthleteListView(Main.searchInMap(athleteSearchInput.getText(), Main.getAthleteMap()));
        });

        /*athleteSearchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            athleteResultsListView.getItems().clear();
            initAthleteListView(Main.searchInMap(athleteSearchInput.getText(), Main.getAthleteMap()));
        });*/
    }
}
