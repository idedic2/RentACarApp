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
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    public Label lblWelcome;
    public ChoiceBox<String>choiceType;
    private Client client;
    private RentACarDAO rentACarDAO;
    private ObservableList<Vehicle>listVehicles;
    //private ObservableList<Vehicle>chosedVehicles;
    private String username;

    public ClientPageController(String username){
        rentACarDAO=RentACarDAO.getInstance();
        //listVehicles= FXCollections.observableArrayList(rentACarDAO.getVehiclesPerType("Putnicki automobil"));
        listVehicles=FXCollections.observableArrayList(rentACarDAO.getVehicles());
        //chosedVehicles= FXCollections.observableArrayList(rentACarDAO.getVehiclesPerType("Putnicki automobil"));
        this.username=username;
        client=rentACarDAO.getClientPerUsername(username);
    }
    @FXML
    public void initialize() {
        lblWelcome.setText(lblWelcome.getText()+" "+client.getUsername());
        tableViewCars.setItems(listVehicles);
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
                            if(vehicle.getAvailability().equals("NE")){
                                showAlert("Upozorenje", "Odabrano vozilo nije dostupno", Alert.AlertType.WARNING);
                                return;
                            }
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
                                ReservationController reservationController = new ReservationController(vehicle,client, null);
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
    public void editMyReservationsAction(ActionEvent actionEvent){
        ArrayList<Reservation> clientReservations=rentACarDAO.getClientReservations(client);
        if(clientReservations.size()==0){
            showAlert("Greška", "Nemate rezervaciju", Alert.AlertType.ERROR);
            return;
        }
        List<String> choices = new ArrayList<>();
        for(Reservation r:clientReservations)
            choices.add("#"+r.getId()+" "+r.getVehicle().getName()+" ( iznajmljivanje: "+r.getPickUpDate().toString()+" vraćanje: "+r.getReturnDate().toString()+" )");
        ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
        dialog.setTitle("Rezervacije");
        dialog.setHeaderText("Odaberite rezervaciju");
        dialog.setContentText("Vaše rezervacije:");
// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            //System.out.println("Your choice: " + result.get());
            String choosen = result.get();
            String tmp = "";
            //broj poslije # treba jer je on id rezervacije
            for (int i = 1; i < choosen.length(); i++) {
                if (choosen.charAt(i) == ' ') break;
                tmp += choosen.charAt(i);
            }
            int idReservation=Integer.parseInt(tmp);
            Reservation reservation=null;
            for(Reservation r:clientReservations){
                if(idReservation==r.getId())
                    reservation=r;
            }
            Vehicle vehicle=rentACarDAO.getVehiclePerId(reservation.getVehicle().getId());
            Stage stage = new Stage();
            Parent root = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/reservation.fxml"));
                ReservationController reservationController = new ReservationController(vehicle, client, reservation);
                loader.setController(reservationController);
                root = loader.load();
                stage.setTitle("Rezerviši vozilo");
                stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                stage.setResizable(true);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
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
   public void editProfilAction(ActionEvent actionEvent){
       Stage stage = new Stage();
       Parent root = null;
       try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/registration.fxml"));
           RegistrationController registrationController = new RegistrationController(client, "");
           loader.setController(registrationController);
           root = loader.load();
           stage.setTitle("Vaš profil");
           stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
           stage.setResizable(true);
           stage.show();
           stage.setOnHiding( event -> {
               Client newClient = registrationController.getClient();
           if (newClient != null) {
               lblWelcome.setText("Dobrodošli "+client.getUsername());
           }
           } );
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
    public void changeType(ActionEvent actionEvent) {
       if(choiceType.getValue().equals("Sva vozila")) listVehicles.setAll(rentACarDAO.getVehicles());
        else
            listVehicles.setAll(rentACarDAO.getVehiclesPerType(choiceType.getValue()));
    }

    public void logOutAction(ActionEvent actionEvent){
        Parent root = null;
        try {
            Stage stage = (Stage) choiceType.getScene().getWindow();
            stage.close();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/start.fxml"));
            StartController startController = new StartController();
            loader.setController(startController);
            root = loader.load();
            primaryStage.setTitle("Dobrodošli");
            primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteProfilAction(ActionEvent actionEvent){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Brisanje korisničkog računa");
        alert.setHeaderText("Jeste li sigurni da želite obrisati korisnički profil");
        alert.setContentText("Umjesto brisanja, možete se odjaviti");

        ButtonType buttonTypeOne = new ButtonType("Obriši");
        ButtonType buttonTypeTwo = new ButtonType("Odjavi se");
        ButtonType buttonTypeCancel = new ButtonType("Zatvori", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
            // ... user chose obrisi profil
            if(rentACarDAO.clientInReservations(client)){
                showAlert("Greška", "Trenutno imate jednu ili više rezervacija i ne možete obrisati profil.", Alert.AlertType.ERROR);
                return;
            }
                rentACarDAO.deleteClient(client);
            Parent root = null;
            try {
                Stage stage = (Stage) choiceType.getScene().getWindow();
                stage.close();
                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/start.fxml"));
                StartController startController = new StartController();
                loader.setController(startController);
                root = loader.load();
                primaryStage.setTitle("Dobrodošli");
                primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                primaryStage.setResizable(false);
                primaryStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (result.get() == buttonTypeTwo) {
            // ... user chose odjavi se
            Parent root = null;
            try {
                Stage stage = (Stage) choiceType.getScene().getWindow();
                stage.close();
                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/start.fxml"));
                StartController startController = new StartController();
                loader.setController(startController);
                root = loader.load();
                primaryStage.setTitle("Dobrodošli");
                primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                primaryStage.setResizable(false);
                primaryStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else {
            // ... user chose CANCEL or closed the dialog

        }
    }
}
