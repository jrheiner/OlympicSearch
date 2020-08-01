package olympic.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private final ObservableList<String> teamAthletes = FXCollections.observableArrayList();
    private final ObservableList<String> teamGames = FXCollections.observableArrayList();
    private MapReference mapReference;

    @FXML
    private Button teamSearchButton;
    @FXML
    private TextField teamSearchInput;
    @FXML
    private ListView<Team> teamResultsListView;
    @FXML
    private Label teamTeamLabel;
    @FXML
    private Label teamNOCLabel;
    @FXML
    private ListView<String> teamAthleteList;
    @FXML
    private ListView<String> teamGameList;

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
        displayTeamProfile(teamResultsListView.getItems().get(0));
    }

    private void displayTeamProfile(Team selectedTeam) {
        teamTeamLabel.setText(selectedTeam.getTeam());
        teamNOCLabel.setText(selectedTeam.getNoc());

        fillTeamAthleteList(selectedTeam.getAthleteList());
        fillTeamGameList(selectedTeam.getOlympicGameList());

    }

    private void fillTeamGameList(ArrayList<String> games) {
        teamGameList.getItems().clear();
        teamGameList.setItems(teamGames);
        Collections.sort(games);
        teamGames.addAll(games);
    }

    private void fillTeamAthleteList(ArrayList<String> athletes) {
        teamAthleteList.getItems().clear();
        teamAthleteList.setItems(teamAthletes);
        Collections.sort(athletes);
        teamAthletes.addAll(athletes);
    }

    private void initTeamListViewHandler() {
        teamResultsListView.setOnMouseClicked(event -> {
            Team selectedTeam = teamResultsListView.getSelectionModel().getSelectedItem();
            if (selectedTeam != null) {
                System.out.println("clicked on " + selectedTeam);
                displayTeamProfile(selectedTeam);
            }
        });
    }

    private void initTeamSearchHandler() {
        teamSearchButton.setOnAction(event -> {
            teamResultsListView.getItems().clear();
            initTeamListView(mapReference.getTeamList().searchTeam(teamSearchInput.getText()));
        });

        teamSearchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            teamResultsListView.getItems().clear();
            initTeamListView(mapReference.getTeamList().searchTeam(teamSearchInput.getText()));
        });
    }

    private void initTeamListView(TreeMap<String, Team> resultMap) {
        teamResultsListView.setCellFactory(param -> new ListCell<>() {
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
        teamResultsListView.setItems(teams);
        for (Map.Entry<String, Team> entry : resultMap.entrySet()) {
            teams.add(entry.getValue());
        }
    }

    private void initTeamListView() {
        TreeMap<String, Team> teamMap = mapReference.getTeamList().getTeamMap();
        initTeamListView(teamMap);
    }

    /**
     * Reload team list to include internal database changes
     */
    void refreshTeamListView() {
        teamResultsListView.getItems().clear();
        initTeamListView();
    }
}
