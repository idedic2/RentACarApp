package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class ClientPageController {
    public TableView tableViewCars;
    public TableColumn colName;
    public TableColumn colNmbSeats;
    public TableColumn colNmbDoors;
    public TableColumn colEngine;
    public TableColumn colTransmission;
    public TableColumn colPrice;
    public TableColumn colId;
    public ChoiceBox<String>choiceType;
    private RentACarDAO rentACarDAO;
    private ObservableList<Vehicle>listVehicles;
    private String username;
    public ClientPageController(String text){
        rentACarDAO=RentACarDAO.getInstance();
        listVehicles= FXCollections.observableArrayList(rentACarDAO.getVehiclesPerType("Putnicki automobil"));
        username=text;
    }
    @FXML
    public void initialize() {
        tableViewCars.setItems(listVehicles);
        choiceType.setValue("Putnicki automobil");
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory("pricePerDay"));
        colNmbSeats.setCellValueFactory(new PropertyValueFactory("seatsNumber"));
        colNmbDoors.setCellValueFactory(new PropertyValueFactory("doorsNumber"));
        colEngine.setCellValueFactory(new PropertyValueFactory("engine"));
        colTransmission.setCellValueFactory(new PropertyValueFactory("transmission"));
        colId.setVisible(false);
    }
    private void showAlert(String title, String headerText, Alert.AlertType type) {
        Alert error = new Alert(type);
        error.setTitle(title);
        error.setHeaderText(headerText);
        error.show();
    }
    public void reservationClientAction(ActionEvent actionEvent) {
        if(tableViewCars.getSelectionModel().getSelectedItem()==null){
            showAlert("Upozorenje", "Odaberite vozilo koje želite rezervisati", Alert.AlertType.CONFIRMATION);
            return;
        }
        TablePosition pos = (TablePosition) tableViewCars.getSelectionModel().getSelectedCells().get(0);
        int index = pos.getRow();
        String selected = tableViewCars.getItems().get(index).toString();
        //selected = selected.substring(1, selected.indexOf(","));
        String[] parts = selected.split(",");
        //parts[0]
        Vehicle vehicle=rentACarDAO.getVehiclePerId(Integer.parseInt(parts[0]));
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/reservation.fxml"));
            User user=rentACarDAO.getUserPerUsername(username);
            if(user==null){
                showAlert("Greška", "Nije pronađen user", Alert.AlertType.ERROR);
                return;
            }
            ReservationController reservationController = new ReservationController(vehicle,user);
            loader.setController(reservationController);
            root = loader.load();
            stage.setTitle("Rezerviši vozilo");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();
            stage.setOnHiding( event -> {
              //  Vehicle newVehicle = addCarController.getVehicle();
                //if (newVehicle != null) {
                  //  rentACarDAO.editVehicle(newVehicle);
                    //listVehicles.setAll(rentACarDAO.getVehicles());
                //}
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void detailsCarAction(ActionEvent actionEvent) {
        if(tableViewCars.getSelectionModel().getSelectedItem()==null){
            showAlert("Upozorenje", "Odaberite vozilo", Alert.AlertType.CONFIRMATION);
            return;
        }
        TablePosition pos = (TablePosition) tableViewCars.getSelectionModel().getSelectedCells().get(0);
        int index = pos.getRow();
        String selected = tableViewCars.getItems().get(index).toString();
        //selected = selected.substring(1, selected.indexOf(","));
        String[] parts = selected.split(",");
        //parts[0]
        Vehicle vehicle=rentACarDAO.getVehiclePerId(Integer.parseInt(parts[0]));
        Stage stage = new Stage();
        Parent root = null;
        try {
            User user=rentACarDAO.getUserPerUsername(username);
            if(user==null){
                showAlert("Greška", "Nije pronađen user", Alert.AlertType.ERROR);
                return;
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/carDetails.fxml"));
            CarDetailsController carDetailsController = new CarDetailsController(vehicle, user);
            loader.setController(carDetailsController);
            root = loader.load();
            stage.setTitle("Informacije o vozilu");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();
            //stage.setOnHiding( event -> {
                //  Vehicle newVehicle = addCarController.getVehicle();
                //if (newVehicle != null) {
                //  rentACarDAO.editVehicle(newVehicle);
                //listVehicles.setAll(rentACarDAO.getVehicles());
                //}
            //} );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void priceRentAction(ActionEvent actionEvent) {
    }

    public void changeType(ActionEvent actionEvent) {
        listVehicles.setAll(rentACarDAO.getVehiclesPerType(choiceType.getValue()));
    }
}
