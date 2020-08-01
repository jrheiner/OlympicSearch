package olympic.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private final ObservableList<String> eventsOlympicGames = FXCollections.observableArrayList();
    private MapReference mapReference;
    @FXML
    private Button eventSearchButton;
    @FXML
    private TextField eventSearchInput;
    @FXML
    private ListView<Event> eventResultsListView;
    @FXML
    private Label eventEventLabel;
    @FXML
    private Label eventDisciplineLabel;
    @FXML
    private ListView<String> eventOlympicGameList;

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
        displayEventProfile(eventResultsListView.getItems().get(0));
    }

    private void displayEventProfile(Event selectedEvent) {
        eventEventLabel.setText(selectedEvent.getEvent());
        eventDisciplineLabel.setText(selectedEvent.getDiscipline());

        fillEventGameList(selectedEvent.getOlympicGameList());
    }

    private void fillEventGameList(ArrayList<String> games) {
        eventOlympicGameList.getItems().clear();
        eventOlympicGameList.setItems(eventsOlympicGames);
        Collections.sort(games);
        eventsOlympicGames.addAll(games);
    }

    private void initEventListViewHandler() {
        eventResultsListView.setOnMouseClicked(event -> {
            Event selectedEvent = eventResultsListView.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                System.out.println("clicked on " + selectedEvent);
                displayEventProfile(selectedEvent);
            }
        });
    }

    private void initEventSearchHandler() {
        eventSearchButton.setOnAction(event -> {
            eventResultsListView.getItems().clear();
            initEventListView(mapReference.getEventList().searchEvent(eventSearchInput.getText()));
        });

        eventSearchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            eventResultsListView.getItems().clear();
            initEventListView(mapReference.getEventList().searchEvent(eventSearchInput.getText()));
        });
    }

    private void initEventListView(TreeMap<String, Event> resultMap) {
        eventResultsListView.setCellFactory(param -> new ListCell<>() {
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
        eventResultsListView.setItems(events);
        for (Map.Entry<String, Event> entry : resultMap.entrySet()) {
            events.add(entry.getValue());
        }
    }

    private void initEventListView() {
        TreeMap<String, Event> eventMap = mapReference.getEventList().getEventMap();
        initEventListView(eventMap);

    }

    public void setListReference(MapReference mapReference) {
        this.mapReference = mapReference;
    }

    /**
     * Reload event list to include internal database changes
     */
    void refreshEventListView() {
        eventResultsListView.getItems().clear();
        initEventListView();
    }
}
