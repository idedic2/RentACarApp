package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.regex.Pattern;

public class AddClientController {
    public TextField fldFirstName;
    public TextField fldLastName;
    public TextField fldEmail;
    public TextField fldUsername;
    public PasswordField fldPassword;
    public TextField fldAddress;
    public TextField fldTelephone;
    private Client client;
    private RentACarDAO rentACarDAO;
    private boolean dodavanje=false;
    private int changeUsername=0;
    private boolean sameUsername=false;
    public AddClientController(Client client, boolean dodavanje){
        this.dodavanje=dodavanje;
        this.client=client;
        rentACarDAO=RentACarDAO.getInstance();
    }
    private boolean allLetters(String str){
        return  str.chars().allMatch(Character::isLetter);
    }
    public Client getClient(){
        return client;
    }
    @FXML
    public void initialize() {
        if (client != null) {
            fldFirstName.setText(client.getFirstName());
            fldLastName.setText(client.getLastName());
            fldEmail.setText(client.getEmail());
            fldUsername.setText(client.getUsername());
            fldPassword.setText(client.getPassword());
            fldAddress.setText(client.getAddress());
            fldTelephone.setText(client.getTelephone());
        }
        fldFirstName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (!fldFirstName.getText().trim().isEmpty()) {
                    if (fldFirstName.getText().length() < 2 || !allLetters(fldFirstName.getText())) {
                        fldFirstName.getStyleClass().removeAll("ispravnoPolje");
                        fldFirstName.getStyleClass().add("neispravnoPolje");
                    } else {
                        fldFirstName.getStyleClass().removeAll("neispravnoPolje");
                        fldFirstName.getStyleClass().add("ispravnoPolje");
                    }
                } else {
                    fldFirstName.getStyleClass().removeAll("ispravnoPolje");
                    fldFirstName.getStyleClass().add("neispravnoPolje");
                }
            }
        });
        fldLastName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (!fldLastName.getText().trim().isEmpty()) {
                    if(fldLastName.getText().length()<2 || !allLetters(fldLastName.getText())){
                        fldLastName.getStyleClass().removeAll("ispravnoPolje");
                        fldLastName.getStyleClass().add("neispravnoPolje");
                    }
                    else{
                        fldLastName.getStyleClass().removeAll("neispravnoPolje");
                        fldLastName.getStyleClass().add("ispravnoPolje");
                    }
                } else {
                    fldLastName.getStyleClass().removeAll("ispravnoPolje");
                    fldLastName.getStyleClass().add("neispravnoPolje");
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
        fldUsername.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
               changeUsername++;
                if (!fldUsername.getText().trim().isEmpty()) {
                    if(!isValidUsername(fldUsername.getText())){
                        fldUsername.getStyleClass().removeAll("ispravnoPolje");
                        fldUsername.getStyleClass().add("neispravnoPolje");
                    }
                    else{
                        fldUsername.getStyleClass().removeAll("neispravnoPolje");
                        fldUsername.getStyleClass().add("ispravnoPolje");
                    }
                } else {
                    fldUsername.getStyleClass().removeAll("ispravnoPolje");
                    fldUsername.getStyleClass().add("neispravnoPolje");
                }
            }
        });
        fldPassword.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (!fldPassword.getText().trim().isEmpty()) {
                    fldPassword.getStyleClass().removeAll("neispravnoPolje");
                    fldPassword.getStyleClass().add("ispravnoPolje");

                } else {
                    fldPassword.getStyleClass().removeAll("ispravnoPolje");
                    fldPassword.getStyleClass().add("neispravnoPolje");
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
    private boolean isValidUsername(String str){
        Pattern pattern = Pattern.compile("[A-Za-z0-9_]+");
        return  pattern.matcher(str).matches();
    }
    private boolean allDigits(String str){
        return  str.chars().allMatch(Character::isDigit);
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    private void showAlert(String title, String headerText, Alert.AlertType type) {
        Alert error = new Alert(type);
        error.setTitle(title);
        error.setHeaderText(headerText);
        error.show();
    }
    public void confirmAddClientAction(ActionEvent actionEvent) {
        if(fldFirstName.getText().trim().isEmpty()){
            showAlert("Greška", "Unesite ime", Alert.AlertType.ERROR);
            return;
        }
        else{
            if(!allLetters(fldFirstName.getText())){
                showAlert("Greška", "Ime mora sadržavati isključivo slova", Alert.AlertType.ERROR);
                return;
            }
            if(fldFirstName.getText().length()<2){
                showAlert("Greška", "Ime mora sadržavati više od 1 slova", Alert.AlertType.ERROR);
                return;
            }
        }
        if(fldLastName.getText().trim().isEmpty()){
            showAlert("Greška", "Unesite prezime", Alert.AlertType.ERROR);
            return;
        }
        else{
            if(!allLetters(fldLastName.getText())){
                showAlert("Greška", "Prezime mora sadržavati isključivo slova", Alert.AlertType.ERROR);
                return;
            }
            if(fldLastName.getText().length()<2){
                showAlert("Greška", "Prezime mora sadržavati više od 1 slova", Alert.AlertType.ERROR);
                return;
            }
        }
        if(fldEmail.getText().trim().isEmpty()){
            showAlert("Greška", "Unesite email adresu", Alert.AlertType.ERROR);
            return;
        }
        else{
            if(!isValidEmailAddress(fldEmail.getText())){
                showAlert("Greška", "Nevalidna email adresa", Alert.AlertType.ERROR);
                return;
            }
        }
        if(fldUsername.getText().trim().isEmpty()){
            showAlert("Greška", "Unesite korisničko ime", Alert.AlertType.ERROR);
            return;
        }
        else{
            if(!isValidUsername(fldUsername.getText())){
                showAlert("Greška", "Korisničko ime mora sadržavati samo brojeve i/ili slova", Alert.AlertType.ERROR);
                return;
            }
            if(fldUsername.getText().length()<2){
                showAlert("Greška", "Korisničko ime mora biti duže od jednog znaka", Alert.AlertType.ERROR);
                return;
            }
        }
        if(fldPassword.getText().trim().isEmpty()){
            showAlert("Greška", "Unesite lozinku", Alert.AlertType.ERROR);
            return;
        }
        if(dodavanje==false && client.getAddress().equals("") && fldAddress.getText().isEmpty()){
            boolean validate=false;
        }
        else {
            if (fldAddress.getText().trim().isEmpty()) {
                showAlert("Greška", "Unesite adresu", Alert.AlertType.ERROR);
                return;
            }
            if (fldTelephone.getText().trim().isEmpty()) {
                showAlert("Greška", "Unesite kontakt telefon", Alert.AlertType.ERROR);
                return;
            } else {
                if (!(allDigits(fldTelephone.getText()) && fldTelephone.getText().length() >= 9)) {
                    showAlert("Greška", "Nevalidan broj telefona", Alert.AlertType.ERROR);
                    return;
                }
            }
        }
        if(!fldUsername.getText().equals(client.getUsername())) {
            if (rentACarDAO.getClientPerUsername(fldUsername.getText()) != null) {
                showAlert("Greška", "Korisničko ime je zauzeto", Alert.AlertType.ERROR);
                fldUsername.getStyleClass().removeAll("ispravnoPolje");
                fldUsername.getStyleClass().add("neispravnoPolje");
                return;
            }
        }
        if (client == null) client = new Client();
        client.setFirstName(fldFirstName.getText());
        client.setLastName(fldLastName.getText());
        client.setEmail(fldEmail.getText());
        client.setUsername(fldUsername.getText());
        client.setPassword(fldPassword.getText());
        client.setAddress(fldAddress.getText());
        client.setTelephone(fldTelephone.getText());
        Stage stage = (Stage) fldFirstName.getScene().getWindow();
        stage.close();
    }

    public void cancelAddClientAction(ActionEvent actionEvent) {
        client=null;
        Stage stage= (Stage) fldFirstName.getScene().getWindow();
        stage.close();
    }
}
