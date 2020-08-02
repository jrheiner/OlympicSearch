package olympic.ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import olympic.Main;
import olympic.database.Athlete;
import olympic.maps.MapReference;

import java.util.regex.Pattern;

/**
 * UI Controller for the popup form to add new athletes or events
 */
@SuppressWarnings("WeakerAccess")
public class AddController {
    private MapReference mapReference;
    private Boolean isNewAthlete;
    private Boolean isValid = false;
    private Boolean validationHandler = false;
    private MainController mainController;
    @FXML
    private Label title;
    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private ComboBox<String> sex;
    @FXML
    private TextField age;
    @FXML
    private TextField height;
    @FXML
    private TextField weight;
    @FXML
    private TextField team;
    @FXML
    private TextField noc;
    @FXML
    private TextField year;
    @FXML
    private ComboBox<String> season;
    @FXML
    private TextField olympicGame;
    @FXML
    private TextField city;
    @FXML
    private TextField discipline;
    @FXML
    private TextField event;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ComboBox<String> medal;

    void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Initialise the UI for adding new athletes or events <p>
     * <p>
     * Adjust form to either adding a new athlete or new event. Fill dropdown menu with values. Initialise event handler and listener.
     */
    void initAddForm() {
        adjustFormMode(isNewAthlete);
        initDropdowns();
        initEventHandler();
    }

    private void initEventHandler() {
        onlyIntInput(id);
        onlyIntInput(age);
        onlyIntInput(height);
        onlyIntInput(id);
        weight.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("([0-9]*[.])?[0-9]*")) {
                weight.setText(newValue.replaceAll("[^0-9.]+", ""));
            }
        });

        year.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                year.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (newValue.length() == 5) {
                year.setText(oldValue);
            }
            olympicGame.setText(year.getText() + " " + (season.getValue() == null ? "" : season.getValue()));
        });

        season.valueProperty().addListener((observable, oldValue, newValue) -> olympicGame.setText(year.getText() + " " + season.getValue()));

        noc.textProperty().addListener((observable, oldValue, newValue) -> {
            noc.setText(newValue.toUpperCase());
            if (!newValue.toUpperCase().matches("[A-Z]+")) {
                noc.setText(newValue.replaceAll("[^A-Z]+", ""));
            }
            if (newValue.length() == 4) {
                noc.setText(oldValue.toUpperCase());
            }
        });

        saveButton.setOnAction(event -> submitForm());
        cancelButton.setOnAction(event -> cancelButton.getScene().getWindow().hide());
    }

    private void onlyIntInput(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private void submitForm() {
        saveButton.setDisable(true);
        if (checkValidity(isNewAthlete)) {
            mainController.saveLineToDatabase(mapReference.getAthleteDB().getAthleteMap().isEmpty() ? 1 : Integer.parseInt(id.getText()), name.getText(), sex.getSelectionModel().getSelectedItem(), Integer.parseInt(age.getText().equals("") ? "-1" : age.getText()), Integer.parseInt(height.getText().equals("") ? "-1" : height.getText()), Float.parseFloat(weight.getText().equals("") ? "-1.0" : weight.getText()), team.getText(), noc.getText(), olympicGame.getText(), Integer.parseInt(year.getText()), season.getValue(), city.getText(), discipline.getText(), event.getText(), medal.getSelectionModel().getSelectedItem());
            saveButton.getScene().getWindow().hide();
            mainController.refreshListViews();
            showConfirmation(id.getText());
        } else {
            saveButton.setDisable(false);
        }
    }

    private void showConfirmation(String id) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");

        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(Main.class.getResourceAsStream("icon.png")));

        alert.setHeaderText(null);
        alert.setContentText("Changes saved for id " + id + ".");

        alert.showAndWait();
    }

    private boolean checkValidity(Boolean isNewAthlete) {
        final Pattern stringPattern = Pattern.compile("\\S.*");
        final Pattern nocPattern = Pattern.compile("[A-Z]+");
        final Pattern integerPattern = Pattern.compile("[0-9]+");
        final Pattern floatPattern = Pattern.compile("([0-9]*[.])?[0-9]+");
        int invalidFields = 0;
        boolean[] validity = new boolean[11];
        TextField[] textFields = {id, name, age, height, weight, team, noc, year, city, discipline, event};

        if (isNewAthlete) {
            validity[0] = true;
            validity[1] = stringPattern.matcher(name.getText()).matches();
            if (sex.getSelectionModel().getSelectedIndex() == -1) {
                invalidFields++;
                markInvalid(sex);
            } else {
                markValid(sex);
            }
            validity[2] = integerPattern.matcher(age.getText()).matches();
            validity[3] = integerPattern.matcher(height.getText()).matches();
            validity[4] = floatPattern.matcher(weight.getText()).matches();
        } else {
            validity[0] = integerPattern.matcher(id.getText()).matches() && !(name.getText().equals(""));
            markValid(sex);
            validity[1] = true;
            validity[2] = true;
            validity[3] = true;
            validity[4] = true;
        }
        validity[5] = stringPattern.matcher(team.getText()).matches();
        validity[6] = nocPattern.matcher(noc.getText()).matches() && (noc.getText().length() == 3);
        validity[7] = integerPattern.matcher(year.getText()).matches();
        if (season.getSelectionModel().getSelectedIndex() == -1) {
            invalidFields++;
            markInvalid(season);
        } else {
            markValid(season);
        }
        validity[8] = stringPattern.matcher(city.getText()).matches();
        validity[9] = stringPattern.matcher(discipline.getText()).matches();
        validity[10] = stringPattern.matcher(event.getText()).matches();

        if (medal.getSelectionModel().getSelectedIndex() == -1) {
            invalidFields++;
            markInvalid(medal);
        } else {
            markValid(medal);
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
            sex.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.equals("")) {
                    checkValidity(isNewAthlete);
                }
            });
            medal.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.equals("")) {
                    checkValidity(isNewAthlete);
                }
            });
            season.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.equals("")) {
                    checkValidity(isNewAthlete);
                }
            });
            validationHandler = true;
        }
    }

    private void initDropdowns() {
        sex.getItems().addAll("M", "F");
        medal.getItems().addAll("Gold", "Silver", "Bronze", "NA");
        season.getItems().addAll("Summer", "Winter");
    }

    private void adjustFormMode(Boolean isNewAthlete) {
        if (isNewAthlete) {
            title.setText("New athlete");
            id.setText(String.valueOf(mapReference.getAthleteDB().getAthleteMap().isEmpty() ? 1 : mapReference.getAthleteDB().getAthleteMap().lastKey() + 1));

            id.setDisable(true);
        } else {
            id.textProperty().addListener((observable, oldValue, newValue) -> {
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
            title.setText("New participation");
            name.setDisable(true);
            sex.setDisable(true);
            age.setDisable(true);
            height.setDisable(true);
            weight.setDisable(true);
        }
    }

    private void fillAthleteData(int id) {
        Athlete selectedAthlete = mapReference.getAthleteDB().getAthleteById(id);
        if (selectedAthlete != null) {
            name.setText(selectedAthlete.getName());
            sex.getSelectionModel().select(selectedAthlete.getSex());
        } else {
            isValid = false;
            name.setText("");
            sex.getSelectionModel().select("");
        }
        age.setText("");
        height.setText("");
        weight.setText("");
    }

    void setIsNewAthlete(Boolean isNewAthlete) {
        this.isNewAthlete = isNewAthlete;
    }

    void setListReference(MapReference mapReference) {
        this.mapReference = mapReference;
    }
}
