package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.KeyEvent;

public class AddCarController {
    public TextField fldName;
    public TextField fldBrand;
    public TextField fldModel;
    public TextField fldColor;
    public CheckBox checkboxAvailability;
    public ChoiceBox choiceEngine;
    public ChoiceBox choiceTransmission;
    public ChoiceBox choiceType;
    public Spinner<Integer> spinnerNmbSeats;
    public Spinner<Integer> spinnerNmbDoors;
    public ComboBox<Double> comboFuelConsumption;
    public ComboBox<Integer> comboYear;
    public ComboBox<Double> comboPrice;
    public Vehicle vehicle;
    private boolean sveOk=true;
    public Vehicle getVehicle() {
        return vehicle;
    }


    public AddCarController(Vehicle vehicle) {
        this.vehicle=vehicle;
    }
    @FXML
    public void initialize() {
        if (vehicle != null) {
        } else {
            choiceEngine.getSelectionModel().selectFirst();
            choiceTransmission.getSelectionModel().selectFirst();
            choiceType.getSelectionModel().selectFirst();
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 5);
            spinnerNmbDoors.setValueFactory(valueFactory);
            SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 5);
            spinnerNmbSeats.setValueFactory(valueFactory2);
        }
        fldName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (fldName.getText().trim().isEmpty()) {
                    fldName.getStyleClass().removeAll("ispravnoPolje");
                    fldName.getStyleClass().add("neispravnoPolje");
                    sveOk = false;
                } else {
                    fldName.getStyleClass().removeAll("neispravnoPolje");
                    fldName.getStyleClass().add("ispravnoPolje");
                    sveOk = true;
                }
            }
        });
        fldBrand.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (fldBrand.getText().trim().isEmpty()) {
                    fldBrand.getStyleClass().removeAll("ispravnoPolje");
                    fldBrand.getStyleClass().add("neispravnoPolje");
                    sveOk = false;
                } else {
                    fldBrand.getStyleClass().removeAll("neispravnoPolje");
                    fldBrand.getStyleClass().add("ispravnoPolje");
                    sveOk = true;
                }
            }
        });
        fldModel.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (fldModel.getText().trim().isEmpty()) {
                    fldModel.getStyleClass().removeAll("ispravnoPolje");
                    fldModel.getStyleClass().add("neispravnoPolje");
                    sveOk = false;
                } else {
                    fldModel.getStyleClass().removeAll("neispravnoPolje");
                    fldModel.getStyleClass().add("ispravnoPolje");
                    sveOk = true;
                }
            }
        });
        fldColor.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (!fldColor.getText().trim().isEmpty()) {
                    if(!allLetters(fldColor.getText())){
                        fldColor.getStyleClass().removeAll("ispravnoPolje");
                        fldColor.getStyleClass().add("neispravnoPolje");
                    }
                    else{
                        fldColor.getStyleClass().removeAll("neispravnoPolje");
                        fldColor.getStyleClass().add("ispravnoPolje");
                    }
                } else {
                    fldColor.getStyleClass().removeAll("ispravnoPolje");
                    fldColor.getStyleClass().add("neispravnoPolje");
                }
            }
        });


    }
    private boolean allLetters(String str){
        return  str.chars().allMatch(Character::isLetter);
    }

    public void addConfirmAction(ActionEvent actionEvent) {

    }
    public void CloseAddCarAction(ActionEvent actionEvent){
        Stage stage= (Stage) fldBrand.getScene().getWindow();
        stage.close();
    }

}
