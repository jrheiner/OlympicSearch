package olympic.ui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.stage.Stage;
import olympic.list.ListReference;

import java.io.IOException;

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
        AthleteTabController.setMainController(this);
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

    public void openPopup(Event event) {
        Parent root;
        AddController addController;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Add.fxml"));
            root = loader.load();
            addController = loader.getController();
            addController.setListReference(listReference);
            Stage stage = new Stage();
            if (((Control) event.getSource()).getId().equals("athleteAddAthleteButton")) {
                stage.setTitle("Add new athlete");
                addController.setIsNewAthlete(true);
            } else {
                stage.setTitle("Add new participation");
            }
            addController.initAddForm();
            stage.setScene(new Scene(root, 660, 490));
            stage.setResizable(false);
            stage.show();
            System.out.println(((Control) event.getSource()).getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
