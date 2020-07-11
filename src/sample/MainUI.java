package sample;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class MainUI implements Initializable {

    private final ObservableList<Athlete> athletes = FXCollections.observableArrayList();

    @FXML
    private Button athleteSearchButton;
    @FXML
    private TextField athleteSearchInput;
    @FXML
    private ListView<Athlete> athleteResultsListView;

    @FXML
    private Label athleteDetailsLabel;
    @FXML
    private Label athleteIdLabel;
    @FXML
    private Label athleteNameLabel;
    @FXML
    private Label athleteSexLabel;
    @FXML
    private Label athleteAgeLabel;
    @FXML
    private Label athleteHeightLabel;
    @FXML
    private Label athleteWeightLabel;

    @FXML
    private TableView<Appearance> athleteAppearanceTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initAthleteTab();


    }

    private void initAthleteTab() {
        initAthleteListView();
        initAthleteSearchHandler();
        initAthleteListViewHandler();
        initAthleteProfile();
        initAthleteAppearanceTable();
    }

    private void initAthleteListViewHandler() {
        athleteResultsListView.setOnMouseClicked(event -> {
            Athlete selectedAthlete = athleteResultsListView.getSelectionModel().getSelectedItem();
            System.out.println("clicked on " + selectedAthlete);
            displayAthleteProfile(selectedAthlete);

        });
    }

    private void initAthleteProfile() {
        displayAthleteProfile(athleteResultsListView.getItems().get(0));
    }

    private void displayAthleteProfile(Athlete selectedAthlete) {
        // display details
        athleteIdLabel.setText(String.valueOf(selectedAthlete.getId()));
        athleteNameLabel.setText(selectedAthlete.getName());
        athleteSexLabel.setText(selectedAthlete.getSex().equalsIgnoreCase("M") ? "Male" : "Female");
        athleteAgeLabel.setText(Main.arrayToStringDisplay(selectedAthlete.getAgeList()));
        athleteHeightLabel.setText(Main.arrayToStringDisplay(selectedAthlete.getHeightList()));
        athleteWeightLabel.setText(Main.arrayToStringDisplay(selectedAthlete.getWeightList()));

        fillAthleteAppearanceTable(selectedAthlete.getAppearanceList());
    }


    public void initAthleteAppearanceTable() {
        TableColumn<Appearance, String> olympicGameTableColumn = new TableColumn<>("Game");
        olympicGameTableColumn.setCellValueFactory(new PropertyValueFactory<>("olympicGame"));
        olympicGameTableColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getOlympicGame().getGame()));
        olympicGameTableColumn.setMinWidth(80);
        olympicGameTableColumn.setMaxWidth(100);


        TableColumn<Appearance, String> teamTableColumn = new TableColumn<>("Team");
        teamTableColumn.setCellValueFactory(new PropertyValueFactory<>("team"));
        teamTableColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getTeam().getTeam()));
        teamTableColumn.setMinWidth(75);
        teamTableColumn.setMaxWidth(150);

        TableColumn<Appearance, String> eventTableColumn = new TableColumn<>("Event");
        eventTableColumn.setCellValueFactory(new PropertyValueFactory<>("event"));
        eventTableColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getEvent().getEvent()));
        eventTableColumn.setMinWidth(150);
        eventTableColumn.setMaxWidth(300);

        TableColumn<Appearance, String> medalTableColumn = new TableColumn<>("Medal");
        medalTableColumn.setCellValueFactory(new PropertyValueFactory<>("medal"));
        medalTableColumn.setMinWidth(50);
        medalTableColumn.setMaxWidth(100);

        athleteAppearanceTable.getColumns().addAll(olympicGameTableColumn, teamTableColumn, eventTableColumn, medalTableColumn);
    }

    public void fillAthleteAppearanceTable(ArrayList<Appearance> appearanceList) {
        athleteAppearanceTable.getItems().clear();
        appearanceList.forEach(appearance -> athleteAppearanceTable.getItems().add(appearance));
    }


    public void initAthleteListView(TreeMap<Integer, Athlete> resultMap) {


        athleteResultsListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Athlete athlete, boolean empty) {
                super.updateItem(athlete, empty);
                if (empty || athlete == null || athlete.getName() == null) {
                    setText(null);
                } else {
                    setText(athlete.getName());
                }
            }
        });


        athleteResultsListView.setItems(athletes);
        for (Map.Entry<Integer, Athlete> entry : resultMap.entrySet()) {
            athletes.add(entry.getValue());
        }
    }

    public void initAthleteListView() {
        TreeMap<Integer, Athlete> athleteMap = Main.getAthleteMap();
        initAthleteListView(athleteMap);
    }

    public void initAthleteSearchHandler() {
        athleteSearchButton.setOnAction(event -> {
            athleteResultsListView.getItems().clear();
            initAthleteListView(Main.searchInMap(athleteSearchInput.getText(), Main.getAthleteMap()));
        });

        /* LIVE SEARCH
        athleteSearchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            athleteResultsListView.getItems().clear();
            initAthleteListView(Main.searchInMap(athleteSearchInput.getText(), Main.getAthleteMap()));
        });*/
    }
}
