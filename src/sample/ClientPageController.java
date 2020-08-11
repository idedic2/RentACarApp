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
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

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
    private ObservableList<Vehicle>chosedVehicles;
    private String username;

    public ClientPageController(String username){
        rentACarDAO=RentACarDAO.getInstance();
        //listVehicles= FXCollections.observableArrayList(rentACarDAO.getVehiclesPerType("Putnicki automobil"));
        listVehicles=FXCollections.observableArrayList(rentACarDAO.getVehicles());
        chosedVehicles= FXCollections.observableArrayList(rentACarDAO.getVehiclesPerType("Putnicki automobil"));
        this.username=username;
    }
    @FXML
    public void initialize() {
        tableViewCars.setItems(chosedVehicles);
        //choiceType.setValue("Putnicki automobil");
        //colId.setCellValueFactory(new PropertyValueFactory("id"));
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory("pricePerDay"));
        colNmbSeats.setCellValueFactory(new PropertyValueFactory("seatsNumber"));
        colNmbDoors.setCellValueFactory(new PropertyValueFactory("doorsNumber"));
        colEngine.setCellValueFactory(new PropertyValueFactory("engine"));
        colTransmission.setCellValueFactory(new PropertyValueFactory("transmission"));
        //colId.setVisible(false);
        detailsButton();
        priceButton();
        reservationButton();
    }
    private void detailsButton() {
        TableColumn<Vehicle, Void> colBtn = new TableColumn("Detalji");
        Callback<TableColumn<Vehicle, Void>, TableCell<Vehicle, Void>> cellFactory = new Callback<TableColumn<Vehicle, Void>, TableCell<Vehicle, Void>>() {
            @Override
            public TableCell<Vehicle, Void> call(final TableColumn<Vehicle, Void> param) {
                final TableCell<Vehicle, Void> cell = new TableCell<Vehicle, Void>() {
                    private final Button btn = new Button("Detalji");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Vehicle vehicle = getTableView().getItems().get(getIndex());
                            //System.out.println("selectedData: " + data);
                            //System.out.println(data.getReturnTime());
                            Stage stage = new Stage();
                            Parent root = null;
                            try {
                                Client client=rentACarDAO.getClientPerUsername(username);
                                if(client==null){
                                    showAlert("Greška", "Nije pronađen user", Alert.AlertType.ERROR);
                                    return;
                                }
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/carDetails.fxml"));
                                CarDetailsController carDetailsController = new CarDetailsController(vehicle, client);
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
        tableViewCars.getColumns().add(colBtn);
    }
    private void priceButton() {
        TableColumn<Vehicle, Void> colBtn = new TableColumn("Cijena");
        Callback<TableColumn<Vehicle, Void>, TableCell<Vehicle, Void>> cellFactory = new Callback<TableColumn<Vehicle, Void>, TableCell<Vehicle, Void>>() {
            @Override
            public TableCell<Vehicle, Void> call(final TableColumn<Vehicle, Void> param) {
                final TableCell<Vehicle, Void> cell = new TableCell<Vehicle, Void>() {
                    private final Button btn = new Button("Cijena");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Vehicle vehicle = getTableView().getItems().get(getIndex());
                            //System.out.println("selectedData: " + data);
                            //System.out.println(data.getReturnTime());
                            Stage stage = new Stage();
                            Parent root = null;
                            try {
                                Client client=rentACarDAO.getClientPerUsername(username);
                                if(client==null){
                                    showAlert("Greška", "Nije pronađen user", Alert.AlertType.ERROR);
                                    return;
                                }
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/priceRenting.fxml"));
                                PriceRentingController priceRentingController = new PriceRentingController(vehicle, client);
                                loader.setController(priceRentingController);
                                root = loader.load();
                                stage.setTitle("Cijena iznajmljivanja vozila");
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
        tableViewCars.getColumns().add(colBtn);
    }
    private void reservationButton() {
        TableColumn<Vehicle, Void> colBtn = new TableColumn("Rezervacija");
        Callback<TableColumn<Vehicle, Void>, TableCell<Vehicle, Void>> cellFactory = new Callback<TableColumn<Vehicle, Void>, TableCell<Vehicle, Void>>() {
            @Override
            public TableCell<Vehicle, Void> call(final TableColumn<Vehicle, Void> param) {
                final TableCell<Vehicle, Void> cell = new TableCell<Vehicle, Void>() {
                    private final Button btn = new Button("Rezervacija");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Vehicle vehicle = getTableView().getItems().get(getIndex());
                            //System.out.println("selectedData: " + data);
                            //System.out.println(data.getReturnTime());
                            Stage stage = new Stage();
                            Parent root = null;
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/reservation.fxml"));
                                Client client=rentACarDAO.getClientPerUsername(username);
                                if(client==null){
                                    showAlert("Greška", "Nije pronađen user", Alert.AlertType.ERROR);
                                    return;
                                }
                                ReservationController reservationController = new ReservationController(vehicle,client);
                                loader.setController(reservationController);
                                root = loader.load();
                                stage.setTitle("Rezerviši vozilo");
                                stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                                stage.setResizable(true);
                                stage.show();
                            } catch (IOException e) {
                                e.printStackTrace();
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
        tableViewCars.getColumns().add(colBtn);
    }
    private void showAlert(String title, String headerText, Alert.AlertType type) {
        Alert error = new Alert(type);
        error.setTitle(title);
        error.setHeaderText(headerText);
        error.show();
    }
    /*public void reservationClientAction(ActionEvent actionEvent) {
        if(tableViewCars.getSelectionModel().getSelectedItem()==null){
            showAlert("Upozorenje", "Odaberite vozilo koje želite rezervisati", Alert.AlertType.WARNING);
            return;
        }
        TablePosition pos = (TablePosition) tableViewCars.getSelectionModel().getSelectedCells().get(0);
        int index = pos.getRow();
        String selected = tableViewCars.getItems().get(index).toString();
        //selected = selected.substring(1, selected.indexOf(","));
        String[] parts = selected.split(",");
        //parts[0]
        System.out.println(Integer.parseInt(parts[0]));
        Vehicle vehicle=rentACarDAO.getVehiclePerId(Integer.parseInt(parts[0]));

        if(vehicle.getAvailability().equals("NE")){
            showAlert("Upozorenje", "Odabrano vozilo nije dostupno", Alert.AlertType.WARNING);
            return;
        }
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/reservation.fxml"));
            Client client=rentACarDAO.getClientPerUsername(username);
            if(client==null){
                showAlert("Greška", "Nije pronađen user", Alert.AlertType.ERROR);
                return;
            }
            ReservationController reservationController = new ReservationController(vehicle,client);
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
*/
    /*public void detailsCarAction(ActionEvent actionEvent) {
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
            Client client=rentACarDAO.getClientPerUsername(username);
            if(client==null){
                showAlert("Greška", "Nije pronađen user", Alert.AlertType.ERROR);
                return;
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/carDetails.fxml"));
            CarDetailsController carDetailsController = new CarDetailsController(vehicle, client);
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
*/
   /* public void priceRentAction(ActionEvent actionEvent) {
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
            Client client=rentACarDAO.getClientPerUsername(username);
            if(client==null){
                showAlert("Greška", "Nije pronađen user", Alert.AlertType.ERROR);
                return;
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/priceRenting.fxml"));
            PriceRentingController priceRentingController = new PriceRentingController(vehicle, client);
            loader.setController(priceRentingController);
            root = loader.load();
            stage.setTitle("Cijena iznajmljivanja vozila");
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
*/
    public void changeType(ActionEvent actionEvent) {
        chosedVehicles.setAll(rentACarDAO.getVehiclesPerType(choiceType.getValue()));
    }
}
