package olympic.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import olympic.database.Athlete;
import olympic.list.ListReference;

import java.util.regex.Pattern;

public class AddController {
    private ListReference listReference;
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
    private ComboBox<String> addSeason;
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

    void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    void initAddForm() {
        adjustFormMode(isNewAthlete);
        initDropdowns();
        initEventHandler();
    }

    private void initEventHandler() {
        onlyIntInput(addId);
        onlyIntInput(addAge);
        onlyIntInput(addHeight);
        onlyIntInput(addId);
        addWeight.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("([0-9]*[.])?[0-9]*")) {
                addWeight.setText(newValue.replaceAll("[^0-9.]+", ""));
            }
        });

        addYear.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                addYear.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (newValue.length() == 5) {
                addYear.setText(oldValue);
            }
            addOlympicGame.setText(addYear.getText() + " " + (addSeason.getValue() == null ? "" : addSeason.getValue()));
        });

        addSeason.valueProperty().addListener((observable, oldValue, newValue) -> addOlympicGame.setText(addYear.getText() + " " + addSeason.getValue()));

        addNOC.textProperty().addListener((observable, oldValue, newValue) -> {
            addNOC.setText(newValue.toUpperCase());
            if (!newValue.toUpperCase().matches("[A-Z]+")) {
                addNOC.setText(newValue.replaceAll("[^A-Z]+", ""));
            }
            if (newValue.length() == 4) {
                addNOC.setText(oldValue.toUpperCase());
            }
        });

        addSave.setOnAction(event -> submitForm());
        addCancel.setOnAction(event -> addCancel.getScene().getWindow().hide());
    }

    private void onlyIntInput(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private void submitForm() {
        addSave.setDisable(true);
        if (checkValidity(isNewAthlete)) {
            mainController.saveLineToDatabase(Integer.parseInt(addId.getText()), addName.getText(), addSex.getSelectionModel().getSelectedItem(), Integer.parseInt(addAge.getText().equals("") ? "-1" : addAge.getText()), Integer.parseInt(addHeight.getText().equals("") ? "-1" : addHeight.getText()), Float.parseFloat(addWeight.getText().equals("") ? "-1.0" : addWeight.getText()), addTeam.getText(), addNOC.getText(), addOlympicGame.getText(), Integer.parseInt(addYear.getText()), addSeason.getValue(), addCity.getText(), addDiscipline.getText(), addEvent.getText(), addMedal.getSelectionModel().getSelectedItem());
            addSave.getScene().getWindow().hide();
            mainController.refreshListViews();
        } else {
            addSave.setDisable(false);
        }
    }

    private boolean checkValidity(Boolean isNewAthlete) {
        final Pattern stringPattern = Pattern.compile("\\S.*");
        final Pattern nocPattern = Pattern.compile("[A-Z]+");
        final Pattern integerPattern = Pattern.compile("[0-9]+");
        final Pattern floatPattern = Pattern.compile("([0-9]*[.])?[0-9]+");
        int invalidFields = 0;
        boolean[] validity = new boolean[11];
        TextField[] textFields = {addId, addName, addAge, addHeight, addWeight, addTeam, addNOC, addYear, addCity, addDiscipline, addEvent};

        if (isNewAthlete) {
            validity[0] = true;
            validity[1] = stringPattern.matcher(addName.getText()).matches();
            if (addSex.getSelectionModel().getSelectedIndex() == -1) {
                invalidFields++;
                markInvalid(addSex);
            } else {
                markValid(addSex);
            }
            validity[2] = integerPattern.matcher(addAge.getText()).matches();
            validity[3] = integerPattern.matcher(addHeight.getText()).matches();
            validity[4] = floatPattern.matcher(addWeight.getText()).matches();
        } else {
            validity[0] = integerPattern.matcher(addId.getText()).matches() && !(addName.getText().equals(""));
            markValid(addSex);
            validity[1] = true;
            validity[2] = true;
            validity[3] = true;
            validity[4] = true;
        }
        validity[5] = stringPattern.matcher(addTeam.getText()).matches();
        validity[6] = nocPattern.matcher(addNOC.getText()).matches() && (addNOC.getText().length() == 3);
        validity[7] = integerPattern.matcher(addYear.getText()).matches();
        if (addSeason.getSelectionModel().getSelectedIndex() == -1) {
            invalidFields++;
            markInvalid(addSeason);
        } else {
            markValid(addSeason);
        }
        validity[8] = stringPattern.matcher(addCity.getText()).matches();
        validity[9] = stringPattern.matcher(addDiscipline.getText()).matches();
        validity[10] = stringPattern.matcher(addEvent.getText()).matches();

        if (addMedal.getSelectionModel().getSelectedIndex() == -1) {
            invalidFields++;
            markInvalid(addMedal);
        } else {
            markValid(addMedal);
        }
        for (int i = 0; i < validity.length; i++) {
            if (!validity[i]) {
                invalidFields++;
                markInvalid(textFields[i]);
            } else {
                markValid(textFields[i]);
            }
        }
        addValidationHandler(isNewAthlete, textFields);
        isValid = invalidFields == 0;
        return isValid;
    }

    private void markInvalid(ComboBox<String> comboBox) {
        comboBox.setStyle("-fx-border-color: #dc3545; -fx-border-width: 2px");
    }

    private void markValid(ComboBox<String> comboBox) {
        comboBox.setStyle("-fx-border-color: #28a745; -fx-border-width: 2px");
    }

    private void markInvalid(TextField textField) {
        textField.setStyle("-fx-border-color: #dc3545; -fx-border-width: 2px");
    }

    private void markValid(TextField textField) {
        textField.setStyle("-fx-border-color: #28a745; -fx-border-width: 2px");
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
            addSeason.valueProperty().addListener((observable, oldValue, newValue) -> {
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
        addSeason.getItems().addAll("Summer", "Winter");
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
        Athlete selectedAthlete = listReference.getAthleteList().getAthleteById(id);
        if (selectedAthlete != null) {
            addName.setText(selectedAthlete.getName());
            addSex.getSelectionModel().select(selectedAthlete.getSex());
        } else {
            isValid = false;
            addName.setText("");
            addSex.getSelectionModel().select("");
        }
        addAge.setText("");
        addHeight.setText("");
        addWeight.setText("");
    }

    void setIsNewAthlete(Boolean isNewAthlete) {
        this.isNewAthlete = isNewAthlete;
    }

    public void setListReference(ListReference listReference) {
        this.listReference = listReference;
    }
}
