package olympic.ui;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import olympic.database.Event;
import olympic.list.ListReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class EventTabController {
    private final ObservableList<Event> events = FXCollections.observableArrayList();
    private final ObservableList<String> eventsOlympicGames = FXCollections.observableArrayList();
    ListReference listReference;
    @FXML
    private Button eventSearchButton;
    @FXML
    private TextField eventSearchInput;
    @FXML
    private ListView<Event> eventResultsListView;
    @FXML
    private Button eventAddButton;
    @FXML
    private Label eventResultCount;
    @FXML
    private Label eventEventLabel;
    @FXML
    private Label eventDisciplineLabel;

    @FXML
    private ListView<String> eventOlympicGameList;

    public void initEventTab() {
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

    public void initEventSearchHandler() {
        eventSearchButton.setOnAction(event -> {
            eventResultsListView.getItems().clear();
            initEventListView(listReference.getEventList().searchEvent(eventSearchInput.getText(), listReference.getEventList().getEventMap()));
        });

        /* LIVE SEARCH*/
        eventSearchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            eventResultsListView.getItems().clear();
            initEventListView(listReference.getEventList().searchEvent(eventSearchInput.getText(), listReference.getEventList().getEventMap()));
        });
    }

    public void initEventListView(TreeMap<String, Event> resultMap) {
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

    public void initEventListView() {
        TreeMap<String, Event> eventMap = listReference.getEventList().getEventMap();
        initEventListView(eventMap);
        eventResultCount.textProperty().bind(Bindings.size((eventResultsListView.getItems())).asString());

    }


    public ListReference getListReference() {
        return listReference;
    }

    public void setListReference(ListReference listReference) {
        this.listReference = listReference;
    }
}
