package olympic.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import olympic.database.Athlete;
import olympic.database.Team;
import olympic.list.ListReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class TeamTabController {


    private final ObservableList<Athlete> athletes = FXCollections.observableArrayList();
    private final ObservableList<Team> teams = FXCollections.observableArrayList();
    private final ObservableList<String> teamAthletes = FXCollections.observableArrayList();
    private final ObservableList<String> teamGames = FXCollections.observableArrayList();
    ListReference listReference;

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

    public ListReference getListReference() {
        return listReference;
    }

    public void setListReference(ListReference listReference) {
        this.listReference = listReference;
    }


    public void initTeamTab() {
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

    public void initTeamSearchHandler() {
        teamSearchButton.setOnAction(event -> {
            teamResultsListView.getItems().clear();
            initTeamListView(listReference.getTeamList().searchTeam(teamSearchInput.getText(), listReference.getTeamList().getTeamMap()));
        });

        /* LIVE SEARCH*/
        teamSearchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            teamResultsListView.getItems().clear();
            initTeamListView(listReference.getTeamList().searchTeam(teamSearchInput.getText(), listReference.getTeamList().getTeamMap()));
        });
    }

    public void initTeamListView(TreeMap<String, Team> resultMap) {
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

    public void initTeamListView() {
        TreeMap<String, Team> teamMap = listReference.getTeamList().getTeamMap();
        initTeamListView(teamMap);
    }
}
