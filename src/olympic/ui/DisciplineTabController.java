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
    private Button searchButton;
    @FXML
    private TextField searchInput;
    @FXML
    private ListView<String> resultsListView;

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
        searchButton.setOnAction(event -> {
            resultsListView.getItems().clear();
            initDisciplineListView(DatabaseUtility.searchInSet(searchInput.getText(), mapReference.getEventDB().getDisciplines()));
        });

        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            resultsListView.getItems().clear();
            initDisciplineListView(DatabaseUtility.searchInSet(searchInput.getText(), mapReference.getEventDB().getDisciplines()));
        });
    }

    private void initDisciplineListView(Set<String> resultSet) {
        resultsListView.setItems(disciplines);
        disciplines.addAll(resultSet);
    }

    private void initDisciplineListView() {
        TreeSet<String> disciplineSet = mapReference.getEventDB().getDisciplines();
        initDisciplineListView(disciplineSet);
    }

    void setListReference(MapReference mapReference) {
        this.mapReference = mapReference;
    }

    /**
     * Reload discipline list to include internal database changes
     */
    void refreshDisciplineListView() {
        resultsListView.getItems().clear();
        initDisciplineListView();
    }
}