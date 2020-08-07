package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

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
    private Vehicle vehicle;
    private User user;
    private RentACarDAO rentACarDAO;
    //private  boolean sveOk=false;
    private boolean dateOk=false;
    public ReservationController(Vehicle vehicle, User user) {
        this.vehicle=vehicle;
        this.user=user;
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
    public void initialize(){
        fldName.setText(user.getFirstName()+" "+user.getLastName());
        fldEmail.setText(user.getEmail());
        fldName.setDisable(true);
        fldEmail.setDisable(true);
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
                    if(!allDigits(fldTelephone.getText()) || fldTelephone.getText().length()<9){
                        fldTelephone.getStyleClass().removeAll("ispravnoPolje");
                        fldTelephone.getStyleClass().add("neispravnoPolje");
                    }
                    else{
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
                        if(allDigits(fldNmbCard.getText()) && fldNmbCard.getText().length()==16){
                            fldNmbCard.getStyleClass().removeAll("neispravnoPolje");
                            fldNmbCard.getStyleClass().add("ispravnoPolje");
                        }
                        else{
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
                    if(allDigits(fldCode.getText()) && (fldCode.getText().length()==3 || fldCode.getText().length()==4)){
                        fldCode.getStyleClass().removeAll("neispravnoPolje");
                        fldCode.getStyleClass().add("ispravnoPolje");
                    }
                    else{
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
                    if(allDigits(fldYear.getText()) && fldYear.getText().length()==4){
                        fldYear.getStyleClass().removeAll("neispravnoPolje");
                        fldYear.getStyleClass().add("ispravnoPolje");
                    }
                    else{
                        fldYear.getStyleClass().removeAll("ispravnoPolje");
                        fldYear.getStyleClass().add("neispravnoPolje");
                    }
                } else {
                    fldYear.getStyleClass().removeAll("ispravnoPolje");
                    fldYear.getStyleClass().add("neispravnoPolje");
                }
            }
        });

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
    public void reservationConfirmAction(ActionEvent actionEvent) {
       boolean sveOk=true;
        if(fldAddress.getText().trim().isEmpty()){
            sveOk=false;
            showAlert("Greška", "Unesite adresu", Alert.AlertType.ERROR);
            return;
        }
        if(fldTelephone.getText().trim().isEmpty()){
            sveOk=false;
            showAlert("Greška", "Unesite kontakt telefon", Alert.AlertType.ERROR);
            return;
        }
        if(!(allDigits(fldTelephone.getText()) && fldTelephone.getText().length()>=9)){
                sveOk=false;
                showAlert("Greška", "Nevalidan broj telefona", Alert.AlertType.ERROR);
                return;

        }
        if(!dateOk){
            showAlert("Greška", "Odaberite datum rentanja/vraćanja vozila", Alert.AlertType.ERROR);
            return;
        }
        if(!checkBoxNow.isSelected()) {
            showAlert("Potvrdite", "Da li ste sigurni da ne želite platiti karticom", Alert.AlertType.CONFIRMATION);
            return;
        }
        if(checkBoxNow.isSelected()){
            boolean cardOk=false;
            if(fldNmbCard.getText().trim().isEmpty()){
                cardOk=false;
                showAlert("Greška", "Unesite broj kartice", Alert.AlertType.ERROR);
                return;
            }

                if(!(allDigits(fldNmbCard.getText()) && fldNmbCard.getText().length()==16)){
                    cardOk=false;
                    showAlert("Greška", "Nevalidan broj kartice", Alert.AlertType.ERROR);
                    return;

            }
            if(fldYear.getText().trim().isEmpty()){
                cardOk=false;
                showAlert("Greška", "Unesite godinu isticanja kartice", Alert.AlertType.ERROR);
                return;
            }
            if(!(allDigits(fldYear.getText()) && fldYear.getText().length()==4)){
                cardOk=false;
                showAlert("Greška", "Neispravna format godine isticanja kartice", Alert.AlertType.ERROR);
                return;
            }
            if(!checkExpirationDate()){
                cardOk=false;
                showAlert("Greška", "Kartica je istekla", Alert.AlertType.ERROR);
                fldYear.getStyleClass().removeAll("ispravnoPolje");
                fldYear.getStyleClass().add("neispravnoPolje");
                choiceMonth.getStyleClass().removeAll("ispravnoPolje");
                choiceMonth.getStyleClass().add("neispravnoPolje");
                return;
            }
            if(fldCode.getText().trim().isEmpty()){
                cardOk=false;
                showAlert("Greška", "Unesite kod", Alert.AlertType.ERROR);
                return;
            }
                if(!(allDigits(fldCode.getText()) && (fldCode.getText().length()==3 || fldCode.getText().length()==4))){
                    cardOk=false;
                    showAlert("Greška", "Nevalidan kod", Alert.AlertType.ERROR);
                    return;

            }

            showAlert("Odlicno", "placate odmah", Alert.AlertType.ERROR);

        }

    }

    public void changeDate(ActionEvent actionEvent) {
        if (datePickup.getValue() == null) {
            dateOk=false;
            datePickup.getStyleClass().removeAll("ispravnoPolje");
            datePickup.getStyleClass().add("neispravnoPolje");
            dateReturn.getStyleClass().removeAll("neispravnoPolje");
            dateReturn.getStyleClass().add("ispravnoPolje");
            //showAlert("Upozorenje", "Odaberite datum rentanja vozila", Alert.AlertType.WARNING);
            //return;
        }
        else if(dateReturn.getValue()==null){
            dateOk=false;
            datePickup.getStyleClass().removeAll("neispravnoPolje");
            datePickup.getStyleClass().add("ispravnoPolje");
            dateReturn.getStyleClass().removeAll("ispravnoPolje");
            dateReturn.getStyleClass().add("neispravnoPolje");
            //showAlert("Upozorenje", "Odaberite datum vraćanja vozila", Alert.AlertType.WARNING);
            //return;
        }
        else{
            if (isReturnAfterPickup()) {
                dateOk=true;
                datePickup.getStyleClass().removeAll("neispravnoPolje");
                datePickup.getStyleClass().add("ispravnoPolje");
                dateReturn.getStyleClass().removeAll("neispravnoPolje");
                dateReturn.getStyleClass().add("ispravnoPolje");
            } else {
                dateOk=false;
                datePickup.getStyleClass().removeAll("ispravnoPolje");
                datePickup.getStyleClass().add("neispravnoPolje");
                dateReturn.getStyleClass().removeAll("ispravnoPolje");
                dateReturn.getStyleClass().add("neispravnoPolje");
                showAlert("Greška", "Datum povratka mora biti nakon datuma rentanja", Alert.AlertType.ERROR);
                return;
            }

        }
    }
    public void backAction(ActionEvent actionEvent){
        Stage stage= (Stage) fldName.getScene().getWindow();
        stage.close();
    }
}
