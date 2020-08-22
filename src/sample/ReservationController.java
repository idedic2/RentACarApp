package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

public class ReservationController {

    public DatePicker datePickup;
    public DatePicker dateReturn;
    public TextField fldName;
    public TextField fldAddress;
    public TextField fldTelephone;
    public TextField fldEmail;
    public CheckBox checkBoxNow;
    public Label lblNmbCard;
    public TextField fldNmbCard;
    public Label lblExpireDate;
    public ChoiceBox<String>choiceMonth;
    public TextField fldYear;
    public Label lblCode;
    public TextField fldCode;
    public Label lblCardData;
    public Label lblTotalPrice;
    public TextField fldPrice;
    public Label lblHeader;
    public ChoiceBox<String>choicePickupHour;
    public ChoiceBox<String>choicePickupMinute;
    public ChoiceBox<String>choiceReturnHour;
    public ChoiceBox<String>choiceReturnMinute;
    public Button btnDelete;
    private Vehicle vehicle;
    private Client client;
    private RentACarDAO rentACarDAO;
    private Reservation reservation;
    public Button btnReservationConfirm;
    public Button btnBack;
    private boolean existClient=false;
    //private  boolean sveOk=false;
    private boolean dateOk=false;
    public ReservationController(Vehicle vehicle, Client client, Reservation reservation) {
        this.vehicle=vehicle;
        this.client=client;
        this.reservation=reservation;
        rentACarDAO=RentACarDAO.getInstance();
    }


