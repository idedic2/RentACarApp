package sample;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.File;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class EmployeePageControllerTest {
    Stage theStage;
    EmployeePageController ctrl;

    @Start
    public void start (Stage stage) throws Exception {
        RentACarDAO.removeInstance();
        File dbfile = new File("rentacar.db");
        dbfile.delete();
        RentACarDAO dao = RentACarDAO.getInstance();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/employeePage.fxml"));
        ctrl = new EmployeePageController("idedic2", "yes");
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Employee");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        theStage = stage;
    }
    @Test
    void addClientAction(FxRobot robot) {
        robot.clickOn("#tabClients");
        robot.clickOn("#btnAddClient");
        robot.lookup("#lblRegistrationText").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblRegistrationText").queryAs(Label.class);
        assertNotNull(lbl);
        assertEquals("Dodajte novog klijenta", lbl.getText());
        robot.clickOn("#fldFirstName");
        robot.write("Klijent");
        robot.clickOn("#fldLastName");
        robot.write("Klijentijević");
        robot.clickOn("#fldEmail");
        robot.write("klijent@gmail.com");
        robot.clickOn("#fldUsername");
        robot.write("klijent123");
        robot.clickOn("#fldPassword");
        robot.write("password");
        robot.clickOn("#fldRetypePassword");
        robot.write("password");
        robot.clickOn("#fldAddress");
        robot.write("Klijentova adresa");
        robot.clickOn("#fldTelephone");
        robot.write("062333457");
        robot.clickOn("#btnRegistrationConfirm");
        robot.clickOn("OK");
        RentACarDAO dao = RentACarDAO.getInstance();
        TableView tableView = robot.lookup("#tableViewClients").queryAs(TableView.class);
        assertEquals(dao.getClients().size(), tableView.getItems().size());
    }

    @Test
    void editClientAction() {
    }

    @Test
    void deleteClientAction(FxRobot robot) {
        robot.clickOn("#tabClients");
        TableView tableView = robot.lookup("#tableViewClients").queryAs(TableView.class);
        robot.clickOn("Klijent");
        robot.clickOn("#btnDeleteClient");
        robot.clickOn("OK");
        RentACarDAO dao = RentACarDAO.getInstance();
        assertEquals(dao.getClients().size(), tableView.getItems().size());
        assertEquals(0, tableView.getItems().size());
    }

    @Test
    void aboutAction(FxRobot robot) {
        robot.clickOn("#menuHelp");
        robot.clickOn("#menuItemAbout");
        robot.lookup("#lblAboutApplication").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblAboutApplication").queryAs(Label.class);
        assertNotNull(lbl);
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }

    @Test
    void logOutAction(FxRobot robot) {
        robot.clickOn("#profil");
        robot.clickOn("#btnLogOut");
        assertFalse(theStage.isShowing());
        robot.lookup("#rent").tryQuery().isPresent();
        Label lbl=robot.lookup("#rent").queryAs(Label.class);
        assertNotNull(lbl);
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }

    @Test
    void addEmployee() {
    }

    @Test
    void editEmployeeAction() {
    }

    @Test
    void deleteEmployeeAction() {
    }

    @Test
    void editProfilAction() {
    }
}