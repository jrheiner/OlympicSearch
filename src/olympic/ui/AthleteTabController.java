package olympic.ui;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import olympic.database.Athlete;
import olympic.database.Participation;
import olympic.database.Team;
import olympic.list.ListReference;
import olympic.utility.ListUtility;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class AthleteTabController {


    private final ObservableList<Athlete> athletes = FXCollections.observableArrayList();
    private final ObservableList<Team> teams = FXCollections.observableArrayList();
    private final ObservableList<String> teamAthletes = FXCollections.observableArrayList();
    private final ObservableList<String> teamGames = FXCollections.observableArrayList();
    ListReference listReference;
    @FXML
    private Button athleteSearchButton;
    @FXML
    private TextField athleteSearchInput;
    @FXML
    private ListView<Athlete> athleteResultsListView;
    @FXML
    private Button athleteAddButton;
    @FXML
    private Label athleteResultCount;
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
    private TableView<Participation> athleteAppearanceTable;

    @FXML

    public ListReference getListReference() {
        return listReference;
    }

    public void setListReference(ListReference listReference) {
        this.listReference = listReference;
    }

    public void initAthleteTab() {
        initAthleteListView();
        initAthleteSearchHandler();
        initAthleteListViewHandler();
        initAthleteProfile();
        initAthleteAppearanceTable();
    }

    private void initAthleteListViewHandler() {
        athleteResultsListView.setOnMouseClicked(event -> {
            Athlete selectedAthlete = athleteResultsListView.getSelectionModel().getSelectedItem();
            if (selectedAthlete != null) {
                System.out.println("clicked on " + selectedAthlete);
                displayAthleteProfile(selectedAthlete);
            }
        });
    }

    private void initAthleteProfile() {
        displayAthleteProfile(athleteResultsListView.getItems().get(0));
    }

    private void displayAthleteProfile(Athlete selectedAthlete) {
        athleteIdLabel.setText(String.valueOf(selectedAthlete.getId()));
        athleteNameLabel.setText(selectedAthlete.getName());
        athleteSexLabel.setText(selectedAthlete.getSex().equalsIgnoreCase("M") ? "Male" : "Female");
        athleteAgeLabel.setText(ListUtility.arrayToStringDisplay(selectedAthlete.getAgeList()));
        athleteHeightLabel.setText(ListUtility.arrayToStringDisplay(selectedAthlete.getHeightList()));
        athleteWeightLabel.setText(ListUtility.arrayToStringDisplay(selectedAthlete.getWeightList()));

        fillAthleteAppearanceTable(selectedAthlete.getParticipationList());
    }

    public void initAthleteAppearanceTable() {
        TableColumn<Participation, String> olympicGameTableColumn = new TableColumn<>("Game");
        olympicGameTableColumn.setCellValueFactory(new PropertyValueFactory<>("olympicGame"));
        olympicGameTableColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getOlympicGame().getGame()));
        olympicGameTableColumn.setMinWidth(80);
        olympicGameTableColumn.setMaxWidth(100);

        TableColumn<Participation, String> teamTableColumn = new TableColumn<>("Team");
        teamTableColumn.setCellValueFactory(new PropertyValueFactory<>("team"));
        teamTableColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getTeam().getTeam()));
        teamTableColumn.setMinWidth(75);
        teamTableColumn.setMaxWidth(150);

        TableColumn<Participation, String> eventTableColumn = new TableColumn<>("Event");
        eventTableColumn.setCellValueFactory(new PropertyValueFactory<>("event"));
        eventTableColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getEvent().getEvent()));
        eventTableColumn.setMinWidth(150);
        eventTableColumn.setMaxWidth(300);

        TableColumn<Participation, String> medalTableColumn = new TableColumn<>("Medal");
        medalTableColumn.setCellValueFactory(new PropertyValueFactory<>("medal"));
        medalTableColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getMedal().equalsIgnoreCase("NA") ? "-" : param.getValue().getMedal()));
        medalTableColumn.setMinWidth(50);
        medalTableColumn.setMaxWidth(100);

        athleteAppearanceTable.getColumns().addAll(olympicGameTableColumn, teamTableColumn, eventTableColumn, medalTableColumn);
    }

    public void fillAthleteAppearanceTable(ArrayList<Participation> participationList) {
        athleteAppearanceTable.getItems().clear();
        participationList.forEach(participation -> athleteAppearanceTable.getItems().add(participation));
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
        TreeMap<Integer, Athlete> athleteMap = listReference.getAthleteList().getAthleteMap();
        initAthleteListView(athleteMap);
        athleteResultCount.textProperty().bind(Bindings.size((athleteResultsListView.getItems())).asString());

    }

    public void initAthleteSearchHandler() {
        athleteSearchButton.setOnAction(event -> {
            athleteResultsListView.getItems().clear();
            initAthleteListView(listReference.getAthleteList().searchAthlete(athleteSearchInput.getText(), listReference.getAthleteList().getAthleteMap()));
        });

/*
        athleteSearchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            athleteResultsListView.getItems().clear();
            initAthleteListView(listReference.getAthleteList().searchAthlete(athleteSearchInput.getText(), listReference.getAthleteList().getAthleteMap()));
        });*/
    }
}
