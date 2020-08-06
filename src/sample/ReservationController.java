package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    public DatePicker dateExpire;
    public Label lblCode;
    public TextField fldCode;
    public Label lblCardData;
    public Label lblTotalPrice;
    private Vehicle vehicle;
    private User user;
    private RentACarDAO rentACarDAO;

    public ReservationController(Vehicle vehicle, User user) {
        this.vehicle=vehicle;
        this.user=user;
        rentACarDAO=RentACarDAO.getInstance();
    }
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    private boolean allDigits(String str){
        return  str.chars().allMatch(Character::isDigit);
    }
    private boolean isReturnAfterPickup(){
        if(datePickup.getValue().isAfter(dateReturn.getValue()))
            return false;
        return true;
    }
    @FXML
    public void initialize(){
        fldName.setText(user.getFirstName()+" "+user.getLastName());
        fldEmail.setText(user.getEmail());
        datePickup.getStyleClass().add("neispravnoPolje");
        dateReturn.getStyleClass().add("neispravnoPolje");
        lblNmbCard.setDisable(true);
        fldNmbCard.setDisable(true);
        lblExpireDate.setDisable(true);
        dateExpire.setDisable(true);
        lblCode.setDisable(true);
        fldCode.setDisable(true);

        datePickup.getEditor().textProperty().addListener(new ChangeListener<String>() {
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
        fldName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String n) {
                if (n.trim().isEmpty()) {
                    fldName.getStyleClass().removeAll("ispravnoPolje");
                    fldName.getStyleClass().add("neispravnoPolje");
                    //returnDateValidate = true;
                } else {
                    fldName.getStyleClass().removeAll("neispravnoPolje");
                    fldName.getStyleClass().add("ispravnoPolje");
                    //returnDateValidate = false;
                }
            }
        });
        fldEmail.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (!fldEmail.getText().trim().isEmpty()) {
                    if(!isValidEmailAddress(fldEmail.getText())){
                        fldEmail.getStyleClass().removeAll("ispravnoPolje");
                        fldEmail.getStyleClass().add("neispravnoPolje");
                    }
                    else{
                        fldEmail.getStyleClass().removeAll("neispravnoPolje");
                        fldEmail.getStyleClass().add("ispravnoPolje");
                    }
                } else {
                    fldEmail.getStyleClass().removeAll("ispravnoPolje");
                    fldEmail.getStyleClass().add("neispravnoPolje");
                }
            }
        });
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
    }
    public void changePayingAction(ActionEvent actionEvent){
        if(checkBoxNow.isSelected()){
            lblNmbCard.setDisable(false);
            fldNmbCard.setDisable(false);
            lblExpireDate.setDisable(false);
            dateExpire.setDisable(false);
            lblCode.setDisable(false);
            fldCode.setDisable(false);
        }
        else{
            lblNmbCard.setDisable(true);
            fldNmbCard.setDisable(true);
            lblExpireDate.setDisable(true);
            dateExpire.setDisable(true);
            lblCode.setDisable(true);
            fldCode.setDisable(true);
        }
    }
    public void reservationConfirmAction(ActionEvent actionEvent) {

    }
    public void backAction(ActionEvent actionEvent){
        Stage stage= (Stage) fldName.getScene().getWindow();
        stage.close();
    }
}
