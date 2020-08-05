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
    public ChoiceBox<String> choiceEngine;
    public ChoiceBox<String> choiceTransmission;
    public ChoiceBox<String> choiceType;
    public Spinner<Integer> spinnerNmbSeats;
    public Spinner<Integer> spinnerNmbDoors;
    public ComboBox<String> comboFuelConsumption;
    public ComboBox<String> comboYear;
    public ComboBox<String> comboPrice;
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
           fldName.setText(vehicle.getName());
           fldBrand.setText(vehicle.getBrand());
           fldModel.setText(vehicle.getModel());
           fldColor.setText(vehicle.getColor());
           choiceEngine.getSelectionModel().select(vehicle.getEngine());
           choiceTransmission.getSelectionModel().select(vehicle.getTransmission());
           choiceType.getSelectionModel().select(vehicle.getType());
           spinnerNmbSeats.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, vehicle.getSeatsNumber()));
           spinnerNmbDoors.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, vehicle.getDoorsNumber()));
           comboFuelConsumption.getSelectionModel().select(Double.toString(vehicle.getFuelConsumption()));
           comboYear.getSelectionModel().select(Double.toString(vehicle.getYear()));
           comboPrice.getSelectionModel().select(Double.toString(vehicle.getPricePerDay()));

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

    private void showAlert(String title, String headerText, Alert.AlertType type) {
        Alert error = new Alert(type);
        error.setTitle(title);
        error.setHeaderText(headerText);
        error.show();
    }
    public void addConfirmAction(ActionEvent actionEvent) {
        boolean sveOk=true;
        if(fldName.getText().trim().isEmpty()){
            sveOk=false;
            showAlert("Greška", "Unesite naziv vozila", Alert.AlertType.ERROR);
            return;
        }
        if(fldBrand.getText().trim().isEmpty()){
            sveOk=false;
            showAlert("Greška", "Unesite brend vozila", Alert.AlertType.ERROR);
            return;
        }
        if(fldModel.getText().trim().isEmpty()){
            sveOk=false;
            showAlert("Greška", "Unesite model vozila", Alert.AlertType.ERROR);
            return;
        }
        if(fldColor.getText().trim().isEmpty()){
            sveOk=false;
            showAlert("Greška", "Unesite boju vozila", Alert.AlertType.ERROR);
            return;
        }
        else{
            if(!allLetters(fldColor.getText())){
                sveOk=false;
                showAlert("Greška", "Boja mora sadržavati isključivo slova", Alert.AlertType.ERROR);
                return;
            }
        }
        if (!sveOk) return;

        if (vehicle == null) vehicle = new Vehicle();
        vehicle.setName(fldName.getText());
        vehicle.setBrand(fldBrand.getText());
        vehicle.setModel(fldModel.getText());
        vehicle.setColor(fldColor.getText());
        vehicle.setAvailability("DA");
        vehicle.setEngine(choiceEngine.getValue());
        vehicle.setTransmission(choiceTransmission.getValue());
        vehicle.setType(choiceType.getValue());
        vehicle.setDoorsNumber(spinnerNmbDoors.getValue());
        vehicle.setSeatsNumber(spinnerNmbSeats.getValue());
        vehicle.setYear(Integer.parseInt(comboYear.getValue()));
        vehicle.setFuelConsumption(Double.parseDouble(comboFuelConsumption.getValue()));
        vehicle.setPricePerDay(Double.parseDouble(comboPrice.getValue()));
        Stage stage = (Stage) fldName.getScene().getWindow();
        stage.close();
    }
    public void CloseAddCarAction(ActionEvent actionEvent){
        vehicle=null;
        Stage stage= (Stage) fldBrand.getScene().getWindow();
        stage.close();
    }


}
