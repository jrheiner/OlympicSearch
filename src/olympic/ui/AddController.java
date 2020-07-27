package olympic.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import olympic.database.Athlete;
import olympic.list.ListReference;
import olympic.utility.ListUtility;

import java.util.regex.Pattern;

public class AddController {
    ListReference listReference;
    private Boolean isNewAthlete = false;
    private Boolean isValid = false;
    private Boolean validationHandler = false;
    private MainController mainController;
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

    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void initAddForm() {
        adjustFormMode(isNewAthlete);
        initDropdowns();
        initEventHandler();
    }

    private void initEventHandler() {
        addYear.textProperty().addListener((observable, oldValue, newValue) -> {
            addOlympicGame.setText(addYear.getText() + " " + addSeason.getText());
        });
        addSeason.textProperty().addListener((observable, oldValue, newValue) -> {
            addOlympicGame.setText(addYear.getText() + " " + addSeason.getText());
        });
        addSave.setOnAction(event -> submitForm());
        addCancel.setOnAction(event -> addCancel.getScene().getWindow().hide());
    }

    private void submitForm() {
        addSave.setDisable(true);
        if (checkValidity(isNewAthlete)) {
            mainController.saveLineToDatabase(Integer.parseInt(addId.getText()), addName.getText(), addSex.getSelectionModel().getSelectedItem(), Integer.parseInt(addAge.getText()), Integer.parseInt(addHeight.getText()), Float.parseFloat(addWeight.getText()), addTeam.getText(), addNOC.getText(), addOlympicGame.getText(), Integer.parseInt(addYear.getText()), addSeason.getText(), addCity.getText(), addDiscipline.getText(), addEvent.getText(), addMedal.getSelectionModel().getSelectedItem());
            addSave.getScene().getWindow().hide();
            mainController.refreshListViews();
        } else {
            addSave.setDisable(false);
        }
    }

    private boolean checkValidity(Boolean isNewAthlete) {
        final Pattern stringPattern = Pattern.compile("\\S.*");
        final Pattern integerPattern = Pattern.compile("[0-9]+");
        final Pattern floatPattern = Pattern.compile("([0-9]*[.])?[0-9]+");
        boolean[] validity = new boolean[12];
        TextField[] textFields = {addId, addName, addAge, addHeight, addWeight, addTeam, addNOC, addYear, addSeason, addCity, addDiscipline, addEvent};

        if (isNewAthlete) {
            validity[0] = true;
            validity[1] = stringPattern.matcher(addName.getText()).matches();
            if (addSex.getSelectionModel().getSelectedIndex() == -1) {
                addSex.setStyle("-fx-border-color: red");
            } else {
                addSex.setStyle("-fx-border-color: green");
            }
            validity[2] = integerPattern.matcher(addAge.getText()).matches();
            validity[3] = integerPattern.matcher(addHeight.getText()).matches();
            validity[4] = floatPattern.matcher(addWeight.getText()).matches();
        } else {
            validity[0] = integerPattern.matcher(addId.getText()).matches();
            validity[1] = !(addName.getText().equals(""));
            validity[2] = true;
            validity[3] = true;
            validity[4] = true;
        }
        validity[5] = stringPattern.matcher(addTeam.getText()).matches();
        validity[6] = stringPattern.matcher(addNOC.getText()).matches() && (addNOC.getText().length() == 3);
        validity[7] = integerPattern.matcher(addYear.getText()).matches();
        validity[8] = stringPattern.matcher(addSeason.getText()).matches();
        validity[9] = stringPattern.matcher(addCity.getText()).matches();
        validity[10] = stringPattern.matcher(addDiscipline.getText()).matches();
        validity[11] = stringPattern.matcher(addEvent.getText()).matches();

        if (addMedal.getSelectionModel().getSelectedIndex() == -1) {
            addMedal.setStyle("-fx-border-color: red");
        } else {
            addMedal.setStyle("-fx-border-color: green");
        }
        int invalidFields = 0;
        for (int i = 0; i < validity.length; i++) {
            if (!validity[i]) {
                invalidFields++;
                textFields[i].setStyle("-fx-border-color: red");
            } else {
                textFields[i].setStyle("-fx-border-color: green");
            }
        }
        addValidationHandler(isNewAthlete, textFields);
        isValid = invalidFields == 0;
        return isValid;
    }

    private void addValidationHandler(Boolean isNewAthlete, TextField[] textFields) {
        if (!validationHandler) {
            for (TextField field : textFields) {
                field.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!oldValue.equals(newValue)) {
                        checkValidity(isNewAthlete);
                    }
                });
            }
            addSex.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.equals("")) {
                    checkValidity(isNewAthlete);
                }
            });
            addMedal.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.equals("")) {
                    checkValidity(isNewAthlete);
                }
            });
            validationHandler = true;
        }
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
