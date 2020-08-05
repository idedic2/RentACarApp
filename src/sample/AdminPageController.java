package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class AdminPageController {
    public TableView<Vehicle> tableViewVehicles;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colBrand;
    public TableColumn colModel;
    public TableColumn colType;
    public TableColumn colYear;
    public TableColumn colTransmission;
    public TableColumn colFuelConsumption;
    public TableColumn colNmbSeats;
    public TableColumn colNmbDoors;
    public TableColumn colEngine;
    public TableColumn colColor;
    public TableColumn colPrice;
    public TableColumn colAvailability;
    private RentACarDAO rentACarDAO;
    private ObservableList<Vehicle> listVehicles;

    public AdminPageController() {
        rentACarDAO = RentACarDAO.getInstance();
        listVehicles = FXCollections.observableArrayList(rentACarDAO.getVehicles());
    }

    @FXML
    public void initialize() {
        tableViewVehicles.setItems(listVehicles);
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colBrand.setCellValueFactory(new PropertyValueFactory("brand"));
        colModel.setCellValueFactory(new PropertyValueFactory("model"));
        colType.setCellValueFactory(new PropertyValueFactory("type"));
        colYear.setCellValueFactory(new PropertyValueFactory("year"));
        colNmbSeats.setCellValueFactory(new PropertyValueFactory("seatsNumber"));
        colNmbDoors.setCellValueFactory(new PropertyValueFactory("doorsNumber"));
        colEngine.setCellValueFactory(new PropertyValueFactory("engine"));
        colTransmission.setCellValueFactory(new PropertyValueFactory("transmission"));
        colFuelConsumption.setCellValueFactory(new PropertyValueFactory("fuelConsumption"));
        colColor.setCellValueFactory(new PropertyValueFactory("color"));
        colPrice.setCellValueFactory(new PropertyValueFactory("pricePerDay"));
        colAvailability.setCellValueFactory(new PropertyValueFactory("availability"));
    }

    public void addVehicle(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addCar.fxml"));
            AddCarController addCarController = new AddCarController(null);
            loader.setController(addCarController);
            root = loader.load();
            stage.setTitle("Dodavanje novog vozila");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();

            stage.setOnHiding( event -> {
                Vehicle vehicle = addCarController.getVehicle();
                if (vehicle != null) {
                    rentACarDAO.addVehicle(vehicle);
                    listVehicles.setAll(rentACarDAO.getVehicles());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showAlert(String title, String headerText, Alert.AlertType type) {
        Alert error = new Alert(type);
        error.setTitle(title);
        error.setHeaderText(headerText);
        error.show();
    }
    public void editVehicleAction(ActionEvent actionEvent) {
        Vehicle vehicle = tableViewVehicles.getSelectionModel().getSelectedItem();
        if (vehicle == null) {
            showAlert("Upozorenje", "Odaberite vozilo koje želite izmijeniti", Alert.AlertType.CONFIRMATION);
            return;
        }

        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addCar.fxml"));
            AddCarController addCarController = new AddCarController(vehicle);
            loader.setController(addCarController);
            root = loader.load();
            stage.setTitle("Izmijeni vozilo");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();

            stage.setOnHiding( event -> {
                Vehicle newVehicle = addCarController.getVehicle();
                if (newVehicle != null) {
                    rentACarDAO.editVehicle(newVehicle);
                    listVehicles.setAll(rentACarDAO.getVehicles());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteVehicleAction(ActionEvent actionEvent) {
    }
}
