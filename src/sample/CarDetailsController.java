package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

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
    public Button btnReservation;
    public Vehicle vehicle;
    public User user;
    public CarDetailsController(Vehicle vehicle, User user) {
        this.vehicle=vehicle;
        this.user=user;
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
        if (vehicle.getAvailability().equals("NE")) btnReservation.setDisable(true);
    }
    public void reservationAction(ActionEvent actionEvent) {

            Stage currentStage = (Stage) lblName.getScene().getWindow();
            currentStage.close();
            Stage stage = new Stage();
            Parent root = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/reservation.fxml"));
                ReservationController reservationController = new ReservationController(vehicle, user);
                loader.setController(reservationController);
                root = loader.load();
                stage.setTitle("Rezerviši");
                stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                stage.setResizable(true);
                stage.show();

            /*stage.setOnHiding( event -> {
                Vehicle newVehicle = addCarController.getVehicle();
                if (newVehicle != null) {
                    rentACarDAO.editVehicle(newVehicle);
                    listVehicles.setAll(rentACarDAO.getVehicles());
                }
            } );*/
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    public void backAction(ActionEvent actionEvent) {
        Stage stage= (Stage) lblBrand.getScene().getWindow();
        stage.close();
    }
}
