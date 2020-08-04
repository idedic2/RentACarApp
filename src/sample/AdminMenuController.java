package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class AdminMenuController {
    public ChoiceBox choiceAction;
    private Stage getNewStage(String stageName) {
        try {
            FXMLLoader loader;
            Stage mainStage = new Stage();
            switch (stageName) {
                case "Evidencija vozila":
                    loader = new FXMLLoader(getClass().getResource("/fxml/adminPage.fxml"));
                    mainStage.getIcons().add(new Image("/images/admin.png"));
                    //AdminMenuController controller = new AdminMenuController();
                    //loader.setController(controller);
                    //mainStage.setOnHidden(event -> writeAdminView(controller.getTabsConfig()));
                    break;
                case "Upravljanje rezervacijama":
                    loader = new FXMLLoader(getClass().getResource("/fxml/reservationEvidention.fxml"));
                    mainStage.getIcons().add(new Image("/images/admin.png"));
                    //ClientPageController clientController = new ClientPageController();
                    //loader.setController(clientController);
                    //mainStage.setOnHidden(event -> writeStudentView(studentController.getTabConfig()));
                    break;
                default:
                    return null;
            }
            Parent root = loader.load();
            mainStage.setTitle(stageName);
            mainStage.setResizable(false);
            mainStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            return mainStage;
        } catch (IOException ignored) {
            return null;
        }
    }
    public void choiceConfirmAction(ActionEvent actionEvent) {
        Stage mainStage = getNewStage(choiceAction.getSelectionModel().getSelectedItem().toString());
        if (mainStage == null)
            return;
        Stage currentStage = (Stage) choiceAction.getScene().getWindow();
        currentStage.close();
        mainStage.show();
    }
}
