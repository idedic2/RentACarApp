package sample;

import javafx.application.Platform;
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
import org.json.JSONObject;

import javax.swing.plaf.synth.SynthDesktopIconUI;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import static java.lang.Thread.currentThread;
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
    private Employee employee;
    private User user;
    public Label lblText;
    private String username;
    public boolean sveOk=true;
    private String userType;
    public RegistrationController(User user, String username, String userType) {
        rentACarDAO = RentACarDAO.getInstance();
        if(user instanceof Client){
            this.client= (Client) user;
            this.employee=null;
            System.out.println(client.getFirstName());
        }
        else if(user instanceof  Employee){
            this.employee= (Employee) user;
            this.client=null;
        }
        else{
            client=null;
            employee=null;
        }
        this.username=username;
        this.userType=userType;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Client getClient() {
        return client;
    }

    private boolean allLetters(String str) {
        return str.chars().allMatch(Character::isLetter);
    }

    private boolean allDigits(String str) {
        return str.chars().allMatch(Character::isDigit);
    }
    private static boolean change=false;
    private static String initMail="";

    @FXML
    public void initialize() {
            change=false;
            new Thread(() -> {
                //fldEmail.textProperty().addListener(new ChangeListener<String>() {
                  //  @Override
                    //public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                        while (true) {
                                if(initMail.equals(fldEmail.getText())) {
                                    change=false;
                                } else {
                                    change=true;
                                }
                                //nije prazno
                                if(change) {
                                    if (!fldEmail.getText().trim().isEmpty()) {
                                        //validno preko metode
                                        if (isValidEmailAddress(fldEmail.getText())) {
                                            try {
                                                if (!validateEmail(fldEmail.getText())) {
                                                    sveOk = false;
                                                    fldEmail.getStyleClass().removeAll("ispravnoPolje");
                                                    fldEmail.getStyleClass().add("neispravnoPolje");
                                                } else {
                                                    sveOk = true;
                                                    fldEmail.getStyleClass().removeAll("neispravnoPolje");
                                                    fldEmail.getStyleClass().add("ispravnoPolje");
                                                }
                                            } catch (UnsupportedEncodingException e) {
                                                e.printStackTrace();
                                            } catch (MalformedURLException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            sveOk = false;
                                            fldEmail.getStyleClass().removeAll("ispravnoPolje");
                                            fldEmail.getStyleClass().add("neispravnoPolje");
                                        }

                                    } else {
                                        sveOk = false;
                                        fldEmail.getStyleClass().removeAll("ispravnoPolje");
                                        fldEmail.getStyleClass().add("neispravnoPolje");
                                    }
                                }
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                    //}});
            }).start();

        /*
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(lblAddress.setText(lblAddress.getText() + Integer.toString(counter)));
                }
            }
        });
         */
        //editovanje postojeceg usera
            //user je klijent
            if (client!=null) {
                if (!username.equals(""))
                    lblText.setText("Podaci o klijentu");
                else lblText.setText("Vaši podaci");
                fldFirstName.setText(client.getFirstName());
                fldLastName.setText(client.getLastName());
                fldEmail.setText(client.getEmail());
                initMail = fldEmail.getText();
                fldUsername.setText(client.getUsername());
                fldPassword.setText(client.getPassword());
                fldRetypePassword.setText(client.getPassword());
                fldAddress.setText(client.getAddress());
                fldTelephone.setText(client.getTelephone());
            }
            else if(employee!=null){
                if (!username.equals(""))
                lblText.setText("Podaci o zaposleniku");
                else lblText.setText("Vaši podaci");
                fldFirstName.setText(employee.getFirstName());
                fldLastName.setText(employee.getLastName());
                fldEmail.setText(employee.getEmail());
                initMail = fldEmail.getText();
                fldUsername.setText(employee.getUsername());
                fldPassword.setText(employee.getPassword());
                fldRetypePassword.setText(employee.getPassword());
                lblAddress.setVisible(false);
                fldAddress.setVisible(false);
                lblTelephone.setVisible(false);
                fldTelephone.setVisible(false);
            }

        else {
            initMail = fldEmail.getText();
            //klijent se sam dodaje
            if (username.equals("") && userType.equals("client")) {
                lblText.setText("Kreirajte Vaš račun");
                //ovdje je uvijek prazno polje
                //initMail = fldEmail.getText();
            }
            if(!username.equals("") && userType.equals("client")){
                lblText.setText("Dodajte novog klijenta");
                //ovdje je uvijek prazno polje
                //initMail = fldEmail.getText();
            }
            if(!username.equals("") && userType.equals("employee")){
                lblText.setText("Dodajte novog zaposlenika");
                //ovdje je uvijek prazno polje
                //initMail = fldEmail.getText();
                lblAddress.setVisible(false);
                fldAddress.setVisible(false);
                lblTelephone.setVisible(false);
                fldTelephone.setVisible(false);
            }
        }
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
                    if (fldLastName.getText().length() < 2 || !allLetters(fldLastName.getText())) {
                        fldLastName.getStyleClass().removeAll("ispravnoPolje");
                        fldLastName.getStyleClass().add("neispravnoPolje");
                    } else {
                        fldLastName.getStyleClass().removeAll("neispravnoPolje");
                        fldLastName.getStyleClass().add("ispravnoPolje");
                    }
                } else {
                    fldLastName.getStyleClass().removeAll("ispravnoPolje");
                    fldLastName.getStyleClass().add("neispravnoPolje");
                }
            }
        });

        fldUsername.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (!fldUsername.getText().trim().isEmpty()) {
                    if (!isValidUsername(fldUsername.getText())) {
                        fldUsername.getStyleClass().removeAll("ispravnoPolje");
                        fldUsername.getStyleClass().add("neispravnoPolje");
                    } else {
                        fldUsername.getStyleClass().removeAll("neispravnoPolje");
                        fldUsername.getStyleClass().add("ispravnoPolje");
                    }
                } else {
                    fldUsername.getStyleClass().removeAll("ispravnoPolje");
                    fldUsername.getStyleClass().add("neispravnoPolje");
                }
            }
        });
        /*fldEmail.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (fldEmail.getText().trim().isEmpty()) {
                    fldEmail.getStyleClass().removeAll("ispravnoPolje");
                    fldEmail.getStyleClass().add("neispravnoPolje");

                }
            }
        });*/
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

    private boolean isValidUsername(String str) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9_]+");
        return pattern.matcher(str).matches();
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
    public  boolean validateEmail(String email) throws UnsupportedEncodingException, MalformedURLException {

        boolean ret=true;
        try {
            String key = "QUJSTVVM1367C57HM0A4";
            Hashtable<String, String> data = new Hashtable<String, String>();
            data.put("format", "json");
            data.put("email", email);

            String datastr = "";
            for (Map.Entry<String,String> entry : data.entrySet()) {
                datastr += "&" + entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8");
            }
            URL url = new URL("https://api.mailboxvalidator.com/v1/validation/single?key=" + key + datastr);

            BufferedReader ulaz = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));

            String json = "", line = null;
            while ((line = ulaz.readLine()) != null)
                json = json + line;
            JSONObject obj = new JSONObject(json);

            if(obj.getString("is_syntax").equals("False")){
                System.out.println(obj.getString("is_syntax"));
                ret=false;
            }
            ulaz.close();
        } catch(MalformedURLException e) {
            System.out.println("String "+" ne predstavlja validan URL");
        } catch(IOException e) {
            System.out.println("Greška pri kreiranju ulaznog toka");
            System.out.println(e.getMessage());
        } catch(Exception e) {
            System.out.println("Poteškoće sa obradom JSON podataka");
            System.out.println(e.getMessage());
        }
        return ret;
    }
    public void registrationConfirmAction(ActionEvent actionEvent) throws UnsupportedEncodingException, MalformedURLException, InterruptedException {
        //boolean sveOk = true;
        if (fldFirstName.getText().trim().isEmpty()) {
            //sveOk = false;
            showAlert("Greška", "Unesite ime", Alert.AlertType.ERROR);
            return;
        }
        if (!allLetters(fldFirstName.getText())) {
            //sveOk = false;
            showAlert("Greška", "Ime mora sadržavati isključivo slova", Alert.AlertType.ERROR);
            return;
        }
        if (fldFirstName.getText().length() < 2) {
            //sveOk = false;
            showAlert("Greška", "Ime mora sadržavati više od 1 slova", Alert.AlertType.ERROR);
            return;
        }

        if (fldLastName.getText().trim().isEmpty()) {
            //sveOk = false;
            showAlert("Greška", "Unesite prezime", Alert.AlertType.ERROR);
            return;
        }

        if (!allLetters(fldLastName.getText())) {
            //sveOk = false;
            showAlert("Greška", "Prezime mora sadržavati isključivo slova", Alert.AlertType.ERROR);
            return;
        }
        if (fldLastName.getText().length() < 2) {
            //sveOk = false;
            showAlert("Greška", "Prezime mora sadržavati više od 1 slova", Alert.AlertType.ERROR);
            return;
        }

        if (fldEmail.getText().trim().isEmpty()) {
            //sveOk = false;
            showAlert("Greška", "Unesite email adresu", Alert.AlertType.ERROR);
            return;
        }
        //currentThread().sleep(1000);
        //System.out.println(currentThread().getName());
        //currentThread().wait();
        if(!sveOk){
            showAlert("Greška", "Nevalidna email adresa", Alert.AlertType.ERROR);
            return;
        }

        /*if (!validateEmail(fldEmail.getText())) {
            sveOk = false;
            showAlert("Greška", "Nevalidna email adresa", Alert.AlertType.ERROR);
            return;
        }*/
       /*if (!isValidEmailAddress(fldEmail.getText())) {
            sveOk = false;
            showAlert("Greška", "Nevalidna email adresa", Alert.AlertType.ERROR);
            return;
        }*/
        if (fldUsername.getText().trim().isEmpty()) {
            //sveOk = false;
            showAlert("Greška", "Unesite korisničko ime", Alert.AlertType.ERROR);
            return;
        }

        if (!isValidUsername(fldUsername.getText())) {
            //sveOk = false;
            showAlert("Greška", "Korisničko ime mora sadržavati samo brojeve i/ili slova", Alert.AlertType.ERROR);
            return;
        }
        if (fldUsername.getText().length() < 2) {
            //sveOk = false;
            showAlert("Greška", "Korisničko ime mora biti duže od jednog znaka", Alert.AlertType.ERROR);
            return;
        }


        if (fldPassword.getText().trim().isEmpty()) {
            //sveOk = false;
            showAlert("Greška", "Unesite lozinku", Alert.AlertType.ERROR);
            return;
        }
        if (fldRetypePassword.getText().trim().isEmpty()) {
            //sveOk = false;
            showAlert("Greška", "Potvrdite lozinku", Alert.AlertType.ERROR);
            return;
        }
        if (!fldPassword.getText().equals(fldRetypePassword.getText())) {
            showAlert("Greška", "Lozinke se ne podudaraju", Alert.AlertType.ERROR);
            return;
        }
        if(!userType.equals("employee")) {
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
        if (this.sveOk) {
            //editovanje
if(client!=null || employee!=null) {
    if (client != null) {
        if (!fldUsername.getText().equals(client.getUsername())) {
            if (rentACarDAO.getUserPerUsername(fldUsername.getText())) {
                showAlert("Greška", "Korisničko ime je zauzeto", Alert.AlertType.ERROR);
                fldUsername.getStyleClass().removeAll("ispravnoPolje");
                fldUsername.getStyleClass().add("neispravnoPolje");
                return;
            }
        }
        if (client.getFirstName().equals(fldFirstName.getText()) && client.getLastName().equals(fldLastName.getText()) && client.getEmail().equals(fldEmail.getText()) && client.getUsername().equals(fldUsername.getText()) && client.getPassword().equals(fldPassword.getText()) && client.getPassword().equals(fldRetypePassword.getText()) && client.getAddress().equals(fldAddress.getText()) && client.getTelephone().equals(fldTelephone.getText())) {
            try {
                Stage stage = (Stage) fldFirstName.getScene().getWindow();
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
    }if (employee != null) {
        if (!fldUsername.getText().equals(employee.getUsername())) {
            if (rentACarDAO.getUserPerUsername(fldUsername.getText())) {
                showAlert("Greška", "Korisničko ime je zauzeto", Alert.AlertType.ERROR);
                fldUsername.getStyleClass().removeAll("ispravnoPolje");
                fldUsername.getStyleClass().add("neispravnoPolje");
                return;
            }
        }
            if (employee.getFirstName().equals(fldFirstName.getText()) && employee.getLastName().equals(fldLastName.getText()) && employee.getEmail().equals(fldEmail.getText()) && employee.getUsername().equals(fldUsername.getText()) && employee.getPassword().equals(fldPassword.getText()) && employee.getPassword().equals(fldRetypePassword.getText())) {
                try {
                    Stage stage = (Stage) fldFirstName.getScene().getWindow();
                    stage.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
    }

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("");
    alert.setHeaderText("Odaberite opciju");
    if (!username.equals("")) {
        if (userType.equals("client"))
            alert.setContentText("Jeste li sigurni da želite izmijeniti podatke o klijentu");
        else if (userType.equals("employee"))
            alert.setContentText("Jeste li sigurni da želite izmijeniti podatke o zaposleniku");
    } else alert.setContentText("Jeste li sigurni da želite izmijeniti Vaše podatke");
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
        if (userType.equals("client")) {
            client.setFirstName(fldFirstName.getText());
            client.setLastName(fldLastName.getText());
            client.setEmail(fldEmail.getText());
            client.setUsername(fldUsername.getText());
            client.setPassword(fldPassword.getText());
            client.setAddress(fldAddress.getText());
            client.setTelephone(fldTelephone.getText());
            rentACarDAO.editClient(client);
        } else {
            employee.setFirstName(fldFirstName.getText());
            employee.setLastName(fldLastName.getText());
            employee.setEmail(fldEmail.getText());
            employee.setUsername(fldUsername.getText());
            employee.setPassword(fldPassword.getText());
            rentACarDAO.editEmployee(employee);
        }
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
            if (rentACarDAO.getUserPerUsername(fldUsername.getText())) {
                showAlert("Greška", "Korisničko ime je zauzeto", Alert.AlertType.ERROR);
                fldUsername.getStyleClass().removeAll("ispravnoPolje");
                fldUsername.getStyleClass().add("neispravnoPolje");
                return;
            }
            //Client client=null;
            try {
                if (userType.equals("client")) {
                    client = new Client(0, fldFirstName.getText(), fldLastName.getText(), fldEmail.getText(), fldUsername.getText(), fldPassword.getText(), fldAddress.getText(), fldTelephone.getText());
                    rentACarDAO.addClient(client);

                } else {
                    employee = new Employee(0, fldFirstName.getText(), fldLastName.getText(), fldEmail.getText(), fldUsername.getText(), fldPassword.getText(), "no");
                    rentACarDAO.addEmployee(employee);
                    Employee getEmployee = rentACarDAO.getEmployeePerUsername(employee.getUsername());
                    if (getEmployee.getUsername().equals(employee.getUsername())) {
                        Stage stage = (Stage) fldUsername.getScene().getWindow();
                        stage.close();
                        showAlert("Uspješna registracija", "Uspješno registrovan novi zaposlenik", Alert.AlertType.INFORMATION);
                        return;

                        //showAlert("Uspješna registracija", "Dobrodošli "+getClient.getUsername(), Alert.AlertType.INFORMATION);
                    } else {
                        showAlert("Greška", "Neuspješno dodavanje novog zaposlenika!", Alert.AlertType.ERROR);
                        return;
                    }
                }
            } catch (NegativeNumberException e) {
                e.printStackTrace();
            }
            Client getClient = rentACarDAO.getClientPerUsername(client.getUsername());
            if (getClient.getUsername().equals(client.getUsername())) {
                if (!username.equals("")) {
                    Stage stage = (Stage) fldUsername.getScene().getWindow();
                    stage.close();
                    showAlert("Uspješna registracija", "Uspješno registrovan novi klijent", Alert.AlertType.INFORMATION);
                    return;
                }
                //showAlert("Uspješna registracija", "Dobrodošli "+getClient.getUsername(), Alert.AlertType.INFORMATION);
            } else {
                showAlert("Greška", "Neuspješna registracija!", Alert.AlertType.ERROR);
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("");
            alert.setHeaderText("Odaberite opciju");
            alert.setContentText("Potvrdite za nastavak");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
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



    public void backRegistrationAction(ActionEvent actionEvent) {
        Stage stage = (Stage) fldUsername.getScene().getWindow();
        stage.close();
        /*if(!username.equals("") ){
            return;
        }*/
        if (client == null && userType.equals("client") && username.equals("")) {
            Stage oldstage = new Stage();
            Parent root = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/start.fxml"));
                StartController startController = new StartController();
                loader.setController(startController);
                root = loader.load();
                oldstage.setTitle("Dobrodošli");
                oldstage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                oldstage.setResizable(false);
                oldstage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}