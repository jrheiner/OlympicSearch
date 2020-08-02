package olympic.ui;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import olympic.database.Athlete;
import olympic.database.Participation;
import olympic.maps.MapReference;
import olympic.utility.DatabaseUtility;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * UI Controller for the athlete tab
 */
public class AthleteTabController {

    private final ObservableList<Athlete> athletes = FXCollections.observableArrayList();
    private MapReference mapReference;
    private MainController mainController;

    @FXML
    private Button athleteSearchButton;
    @FXML
    private TextField athleteSearchInput;
    @FXML
    private ListView<Athlete> athleteResultsListView;
    @FXML
    private Button athleteAddAthleteButton;
    @FXML
    private Button athleteAddParticipationButton;
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
    private TableView<Participation> athleteParticipationTable;
    @FXML
    private GridPane athleteProfilePane;

    void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    void setListReference(MapReference mapReference) {
        this.mapReference = mapReference;
    }

    /**
     * Initialise Athlete tab UI.
     * <p>
     * Fill athlete list. Initialise event handler for search and athlete list. Set default Athlete profile.
     */
    void initAthleteTab() {
        initAthleteListView();
        initAthleteSearchHandler();
        initAthleteListViewHandler();
        initAthleteProfile();
        initAthleteParticipationTable();

        EventHandler<ActionEvent> openPopup = event -> mainController.openPopup(event);
        athleteAddAthleteButton.setOnAction(openPopup);
        athleteAddParticipationButton.setOnAction(openPopup);
    }

    /**
     * Reload athlete list to include internal database changes
     */
    void refreshAthleteListView() {
        athleteResultsListView.getItems().clear();
        initAthleteListView(mapReference.getAthleteDB().getAthleteMap());
    }

    private void initAthleteListViewHandler() {
        athleteResultsListView.setOnMouseClicked(event -> {
            Athlete selectedAthlete = athleteResultsListView.getSelectionModel().getSelectedItem();
            if (selectedAthlete != null) {
                displayAthleteProfile(selectedAthlete);
            }
        });
    }

    private void initAthleteProfile() {
        athleteProfilePane.setVisible(false);
    }

    private void displayAthleteProfile(Athlete selectedAthlete) {
        athleteProfilePane.setVisible(true);
        athleteIdLabel.setText(String.valueOf(selectedAthlete.getId()));
        athleteNameLabel.setText(selectedAthlete.getName());
        athleteSexLabel.setText(selectedAthlete.getSex().equalsIgnoreCase("M") ? "Male" : "Female");
        athleteAgeLabel.setText(DatabaseUtility.arrayToStringDisplay(selectedAthlete.getAgeList()));
        athleteHeightLabel.setText(DatabaseUtility.arrayToStringDisplay(selectedAthlete.getHeightList()));
        athleteWeightLabel.setText(DatabaseUtility.arrayToStringDisplay(selectedAthlete.getWeightList()));

        fillAthleteParticipationTable(selectedAthlete.getParticipationList());
    }

    private void initAthleteParticipationTable() {
        TableColumn<Participation, String> olympicGameTableColumn = new TableColumn<>("Game");
        olympicGameTableColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getOlympicGame().getGame()));
        olympicGameTableColumn.setMinWidth(80);
        olympicGameTableColumn.setMaxWidth(100);

        TableColumn<Participation, String> teamTableColumn = new TableColumn<>("Team");
        teamTableColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getTeam().getTeam()));
        teamTableColumn.setMinWidth(75);
        teamTableColumn.setMaxWidth(150);

        TableColumn<Participation, String> eventTableColumn = new TableColumn<>("Event");
        eventTableColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getEvent().getEvent()));
        eventTableColumn.setMinWidth(150);
        eventTableColumn.setMaxWidth(300);

        TableColumn<Participation, String> medalTableColumn = new TableColumn<>("Medal");
        medalTableColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getMedal().equalsIgnoreCase("NA") ? "-" : param.getValue().getMedal()));
        medalTableColumn.setMinWidth(50);
        medalTableColumn.setMaxWidth(100);

        athleteParticipationTable.getColumns().addAll(olympicGameTableColumn, teamTableColumn, eventTableColumn, medalTableColumn);
    }

    private void fillAthleteParticipationTable(ArrayList<Participation> participationList) {
        athleteParticipationTable.getItems().clear();
        participationList.forEach(participation -> athleteParticipationTable.getItems().add(participation));
        if (athleteParticipationTable.getColumns().size() > 0) {
            athleteParticipationTable.getColumns().get(0).setSortType(TableColumn.SortType.ASCENDING);
            athleteParticipationTable.getSortOrder().add(athleteParticipationTable.getColumns().get(0));
        }
    }

    private void initAthleteListView(TreeMap<Integer, Athlete> resultMap) {
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

    private void initAthleteListView() {
        TreeMap<Integer, Athlete> athleteMap = mapReference.getAthleteDB().getAthleteMap();
        initAthleteListView(athleteMap);
        athleteResultCount.textProperty().bind(Bindings.size((athleteResultsListView.getItems())).asString());

    }

    private void initAthleteSearchHandler() {
        athleteSearchButton.setOnAction(event -> {
            athleteResultsListView.getItems().clear();
            initAthleteListView(mapReference.getAthleteDB().searchAthlete(athleteSearchInput.getText()));
        });

        athleteSearchInput.setOnAction(action -> {
            athleteResultsListView.getItems().clear();
            initAthleteListView(mapReference.getAthleteDB().searchAthlete(athleteSearchInput.getText()));
        });

    }
}
