package olympic.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import olympic.database.Athlete;
import olympic.list.ListReference;
import olympic.utility.ListUtility;

public class AddController {
    ListReference listReference;
    Boolean isNewAthlete = false;
    Boolean isValid = false;
    @FXML
    private Label addTitle;
    @FXML
    private TextField addId;
    @FXML
    private TextField addName;
    @FXML
    private ComboBox<String> addSex;
    @FXML
    private TextField addAge;
    @FXML
    private TextField addHeight;
    @FXML
    private TextField addWeight;
    @FXML
    private TextField addTeam;
    @FXML
    private TextField addNOC;
    @FXML
    private TextField addYear;
    @FXML
    private TextField addSeason;
    @FXML
    private TextField addOlympicGame;
    @FXML
    private TextField addCity;
    @FXML
    private TextField addEvent;
    @FXML
    private ComboBox<String> addMedal;

    public void initAddForm() {
        adjustFormMode(isNewAthlete);
        initDropdowns();
    }

    private void initDropdowns() {
        addSex.getItems().addAll("M", "F");
        addMedal.getItems().addAll("Gold", "Silver", "Bronze", "NA");
    }

    private void adjustFormMode(Boolean isNewAthlete) {
        if (isNewAthlete) {
            addTitle.setText("New athlete");
            addId.setText(String.valueOf(listReference.getAthleteList().getAthleteMap().lastKey() + 1));

            addId.setDisable(true);
        } else {
            addId.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.equals("")) {
                    fillAthleteData(Integer.parseInt(newValue));
                } else {
                    fillAthleteData(0);
                }
            });
            addTitle.setText("New participation");
            addName.setDisable(true);
            addSex.setDisable(true);
            addAge.setDisable(true);
            addHeight.setDisable(true);
            addWeight.setDisable(true);
        }
    }

    private void fillAthleteData(int id) {
        // get athlete by id and fill below fields
        Athlete selectedAthlete = listReference.getAthleteList().getAthleteById(id);
        if (selectedAthlete != null) {
            addName.setText(selectedAthlete.getName());
            addSex.getSelectionModel().select(selectedAthlete.getSex());
            addAge.setText(ListUtility.arrayToStringDisplay(selectedAthlete.getAgeList()));
            addHeight.setText(ListUtility.arrayToStringDisplay(selectedAthlete.getHeightList()));
            addWeight.setText(ListUtility.arrayToStringDisplay(selectedAthlete.getWeightList()));
        } else {
            isValid = false;
            addName.setText("");
            addSex.getSelectionModel().select("");
            addAge.setText("");
            addHeight.setText("");
            addWeight.setText("");
        }

    }

    public Boolean getIsNewAthlete() {
        return isNewAthlete;
    }

    public void setIsNewAthlete(Boolean isNewAthlete) {
        this.isNewAthlete = isNewAthlete;
    }

    public ListReference getListReference() {
        return listReference;
    }

    public void setListReference(ListReference listReference) {
        this.listReference = listReference;
    }
}
