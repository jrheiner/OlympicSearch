package olympic.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import olympic.maps.MapReference;
import olympic.utility.DatabaseUtility;

import java.util.Set;
import java.util.TreeSet;

/**
 * UI Controller for the discipline tab
 */
public class DisciplineTabController {
    private final ObservableList<String> disciplines = FXCollections.observableArrayList();
    private MapReference mapReference;
    @FXML
    private Button disciplineSearchButton;
    @FXML
    private TextField disciplineSearchInput;
    @FXML
    private ListView<String> disciplineResultsListView;

    /**
     * Initialise Discipline tab UI.
     * <p>
     * Fill discipline list. Initialise event handler for searching.
     */
    void initDisciplineTab() {
        initDisciplineListView();
        initDisciplineSearchHandler();
    }

    private void initDisciplineSearchHandler() {
        disciplineSearchButton.setOnAction(event -> {
            disciplineResultsListView.getItems().clear();
            initDisciplineListView(DatabaseUtility.searchInSet(disciplineSearchInput.getText(), mapReference.getEventList().getDisciplines()));
        });

        disciplineSearchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            disciplineResultsListView.getItems().clear();
            initDisciplineListView(DatabaseUtility.searchInSet(disciplineSearchInput.getText(), mapReference.getEventList().getDisciplines()));
        });
    }

    private void initDisciplineListView(Set<String> resultSet) {
        disciplineResultsListView.setItems(disciplines);
        disciplines.addAll(resultSet);
    }

    private void initDisciplineListView() {
        TreeSet<String> disciplineSet = mapReference.getEventList().getDisciplines();
        initDisciplineListView(disciplineSet);
    }

    public void setListReference(MapReference mapReference) {
        this.mapReference = mapReference;
    }

    /**
     * Reload discipline list to include internal database changes
     */
    void refreshDisciplineListView() {
        disciplineResultsListView.getItems().clear();
        initDisciplineListView();
    }
}