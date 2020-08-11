package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import sun.util.cldr.CLDRLocaleDataMetaInfo;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.Optional;

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
    public TableView<Client> tableViewClients;
    public TableColumn colIdClient;
    public TableColumn colFirstName;
    public TableColumn colLastName;
    public TableColumn colEmail;
    public TableColumn colUsername;
    public TableColumn colPassword;
    public TableColumn colAddress;
    public TableColumn colTelephone;
    public TableView<Reservation> tableViewReservations;
    public TableColumn colIdReservation;
    public TableColumn<Reservation, Integer> colIdVehicle;
    public TableColumn<Reservation, String> colNameVehicle;
    public TableColumn<Reservation, String> colFirstNameClient;
    public TableColumn<Reservation, String> colLastNameClient;
    public TableColumn<Reservation, String> colUsernameClient;
    public TableColumn colCardNumber;
    public TableColumn colPickupDate;
    public TableColumn colReturnDate;
    public TableColumn colPickupTime;
    public TableColumn colReturnTime;
    public Label lblWelcome;
    private RentACarDAO rentACarDAO;
    private ObservableList<Vehicle> listVehicles;
    private ObservableList<Client> listClients;
    private ObservableList<Reservation>listReservations;
    private String username;

    public AdminPageController(String username) {
        rentACarDAO = RentACarDAO.getInstance();
        listVehicles = FXCollections.observableArrayList(rentACarDAO.getVehicles());
        listClients=FXCollections.observableArrayList(rentACarDAO.getClients());
        listReservations=FXCollections.observableArrayList(rentACarDAO.getReservations());
        this.username=username;
    }
    @FXML
    public void initialize() {
        lblWelcome.setText(lblWelcome.getText()+" "+username);
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
        tableViewClients.setItems(listClients);
        colIdClient.setCellValueFactory(new PropertyValueFactory("id"));
        colFirstName.setCellValueFactory(new PropertyValueFactory("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory("lastName"));
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colUsername.setCellValueFactory(new PropertyValueFactory("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory("password"));
        colAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colTelephone.setCellValueFactory(new PropertyValueFactory("telephone"));
        tableViewReservations.setItems(listReservations);
        colIdReservation.setCellValueFactory(new PropertyValueFactory("id"));
        colIdVehicle.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getVehicle().getId()).asObject());
        colNameVehicle.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getVehicle().getName()));
        colFirstNameClient.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getClient().getFirstName()));
        colLastNameClient.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getClient().getLastName()));
        colUsernameClient.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getClient().getUsername()));
        colCardNumber.setCellValueFactory(new PropertyValueFactory("cardNumber"));
        colPickupDate.setCellValueFactory(new PropertyValueFactory("pickUpDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory("returnDate"));
        colPickupTime.setCellValueFactory(new PropertyValueFactory("pickupTime"));
        colReturnTime.setCellValueFactory(new PropertyValueFactory("returnTime"));
        addButtonToTable();
    }
    private void addButtonToTable() {
        TableColumn<Reservation, Void> colBtn = new TableColumn("Brisanje");

        Callback<TableColumn<Reservation, Void>, TableCell<Reservation, Void>> cellFactory = new Callback<TableColumn<Reservation, Void>, TableCell<Reservation, Void>>() {
            @Override
            public TableCell<Reservation, Void> call(final TableColumn<Reservation, Void> param) {
                final TableCell<Reservation, Void> cell = new TableCell<Reservation, Void>() {
                    private final Button btn = new Button("Obriši");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Reservation data = getTableView().getItems().get(getIndex());
                            //System.out.println("selectedData: " + data);
                            //System.out.println(data.getReturnTime());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Potvrda brisanja");
                            alert.setHeaderText("Brisanje rezervacije");
                            alert.setContentText("Da li ste sigurni da želite obrisati rezervaciju?");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK){
                                //System.out.println(data.getReturnTime());
                                data.getVehicle().setAvailability("DA");
                                rentACarDAO.deleteReservation(data);
                                listVehicles.setAll(rentACarDAO.getVehicles());
                                listReservations.setAll(rentACarDAO.getReservations());
                                AddReservationController addReservationController=new AddReservationController();
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);
        tableViewReservations.getColumns().add(colBtn);

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
            showAlert("Upozorenje", "Odaberite vozilo koje želite izmijeniti", Alert.AlertType.WARNING);
            return;
        }
        if(rentACarDAO.isVehicleReserved(vehicle)){
            showAlert("Greška", "Odabrano vozilo je rezervisano i ne može se trenutno mijenjati", Alert.AlertType.ERROR);
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
                    //ako je vozilo rezervisano trenutno ne smije se mijenjati
                        rentACarDAO.editVehicle(newVehicle);
                        listVehicles.setAll(rentACarDAO.getVehicles());

                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteVehicleAction(ActionEvent actionEvent) {
        Vehicle vehicle = tableViewVehicles.getSelectionModel().getSelectedItem();
        if (vehicle == null) {
            showAlert("Upozorenje", "Odaberite vozilo koje želite obrisati", Alert.AlertType.CONFIRMATION);
            return;
        }
        if(rentACarDAO.isVehicleReserved(vehicle)){
            showAlert("Greška", "Odabrano vozilo je rezervisano i ne može se trenutno obrisati", Alert.AlertType.ERROR);
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrda brisanja");
        alert.setHeaderText("Brisanje grada "+vehicle.getName());
        alert.setContentText("Da li ste sigurni da želite obrisati grad " +vehicle.getName()+"?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
                rentACarDAO.deleteVehicle(vehicle);
                listVehicles.setAll(rentACarDAO.getVehicles());
        }
    }

    public void addClientAction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addClient.fxml"));
            AddClientController addClientController = new AddClientController(null, true);
            loader.setController(addClientController);
            root = loader.load();
            stage.setTitle("Dodavanje novog klijenta");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();
            stage.setOnHiding( event -> {
                Client client = addClientController.getClient();
                if (client != null) {
                    rentACarDAO.addClient(client);
                    listClients.setAll(rentACarDAO.getClients());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editClientAction(ActionEvent actionEvent) {
        Client client = tableViewClients.getSelectionModel().getSelectedItem();
        if (client == null) {
            showAlert("Upozorenje", "Odaberite klijenta kojeg želite izmijeniti", Alert.AlertType.CONFIRMATION);
            return;
        }
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addClient.fxml"));
            AddClientController addClientController = new AddClientController(client, false);
            loader.setController(addClientController);
            root = loader.load();
            stage.setTitle("Izmijeni klijenta");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();

            stage.setOnHiding( event -> {
                Client newClient = addClientController.getClient();
                if (newClient != null) {
                    rentACarDAO.editClient(newClient);
                    listClients.setAll(rentACarDAO.getClients());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteClientAction(ActionEvent actionEvent) {
        Client client = tableViewClients.getSelectionModel().getSelectedItem();
        if (client == null) {
            showAlert("Upozorenje", "Odaberite klijenta kojeg želite obrisati", Alert.AlertType.CONFIRMATION);
            return;
        }
        if(rentACarDAO.clientInReservations(client)){
            showAlert("Greška", "Odabrani klijent je iznajmio vozilo i trenutno se ne može obrisati", Alert.AlertType.ERROR);
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrda brisanja");
        alert.setHeaderText("Brisanje klijenta "+client.getFirstName()+" "+client.getLastName());
        alert.setContentText("Da li ste sigurni da želite obrisati klijenta " +client.getFirstName()+" "+client.getLastName()+"?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
                rentACarDAO.deleteClient(client);
                listClients.setAll(rentACarDAO.getClients());
            }
    }
    public void logOutAction(ActionEvent actionEvent){
        Parent root = null;
        try {
            Stage stage = (Stage) lblWelcome.getScene().getWindow();
            stage.close();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/start.fxml"));
            root = loader.load();
            primaryStage.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE));
            primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addReservationAction(ActionEvent actionEvent){
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addReservation.fxml"));
            AddReservationController addReservationController= new AddReservationController();
            loader.setController(addReservationController);
            root = loader.load();
            stage.getIcons().add(new Image("/images/admin.png"));
            stage.setTitle("Dodavanje nove rezervacije");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();
            stage.setOnHiding( event -> {
                /*Client client = addClientController.getClient();
                if (client != null) {
                    rentACarDAO.addClient(client);
                    listClients.setAll(rentACarDAO.getClients());
                }*/
                listReservations.setAll(rentACarDAO.getReservations());
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
