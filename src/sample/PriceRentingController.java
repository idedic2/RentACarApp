package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static java.time.temporal.ChronoUnit.DAYS;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class PriceRentingController {
    public TextField fldPrice;
    public Label lblPrice;
    public DatePicker datePickup;
    public DatePicker dateReturn;
    private User user;
    private Vehicle vehicle;

    public PriceRentingController(Vehicle vehicle, User user) {
        this.user=user;
        this.vehicle=vehicle;
    }

    public void reservationAction(ActionEvent actionEvent) {
        Stage currentStage = (Stage) fldPrice.getScene().getWindow();
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
        Stage stage= (Stage) fldPrice.getScene().getWindow();
        stage.close();
    }
    private void showAlert(String title, String headerText, Alert.AlertType type) {
        Alert error = new Alert(type);
        error.setTitle(title);
        error.setHeaderText(headerText);
        error.show();
    }
    private boolean isReturnAfterPickup(){
        if(datePickup.getValue().isAfter(dateReturn.getValue()))
            return false;
        return true;
    }
    private Double priceForRenting(){
        long daysBetween = DAYS.between(datePickup.getValue(), dateReturn.getValue());
        return daysBetween*vehicle.getPricePerDay();
    }
    public void changeDate(ActionEvent actionEvent) {
        if (datePickup.getValue() == null) {
            datePickup.getStyleClass().removeAll("ispravnoPolje");
            datePickup.getStyleClass().add("neispravnoPolje");
            dateReturn.getStyleClass().removeAll("neispravnoPolje");
            dateReturn.getStyleClass().add("ispravnoPolje");
            //showAlert("Upozorenje", "Odaberite datum rentanja vozila", Alert.AlertType.WARNING);
            //return;
        }
        else if(dateReturn.getValue()==null){
            datePickup.getStyleClass().removeAll("neispravnoPolje");
            datePickup.getStyleClass().add("ispravnoPolje");
            dateReturn.getStyleClass().removeAll("ispravnoPolje");
            dateReturn.getStyleClass().add("neispravnoPolje");
            //showAlert("Upozorenje", "Odaberite datum vraćanja vozila", Alert.AlertType.WARNING);
            //return;
        }
        else{
            if(datePickup.getValue().isEqual(dateReturn.getValue())){
                datePickup.getStyleClass().removeAll("ispravnoPolje");
                datePickup.getStyleClass().add("neispravnoPolje");
                dateReturn.getStyleClass().removeAll("ispravnoPolje");
                dateReturn.getStyleClass().add("neispravnoPolje");
                showAlert("Greška", "Minimum rentanja je 24h", Alert.AlertType.ERROR);
                return;
            }
            if (isReturnAfterPickup()) {
                datePickup.getStyleClass().removeAll("neispravnoPolje");
                datePickup.getStyleClass().add("ispravnoPolje");
                dateReturn.getStyleClass().removeAll("neispravnoPolje");
                dateReturn.getStyleClass().add("ispravnoPolje");
                fldPrice.setVisible(true);
                fldPrice.setText(priceForRenting() + " KM");

            } else {
                datePickup.getStyleClass().removeAll("ispravnoPolje");
                datePickup.getStyleClass().add("neispravnoPolje");
                dateReturn.getStyleClass().removeAll("ispravnoPolje");
                dateReturn.getStyleClass().add("neispravnoPolje");
                showAlert("Greška", "Datum povratka mora biti nakon datuma rentanja", Alert.AlertType.ERROR);

            }

        }
    }
}
