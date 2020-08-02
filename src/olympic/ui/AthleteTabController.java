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
    private Button searchButton;
    @FXML
    private TextField searchInput;
    @FXML
    private ListView<Athlete> resultsListView;
    @FXML
    private Button addAthleteButton;
    @FXML
    private Button addParticipationButton;
    @FXML
    private Label resultCount;
    @FXML
    private Label idLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label sexLabel;
    @FXML
    private Label ageLabel;
    @FXML
    private Label heightLabel;
    @FXML
    private Label weightLabel;
    @FXML
    private TableView<Participation> participationTable;
    @FXML
    private GridPane profilePane;

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
        addAthleteButton.setOnAction(openPopup);
        addParticipationButton.setOnAction(openPopup);
    }

    /**
     * Reload athlete list to include internal database changes
     */
    void refreshAthleteListView() {
        resultsListView.getItems().clear();
        initAthleteListView(mapReference.getAthleteDB().getAthleteMap());
    }

    private void initAthleteListViewHandler() {
        resultsListView.setOnMouseClicked(event -> {
            Athlete selectedAthlete = resultsListView.getSelectionModel().getSelectedItem();
            if (selectedAthlete != null) {
                displayAthleteProfile(selectedAthlete);
            }
        });
    }

    private void initAthleteProfile() {
        profilePane.setVisible(false);
    }

    private void displayAthleteProfile(Athlete selectedAthlete) {
        profilePane.setVisible(true);
        idLabel.setText(String.valueOf(selectedAthlete.getId()));
        nameLabel.setText(selectedAthlete.getName());
        sexLabel.setText(selectedAthlete.getSex().equalsIgnoreCase("M") ? "Male" : "Female");
        ageLabel.setText(DatabaseUtility.arrayToStringDisplay(selectedAthlete.getAgeList()));
        heightLabel.setText(DatabaseUtility.arrayToStringDisplay(selectedAthlete.getHeightList()));
        weightLabel.setText(DatabaseUtility.arrayToStringDisplay(selectedAthlete.getWeightList()));

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

        //noinspection unchecked
        participationTable.getColumns().addAll(olympicGameTableColumn, teamTableColumn, eventTableColumn, medalTableColumn);
    }

    private void fillAthleteParticipationTable(ArrayList<Participation> participationList) {
        participationTable.getItems().clear();
        participationList.forEach(participation -> participationTable.getItems().add(participation));
        if (participationTable.getColumns().size() > 0) {
            participationTable.getColumns().get(0).setSortType(TableColumn.SortType.ASCENDING);
            participationTable.getSortOrder().add(participationTable.getColumns().get(0));
        }
    }

    private void initAthleteListView(TreeMap<Integer, Athlete> resultMap) {
        resultsListView.setCellFactory(param -> new ListCell<>() {
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
        resultsListView.setItems(athletes);
        for (Map.Entry<Integer, Athlete> entry : resultMap.entrySet()) {
            athletes.add(entry.getValue());
        }
    }

    private void initAthleteListView() {
        TreeMap<Integer, Athlete> athleteMap = mapReference.getAthleteDB().getAthleteMap();
        initAthleteListView(athleteMap);
        resultCount.textProperty().bind(Bindings.size((resultsListView.getItems())).asString());

    }

    private void initAthleteSearchHandler() {
        searchButton.setOnAction(event -> {
            resultsListView.getItems().clear();
            initAthleteListView(mapReference.getAthleteDB().searchAthlete(searchInput.getText()));
        });

        searchInput.setOnAction(action -> {
            resultsListView.getItems().clear();
            initAthleteListView(mapReference.getAthleteDB().searchAthlete(searchInput.getText()));
        });

    }
}
