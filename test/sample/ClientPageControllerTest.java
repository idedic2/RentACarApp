package sample;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
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
class ClientPageControllerTest {
    Stage theStage;
    ClientPageController ctrl;


    @Start
    public void start (Stage stage) throws Exception {
        RentACarDAO.removeInstance();
        File dbfile = new File("rentacar.db");
        dbfile.delete();
        RentACarDAO dao = RentACarDAO.getInstance();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/clientPage.fxml"));
        ctrl = new ClientPageController("klijent");
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Klijent");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        theStage = stage;
    }

    @Test
    void editMyReservationsAction(FxRobot robot) {

    }

    @Test
    void editProfilAction(FxRobot robot) {
        robot.clickOn("#menuProfil");
        robot.clickOn("#editProfil");
        robot.lookup("#lblRegistrationText").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblRegistrationText").queryAs(Label.class);
        assertNotNull(lbl);
        robot.clickOn("#fldTelephone");
        KeyCode ctrl = KeyCode.CONTROL;
        if (System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("062154777");
        robot.clickOn("#btnRegistrationConfirm");
        try{
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.clickOn("OK");
        RentACarDAO dao = RentACarDAO.getInstance();
        Client client=dao.getClientPerUsername("klijent");
        assertEquals("062154777", client.getTelephone());
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }

    @Test
    void changeType(FxRobot robot) {
        ChoiceBox choiceBox=robot.lookup("#choiceType").queryAs(ChoiceBox.class);
        assertNotNull(choiceBox);
        Platform.runLater(()->choiceBox.show());
        try{
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.clickOn("Transportno vozilo");
        RentACarDAO dao = RentACarDAO.getInstance();
        TableView tableView = robot.lookup("#tableViewCars").queryAs(TableView.class);
        assertEquals(dao.getVehiclesPerType("Transportno vozilo").size(), tableView.getItems().size());
    }

    @Test
    void logOutAction(FxRobot robot) {
        robot.clickOn("#menuProfil");
        robot.clickOn("#menuItemLogOut");
        assertFalse(theStage.isShowing());
        robot.lookup("#rent").tryQuery().isPresent();
        Label lbl=robot.lookup("#rent").queryAs(Label.class);
        assertNotNull(lbl);
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }

    @Test
    void deleteProfilAction(FxRobot robot) {
        robot.clickOn("#menuProfil");
        robot.clickOn("#deleteProfil");
        robot.clickOn("Obriši");
        assertFalse(theStage.isShowing());
        robot.lookup("#rent").tryQuery().isPresent();
        Label lbl=robot.lookup("#rent").queryAs(Label.class);
        assertNotNull(lbl);
        RentACarDAO dao = RentACarDAO.getInstance();
        assertEquals(0, dao.getClients().size());
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }
    @Test
    void deleteProfilAction2(FxRobot robot) {
        robot.clickOn("#menuProfil");
        robot.clickOn("#deleteProfil");
        robot.clickOn("Odjavi se");
        assertFalse(theStage.isShowing());
        robot.lookup("#rent").tryQuery().isPresent();
        Label lbl=robot.lookup("#rent").queryAs(Label.class);
        assertNotNull(lbl);
        RentACarDAO dao = RentACarDAO.getInstance();
        assertEquals(1, dao.getClients().size());
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }

    @Test
    void aboutAction(FxRobot robot) {
        robot.clickOn("#menuHelp");
        robot.clickOn("#menuItemAboutApp");
        robot.lookup("#lblAboutApplication").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblAboutApplication").queryAs(Label.class);
        assertNotNull(lbl);
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }
}