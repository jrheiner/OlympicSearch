package sample.UIController;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Database.Athlete;
import sample.Database.Participation;
import sample.Database.Team;
import sample.DatabaseLists.ListReference;
import sample.DatabaseLists.TeamList;
import sample.Utility.ListUtility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class MainUI {


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

    //@Override
    public void initializeUI() { //URL url, ResourceBundle resourceBundle
        initAthleteTab();
        initTeamTab();
    }

    private void initTeamTab() {
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
            System.out.println("clicked on " + selectedTeam);
            displayTeamProfile(selectedTeam);

        });
    }

    public void initTeamSearchHandler() {
        teamSearchButton.setOnAction(event -> {
            teamResultsListView.getItems().clear();
            initTeamListView(TeamList.searchInTeamMap(teamSearchInput.getText(), TeamList.getTeamMap()));
        });

        /* LIVE SEARCH*/
        teamSearchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            teamResultsListView.getItems().clear();
            initTeamListView(TeamList.searchInTeamMap(teamSearchInput.getText(), TeamList.getTeamMap()));
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
        TreeMap<String, Team> teamMap = TeamList.getTeamMap();
        initTeamListView(teamMap);
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
    }

    public void initAthleteSearchHandler() {
        athleteSearchButton.setOnAction(event -> {
            athleteResultsListView.getItems().clear();
            initAthleteListView(listReference.getAthleteList().searchAthlete(athleteSearchInput.getText(), listReference.getAthleteList().getAthleteMap()));
        });

        /* LIVE SEARCH
        athleteSearchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            athleteResultsListView.getItems().clear();
            initAthleteListView(AthleteList.searchAthlete(athleteSearchInput.getText(), AthleteList.getAthleteMap()));
        });*/
    }
}
