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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
    public ObservableList<User> listUsers;
    public RentACarDAO rentACarDAO;

    public RegistrationController() {
        rentACarDAO = RentACarDAO.getInstance();
        listUsers= FXCollections.observableArrayList(rentACarDAO.getUsers());
    }
    private boolean allLetters(String str){
        return  str.chars().allMatch(Character::isLetter);
    }
    private boolean doesExistUsername(String username){
        for(User user: listUsers){
            if(user.getUsername().equals(username))
                return true;
        }
        return false;
    }
    @FXML
    public void initialize(){
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
        else{
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
        }

        if(fldLastName.getText().trim().isEmpty()){
            sveOk=false;
            showAlert("Greška", "Unesite prezime", Alert.AlertType.ERROR);
            return;
        }
        else{
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
        }

        if(fldEmail.getText().trim().isEmpty()){
            sveOk=false;
            showAlert("Greška", "Unesite email adresu", Alert.AlertType.ERROR);
            return;
        }
        else{
            if(!isValidEmailAddress(fldEmail.getText())){
                sveOk=false;
                showAlert("Greška", "Nevalidna email adresa", Alert.AlertType.ERROR);
                return;
            }
        }
        if(fldUsername.getText().trim().isEmpty()){
            sveOk=false;
            showAlert("Greška", "Unesite korisničko ime", Alert.AlertType.ERROR);
            return;
        }
        else{
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
        }
        if(fldPassword.getText().trim().isEmpty()){
            sveOk=false;
            showAlert("Greška", "Unesite lozinku", Alert.AlertType.ERROR);
            return;
        }
        else{
            if(!isValidUsername(fldUsername.getText())){
                sveOk=false;
                showAlert("Greška", "Korisničko ime mora sadržavati samo brojeve i/ili slova", Alert.AlertType.ERROR);
                return;
            }
        }
        if(sveOk){
            //treba provjeriti postoji li korisnik u bazi, ako ne dodat ga
            //pitat korisnika zeli li nastaviti i ako potvrdi onda slj prozor
            if(doesExistUsername(fldUsername.getText())){
                showAlert("Greška", "Korisničko ime je zauzeto", Alert.AlertType.ERROR);
                fldUsername.getStyleClass().removeAll("ispravnoPolje");
                fldUsername.getStyleClass().add("neispravnoPolje");
                return;
            }
            else {
                User user=new User(0, fldFirstName.getText(), fldLastName.getText(), fldEmail.getText(), fldUsername.getText(), fldPassword.getText());
                rentACarDAO.addUser(user);
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
                        //ClientFileController controller = new ClientFileController(newPerson);
                        //loader.setController(controller);
                        root = loader.load();
                        primaryStage.setTitle("Client File");
                        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                        primaryStage.initModality(Modality.APPLICATION_MODAL);
                        primaryStage.setResizable(false);
                        primaryStage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // ... user chose CANCEL or closed the dialog
                }
            }

        }
    }
}
