package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class StartController {


    public void LoginAction(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        stage.setTitle("Loguj se");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
    }

    public void RegistracijaAction(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/registration.fxml"));
        stage.setTitle("Registruj se");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
    }
}
