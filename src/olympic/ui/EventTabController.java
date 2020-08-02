package olympic.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import olympic.database.Event;
import olympic.maps.MapReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * UI Controller for the event tab
 */
public class EventTabController {
    private final ObservableList<Event> events = FXCollections.observableArrayList();
    private final ObservableList<String> olympicGames = FXCollections.observableArrayList();
    private MapReference mapReference;
    @FXML
    private Button searchButton;
    @FXML
    private TextField searchInput;
    @FXML
    private ListView<Event> resultsListView;
    @FXML
    private Label eventLabel;
    @FXML
    private Label disciplineLabel;
    @FXML
    private ListView<String> olympicGameList;
    @FXML
    private GridPane profilePane;

    /**
     * Initialise Event tab UI.
     * <p>
     * Fill event list. Initialise event handler for search or event list and set default event profile.
     */
    void initEventTab() {
        initEventListView();
        initEventSearchHandler();
        initEventListViewHandler();
        initEventProfile();
    }

    private void initEventProfile() {
        profilePane.setVisible(false);
    }

    private void displayEventProfile(Event selectedEvent) {
        profilePane.setVisible(true);
        eventLabel.setText(selectedEvent.getEvent());
        disciplineLabel.setText(selectedEvent.getDiscipline());

        fillEventGameList(selectedEvent.getOlympicGameList());
    }

    private void fillEventGameList(ArrayList<String> games) {
        olympicGameList.getItems().clear();
        olympicGameList.setItems(olympicGames);
        Collections.sort(games);
        olympicGames.addAll(games);
    }

    private void initEventListViewHandler() {
        resultsListView.setOnMouseClicked(event -> {
            Event selectedEvent = resultsListView.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                displayEventProfile(selectedEvent);
            }
        });
    }

    private void initEventSearchHandler() {
        searchButton.setOnAction(event -> {
            resultsListView.getItems().clear();
            initEventListView(mapReference.getEventDB().searchEvent(searchInput.getText()));
        });

        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            resultsListView.getItems().clear();
            initEventListView(mapReference.getEventDB().searchEvent(searchInput.getText()));
        });
    }

    private void initEventListView(TreeMap<String, Event> resultMap) {
        resultsListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Event event, boolean empty) {
                super.updateItem(event, empty);
                if (empty || event == null || event.getEvent() == null) {
                    setText(null);
                } else {
                    setText(event.getEvent());
                }
            }
        });
        resultsListView.setItems(events);
        for (Map.Entry<String, Event> entry : resultMap.entrySet()) {
            events.add(entry.getValue());
        }
    }

    private void initEventListView() {
        TreeMap<String, Event> eventMap = mapReference.getEventDB().getEventMap();
        initEventListView(eventMap);

    }

    void setListReference(MapReference mapReference) {
        this.mapReference = mapReference;
    }

    /**
     * Reload event list to include internal database changes
     */
    void refreshEventListView() {
        resultsListView.getItems().clear();
        initEventListView();
    }
}
