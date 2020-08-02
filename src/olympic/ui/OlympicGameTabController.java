package olympic.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import olympic.database.OlympicGame;
import olympic.maps.MapReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * UI Controller for the olympic game tab
 */
public class OlympicGameTabController {
    private final ObservableList<OlympicGame> olympicGames = FXCollections.observableArrayList();
    private final ObservableList<String> olympicGamesEvents = FXCollections.observableArrayList();
    private MapReference mapReference;
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
    @FXML
    private GridPane olympicGameProfilePane;

    /**
     * Initialise Game tab UI.
     * <p>
     * Fill game list. Initialise event handler for search and game list. Set default game profile.
     */
    void initOlympicGameTab() {
        initOlympicGameListView();
        initOlympicGameSearchHandler();
        initOlympicGameListViewHandler();
        initOlympicGameProfile();
    }

    private void initOlympicGameProfile() {
        olympicGameProfilePane.setVisible(false);
    }

    private void displayOlympicGameProfile(OlympicGame selectedGame) {
        olympicGameProfilePane.setVisible(true);
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
            if (selectedGame != null) {
                displayOlympicGameProfile(selectedGame);
            }
        });
    }

    private void initOlympicGameSearchHandler() {
        olympicGameSearchButton.setOnAction(event -> {
            olympicGameResultsListView.getItems().clear();
            initOlympicGameListView(mapReference.getOlympicGameDB().searchOlympicGame(olympicGameSearchInput.getText()));
        });

        olympicGameSearchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                olympicGameSearchInput.setText(newValue.replaceAll("[^\\d]", ""));
            } else {
                olympicGameResultsListView.getItems().clear();
                initOlympicGameListView(mapReference.getOlympicGameDB().searchOlympicGame(olympicGameSearchInput.getText()));
            }
        });
    }

    private void initOlympicGameListView(TreeMap<String, OlympicGame> resultMap) {
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

    private void initOlympicGameListView() {
        TreeMap<String, OlympicGame> olympicGameMap = mapReference.getOlympicGameDB().getOlympicGameMap();
        initOlympicGameListView(olympicGameMap);
    }

    public void setListReference(MapReference mapReference) {
        this.mapReference = mapReference;
    }

    /**
     * Reload game list to include internal database changes
     */
    void refreshOlympicGameListView() {
        olympicGameResultsListView.getItems().clear();
        initOlympicGameListView();
    }
}