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
    private TableView<Appearance> athleteDataTable;
    @FXML
    private TableView<Appearance> athleteAppearanceTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initAthleteListView();
        initSearchHandler();
        initAthleteAppearanceTable();
        initAthleteDataTable();

        athleteResultsListView.setOnMouseClicked(event -> {
            Athlete selectedAthlete = athleteResultsListView.getSelectionModel().getSelectedItem();
            System.out.println("clicked on " + selectedAthlete);

            // display details
            athleteIdLabel.setText(String.valueOf(selectedAthlete.getId()));
            athleteNameLabel.setText(selectedAthlete.getName());
            athleteSexLabel.setText(selectedAthlete.getSex());

            fillAthleteAppearanceTable(selectedAthlete.getAppearanceList());
            fillAthleteDataTable(selectedAthlete, selectedAthlete.getAppearanceList());

        });
    }

    public void initAthleteDataTable() {
        TableColumn<Appearance, String> olympicGameTableColumn = new TableColumn<>("Game");
        olympicGameTableColumn.setCellValueFactory(new PropertyValueFactory<>("olympicGame"));
        olympicGameTableColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getOlympicGame().getGame()));


        TableColumn<Appearance, String> ageTableColumn = new TableColumn<>("Age");
        ageTableColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        //ageTableColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getAge().toString()));

        TableColumn<Appearance, String> heightTableColumn = new TableColumn<>("Height");
        heightTableColumn.setCellValueFactory(new PropertyValueFactory<>("height"));
        //heightTableColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getHeight().toString()));

        TableColumn<Appearance, String> weightTableColumn = new TableColumn<>("Weight");
        weightTableColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        //heightTableColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getWeight().toString()));

        athleteDataTable.getColumns().addAll(olympicGameTableColumn, ageTableColumn, heightTableColumn, weightTableColumn);
    }

    public void fillAthleteDataTable(Athlete athlete, ArrayList<Appearance> appearanceList) {
        athleteDataTable.getItems().clear();
        appearanceList.forEach(appearance -> athleteDataTable.getItems().add(appearance));
    }


    public void initAthleteAppearanceTable() {
        TableColumn<Appearance, String> olympicGameTableColumn = new TableColumn<>("Game");
        olympicGameTableColumn.setCellValueFactory(new PropertyValueFactory<>("olympicGame"));
        olympicGameTableColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getOlympicGame().getGame()));


        TableColumn<Appearance, String> teamTableColumn = new TableColumn<>("Team");
        teamTableColumn.setCellValueFactory(new PropertyValueFactory<>("team"));
        teamTableColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getTeam().getTeam()));

        TableColumn<Appearance, String> eventTableColumn = new TableColumn<>("Event");
        eventTableColumn.setCellValueFactory(new PropertyValueFactory<>("event"));
        eventTableColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getEvent().getEvent()));

        TableColumn<Appearance, String> medalTableColumn = new TableColumn<>("Medal");
        medalTableColumn.setCellValueFactory(new PropertyValueFactory<>("medal"));

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

    public void initSearchHandler() {
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
