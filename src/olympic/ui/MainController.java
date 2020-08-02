package olympic.ui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import olympic.Main;
import olympic.filehandle.Writer;
import olympic.maps.MapReference;
import olympic.utility.DatabaseUtility;

import java.io.IOException;

/**
 * UI Controller for the main window, coordinating single tab controllers
 */
public class MainController {

    private MapReference mapReference;
    private Writer fileWriter;
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

    public void setFileWriter(Writer fileWriter) {
        this.fileWriter = fileWriter;
    }

    public void setListReference(MapReference mapReference) {
        this.mapReference = mapReference;
    }

    /**
     * Initialise UI and UI elements of all tabs.
     */
    public void initializeUI() {
        AthleteTabController.setListReference(mapReference);
        AthleteTabController.setMainController(this);
        AthleteTabController.initAthleteTab();

        TeamTabController.setListReference(mapReference);
        TeamTabController.initTeamTab();

        EventTabController.setListReference(mapReference);
        EventTabController.initEventTab();

        DisciplineTabController.setListReference(mapReference);
        DisciplineTabController.initDisciplineTab();

        OlympicGameTabController.setListReference(mapReference);
        OlympicGameTabController.initOlympicGameTab();
    }

    /**
     * Open new popup window <p>
     * Open new popup and set form mode to either adding a new athlete or new event.
     *
     * @param event Event Handler action
     */
    void openPopup(Event event) {
        Parent root;
        AddController addController;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Add.fxml"));
            root = loader.load();
            addController = loader.getController();
            addController.setListReference(mapReference);
            addController.setMainController(this);
            Stage addStage = new Stage();
            if (((Control) event.getSource()).getId().equals("athleteAddAthleteButton")) {
                addStage.setTitle("Add new athlete");
                addController.setIsNewAthlete(true);
            } else {
                addStage.setTitle("Add new participation");
                addController.setIsNewAthlete(false);
            }
            addController.initAddForm();
            addStage.getIcons().add(new Image(Main.class.getResourceAsStream("icon.png")));
            addStage.setScene(new Scene(root, 660, 490));
            addStage.setResizable(false);
            addStage.initModality(Modality.APPLICATION_MODAL);
            addStage.showAndWait();
            System.out.println(((Control) event.getSource()).getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Passes data to file writer to permanently save it and adds it to the internal database.
     *
     * @param id          Athlete id
     * @param name        Athlete name
     * @param sex         Athlete sex
     * @param age         Athlete age
     * @param height      Athlete height
     * @param weight      Athlete weight
     * @param team        Athletes team
     * @param noc         Teams national olympic committee
     * @param olympicGame Game name
     * @param year        Game year
     * @param season      Game season
     * @param city        Game city
     * @param sport       Event discipline
     * @param event       Event name
     * @param medal       Athlete medal
     */
    void saveLineToDatabase(int id, String name, String sex, int age, int height, float weight, String team, String noc, String olympicGame, int year, String season, String city, String sport, String event, String medal) {
        DatabaseUtility.addLineToDatabase(id, name, sex, age, height, weight, team, noc, olympicGame, year, season, city, sport, event, medal, mapReference);
        fileWriter.save(id, name, sex, age, height, weight, team, noc, olympicGame, year, season, city, sport, event, medal);
    }

    /**
     * Reload all tabs to include internal database changes
     */
    void refreshListViews() {
        AthleteTabController.refreshAthleteListView();
        TeamTabController.refreshTeamListView();
        EventTabController.refreshEventListView();
        DisciplineTabController.refreshDisciplineListView();
        OlympicGameTabController.refreshOlympicGameListView();
    }
}
