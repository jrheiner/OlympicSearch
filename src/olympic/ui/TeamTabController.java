package olympic.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import olympic.database.Team;
import olympic.maps.MapReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * UI Controller for the team tab
 */
public class TeamTabController {
    private final ObservableList<Team> teams = FXCollections.observableArrayList();
    private final ObservableList<String> athletes = FXCollections.observableArrayList();
    private final ObservableList<String> games = FXCollections.observableArrayList();
    private MapReference mapReference;

    @FXML
    private Button searchButton;
    @FXML
    private TextField searchInput;
    @FXML
    private ListView<Team> resultsListView;
    @FXML
    private Label teamLabel;
    @FXML
    private Label nocLabel;
    @FXML
    private ListView<String> athleteListView;
    @FXML
    private ListView<String> gameListView;
    @FXML
    private GridPane profilePane;

    void setListReference(MapReference mapReference) {
        this.mapReference = mapReference;
    }

    /**
     * Initialise Team tab UI.
     * <p>
     * Fill team list. Initialise event handler for search and team list. Set default team profile.
     */
    void initTeamTab() {
        initTeamListView();
        initTeamSearchHandler();
        initTeamListViewHandler();
        initTeamProfile();
    }

    private void initTeamProfile() {
        profilePane.setVisible(false);
    }

    private void displayTeamProfile(Team selectedTeam) {
        profilePane.setVisible(true);
        teamLabel.setText(selectedTeam.getTeam());
        nocLabel.setText(selectedTeam.getNoc());

        fillTeamAthleteList(selectedTeam.getAthleteList());
        fillTeamGameList(selectedTeam.getOlympicGameList());

    }

    private void fillTeamGameList(ArrayList<String> games) {
        gameListView.getItems().clear();
        gameListView.setItems(this.games);
        Collections.sort(games);
        this.games.addAll(games);
    }

    private void fillTeamAthleteList(ArrayList<String> athletes) {
        athleteListView.getItems().clear();
        athleteListView.setItems(this.athletes);
        Collections.sort(athletes);
        this.athletes.addAll(athletes);
    }

    private void initTeamListViewHandler() {
        resultsListView.setOnMouseClicked(event -> {
            Team selectedTeam = resultsListView.getSelectionModel().getSelectedItem();
            if (selectedTeam != null) {
                displayTeamProfile(selectedTeam);
            }
        });
    }

    private void initTeamSearchHandler() {
        searchButton.setOnAction(event -> {
            resultsListView.getItems().clear();
            initTeamListView(mapReference.getTeamDB().searchTeam(searchInput.getText()));
        });

        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            resultsListView.getItems().clear();
            initTeamListView(mapReference.getTeamDB().searchTeam(searchInput.getText()));
        });
    }

    private void initTeamListView(TreeMap<String, Team> resultMap) {
        resultsListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Team team, boolean empty) {
                super.updateItem(team, empty);
                if (empty || team == null || team.getTeam() == null) {
                    setText(null);
                } else {
                    setText(team.getTeam());
                }
            }
        });
        resultsListView.setItems(teams);
        for (Map.Entry<String, Team> entry : resultMap.entrySet()) {
            teams.add(entry.getValue());
        }
    }

    private void initTeamListView() {
        TreeMap<String, Team> teamMap = mapReference.getTeamDB().getTeamMap();
        initTeamListView(teamMap);
    }

    /**
     * Reload team list to include internal database changes
     */
    void refreshTeamListView() {
        resultsListView.getItems().clear();
        initTeamListView();
    }
}
