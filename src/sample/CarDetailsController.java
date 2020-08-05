package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CarDetailsController {
    public Label lblNmbDoors;
    public Label lblBrand;
    public Label lblModel;
    public Label lblType;
    public Label lblYear;
    public Label lblTransmission;
    public Label lblFuelConsumption;
    public Label lblNmbSeats;
    public Label lblEngine;
    public Label lblColor;
    public Label lblAvailability;
    public Label lblPrice;
    public Label lblName;
    public Vehicle vehicle;

    public CarDetailsController(Vehicle vehicle) {
        this.vehicle=vehicle;
    }
    private void showAlert(String title, String headerText, Alert.AlertType type) {
        Alert error = new Alert(type);
        error.setTitle(title);
        error.setHeaderText(headerText);
        error.show();
    }
    @FXML
    public void initialize() {
        if(vehicle==null){
            showAlert("Greška", "Vozilo nije odabrano", Alert.AlertType.ERROR);
            return;
        }
        else{
            lblName.setText(vehicle.getName());
            lblBrand.setText(vehicle.getBrand());
            lblModel.setText(vehicle.getModel());
            lblColor.setText(vehicle.getColor());
            lblEngine.setText(vehicle.getEngine());
            lblTransmission.setText(vehicle.getTransmission());
            lblNmbDoors.setText(Integer.toString(vehicle.getDoorsNumber()));
            lblNmbSeats.setText(Integer.toString(vehicle.getSeatsNumber()));
            lblType.setText(vehicle.getType());
            lblPrice.setText(Double.toString(vehicle.getPricePerDay()));
            lblAvailability.setText(vehicle.getAvailability());
            lblFuelConsumption.setText(Double.toString(vehicle.getFuelConsumption()));
            lblYear.setText(Integer.toString(vehicle.getYear()));
        }
    }
    public void reservationAction(ActionEvent actionEvent) {
    }

    public void backAction(ActionEvent actionEvent) {
        Stage stage= (Stage) lblBrand.getScene().getWindow();
        stage.close();
    }
}
