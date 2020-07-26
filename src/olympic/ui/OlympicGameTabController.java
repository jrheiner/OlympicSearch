package olympic.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import olympic.database.OlympicGame;
import olympic.list.ListReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class OlympicGameTabController {
    private final ObservableList<OlympicGame> olympicGames = FXCollections.observableArrayList();
    private final ObservableList<String> olympicGamesEvents = FXCollections.observableArrayList();
    ListReference listReference;
    @FXML
    private Button olympicGameSearchButton;
    @FXML
    private TextField olympicGameSearchInput;
    @FXML
    private ListView<OlympicGame> olympicGameResultsListView;
    @FXML
    private Label olympicGameGameLabel;
    @FXML
    private Label olympicGameCityLabel;
    @FXML
    private ListView<String> olympicGameEventList;

    public void initOlympicGameTab() {
        initOlympicGameListView();
        initOlympicGameSearchHandler();
        initOlympicGameListViewHandler();
        initOlympicGameProfile();
    }

    private void initOlympicGameProfile() {
        displayOlympicGameProfile(olympicGameResultsListView.getItems().get(0));
    }

    private void displayOlympicGameProfile(OlympicGame selectedGame) {
        olympicGameGameLabel.setText(selectedGame.getGame());
        olympicGameCityLabel.setText(selectedGame.getCity());

        fillOlympicGameEventList(selectedGame.getEventList());

    }

    private void fillOlympicGameEventList(ArrayList<String> games) {
        olympicGameEventList.getItems().clear();
        olympicGameEventList.setItems(olympicGamesEvents);
        Collections.sort(games);
        olympicGamesEvents.addAll(games);
    }


    private void initOlympicGameListViewHandler() {
        olympicGameResultsListView.setOnMouseClicked(event -> {
            OlympicGame selectedGame = olympicGameResultsListView.getSelectionModel().getSelectedItem();
            System.out.println("clicked on " + selectedGame);
            displayOlympicGameProfile(selectedGame);

        });
    }

    public void initOlympicGameSearchHandler() {
        olympicGameSearchButton.setOnAction(event -> {
            olympicGameResultsListView.getItems().clear();
            initOlympicGameListView(listReference.getOlympicGameList().searchOlympicGame(olympicGameSearchInput.getText(), listReference.getOlympicGameList().getOlympicGameMap()));
        });

        /* LIVE SEARCH*/
        olympicGameSearchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            olympicGameResultsListView.getItems().clear();
            initOlympicGameListView(listReference.getOlympicGameList().searchOlympicGame(olympicGameSearchInput.getText(), listReference.getOlympicGameList().getOlympicGameMap()));
        });
    }

    public void initOlympicGameListView(TreeMap<String, OlympicGame> resultMap) {
        olympicGameResultsListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(OlympicGame olympicGame, boolean empty) {
                super.updateItem(olympicGame, empty);
                if (empty || olympicGame == null || olympicGame.getGame() == null) {
                    setText(null);
                } else {
                    setText(olympicGame.getGame());
                }
            }
        });
        olympicGameResultsListView.setItems(olympicGames);
        for (Map.Entry<String, OlympicGame> entry : resultMap.entrySet()) {
            olympicGames.add(entry.getValue());
        }
    }

    public void initOlympicGameListView() {
        TreeMap<String, OlympicGame> olympicGameMap = listReference.getOlympicGameList().getOlympicGameMap();
        initOlympicGameListView(olympicGameMap);
    }


    public ListReference getListReference() {
        return listReference;
    }

    public void setListReference(ListReference listReference) {
        this.listReference = listReference;
    }
}