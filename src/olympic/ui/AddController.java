package olympic.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import olympic.database.Athlete;
import olympic.list.ListReference;
import olympic.utility.ListUtility;

import java.util.ArrayList;
import java.util.regex.Pattern;

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
    private TextField addDiscipline;
    @FXML
    private TextField addEvent;
    @FXML
    private Button addSave;
    @FXML
    private Button addCancel;
    @FXML
    private ComboBox<String> addMedal;

    public void initAddForm() {
        adjustFormMode(isNewAthlete);
        initDropdowns();
        addYear.textProperty().addListener((observable, oldValue, newValue) -> {
            addOlympicGame.setText(addSeason.getText() + " " + addYear.getText());
        });
        addSeason.textProperty().addListener((observable, oldValue, newValue) -> {
            addOlympicGame.setText(addSeason.getText() + " " + addYear.getText());
        });
        addSave.setOnAction(event -> submitForm());
        addCancel.setOnAction(event -> addCancel.getScene().getWindow().hide());
    }

    private void submitForm() {


        if (checkValidity(isNewAthlete)) {
            // [ID, Name, Sex, Age, Height, Weight, Team, NOC, Games, Year, Season, City, Sport, Event, Medal]
            ArrayList<String> formData = new ArrayList<>();
            // add all fields to array
            formData.add(String.valueOf(addId));

            //pass formData to filehandle Writer
        }
    }

    private boolean checkValidity(Boolean isNewAthlete) {
        final Pattern stringPattern = Pattern.compile("\\S.*");
        final Pattern integerPattern = Pattern.compile("[0-9]+");
        final Pattern floatPattern = Pattern.compile("([0-9]*[.])?[0-9]+");
        boolean idValidity;
        boolean nameValidity;
        boolean sexValidity;
        boolean ageValidity;
        boolean heightValidity;
        boolean weightValidity;
        boolean teamValidity;
        boolean nocValidity;
        boolean yearValidity;
        boolean seasonValidity;
        boolean cityValidity;
        boolean disciplineValidity;
        boolean eventValidity;
        boolean medalValidity;
        if (isNewAthlete) {
            idValidity = true;
            nameValidity = stringPattern.matcher(addName.getText()).matches();
            sexValidity = (addSex.getSelectionModel().getSelectedIndex() > -1);
            ageValidity = integerPattern.matcher(addAge.getText()).matches();
            heightValidity = integerPattern.matcher(addHeight.getText()).matches();
            weightValidity = floatPattern.matcher(addWeight.getText()).matches();
        } else {
            idValidity = integerPattern.matcher(addId.getText()).matches();
            nameValidity = !(addName.getText().equals(""));
            sexValidity = true;
            ageValidity = true;
            heightValidity = true;
            weightValidity = true;
        }
        teamValidity = stringPattern.matcher(addTeam.getText()).matches();
        nocValidity = stringPattern.matcher(addNOC.getText()).matches() && (addNOC.getText().length() == 3);
        yearValidity = integerPattern.matcher(addYear.getText()).matches();
        seasonValidity = stringPattern.matcher(addSeason.getText()).matches();
        cityValidity = stringPattern.matcher(addCity.getText()).matches();
        disciplineValidity = stringPattern.matcher(addDiscipline.getText()).matches();
        eventValidity = stringPattern.matcher(addEvent.getText()).matches();
        medalValidity = (addMedal.getSelectionModel().getSelectedIndex() > -1);

        isValid = (idValidity && nameValidity &&
                sexValidity && ageValidity &&
                heightValidity && weightValidity &&
                teamValidity && nocValidity &&
                yearValidity && seasonValidity &&
                cityValidity && disciplineValidity &&
                eventValidity && medalValidity);
        System.out.println(isValid);
        return isValid;
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
                try {
                    if (!newValue.equals("")) {
                        fillAthleteData(Integer.parseInt(newValue));
                    } else {
                        fillAthleteData(0);
                    }
                } catch (java.lang.NumberFormatException e) {
                    isValid = false;
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
