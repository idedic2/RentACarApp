package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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
    private Reservation reservation;
    private ObservableList<Vehicle>vehicles;
    private ObservableList<Client>clients;
    public TextField fldSearchClient;
    private Vehicle currVehicle;
    public ChoiceBox<String>choiceFilterClient;
    public ChoiceBox<String>choiceFilterVehicle;
    private boolean change=false;
    public AddReservationController(Reservation reservation) {
        rentACarDAO = RentACarDAO.getInstance();
        this.reservation = reservation;
        vehicles = FXCollections.observableArrayList(rentACarDAO.getVehiclesPerAvailability());
        if (reservation == null) clients = FXCollections.observableArrayList(rentACarDAO.getClients());
        else {
            ArrayList<Client> clientsTemp = new ArrayList<>();
            clientsTemp.add(reservation.getClient());
            clients = FXCollections.observableArrayList(clientsTemp);
            //vehicles.add(reservation.getVehicle());
        }
    }
    public void changeFilterVehicle(ActionEvent actionEvent){
        if(choiceFilterVehicle.getValue().equals("Svi"))
            fldSearchVehicle.setText("");
        if(fldSearchVehicle.getText().isEmpty()) {
            listVehicles.setItems(vehicles);
        }
        else{
            FilteredList<Vehicle> filteredListVehicles=new FilteredList<Vehicle>(vehicles, p->true);

            switch (choiceFilterVehicle.getValue())
            {
                case "Naziv":
                    filteredListVehicles.setPredicate(p -> p.getName().toLowerCase().startsWith(fldSearchVehicle.getText().toLowerCase().trim()));
                    break;
                case "Model":
                    filteredListVehicles.setPredicate(p -> p.getModel().toLowerCase().startsWith(fldSearchVehicle.getText().toLowerCase().trim()));
                    break;

            }
            listVehicles.setItems(filteredListVehicles);

        }
    }
    public void changeFilterClient(ActionEvent actionEvent){
        if(choiceFilterClient.getValue().equals("Svi"))
            fldSearchClient.setText("");
        if(fldSearchClient.getText().isEmpty()) {
            listClients.setItems(clients);
        }
        else{
            FilteredList<Client> filteredListClients=new FilteredList<Client>(clients, p->true);

                switch (choiceFilterClient.getValue())
                {
                    case "Ime":
                        filteredListClients.setPredicate(p -> p.getFirstName().toLowerCase().startsWith(fldSearchClient.getText().toLowerCase().trim()));
                        break;
                    case "Prezime":
                        filteredListClients.setPredicate(p -> p.getLastName().toLowerCase().startsWith(fldSearchClient.getText().toLowerCase().trim()));
                        break;
                    case "Username":
                        filteredListClients.setPredicate(p -> p.getUsername().toLowerCase().startsWith(fldSearchClient.getText().toLowerCase().trim()));
                        break;
                    case "Adresa":
                        filteredListClients.setPredicate(p -> String.valueOf(p.getAddress()).toLowerCase().startsWith(fldSearchClient.getText().toLowerCase().trim()));
                        break;

                }
                listClients.setItems(filteredListClients);

        }

    }

     @FXML
     public void initialize() {
         FilteredList<Vehicle> filteredListVehicles=new FilteredList<Vehicle>(vehicles, p->true);
         fldSearchVehicle.setOnKeyReleased(keyEvent ->
         {
             switch (choiceFilterVehicle.getValue())
             {
                 case "Naziv":
                     filteredListVehicles.setPredicate(p -> p.getName().toLowerCase().startsWith(fldSearchVehicle.getText().toLowerCase().trim()));
                     break;
                 case "Model":
                     filteredListVehicles.setPredicate(p -> p.getModel().toLowerCase().startsWith(fldSearchVehicle.getText().toLowerCase().trim()));
                     break;

             }
             listVehicles.setItems(filteredListVehicles);

         });
         FilteredList<Client> filteredListClients=new FilteredList<Client>(clients, p->true);
         fldSearchClient.setOnKeyReleased(keyEvent ->
         {
             switch (choiceFilterClient.getValue())
             {
                 case "Ime":
                     filteredListClients.setPredicate(p -> p.getFirstName().toLowerCase().startsWith(fldSearchClient.getText().toLowerCase().trim()));
                     break;
                 case "Prezime":
                     filteredListClients.setPredicate(p -> p.getLastName().toLowerCase().startsWith(fldSearchClient.getText().toLowerCase().trim()));
                     break;
                 case "Username":
                     filteredListClients.setPredicate(p -> p.getUsername().toLowerCase().startsWith(fldSearchClient.getText().toLowerCase().trim()));
                     break;
                 case "Adresa":
                     filteredListClients.setPredicate(p -> String.valueOf(p.getAddress()).toLowerCase().startsWith(fldSearchClient.getText().toLowerCase().trim()));
                     break;

             }
             listClients.setItems(filteredListClients);
         });
         if (reservation != null) {
             //vehicles.add(reservation.getVehicle());
             listClients.setDisable(true);
             //listVehicles.getSelectionModel().select(reservation.getVehicle());
             choiceFilterClient.setDisable(true);
             fldSearchClient.setDisable(true);
             datePickup.setValue(reservation.getPickUpDate());
             dateReturn.setValue(reservation.getReturnDate());
             String[] tmpPickup = reservation.getPickupTime().split(":");
             hourPickup.setValue(tmpPickup[0]);
             minutePickup.setValue(tmpPickup[1]);
             String[] tmpReturn = reservation.getReturnTime().split(":");
             hourPickup.setValue(tmpReturn[0]);
             minutePickup.setValue(tmpReturn[1]);
         }
         listClients.setItems(clients);
         listVehicles.setItems(vehicles);

         listVehicles.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Vehicle>() {
             @Override
             public void changed(ObservableValue<? extends Vehicle> observable, Vehicle oldValue, Vehicle newValue) {
                 currVehicle=newValue;
                 change=true;
                 System.out.println(newValue);
             }
         });
     }

     public Reservation getReservation(){
        return reservation;
     }
    public void confirmAddReservationAction(ActionEvent actionEvent) {
        if(reservation==null) {
            if (listVehicles.getSelectionModel().getSelectedItem() == null) {
                showAlert("Greška", "Odaberite vozilo", Alert.AlertType.ERROR);
                return;
            }
        }
        if(reservation==null) {
            if (listClients.getSelectionModel().getSelectedItem() == null) {
                showAlert("Greška", "Odaberite klijenta", Alert.AlertType.ERROR);
                return;
            }
        }
        if(reservation!=null) {
            if (reservation.getPickUpDate().isEqual(datePickup.getValue()) && reservation.getReturnDate().isEqual(dateReturn.getValue())) {
                dateOk = true;
            }
        }
        if(!dateOk){
            showAlert("Greška", "Odaberite ispravan datum rentanja/vraćanja vozila", Alert.AlertType.ERROR);
            return;
        }
        if(reservation==null) {
            reservation = new Reservation();
            Vehicle vehicle = listVehicles.getSelectionModel().getSelectedItem();
            Client client = listClients.getSelectionModel().getSelectedItem();
            vehicle.setAvailability("NE");
            rentACarDAO.editVehicle(vehicle);
            try{
            Reservation reservation = new Reservation(0, vehicle, client, datePickup.getValue(), dateReturn.getValue(), hourPickup.getValue() + ":" + minutePickup.getValue(), hourReturn.getValue() + ":" + minuteReturn.getValue(), null);
            this.reservation=reservation;
            }
            catch (NegativeNumberException | InvalidTimeFormatException e){
                e.printStackTrace();
            }
            rentACarDAO.addReservation(reservation);
        }
        else{
            if(change) {
                if (reservation.getVehicle().getId() != currVehicle.getId()) {
                    //listVehicles.getSelectionModel().getSelectedItem().setAvailability("NE");
                    //rentACarDAO.editVehicle(listVehicles.getSelectionModel().getSelectedItem());
                    currVehicle.setAvailability("NE");
                    rentACarDAO.editVehicle(currVehicle);
                    reservation.getVehicle().setAvailability("DA");

                    rentACarDAO.editVehicle(reservation.getVehicle());
                    reservation.setVehicle(currVehicle);
                    vehicles.setAll(rentACarDAO.getVehiclesPerAvailability());
                    //listVehicles.refresh();
                }
            }
           reservation.setPickUpDate(datePickup.getValue());
           reservation.setReturnDate(dateReturn.getValue());
            try {
                reservation.setPickupTime(hourPickup.getValue()+":"+minutePickup.getValue());
            } catch (InvalidTimeFormatException e) {
                e.printStackTrace();
            }
            try {
                reservation.setReturnTime(hourReturn.getValue()+":"+minuteReturn.getValue());
            } catch (InvalidTimeFormatException e) {
                e.printStackTrace();
            }
            rentACarDAO.editReservation(reservation);
           System.out.println(reservation.getVehicle().getName());

        }
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
        reservation=null;
        Stage stage= (Stage) hourPickup.getScene().getWindow();
        stage.close();
    }
}
