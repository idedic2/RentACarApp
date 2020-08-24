package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    public Client client;
    public ImageView imageVehicle;
    public Label lblImage;
    public CarDetailsController(Vehicle vehicle, Client client) {
        this.vehicle=vehicle;
        this.client=client;
    }
    private void showAlert(String title, String headerText, Alert.AlertType type) {
        Alert error = new Alert(type);
        error.setTitle(title);
        error.setHeaderText(headerText);
        error.show();
    }
    @FXML
    public void initialize() throws FileNotFoundException {
        if(vehicle==null){
            showAlert("Greška", "Vozilo nije odabrano", Alert.AlertType.ERROR);
            return;
        }
        else{
            lblImage.setText(lblImage.getText()+vehicle.getName());
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
            String path="File:"+vehicle.getImage();
            System.out.println(path);
            Image image = new Image(path);
            imageVehicle.setImage(image);
            //imageVehicle=new ImageView(image);
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
                ReservationController reservationController = new ReservationController(vehicle, client, null);
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