    private boolean allDigits(String str){
        return  str.chars().allMatch(Character::isDigit);
    }
    private boolean isReturnAfterPickup(){
        if(datePickup.getValue().isAfter(dateReturn.getValue()))
            return false;
        return true;
    }
    private int getMonth(String month){
        if(month.equals("JAN"))return 1;
        if(month.equals("FEB"))return 2;
        if(month.equals("MAR"))return 3;
        if(month.equals("APR"))return 4;
        if(month.equals("MAJ"))return 5;
        if(month.equals("JUN"))return 6;
        if(month.equals("JUL"))return 7;
        if(month.equals("AUG"))return 8;
        if(month.equals("SEP"))return 9;
        if(month.equals("OKT"))return 10;
        if(month.equals("NOV"))return 11;
        return 12;
    }
    private int getDays(String month){
        if(month.equals("JAN") || month.equals("MAR") || month.equals("MAJ") || month.equals("JUL") || month.equals("AUG") || month.equals("OKT") || month.equals("DEC"))
            return 31;
        if(month.equals("APR") || month.equals("JUN") || month.equals("SEP") || month.equals("NOV"))
            return 30;
        if(isLeap(Integer.parseInt(fldYear.getText())))return 29;
        return 28;
    }
    private boolean isLeap(int year){
        boolean leap = false;
        if(year % 4 == 0)
        {
            if( year % 100 == 0)
            {
                // year is divisible by 400, hence the year is a leap year
                if ( year % 400 == 0)
                    leap = true;
                else
                    leap = false;
            }
            else
                leap = true;
        }
        else
            leap = false;
        return leap;
    }
    public LocalDate stringToDate(String date){
        String []temp=date.split("\\.");
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("d/MM/yyyy");
        String d="";
        if(Integer.parseInt(temp[0])>=1 && Integer.parseInt(temp[0])<=9)d+="0";
        d+=temp[0]+"/";
        if(Integer.parseInt(temp[1]) >= 1 && Integer.parseInt(temp[1])<=9)d+="0";
        d+=temp[1]+"/"+temp[2];
        LocalDate localDate=LocalDate.parse(d, formatter);
        return localDate;
    }
    private boolean checkExpirationDate(){
        String expire=getDays(choiceMonth.getValue())+"."+getMonth(choiceMonth.getValue())+"."+fldYear.getText();
        LocalDate expireDate=stringToDate(expire);
        LocalDate localDate=LocalDate.now();
        if(expireDate.isBefore(localDate))return false;
        return true;
    }
    @FXML
    public void initialize() {
        lblHeader.setText(lblHeader.getText()+ ": "+vehicle.getName());
        if(reservation==null)btnDelete.setDisable(true);
        if (reservation != null) {
            fldName.setText(reservation.getClient().getFirstName() + " " + reservation.getClient().getLastName());
            fldEmail.setText(reservation.getClient().getEmail());
            fldAddress.setText(reservation.getClient().getAddress());
            fldTelephone.setText(reservation.getClient().getTelephone());
            fldName.setDisable(true);
            fldEmail.setDisable(true);
            fldAddress.setDisable(true);
            fldTelephone.setDisable(true);
            datePickup.setValue(reservation.getPickUpDate());
            dateReturn.setValue(reservation.getReturnDate());
            String []tmp=reservation.getPickupTime().split(":");
            choicePickupHour.setValue(tmp[0]);
            choicePickupMinute.setValue(tmp[1]);
            String []tmp2=reservation.getReturnTime().split(":");
            choiceReturnHour.setValue(tmp2[0]);
            choiceReturnMinute.setValue(tmp2[1]);
            fldPrice.setText(priceForRenting() + " KM");
            if(reservation.getCard()!=null){
                checkBoxNow.setSelected(true);
                fldNmbCard.setText(reservation.getCard().getCardNumber());
                fldCode.setText(reservation.getCard().getCode());
                LocalDate date=reservation.getCard().getExpirationDate();
                fldYear.setText(Integer.toString(date.getYear()));
                choiceMonth.setValue(date.getMonth().toString().substring(0, 2));
                checkBoxNow.setDisable(true);
            }
            lblNmbCard.setDisable(true);
            fldNmbCard.setDisable(true);
            lblExpireDate.setDisable(true);
            choiceMonth.setDisable(true);
            fldYear.setDisable(true);
            lblCode.setDisable(true);
            fldCode.setDisable(true);
            fldNmbCard.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                    if (!fldNmbCard.getText().trim().isEmpty()) {
                        if (allDigits(fldNmbCard.getText()) && fldNmbCard.getText().length() == 16) {
                            fldNmbCard.getStyleClass().removeAll("neispravnoPolje");
                            fldNmbCard.getStyleClass().add("ispravnoPolje");
                        } else {
                            fldNmbCard.getStyleClass().removeAll("ispravnoPolje");
                            fldNmbCard.getStyleClass().add("neispravnoPolje");
                        }
                    } else {
                        fldNmbCard.getStyleClass().removeAll("ispravnoPolje");
                        fldNmbCard.getStyleClass().add("neispravnoPolje");
                    }
                }
            });
            fldCode.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                    if (!fldCode.getText().trim().isEmpty()) {
                        if (allDigits(fldCode.getText()) && (fldCode.getText().length() == 3 || fldCode.getText().length() == 4)) {
                            fldCode.getStyleClass().removeAll("neispravnoPolje");
                            fldCode.getStyleClass().add("ispravnoPolje");
                        } else {
                            fldCode.getStyleClass().removeAll("ispravnoPolje");
                            fldCode.getStyleClass().add("neispravnoPolje");
                        }
                    } else {
                        fldCode.getStyleClass().removeAll("ispravnoPolje");
                        fldCode.getStyleClass().add("neispravnoPolje");
                    }
                }
            });
            fldYear.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                    if (!fldYear.getText().trim().isEmpty()) {
                        if (allDigits(fldYear.getText()) && fldYear.getText().length() == 4) {
                            fldYear.getStyleClass().removeAll("neispravnoPolje");
                            fldYear.getStyleClass().add("ispravnoPolje");
                            choiceMonth.getStyleClass().removeAll("neispravnoPolje");
                            choiceMonth.getStyleClass().add("ispravnoPolje");
                        } else {
                            fldYear.getStyleClass().removeAll("ispravnoPolje");
                            fldYear.getStyleClass().add("neispravnoPolje");
                            choiceMonth.getStyleClass().removeAll("ispravnoPolje");
                            choiceMonth.getStyleClass().add("neispravnoPolje");
                        }
                    } else {
                        fldYear.getStyleClass().removeAll("ispravnoPolje");
                        fldYear.getStyleClass().add("neispravnoPolje");
                        choiceMonth.getStyleClass().removeAll("ispravnoPolje");
                        choiceMonth.getStyleClass().add("neispravnoPolje");
                    }
                }
            });
        }
        else {
            lblTotalPrice.setVisible(false);
            fldPrice.setVisible(false);
            fldName.setText(client.getFirstName() + " " + client.getLastName());
            fldEmail.setText(client.getEmail());
            if (!client.getAddress().equals("")) {
                fldAddress.setText(client.getAddress());
                fldTelephone.setText(client.getTelephone());
                fldAddress.setDisable(true);
                fldTelephone.setDisable(true);
            }
            fldName.setDisable(true);
            fldEmail.setDisable(true);
            //((client=rentACarDAO.getClient(user.getId());
            //if(client!=null)existClient=true;
            //datePickup.getStyleClass().add("neispravnoPolje");
            //dateReturn.getStyleClass().add("neispravnoPolje");
            lblNmbCard.setDisable(true);
            fldNmbCard.setDisable(true);
            lblExpireDate.setDisable(true);
            choiceMonth.setDisable(true);
            fldYear.setDisable(true);
            lblCode.setDisable(true);
            fldCode.setDisable(true);
        /*datePickup.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String n) {
                if (n.trim().isEmpty()) {
                    datePickup.getStyleClass().removeAll("ispravnoPolje");
                    datePickup.getStyleClass().add("neispravnoPolje");
                    //pickupDateValidate = true;
                } else {
                    datePickup.getStyleClass().removeAll("neispravnoPolje");
                    datePickup.getStyleClass().add("ispravnoPolje");
                    //pickupDateValidate = false;
                }
            }
        });

        dateReturn.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String n) {
                if (n.trim().isEmpty()) {
                    dateReturn.getStyleClass().removeAll("ispravnoPolje");
                    dateReturn.getStyleClass().add("neispravnoPolje");
                    //returnDateValidate = true;
                } else {
                    dateReturn.getStyleClass().removeAll("neispravnoPolje");
                    dateReturn.getStyleClass().add("ispravnoPolje");
                    //returnDateValidate = false;
                }
            }
        });
*/
            fldTelephone.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                    if (!fldTelephone.getText().trim().isEmpty()) {
                        if (!allDigits(fldTelephone.getText()) || fldTelephone.getText().length() < 9) {
                            fldTelephone.getStyleClass().removeAll("ispravnoPolje");
                            fldTelephone.getStyleClass().add("neispravnoPolje");
                        } else {
                            fldTelephone.getStyleClass().removeAll("neispravnoPolje");
                            fldTelephone.getStyleClass().add("ispravnoPolje");
                        }
                    } else {
                        fldTelephone.getStyleClass().removeAll("ispravnoPolje");
                        fldTelephone.getStyleClass().add("neispravnoPolje");
                    }
                }
            });
            fldAddress.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String n) {
                    if (fldAddress.getText().trim().isEmpty()) {
                        fldAddress.getStyleClass().removeAll("ispravnoPolje");
                        fldAddress.getStyleClass().add("neispravnoPolje");
                        //returnDateValidate = true;
                    } else {
                        fldAddress.getStyleClass().removeAll("neispravnoPolje");
                        fldAddress.getStyleClass().add("ispravnoPolje");
                        //returnDateValidate = false;
                    }
                }
            });
            fldNmbCard.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                    if (!fldNmbCard.getText().trim().isEmpty()) {
                        if (allDigits(fldNmbCard.getText()) && fldNmbCard.getText().length() == 16) {
                            fldNmbCard.getStyleClass().removeAll("neispravnoPolje");
                            fldNmbCard.getStyleClass().add("ispravnoPolje");
                        } else {
                            fldNmbCard.getStyleClass().removeAll("ispravnoPolje");
                            fldNmbCard.getStyleClass().add("neispravnoPolje");
                        }
                    } else {
                        fldNmbCard.getStyleClass().removeAll("ispravnoPolje");
                        fldNmbCard.getStyleClass().add("neispravnoPolje");
                    }
                }
            });
            fldCode.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                    if (!fldCode.getText().trim().isEmpty()) {
                        if (allDigits(fldCode.getText()) && (fldCode.getText().length() == 3 || fldCode.getText().length() == 4)) {
                            fldCode.getStyleClass().removeAll("neispravnoPolje");
                            fldCode.getStyleClass().add("ispravnoPolje");
                        } else {
                            fldCode.getStyleClass().removeAll("ispravnoPolje");
                            fldCode.getStyleClass().add("neispravnoPolje");
                        }
                    } else {
                        fldCode.getStyleClass().removeAll("ispravnoPolje");
                        fldCode.getStyleClass().add("neispravnoPolje");
                    }
                }
            });
            fldYear.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                    if (!fldYear.getText().trim().isEmpty()) {
                        if (allDigits(fldYear.getText()) && fldYear.getText().length() == 4) {
                            fldYear.getStyleClass().removeAll("neispravnoPolje");
                            fldYear.getStyleClass().add("ispravnoPolje");
                            choiceMonth.getStyleClass().removeAll("neispravnoPolje");
                            choiceMonth.getStyleClass().add("ispravnoPolje");
                        } else {
                            fldYear.getStyleClass().removeAll("ispravnoPolje");
                            fldYear.getStyleClass().add("neispravnoPolje");
                            choiceMonth.getStyleClass().removeAll("ispravnoPolje");
                            choiceMonth.getStyleClass().add("neispravnoPolje");
                        }
                    } else {
                        fldYear.getStyleClass().removeAll("ispravnoPolje");
                        fldYear.getStyleClass().add("neispravnoPolje");
                        choiceMonth.getStyleClass().removeAll("ispravnoPolje");
                        choiceMonth.getStyleClass().add("neispravnoPolje");
                    }
                }
            });

        }
    }
    public void changePayingAction(ActionEvent actionEvent){
        if(checkBoxNow.isSelected()){
            lblNmbCard.setDisable(false);
            fldNmbCard.setDisable(false);
            lblExpireDate.setDisable(false);
            choiceMonth.setDisable(false);
            fldYear.setDisable(false);
            lblCode.setDisable(false);
            fldCode.setDisable(false);
        }
        else{
            lblNmbCard.setDisable(true);
            fldNmbCard.setDisable(true);
            lblExpireDate.setDisable(true);
            choiceMonth.setDisable(true);
            fldYear.setDisable(true);
            lblCode.setDisable(true);
            fldCode.setDisable(true);
        }
    }
    private void showAlert(String title, String headerText, Alert.AlertType type) {
        Alert error = new Alert(type);
        error.setTitle(title);
        error.setHeaderText(headerText);
        error.show();
    }
    public void deleteReservationAction(ActionEvent actionEvent){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrda brisanja");
        alert.setHeaderText("Brisanje rezervacije");
        alert.setContentText("Da li ste sigurni da želite obrisati rezervaciju?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            //System.out.println(data.getReturnTime());
            reservation.getVehicle().setAvailability("DA");
            rentACarDAO.deleteReservation(reservation);
           Stage stage= (Stage) fldName.getScene().getWindow();
           stage.close();
        }
    }
    public void reservationConfirmAction(ActionEvent actionEvent) {
       boolean sveOk=true;
       if(reservation==null) {
           if (fldAddress.getText().trim().isEmpty()) {
               sveOk = false;
               showAlert("Greška", "Unesite adresu", Alert.AlertType.ERROR);
               return;
           }
           if (fldTelephone.getText().trim().isEmpty()) {
               sveOk = false;
               showAlert("Greška", "Unesite kontakt telefon", Alert.AlertType.ERROR);
               return;
           }
           if (!(allDigits(fldTelephone.getText()) && fldTelephone.getText().length() >= 9)) {
               sveOk = false;
               showAlert("Greška", "Nevalidan broj telefona", Alert.AlertType.ERROR);
               return;

           }
       }
        if(!dateOk){
            showAlert("Greška", "Odaberite ispravan datum rentanja/vraćanja vozila", Alert.AlertType.ERROR);
            return;
        }
        /*if(!checkBoxNow.isSelected()) {
            //showAlert("Potvrdite", "Da li ste sigurni da ne želite platiti karticom", Alert.AlertType.CONFIRMATION);
            //return;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potvrda rezervisanja");
            alert.setHeaderText("Rezervisanje vozila  "+vehicle.getName());
            alert.setContentText("Da li ste sigurni da želite rezervisati vozilo " +vehicle.getName()+"?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                vehicle.setAvailability("NE");
                rentACarDAO.editVehicle(vehicle);

                listVehicles.setAll(rentACarDAO.getVehicles());
            }
        }*/
        if(reservation!=null){
            long daysBetween = DAYS.between(datePickup.getValue(), reservation.getPickUpDate());
            if(daysBetween>7 && datePickup.getValue().isAfter(reservation.getPickUpDate())){
                showAlert("Greška", "Datum preuzimanja vozila možete odgoditi za maksimalno 7 dana", Alert.AlertType.ERROR);
                return;
            }

        }
        if(reservation==null || reservation.getCard()==null) {
            if (checkBoxNow.isSelected()) {
                boolean cardOk = false;
                if (fldNmbCard.getText().trim().isEmpty()) {
                    cardOk = false;
                    showAlert("Greška", "Unesite broj kartice", Alert.AlertType.ERROR);
                    return;
                }

                if (!(allDigits(fldNmbCard.getText()) && fldNmbCard.getText().length() == 16)) {
                    cardOk = false;
                    showAlert("Greška", "Nevalidan broj kartice", Alert.AlertType.ERROR);
                    return;
                }
                if (fldYear.getText().trim().isEmpty()) {
                    cardOk = false;
                    showAlert("Greška", "Unesite godinu isticanja kartice", Alert.AlertType.ERROR);
                    return;
                }
                if (!(allDigits(fldYear.getText()) && fldYear.getText().length() == 4)) {
                    cardOk = false;
                    showAlert("Greška", "Neispravna format godine isticanja kartice", Alert.AlertType.ERROR);
                    return;
                }
                if (!checkExpirationDate()) {
                    cardOk = false;
                    showAlert("Greška", "Kartica je istekla", Alert.AlertType.ERROR);
                    fldYear.getStyleClass().removeAll("ispravnoPolje");
                    fldYear.getStyleClass().add("neispravnoPolje");
                    choiceMonth.getStyleClass().removeAll("ispravnoPolje");
                    choiceMonth.getStyleClass().add("neispravnoPolje");
                    return;
                }
                if (checkExpirationDate()) {
                    fldYear.getStyleClass().removeAll("neispravnoPolje");
                    fldYear.getStyleClass().add("ispravnoPolje");
                    choiceMonth.getStyleClass().removeAll("neispravnoPolje");
                    choiceMonth.getStyleClass().add("ispravnoPolje");
                }
                if (fldCode.getText().trim().isEmpty()) {
                    cardOk = false;
                    showAlert("Greška", "Unesite kod", Alert.AlertType.ERROR);
                    return;
                }
                if (!(allDigits(fldCode.getText()) && (fldCode.getText().length() == 3 || fldCode.getText().length() == 4))) {
                    cardOk = false;
                    showAlert("Greška", "Nevalidan kod", Alert.AlertType.ERROR);
                    return;

                }
            }
        }
        if (reservation == null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potvrda rezervisanja");
            alert.setHeaderText("Rezervisanje vozila  " + vehicle.getName());
            alert.setContentText("Da li ste sigurni da želite rezervisati vozilo " + vehicle.getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                vehicle.setAvailability("NE");
                rentACarDAO.editVehicle(vehicle);
                Card card = null;
                if (checkBoxNow.isSelected()) {
                    String expire = getDays(choiceMonth.getValue()) + "." + getMonth(choiceMonth.getValue()) + "." + fldYear.getText();
                    LocalDate expireDate = stringToDate(expire);
                    try{
                    card = new Card(0, fldNmbCard.getText(), fldCode.getText(), expireDate);
                    }
                    catch (NegativeNumberException e){
                        e.printStackTrace();
                    }
                    rentACarDAO.addCard(card);
                    //dobavi ponovo karticu iz baze
                    card = rentACarDAO.getCard(fldNmbCard.getText());
                }
                try{
                Reservation reservation = new Reservation(0, vehicle, client, datePickup.getValue(), dateReturn.getValue(), choicePickupHour.getValue() + ":" + choicePickupMinute.getValue(), choiceReturnHour.getValue() + ":" + choiceReturnMinute.getValue(), card);
                }
                catch (NegativeNumberException e){
                    e.printStackTrace();
                }
                rentACarDAO.addReservation(reservation);
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potvrda izmjene");
            alert.setHeaderText("Izmjena rezervacije vozila " + vehicle.getName());
            alert.setContentText("Da li ste sigurni da želite izmijeniti rezervaciju vozila " + vehicle.getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                reservation.setPickUpDate(datePickup.getValue());
                reservation.setReturnDate(dateReturn.getValue());
                reservation.setPickupTime(choicePickupHour.getValue() + ":" + choicePickupMinute.getValue());
                reservation.setReturnTime(choiceReturnHour.getValue() + ":" + choiceReturnMinute.getValue());
                if(checkBoxNow.isSelected()){
                    String expire = getDays(choiceMonth.getValue()) + "." + getMonth(choiceMonth.getValue()) + "." + fldYear.getText();
                    LocalDate expireDate = stringToDate(expire);
                    Card card=null;
                    try{
                    card = new Card(0, fldNmbCard.getText(), fldCode.getText(), expireDate);
                    }
                    catch (NegativeNumberException e){
                        e.printStackTrace();
                    }
                    rentACarDAO.addCard(card);
                    //dobavi ponovo karticu iz baze
                    card = rentACarDAO.getCard(fldNmbCard.getText());
                    reservation.setCard(card);
                }
                rentACarDAO.editReservation(reservation);
            }
        }
            Stage stage = (Stage) lblTotalPrice.getScene().getWindow();
            stage.close();
        }

    private Double priceForRenting(){
        long daysBetween = DAYS.between(datePickup.getValue(), dateReturn.getValue());
        return daysBetween*vehicle.getPricePerDay();
    }
    public void changeDate(ActionEvent actionEvent) {
        if (datePickup.getValue() == null) {
            dateOk=false;
            lblTotalPrice.setVisible(false);
            fldPrice.setVisible(false);
            datePickup.getStyleClass().removeAll("ispravnoPolje");
            datePickup.getStyleClass().add("neispravnoPolje");
            dateReturn.getStyleClass().removeAll("neispravnoPolje");
            dateReturn.getStyleClass().add("ispravnoPolje");
            //showAlert("Upozorenje", "Odaberite datum rentanja vozila", Alert.AlertType.WARNING);
            return;
        }
        if(dateReturn.getValue()==null){
            dateOk=false;
            lblTotalPrice.setVisible(false);
            fldPrice.setVisible(false);
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
            lblTotalPrice.setVisible(false);
            fldPrice.setVisible(false);
            showAlert("Greška", "Odabrani datum/datumi iz prošlosti", Alert.AlertType.ERROR);
            return;
        }
            if(datePickup.getValue().isEqual(dateReturn.getValue())){
                dateOk=false;
                datePickup.getStyleClass().removeAll("ispravnoPolje");
                datePickup.getStyleClass().add("neispravnoPolje");
                dateReturn.getStyleClass().removeAll("ispravnoPolje");
                dateReturn.getStyleClass().add("neispravnoPolje");
                lblTotalPrice.setVisible(false);
                fldPrice.setVisible(false);
                showAlert("Greška", "Minimum rentanja je 24h", Alert.AlertType.ERROR);
                return;
            }
            if (isReturnAfterPickup()) {
                    dateOk = true;
                    datePickup.getStyleClass().removeAll("neispravnoPolje");
                    datePickup.getStyleClass().add("ispravnoPolje");
                    dateReturn.getStyleClass().removeAll("neispravnoPolje");
                    dateReturn.getStyleClass().add("ispravnoPolje");
                    lblTotalPrice.setVisible(true);
                    fldPrice.setVisible(true);
                    fldPrice.setText(priceForRenting() + " KM");
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
                lblTotalPrice.setVisible(false);
                fldPrice.setVisible(false);
                datePickup.getStyleClass().removeAll("ispravnoPolje");
                datePickup.getStyleClass().add("neispravnoPolje");
                dateReturn.getStyleClass().removeAll("ispravnoPolje");
                dateReturn.getStyleClass().add("neispravnoPolje");
                showAlert("Greška", "Datum povratka mora biti nakon datuma rentanja", Alert.AlertType.ERROR);

            }

        }
    public void backAction(ActionEvent actionEvent){
        Stage stage= (Stage) fldName.getScene().getWindow();
        stage.close();
    }
}
