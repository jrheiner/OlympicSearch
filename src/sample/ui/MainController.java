package sample.ui;

import javafx.fxml.FXML;
import sample.databaseLists.ListReference;

public class MainController {

    ListReference listReference;

    @FXML
    private AthleteTabController AthleteTabController;
    @FXML
    private TeamTabController TeamTabController;
    @FXML
    private EventTabController EventTabController;
    @FXML
    private OlympicGameTabController OlympicGameTabController;
    @FXML
    private DisciplineTabController DisciplineTabController;

    public ListReference getListReference() {
        return listReference;
    }

    public void setListReference(ListReference listReference) {
        this.listReference = listReference;
    }


    public void initializeUI() {
        AthleteTabController.setListReference(listReference);
        AthleteTabController.initAthleteTab();

        TeamTabController.setListReference(listReference);
        TeamTabController.initTeamTab();

        EventTabController.setListReference(listReference);
        EventTabController.initEventTab();

        DisciplineTabController.setListReference(listReference);
        DisciplineTabController.initDisciplineTab();

        OlympicGameTabController.setListReference(listReference);
        OlympicGameTabController.initOlympicGameTab();


    }

}
