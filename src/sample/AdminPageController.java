package sample;

import com.sun.javaws.jnl.XMLFormat;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JRException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import sun.util.cldr.CLDRLocaleDataMetaInfo;

import javax.xml.crypto.Data;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    public Button btnAddVehicle;
    public Button btnEditVehicle;
    public Button btnDeleteVehicle;
    public Button btnVehicleReport;
    public Button btnAddReservation;
    public Button btnEditReservation;
    public Button btnDeleteReservation;
    public Button btnReportReservation;
    public Button btnAddClient;
    public Button btnEditClient;
    public Button btnDeleteClient;
    public Button btnReportClient;
    public Button btnWriteVehiclesXML;
    public Button btnWriteClientsXML;
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
        editVehicle();
        deleteVehicle();
        //Image addVehicleIcon=new Image("/images/addCar.png");
        //btnAddVehicle.setGraphic(new ImageView(addVehicleIcon));
        tableViewClients.setItems(listClients);
        colIdClient.setCellValueFactory(new PropertyValueFactory("id"));
        colFirstName.setCellValueFactory(new PropertyValueFactory("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory("lastName"));
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colUsername.setCellValueFactory(new PropertyValueFactory("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory("password"));
        colAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colTelephone.setCellValueFactory(new PropertyValueFactory("telephone"));
        editClient();
        deleteClient();
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
        //colPaying.setCellFactory(column -> new CheckBoxTableCell<>());
        paying();
        editReservation();
        deleteReservation();
    }

    public void paying(){
        TableColumn select = new TableColumn("Plaćanje karticom");
        select.setMinWidth(200);
        select.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Reservation, CheckBox>, ObservableValue<CheckBox>>() {

            @Override
            public ObservableValue<CheckBox> call(
                    TableColumn.CellDataFeatures<Reservation, CheckBox> arg0) {
                Reservation reservation = arg0.getValue();

                CheckBox checkBox = new CheckBox();

                checkBox.selectedProperty().setValue(reservation.isOnline());
                checkBox.setDisable(true);
                /*checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    public void changed(ObservableValue<? extends Boolean> ov,
                                        Boolean old_val, Boolean new_val) {

                        user.setSelected(new_val);

                    }
                });
*/
                return new SimpleObjectProperty<CheckBox>(checkBox);

            }

        });
        tableViewReservations.getColumns().addAll( select);
    }
    private void deleteReservation() {
        TableColumn<Reservation, Void> colBtn = new TableColumn("Brisanje");

        Callback<TableColumn<Reservation, Void>, TableCell<Reservation, Void>> cellFactory = new Callback<TableColumn<Reservation, Void>, TableCell<Reservation, Void>>() {
            @Override
            public TableCell<Reservation, Void> call(final TableColumn<Reservation, Void> param) {
                final TableCell<Reservation, Void> cell = new TableCell<Reservation, Void>() {
                    private final Button btn = new Button("Obriši");
                    {
                        //btn.getStyleClass().add("deleteButton");

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
                                AddReservationController addReservationController=new AddReservationController(null);
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
    private void editVehicle() {
        TableColumn<Vehicle, Void> colBtn = new TableColumn("Mijenjanje");
        Callback<TableColumn<Vehicle, Void>, TableCell<Vehicle, Void>> cellFactory = new Callback<TableColumn<Vehicle, Void>, TableCell<Vehicle, Void>>() {
            @Override
            public TableCell<Vehicle, Void> call(final TableColumn<Vehicle, Void> param) {
                final TableCell<Vehicle, Void> cell = new TableCell<Vehicle, Void>() {
                    private final Button btn = new Button("Izmijeni");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Vehicle vehicle = getTableView().getItems().get(getIndex());
                            //System.out.println("selectedData: " + data);
                            //System.out.println(data.getReturnTime());
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

                                stage.setOnHiding( event2 -> {
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
        tableViewVehicles.getColumns().add(colBtn);

    }
    private void deleteVehicle() {
        TableColumn<Vehicle, Void> colBtn = new TableColumn("Brisanje");
        Callback<TableColumn<Vehicle, Void>, TableCell<Vehicle, Void>> cellFactory = new Callback<TableColumn<Vehicle, Void>, TableCell<Vehicle, Void>>() {
            @Override
            public TableCell<Vehicle, Void> call(final TableColumn<Vehicle, Void> param) {
                final TableCell<Vehicle, Void> cell = new TableCell<Vehicle, Void>() {
                    private final Button btn = new Button("Obriši");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Vehicle vehicle = getTableView().getItems().get(getIndex());
                            //System.out.println("selectedData: " + data);
                            //System.out.println(data.getReturnTime());
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
        tableViewVehicles.getColumns().add(colBtn);

    }
    private void editClient() {
        TableColumn<Client, Void> colBtn = new TableColumn("Mijenjanje");
        Callback<TableColumn<Client, Void>, TableCell<Client, Void>> cellFactory = new Callback<TableColumn<Client, Void>, TableCell<Client, Void>>() {
            @Override
            public TableCell<Client, Void> call(final TableColumn<Client, Void> param) {
                final TableCell<Client, Void> cell = new TableCell<Client, Void>() {
                    private final Button btn = new Button("Izmijeni");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Client client = getTableView().getItems().get(getIndex());
                            //System.out.println("selectedData: " + data);
                            //System.out.println(data.getReturnTime());
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

                                stage.setOnHiding( event2 -> {
                                    Client newClient = addClientController.getClient();
                                    if (newClient != null) {
                                        rentACarDAO.editClient(newClient);
                                        listClients.setAll(rentACarDAO.getClients());
                                    }
                                } );
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
        tableViewClients.getColumns().add(colBtn);

    }
    private void deleteClient() {
        TableColumn<Client, Void> colBtn = new TableColumn("Brisanje");
        Callback<TableColumn<Client, Void>, TableCell<Client, Void>> cellFactory = new Callback<TableColumn<Client, Void>, TableCell<Client, Void>>() {
            @Override
            public TableCell<Client, Void> call(final TableColumn<Client, Void> param) {
                final TableCell<Client, Void> cell = new TableCell<Client, Void>() {
                    private final Button btn = new Button("Obriši");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Client client = getTableView().getItems().get(getIndex());
                            //System.out.println("selectedData: " + data);
                            //System.out.println(data.getReturnTime());
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
        tableViewClients.getColumns().add(colBtn);

    }
    private void showAlert(String title, String headerText, Alert.AlertType type) {
        Alert error = new Alert(type);
        error.setTitle(title);
        error.setHeaderText(headerText);
        error.show();
    }
    public void editVehicleAction(ActionEvent actionEvent) {
        if(listVehicles.isEmpty()){
            showAlert("Upozorenje", "Trenutno nema vozila", Alert.AlertType.CONFIRMATION);
            return;
        }
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
        if(listVehicles.isEmpty()){
            showAlert("Upozorenje", "Trenutno nema vozila", Alert.AlertType.CONFIRMATION);
            return;
        }
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
        if(listClients.isEmpty()){
            showAlert("Upozorenje", "Trenutno nema klijenata", Alert.AlertType.CONFIRMATION);
            return;
        }
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
        if(listClients.isEmpty()){
            showAlert("Upozorenje", "Trenutno nema klijenata", Alert.AlertType.CONFIRMATION);
            return;
        }
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
    public void addReservationAction(ActionEvent actionEvent){
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addReservation.fxml"));
            AddReservationController addReservationController= new AddReservationController(null);
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
                listVehicles.setAll(rentACarDAO.getVehicles());
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void deleteReservationAction(ActionEvent a){
        if(listReservations.isEmpty()){
            showAlert("Upozorenje", "Trenutno nema rezervacija", Alert.AlertType.CONFIRMATION);
            return;
        }
        Reservation reservation = tableViewReservations.getSelectionModel().getSelectedItem();
        if (reservation == null) {
            showAlert("Upozorenje", "Odaberite rezervaciju koju želite obrisati", Alert.AlertType.CONFIRMATION);
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrda brisanja");
        alert.setHeaderText("Brisanje rezervacije");
        alert.setContentText("Da li ste sigurni da želite obrisati rezervaciju?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            //System.out.println(data.getReturnTime());
            reservation.getVehicle().setAvailability("DA");
            rentACarDAO.deleteReservation(reservation);
            listVehicles.setAll(rentACarDAO.getVehicles());
            listReservations.setAll(rentACarDAO.getReservations());
            //AddReservationController addReservationController=new AddReservationController(null);
        }
    }
    public void editReservationAction(ActionEvent actionEvent){
        if(listReservations.isEmpty()){
            showAlert("Upozorenje", "Trenutno nema rezervacija", Alert.AlertType.CONFIRMATION);
            return;
        }
        Reservation reservation = tableViewReservations.getSelectionModel().getSelectedItem();
        if (reservation == null) {
            showAlert("Upozorenje", "Odaberite rezervaciju koju želite izmijeniti", Alert.AlertType.CONFIRMATION);
            return;
        }
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addReservation.fxml"));
             AddReservationController addReservationController = new AddReservationController(reservation);
            loader.setController(addReservationController);
            root = loader.load();
            stage.setTitle("Izmijeni rezervaciju");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();
            stage.setOnHiding( event -> {
                Reservation newReservation = addReservationController.getReservation();
                if (newReservation != null) {
                    listReservations.setAll(rentACarDAO.getReservations());
                    listVehicles.setAll(rentACarDAO.getVehicles());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void editReservation() {
        TableColumn<Reservation, Void> colBtn = new TableColumn("Mijenjanje");
        Callback<TableColumn<Reservation, Void>, TableCell<Reservation, Void>> cellFactory = new Callback<TableColumn<Reservation, Void>, TableCell<Reservation, Void>>() {
            @Override
            public TableCell<Reservation, Void> call(final TableColumn<Reservation, Void> param) {
                final TableCell<Reservation, Void> cell = new TableCell<Reservation, Void>() {
                    private final Button btn = new Button("Izmijeni");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Reservation reservation = getTableView().getItems().get(getIndex());
                            //System.out.println("selectedData: " + data);
                            //System.out.println(data.getReturnTime());
                            Stage stage = new Stage();
                            Parent root = null;
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addReservation.fxml"));
                                AddReservationController addReservationController = new AddReservationController(reservation);
                                loader.setController(addReservationController);
                                root = loader.load();
                                stage.setTitle("Izmijeni rezervaciju");
                                stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                                stage.setResizable(true);
                                stage.show();
                                stage.setOnHiding( event2 -> {
                                    Reservation newReservation = addReservationController.getReservation();
                                    if (newReservation != null) {
                                        //rentACarDAO.editReservation(newReservation);
                                        listReservations.setAll(rentACarDAO.getReservations());
                                        listVehicles.setAll(rentACarDAO.getVehicles());
                                    }
                                } );
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
        tableViewReservations.getColumns().add(colBtn);

    }
    public void vehicleReportAction(ActionEvent actionEvent){
        try {

            new PrintReport().showReport(rentACarDAO.getConn(), "/reports/vehiclesReport.jrxml");
        } catch (JRException | URISyntaxException e1) {
            e1.printStackTrace();
        }
    }
    public void clientReportAction(ActionEvent actionEvent){
        try {

            new PrintReport().showReport(rentACarDAO.getConn(), "/reports/clientsReport.jrxml");
        } catch (JRException | URISyntaxException e1) {
            e1.printStackTrace();
        }
    }
    public void reservationReportAction(ActionEvent actionEvent){
        try {

            new PrintReport().showReport(rentACarDAO.getConn(), "/reports/reservationsReport.jrxml");
        } catch (JRException | URISyntaxException e1) {
            e1.printStackTrace();
        }
    }
    public void writeVehiclesXMLAction(ActionEvent actionEvent){
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Zapiši XML datoteku");
            Stage stage = (Stage)tableViewVehicles.getScene().getWindow();
            File file = fileChooser.showSaveDialog(stage);
            if (file == null) // Kliknuto na cancel
                return;
            writeVehicles(file);
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong file format");
            alert.setContentText("An error occured during file save.");
            alert.showAndWait();
        }
    }
    public void writeVehicles(File file)  {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        Document document = null;

        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.newDocument();
        } catch (ParserConfigurationException err) {
            err.printStackTrace();
        }

        Element root = document.createElement("vehicles");
        document.appendChild(root);
        for (Vehicle vehicle : listVehicles) {
            Element eVehicle = document.createElement("vehicle");
            if (vehicle.getAvailability().equals("DA")) {
                Attr availabilityAttr = document.createAttribute("availability");
                availabilityAttr.setValue("true");
                eVehicle.setAttributeNode(availabilityAttr);
            }

            Element nameVehicle = document.createElement("name");
            nameVehicle.appendChild(document.createTextNode(vehicle.getName()));
            eVehicle.appendChild(nameVehicle);

            Element brandVehicle = document.createElement("brand");
            brandVehicle.appendChild(document.createTextNode(vehicle.getBrand()));
            eVehicle.appendChild(brandVehicle);

            Element modelVehicle = document.createElement("model");
            modelVehicle.appendChild(document.createTextNode(vehicle.getModel()));
            eVehicle.appendChild(modelVehicle);

            Element typeVehicle = document.createElement("type");
            typeVehicle.appendChild(document.createTextNode(vehicle.getType()));
            eVehicle.appendChild(typeVehicle);

            Element yearVehicle = document.createElement("year");
            yearVehicle.appendChild(document.createTextNode(Integer.toString(vehicle.getYear())));
            eVehicle.appendChild(yearVehicle);

            Element seatsNumber = document.createElement("seatsNumber");
            seatsNumber.appendChild(document.createTextNode(Integer.toString(vehicle.getSeatsNumber())));
            eVehicle.appendChild(seatsNumber);

            Element doorsNumber = document.createElement("doorsNumber");
            doorsNumber.appendChild(document.createTextNode(Integer.toString(vehicle.getDoorsNumber())));
            eVehicle.appendChild(doorsNumber);

            Element engine= document.createElement("engine");
            engine.appendChild(document.createTextNode(vehicle.getEngine()));
            eVehicle.appendChild(engine);

            Element transmission= document.createElement("transmission");
            transmission.appendChild(document.createTextNode(vehicle.getTransmission()));
            eVehicle.appendChild(transmission);

            Element fuelConsumption= document.createElement("fuelConsumption");
            fuelConsumption.appendChild(document.createTextNode(Double.toString(vehicle.getFuelConsumption())));
            eVehicle.appendChild(fuelConsumption);

            Element color= document.createElement("color");
            color.appendChild(document.createTextNode(vehicle.getColor()));
            eVehicle.appendChild(color);

            Element pricePerDay= document.createElement("pricePerDay");
            pricePerDay.appendChild(document.createTextNode(Double.toString(vehicle.getPricePerDay())));
            eVehicle.appendChild(pricePerDay);
            root.appendChild(eVehicle);
        }

        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);

            StreamResult streamResult = new StreamResult(file);
            transformer.transform(source, streamResult);
        } catch(TransformerException err) {
            err.printStackTrace();
        }
    }

    public void writeClientsXMLAction(ActionEvent actionEvent){
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Zapiši XML datoteku");
            Stage stage = (Stage)tableViewClients.getScene().getWindow();
            File file = fileChooser.showSaveDialog(stage);
            if (file == null) // Kliknuto na cancel
                return;
            writeClients(file);
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong file format");
            alert.setContentText("An error occured during file save.");
            alert.showAndWait();
        }
    }
    public void writeClients(File file)  {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        Document document = null;

        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.newDocument();
        } catch (ParserConfigurationException err) {
            err.printStackTrace();
        }

        Element root = document.createElement("clients");
        document.appendChild(root);

        for (Client client : listClients) {
            Element eClient = document.createElement("client");

            Element firstName = document.createElement("firstName");
            firstName.appendChild(document.createTextNode(client.getFirstName()));
            eClient.appendChild(firstName);
            Element lastName = document.createElement("lastName");
            lastName.appendChild(document.createTextNode(client.getLastName()));
            eClient.appendChild(lastName);
            Element email = document.createElement("email");
            email.appendChild(document.createTextNode(client.getEmail()));
            eClient.appendChild(email);
            Element username = document.createElement("username");
            username.appendChild(document.createTextNode(client.getUsername()));
            eClient.appendChild(username);
            Element address = document.createElement("address");
            address.appendChild(document.createTextNode(client.getAddress()));
            eClient.appendChild(address);
            Element telephone = document.createElement("telephone");
            telephone.appendChild(document.createTextNode(client.getTelephone()));
            eClient.appendChild(telephone);
            /*ArrayList<Reservation>clientReservations=new ArrayList<>();
            for(Reservation r: listReservations){
                if(r.getClient().getUsername().equals(client.getUsername()))
                    clientReservations.add(r);
            }*/
            for (Reservation reservation : listReservations) {
                if (client.getUsername().equals(reservation.getClient().getUsername())) {
                    Element eReservation = document.createElement("reservation");
                    if (reservation.getCard()!=null) {
                        Attr onlineAttr = document.createAttribute("payingOnline");
                        onlineAttr.setValue("true");
                        eReservation.setAttributeNode(onlineAttr);
                    }

                    Element vehicleName = document.createElement("vehicleName");
                    vehicleName.appendChild(document.createTextNode(reservation.getVehicle().getName()));
                    eReservation.appendChild(vehicleName);

                    Element pickupDate = document.createElement("pickupDate");
                    pickupDate.appendChild(document.createTextNode(reservation.getPickUpDate().getDayOfMonth()+"/"+reservation.getPickUpDate().getMonth()+"/"+reservation.getPickUpDate().getYear()));
                    eReservation.appendChild(pickupDate);

                    Element returnDate = document.createElement("returnDate");
                    returnDate.appendChild(document.createTextNode(reservation.getReturnDate().getDayOfMonth()+"/"+reservation.getReturnDate().getMonth()+"/"+reservation.getReturnDate().getYear()));
                    eReservation.appendChild(returnDate);

                    Element pickupTime = document.createElement("pickupTime");
                    pickupTime.appendChild(document.createTextNode(reservation.getPickupTime()));
                    eReservation.appendChild(pickupTime);

                    Element returnTime = document.createElement("returnTime");
                    returnTime.appendChild(document.createTextNode(reservation.getReturnTime()));
                    eReservation.appendChild(returnTime);

                    eClient.appendChild(eReservation);
                }
            }

            root.appendChild(eClient);
        }

        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);

            StreamResult streamResult = new StreamResult(file);
            transformer.transform(source, streamResult);
        } catch(TransformerException err) {
            err.printStackTrace();
        }
    }

}
