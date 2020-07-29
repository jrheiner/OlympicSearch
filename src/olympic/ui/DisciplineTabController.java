package olympic.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import olympic.list.ListReference;
import olympic.utility.ListUtility;

import java.util.Set;
import java.util.TreeSet;

public class DisciplineTabController {
    private final ObservableList<String> disciplines = FXCollections.observableArrayList();
    private ListReference listReference;
    @FXML
    private Button disciplineSearchButton;
    @FXML
    private TextField disciplineSearchInput;
    @FXML
    private ListView<String> disciplineResultsListView;

    void initDisciplineTab() {
        initDisciplineListView();
        initDisciplineSearchHandler();
    }

    private void initDisciplineSearchHandler() {
        disciplineSearchButton.setOnAction(event -> {
            disciplineResultsListView.getItems().clear();
            initDisciplineListView(ListUtility.searchInSet(disciplineSearchInput.getText(), listReference.getEventList().getDisciplines()));
        });

        /* LIVE SEARCH*/
        disciplineSearchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            disciplineResultsListView.getItems().clear();
            initDisciplineListView(ListUtility.searchInSet(disciplineSearchInput.getText(), listReference.getEventList().getDisciplines()));
        });
    }

    private void initDisciplineListView(Set<String> resultSet) {
        disciplineResultsListView.setItems(disciplines);
        disciplines.addAll(resultSet);
    }

    private void initDisciplineListView() {
        TreeSet<String> disciplineSet = listReference.getEventList().getDisciplines();
        initDisciplineListView(disciplineSet);
    }

    public void setListReference(ListReference listReference) {
        this.listReference = listReference;
    }

    void refreshDisciplineListView() {
        disciplineResultsListView.getItems().clear();
        initDisciplineListView();
    }
}