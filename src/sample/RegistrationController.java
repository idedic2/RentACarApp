package sample;

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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Pattern;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class RegistrationController {
    public TextField fldFirstName;
    public TextField fldLastName;
    public TextField fldEmail;
    public TextField fldUsername;
    public PasswordField fldPassword;
    public PasswordField fldRetypePassword;
    public TextField fldAddress;
    public TextField fldTelephone;
    public Label lblAddress;
    public Label lblTelephone;
    public RentACarDAO rentACarDAO;
    private Client client;
    public RegistrationController(Client client) {
        rentACarDAO = RentACarDAO.getInstance();
        this.client=client;
    }
    private boolean allLetters(String str){
        return  str.chars().allMatch(Character::isLetter);
    }
    private boolean allDigits(String str){
        return  str.chars().allMatch(Character::isDigit);
    }
    @FXML
    public void initialize(){
        if(client!=null){
            fldFirstName.setText(client.getFirstName());
            fldLastName.setText(client.getLastName());
            fldEmail.setText(client.getEmail());
            fldUsername.setText(client.getUsername());
            fldPassword.setText(client.getPassword());
            fldRetypePassword.setText(client.getPassword());
            fldAddress.setText(client.getAddress());
            fldTelephone.setText(client.getTelephone());
        }
        if(client==null){
            lblAddress.setVisible(false);
            lblTelephone.setVisible(false);
            fldAddress.setVisible(false);
            fldTelephone.setVisible(false);
        }
        if(client!=null){
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
        }
        fldFirstName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (!fldFirstName.getText().trim().isEmpty()) {
                    if(fldFirstName.getText().length()<2 || !allLetters(fldFirstName.getText())){
                        fldFirstName.getStyleClass().removeAll("ispravnoPolje");
                        fldFirstName.getStyleClass().add("neispravnoPolje");
                    }
                    else{
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
        fldRetypePassword.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (!fldRetypePassword.getText().trim().isEmpty()) {
                    fldRetypePassword.getStyleClass().removeAll("neispravnoPolje");
                    fldRetypePassword.getStyleClass().add("ispravnoPolje");

                } else {
                    fldRetypePassword.getStyleClass().removeAll("ispravnoPolje");
                    fldRetypePassword.getStyleClass().add("neispravnoPolje");
                }
            }
        });
    }
    private boolean isValidUsername(String str){
        Pattern pattern = Pattern.compile("[A-Za-z0-9_]+");
        return  pattern.matcher(str).matches();
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
    public void registrationConfirmAction(ActionEvent actionEvent) {
        boolean sveOk=true;
        if(fldFirstName.getText().trim().isEmpty()){
            sveOk=false;
            showAlert("Greška", "Unesite ime", Alert.AlertType.ERROR);
            return;
        }
            if(!allLetters(fldFirstName.getText())){
                sveOk=false;
                showAlert("Greška", "Ime mora sadržavati isključivo slova", Alert.AlertType.ERROR);
                return;
            }
            if(fldFirstName.getText().length()<2){
                sveOk=false;
                showAlert("Greška", "Ime mora sadržavati više od 1 slova", Alert.AlertType.ERROR);
                return;
        }

        if(fldLastName.getText().trim().isEmpty()){
            sveOk=false;
            showAlert("Greška", "Unesite prezime", Alert.AlertType.ERROR);
            return;
        }

            if(!allLetters(fldLastName.getText())){
                sveOk=false;
                showAlert("Greška", "Prezime mora sadržavati isključivo slova", Alert.AlertType.ERROR);
                return;
            }
            if(fldLastName.getText().length()<2){
                sveOk=false;
                showAlert("Greška", "Prezime mora sadržavati više od 1 slova", Alert.AlertType.ERROR);
                return;
            }

        if(fldEmail.getText().trim().isEmpty()){
            sveOk=false;
            showAlert("Greška", "Unesite email adresu", Alert.AlertType.ERROR);
            return;
        }

            if(!isValidEmailAddress(fldEmail.getText())){
                sveOk=false;
                showAlert("Greška", "Nevalidna email adresa", Alert.AlertType.ERROR);
                return;
            }

        if(fldUsername.getText().trim().isEmpty()){
            sveOk=false;
            showAlert("Greška", "Unesite korisničko ime", Alert.AlertType.ERROR);
            return;
        }

            if(!isValidUsername(fldUsername.getText())){
                sveOk=false;
                showAlert("Greška", "Korisničko ime mora sadržavati samo brojeve i/ili slova", Alert.AlertType.ERROR);
                return;
            }
            if(fldUsername.getText().length()<2){
                sveOk=false;
                showAlert("Greška", "Korisničko ime mora biti duže od jednog znaka", Alert.AlertType.ERROR);
                return;
            }

        if(fldPassword.getText().trim().isEmpty()){
            sveOk=false;
            showAlert("Greška", "Unesite lozinku", Alert.AlertType.ERROR);
            return;
        }
        if(fldRetypePassword.getText().trim().isEmpty()){
            sveOk=false;
            showAlert("Greška", "Potvrdite lozinku", Alert.AlertType.ERROR);
            return;
        }
        if(!fldPassword.getText().equals(fldRetypePassword.getText())){
            showAlert("Greška", "Lozinke se ne podudaraju", Alert.AlertType.ERROR);
            return;
        }
        if(client!=null){
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
        if(sveOk){
            if(client!=null) {
                if (!fldUsername.getText().equals(client.getUsername())) {
                    if (rentACarDAO.getClientPerUsername(fldUsername.getText()) != null) {
                        showAlert("Greška", "Korisničko ime je zauzeto", Alert.AlertType.ERROR);
                        fldUsername.getStyleClass().removeAll("ispravnoPolje");
                        fldUsername.getStyleClass().add("neispravnoPolje");
                        return;
                    }
                }
                if(client.getFirstName().equals(fldFirstName.getText()) && client.getLastName().equals(fldLastName.getText()) && client.getEmail().equals(fldEmail.getText()) && client.getUsername().equals(fldUsername.getText()) && client.getPassword().equals(fldPassword.getText()) && client.getPassword().equals(fldRetypePassword.getText()) && client.getAddress().equals(fldAddress.getText()) && client.getTelephone().equals(fldTelephone.getText())){
                    try {
                        Stage stage = (Stage) fldFirstName.getScene().getWindow();
                        stage.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("");
                alert.setHeaderText("Odaberite opciju");
                alert.setContentText("Jeste li sigurni da želite izmijeniti Vaše podatke");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    client.setFirstName(fldFirstName.getText());
                    client.setLastName(fldLastName.getText());
                    client.setAddress(fldAddress.getText());
                    client.setUsername(fldUsername.getText());
                    client.setPassword(fldPassword.getText());
                    client.setAddress(fldAddress.getText());
                    client.setTelephone(fldTelephone.getText());
                    rentACarDAO.editClient(client);
                    try {
                        Stage stage = (Stage) fldFirstName.getScene().getWindow();
                        stage.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }
            }
            //treba provjeriti postoji li korisnik u bazi, ako ne dodat ga
            //pitat korisnika zeli li nastaviti i ako potvrdi onda slj prozor
            if(rentACarDAO.getClientPerUsername(fldUsername.getText())!=null){
                showAlert("Greška", "Korisničko ime je zauzeto", Alert.AlertType.ERROR);
                fldUsername.getStyleClass().removeAll("ispravnoPolje");
                fldUsername.getStyleClass().add("neispravnoPolje");
                return;
            }
                Client client=new Client(0, fldFirstName.getText(), fldLastName.getText(), fldEmail.getText(), fldUsername.getText(), fldPassword.getText(), "", "");
                rentACarDAO.addClient(client);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("");
                alert.setHeaderText("Odaberite opciju");
                alert.setContentText("Potvrdite za nastavak");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    // ... user chose OK
                    Parent root = null;
                    try {
                        Stage stage = (Stage) fldFirstName.getScene().getWindow();
                        stage.close();
                        Stage primaryStage = new Stage();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/clientPage.fxml"));
                        ClientPageController clientPageController = new ClientPageController(client.getUsername());
                        loader.setController(clientPageController);
                        root = loader.load();
                        primaryStage.setTitle("Client File");
                        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                        primaryStage.initModality(Modality.APPLICATION_MODAL);
                        primaryStage.setResizable(false);
                        primaryStage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

    }
}
