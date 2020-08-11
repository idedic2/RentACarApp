package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class StartController {

    public Button btnLogin;

    public void loginAction(ActionEvent actionEvent) {
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        Parent root = null;
        try {
            stage.close();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            root = loader.load();
            primaryStage.setTitle("Logujte se");
            primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registrationAction(ActionEvent actionEvent) {
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        Parent root = null;
        try {
            stage.close();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/registration.fxml"));
            RegistrationController registrationController = new RegistrationController(null);
            loader.setController(registrationController);
            root = loader.load();
            primaryStage.setTitle("Registrujte se");
            primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            primaryStage.setResizable(true);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
