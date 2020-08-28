package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AboutController {
    public Button buttonOk;
    public ImageView imageViewAbout;

    @FXML
    public void initialize() {
        imageViewAbout.setImage(new Image("/images/about.png"));
    }

    public void buttonOkClick(ActionEvent actionEvent) {
        Stage stage = (Stage) buttonOk.getScene().getWindow();
        stage.close();
    }
}
