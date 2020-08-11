package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.ChronoUnit.DAYS;

public class AddReservationController {

    public ListView<Vehicle> listVehicles;
    public ListView<Client> listClients;
    public DatePicker datePickup;
    public DatePicker dateReturn;
    public ChoiceBox<String> hourPickup;
    public ChoiceBox<String> minutePickup;
    public ChoiceBox<String> hourReturn;
    public ChoiceBox<String> minuteReturn;
    public TextField fldSearchVehicle;
    private boolean dateOk=false;
    private RentACarDAO rentACarDAO;
    private ObservableList<Vehicle>vehicles;
    private ObservableList<Client>clients;
    public AddReservationController(){
        rentACarDAO=RentACarDAO.getInstance();
        vehicles= FXCollections.observableArrayList(rentACarDAO.getVehiclesPerAvailability());
        clients=FXCollections.observableArrayList(rentACarDAO.getClients());
     }
     @FXML
     public void initialize(){
        listVehicles.setItems(vehicles);
        listClients.setItems(clients);

     }

    public void confirmAddReservationAction(ActionEvent actionEvent) {
        if(listVehicles.getSelectionModel().getSelectedItem()==null){
            showAlert("Greška", "Odaberite vozilo", Alert.AlertType.ERROR);
            return;
        }
        if(listClients.getSelectionModel().getSelectedItem()==null){
            showAlert("Greška", "Odaberite klijenta", Alert.AlertType.ERROR);
            return;
        }
        if(!dateOk){
            showAlert("Greška", "Odaberite ispravan datum rentanja/vraćanja vozila", Alert.AlertType.ERROR);
            return;
        }
        Vehicle vehicle=listVehicles.getSelectionModel().getSelectedItem();
        Client client=listClients.getSelectionModel().getSelectedItem();
        vehicle.setAvailability("NE");
        rentACarDAO.editVehicle(vehicle);
        Reservation reservation=new Reservation(0, vehicle, client, datePickup.getValue(), dateReturn.getValue(), hourPickup.getValue()+":"+minutePickup.getValue(), hourReturn.getValue()+":"+minuteReturn.getValue(), null);
        rentACarDAO.addReservation(reservation);
        Stage stage = (Stage) fldSearchVehicle.getScene().getWindow();
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
    public void changeData(ActionEvent actionEvent) {
        if (datePickup.getEditor().getText().isEmpty()) {
            dateOk=false;
            //lblTotalPrice.setVisible(false);
            //fldPrice.setVisible(false);
            datePickup.getStyleClass().removeAll("ispravnoPolje");
            datePickup.getStyleClass().add("neispravnoPolje");
            dateReturn.getStyleClass().removeAll("neispravnoPolje");
            dateReturn.getStyleClass().add("ispravnoPolje");
            //showAlert("Upozorenje", "Odaberite datum rentanja vozila", Alert.AlertType.WARNING);
            return;
        }

        if(dateReturn.getEditor().getText().isEmpty()){
            dateOk=false;
            //lblTotalPrice.setVisible(false);
            //fldPrice.setVisible(false);
            datePickup.getStyleClass().removeAll("neispravnoPolje");
            datePickup.getStyleClass().add("ispravnoPolje");
            dateReturn.getStyleClass().removeAll("ispravnoPolje");
            dateReturn.getStyleClass().add("neispravnoPolje");
            //showAlert("Upozorenje", "Odaberite datum vraćanja vozila", Alert.AlertType.WARNING);
            return;
        }
        if(datePickup.getValue().isBefore(LocalDate.now()) || dateReturn.getValue().isBefore(LocalDate.now())){
            dateOk=false;
            datePickup.getStyleClass().removeAll("ispravnoPolje");
            datePickup.getStyleClass().add("neispravnoPolje");
            dateReturn.getStyleClass().removeAll("ispravnoPolje");
            dateReturn.getStyleClass().add("neispravnoPolje");
            //lblTotalPrice.setVisible(false);
            //fldPrice.setVisible(false);
            showAlert("Greška", "Odabrani datum/datumi iz prošlosti", Alert.AlertType.ERROR);
            return;
        }
            if(datePickup.getValue().isEqual(dateReturn.getValue())){
                dateOk=false;
                datePickup.getStyleClass().removeAll("ispravnoPolje");
                datePickup.getStyleClass().add("neispravnoPolje");
                dateReturn.getStyleClass().removeAll("ispravnoPolje");
                dateReturn.getStyleClass().add("neispravnoPolje");
                //lblTotalPrice.setVisible(false);
                //fldPrice.setVisible(false);
                showAlert("Greška", "Minimum rentanja je 24h", Alert.AlertType.ERROR);
                return;
            }
            if (isReturnAfterPickup()) {
                dateOk = true;
                datePickup.getStyleClass().removeAll("neispravnoPolje");
                datePickup.getStyleClass().add("ispravnoPolje");
                dateReturn.getStyleClass().removeAll("neispravnoPolje");
                dateReturn.getStyleClass().add("ispravnoPolje");
                //lblTotalPrice.setVisible(true);
                //fldPrice.setVisible(true);
                //fldPrice.setText(priceForRenting() + " KM");
                if(DAYS.between(LocalDate.now(), datePickup.getValue())>14){
                    dateOk=false;
                    showAlert("Greška", "Najkasniji datum preuzimanja vozila je dvije sedmice od današnjeg dana", Alert.AlertType.ERROR);
                    datePickup.getStyleClass().removeAll("ispravnoPolje");
                    datePickup.getStyleClass().add("neispravnoPolje");
                    dateReturn.getStyleClass().removeAll("ispravnoPolje");
                    dateReturn.getStyleClass().add("neispravnoPolje");
                    return;
                }

            } else {
                dateOk=false;
                //lblTotalPrice.setVisible(false);
                //fldPrice.setVisible(false);
                datePickup.getStyleClass().removeAll("ispravnoPolje");
                datePickup.getStyleClass().add("neispravnoPolje");
                dateReturn.getStyleClass().removeAll("ispravnoPolje");
                dateReturn.getStyleClass().add("neispravnoPolje");
                showAlert("Greška", "Datum povratka mora biti nakon datuma rentanja", Alert.AlertType.ERROR);

            }
        }


    public void cancelAddReservationAction(ActionEvent actionEvent) {
        Stage stage= (Stage) hourPickup.getScene().getWindow();
        stage.close();
    }
}
