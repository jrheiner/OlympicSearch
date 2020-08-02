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
    private final ObservableList<String> events = FXCollections.observableArrayList();
    private MapReference mapReference;
    @FXML
    private Button searchButton;
    @FXML
    private TextField searchInput;
    @FXML
    private ListView<OlympicGame> resultsListView;
    @FXML
    private Label gameLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private ListView<String> eventListView;
    @FXML
    private GridPane profilePane;

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
        profilePane.setVisible(false);
    }

    private void displayOlympicGameProfile(OlympicGame selectedGame) {
        profilePane.setVisible(true);
        gameLabel.setText(selectedGame.getGame());
        cityLabel.setText(selectedGame.getCity());

        fillOlympicGameEventList(selectedGame.getEventList());

    }

    private void fillOlympicGameEventList(ArrayList<String> games) {
        eventListView.getItems().clear();
        eventListView.setItems(events);
        Collections.sort(games);
        events.addAll(games);
    }

    private void initOlympicGameListViewHandler() {
        resultsListView.setOnMouseClicked(event -> {
            OlympicGame selectedGame = resultsListView.getSelectionModel().getSelectedItem();
            if (selectedGame != null) {
                displayOlympicGameProfile(selectedGame);
            }
        });
    }

    private void initOlympicGameSearchHandler() {
        searchButton.setOnAction(event -> {
            resultsListView.getItems().clear();
            initOlympicGameListView(mapReference.getOlympicGameDB().searchOlympicGame(searchInput.getText()));
        });

        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                searchInput.setText(newValue.replaceAll("[^\\d]", ""));
            } else {
                resultsListView.getItems().clear();
                initOlympicGameListView(mapReference.getOlympicGameDB().searchOlympicGame(searchInput.getText()));
            }
        });
    }

    private void initOlympicGameListView(TreeMap<String, OlympicGame> resultMap) {
        resultsListView.setCellFactory(param -> new ListCell<>() {
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
        resultsListView.setItems(olympicGames);
        for (Map.Entry<String, OlympicGame> entry : resultMap.entrySet()) {
            olympicGames.add(entry.getValue());
        }
    }

    private void initOlympicGameListView() {
        TreeMap<String, OlympicGame> olympicGameMap = mapReference.getOlympicGameDB().getOlympicGameMap();
        initOlympicGameListView(olympicGameMap);
    }

    void setListReference(MapReference mapReference) {
        this.mapReference = mapReference;
    }

    /**
     * Reload game list to include internal database changes
     */
    void refreshOlympicGameListView() {
        resultsListView.getItems().clear();
        initOlympicGameListView();
    }
}