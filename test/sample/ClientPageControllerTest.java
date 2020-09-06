package sample;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.File;
import java.time.LocalDate;

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
        Stage stage= (Stage) choiceBox.getScene().getWindow();
        Platform.runLater(() -> stage.close());
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
        robot.clickOn("#buttonOk");
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
        //Stage stage= (Stage) lbl.getScene().getWindow();
        //Platform.runLater(() -> stage.close());
    }
    @Test
    void detailsButton(FxRobot robot) {
        TableView tableView = robot.lookup("#tableViewCars").queryAs(TableView.class);
        tableView.getSelectionModel().selectFirst();
        robot.clickOn("Detalji");
        robot.lookup("#lblName").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblName").queryAs(Label.class);
        assertNotNull(lbl);
        robot.clickOn("#btnBack");
        assertTrue(theStage.isShowing());
        tableView.getSelectionModel().selectFirst();
        robot.clickOn("Detalji");
        robot.lookup("#lblName").tryQuery().isPresent();
        Label label1=robot.lookup("#lblName").queryAs(Label.class);
        assertNotNull(label1);
        try{
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.clickOn("#btnReservationFromDetails");
        robot.lookup("#lblHeader").tryQuery().isPresent();
        Label label=robot.lookup("#lblHeader").queryAs(Label.class);
        assertNotNull(label);
        DatePicker datePicker=robot.lookup("#datePickup").queryAs(DatePicker.class);
        datePicker.setValue(LocalDate.now());
        DatePicker datePicker1=robot.lookup("#dateReturn").queryAs(DatePicker.class);
        datePicker1.setValue(LocalDate.of(2020, 9, LocalDate.now().getDayOfMonth()+1));
        robot.clickOn("#btnReservationConfirm");
        robot.clickOn("OK");
        assertTrue(theStage.isShowing());
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }


    @Test
    void reservationButton(FxRobot robot) {
        TableView tableView = robot.lookup("#tableViewCars").queryAs(TableView.class);
        tableView.getSelectionModel().selectFirst();
        robot.clickOn("#btnReservation");
        robot.lookup("#lblHeader").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblHeader").queryAs(Label.class);
        assertNotNull(lbl);
        robot.clickOn("#btnBack");
        assertTrue(theStage.isShowing());
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }
    @Test
    void reservationButton2(FxRobot robot) {
        TableView tableView = robot.lookup("#tableViewCars").queryAs(TableView.class);
        tableView.getSelectionModel().selectFirst();
        robot.clickOn("#btnReservation");
        robot.lookup("#lblHeader").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblHeader").queryAs(Label.class);
        assertNotNull(lbl);
        robot.clickOn("#datePickup");
        robot.write("9/7/2020");
        robot.clickOn("#dateReturn");
        robot.write("9/20/2020");
        robot.clickOn("#btnReservationConfirm");
        robot.clickOn("OK");
        assertTrue(theStage.isShowing());
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }
    @Test
    void reservationButton3(FxRobot robot) {
        TableView tableView = robot.lookup("#tableViewCars").queryAs(TableView.class);
        tableView.getSelectionModel().selectFirst();
        robot.clickOn("#btnReservation");
        robot.lookup("#lblHeader").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblHeader").queryAs(Label.class);
        assertNotNull(lbl);
        robot.clickOn("#datePickup");
        robot.write("9/7/2020");
        robot.clickOn("#dateReturn");
        robot.write("9/20/2020");
        robot.clickOn("#btnReservationConfirm");
        robot.clickOn("OK");
        assertTrue(theStage.isShowing());
        tableView.getSelectionModel().selectFirst();
        robot.clickOn("#btnReservation");
        robot.clickOn("OK");
        assertTrue(theStage.isShowing());
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }
    @Test
    void reservationButton4(FxRobot robot) {
        TableView tableView = robot.lookup("#tableViewCars").queryAs(TableView.class);
        tableView.getSelectionModel().selectFirst();
        robot.clickOn("#btnReservation");
        robot.lookup("#lblHeader").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblHeader").queryAs(Label.class);
        assertNotNull(lbl);
        robot.clickOn("#btnReservationConfirm");
        robot.clickOn("OK");
        /*robot.clickOn("#datePickup");
        robot.write("9/3/2020");
        robot.clickOn("#dateReturn");
        robot.write("9/20/2020");*/
        DatePicker datePicker=robot.lookup("#datePickup").queryAs(DatePicker.class);
        datePicker.setValue(LocalDate.now());
        DatePicker datePicker1=robot.lookup("#dateReturn").queryAs(DatePicker.class);
        datePicker1.setValue(LocalDate.of(2020, 9, LocalDate.now().getDayOfMonth()+1));
        try{
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Background bg = datePicker.getBackground();
        boolean colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("9acd32"))
                colorFound = true;
        assertTrue(colorFound);
        try{
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.clickOn("#btnReservationConfirm");
        robot.clickOn("OK");
        assertTrue(theStage.isShowing());
        tableView.getSelectionModel().selectFirst();
        robot.clickOn("#btnReservation");
        robot.clickOn("OK");
        assertTrue(theStage.isShowing());
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }

    @Test
    void reservationButton5(FxRobot robot) {
        TableView tableView = robot.lookup("#tableViewCars").queryAs(TableView.class);
        tableView.getSelectionModel().selectFirst();
        robot.clickOn("#btnReservation");
        robot.lookup("#lblHeader").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblHeader").queryAs(Label.class);
        assertNotNull(lbl);
       /* robot.clickOn("#datePickup");
        robot.write("9/3/2020");
        robot.clickOn("#dateReturn");
        robot.write("9/20/2020");*/
        DatePicker datePicker=robot.lookup("#datePickup").queryAs(DatePicker.class);
        datePicker.setValue(LocalDate.now());
        DatePicker datePicker1=robot.lookup("#dateReturn").queryAs(DatePicker.class);
        datePicker1.setValue(LocalDate.of(2020, 9, LocalDate.now().getDayOfMonth()+1));
        robot.clickOn("#btnReservationConfirm");
        robot.clickOn("OK");
        assertTrue(theStage.isShowing());
        try{
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.clickOn("#menuReservations");
        robot.clickOn("#menuItemEditReservation");
        robot.clickOn("OK");
        robot.lookup("#lblHeader").tryQuery().isPresent();
        Label label=robot.lookup("#lblHeader").queryAs(Label.class);
        assertNotNull(label);
        robot.clickOn("#btnDelete");
        robot.clickOn("OK");
        assertTrue(theStage.isShowing());
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }
    @Test
    void reservationButton6(FxRobot robot) {
        TableView tableView = robot.lookup("#tableViewCars").queryAs(TableView.class);
        tableView.getSelectionModel().selectFirst();
        robot.clickOn("#btnPrice");
        robot.lookup("#lblText").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblText").queryAs(Label.class);
        assertNotNull(lbl);
        robot.clickOn("#btnBack");
        assertTrue(theStage.isShowing());
        tableView.getSelectionModel().selectFirst();
        robot.clickOn("#btnPrice");
        robot.lookup("#lblText").tryQuery().isPresent();
        Label label=robot.lookup("#lblText").queryAs(Label.class);
        assertNotNull(label);
        DatePicker datePicker=robot.lookup("#datePickup").queryAs(DatePicker.class);
        datePicker.setValue(LocalDate.now());
        DatePicker datePicker1=robot.lookup("#dateReturn").queryAs(DatePicker.class);
        datePicker1.setValue(LocalDate.of(2020, 9, LocalDate.now().getDayOfMonth()+1));
        robot.lookup("#fldPrice").tryQuery().isPresent();
        TextField textField=robot.lookup("#fldPrice").queryAs(TextField.class);
        assertEquals("140.0 KM", textField.getText());
        robot.clickOn("#btnReservationFromPrice");
        robot.lookup("#lblHeader").tryQuery().isPresent();
        Label label1=robot.lookup("#lblHeader").queryAs(Label.class);
        assertNotNull(label1);
        robot.clickOn("#btnBack");
        assertTrue(theStage.isShowing());

        /*tableView.getSelectionModel().selectFirst();
        robot.clickOn("#btnPrice");
        robot.lookup("#lblText").tryQuery().isPresent();
        Label label2=robot.lookup("#lblText").queryAs(Label.class);
        assertNotNull(label2);
        datePicker.setValue(LocalDate.now());
        datePicker1.setValue(LocalDate.now());
        robot.clickOn("OK");
        datePicker.setValue(LocalDate.of(2020, 5, 10));
        datePicker1.setValue(LocalDate.now());
        robot.clickOn("OK");
        robot.clickOn("#btnBack");
        assertTrue(theStage.isShowing());*/

        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }
    @Test
    void reservationButton10(FxRobot robot) {
        TableView tableView = robot.lookup("#tableViewCars").queryAs(TableView.class);
        tableView.getSelectionModel().selectFirst();
        robot.clickOn("#btnReservation");
        robot.lookup("#lblHeader").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblHeader").queryAs(Label.class);
        assertNotNull(lbl);
        robot.clickOn("#datePickup");
        robot.write("9/7/2020");
        robot.clickOn("#dateReturn");
        robot.write("9/20/2020");
        robot.clickOn("#checkBoxNow");
        robot.clickOn("#fldNmbCard");
        robot.write("1234123412341234");
        robot.clickOn("#fldCode");
        robot.write("123");
        robot.clickOn("#btnReservationConfirm");
        robot.clickOn("OK");
        robot.clickOn("#fldYear");
        robot.write("20233");
        robot.clickOn("#btnReservationConfirm");
        robot.clickOn("OK");
        robot.clickOn("#fldYear");
        KeyCode ctrl = KeyCode.CONTROL;
        if (System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("2023");
        robot.clickOn("#btnReservationConfirm");
        robot.clickOn("OK");
        assertTrue(theStage.isShowing());
        RentACarDAO dao=RentACarDAO.getInstance();
        assertEquals(1, dao.getReservations().size());
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }
    @Test
    void reservationButton11(FxRobot robot) {
        TableView tableView = robot.lookup("#tableViewCars").queryAs(TableView.class);
        tableView.getSelectionModel().selectFirst();
        robot.clickOn("#btnReservation");
        robot.lookup("#lblHeader").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblHeader").queryAs(Label.class);
        assertNotNull(lbl);
        robot.clickOn("#datePickup");
        robot.write("9/7/2020");
        robot.clickOn("#dateReturn");
        robot.write("9/20/2020");
        robot.clickOn("#checkBoxNow");
        robot.clickOn("#fldNmbCard");
        robot.write("1234123412341234");
        robot.clickOn("#fldYear");
        robot.write("2023");
        robot.clickOn("#btnReservationConfirm");
        robot.clickOn("OK");
        robot.clickOn("#fldCode");
        robot.write("13");
        robot.clickOn("#btnReservationConfirm");
        robot.clickOn("OK");
        robot.clickOn("#fldCode");
        KeyCode ctrl = KeyCode.CONTROL;
        if (System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("1234");
        robot.clickOn("#btnReservationConfirm");
        robot.clickOn("OK");
        assertTrue(theStage.isShowing());
        RentACarDAO dao=RentACarDAO.getInstance();
        assertEquals(1, dao.getReservations().size());
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }
    @Test
    void reservationButton12(FxRobot robot) {
        TableView tableView = robot.lookup("#tableViewCars").queryAs(TableView.class);
        tableView.getSelectionModel().selectFirst();
        robot.clickOn("#btnReservation");
        robot.lookup("#lblHeader").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblHeader").queryAs(Label.class);
        assertNotNull(lbl);
       /* robot.clickOn("#datePickup");
        robot.write("9/3/2020");
        robot.clickOn("#dateReturn");
        robot.write("9/20/2020");*/
        DatePicker datePicker=robot.lookup("#datePickup").queryAs(DatePicker.class);
        datePicker.setValue(LocalDate.now());
        DatePicker datePicker1=robot.lookup("#dateReturn").queryAs(DatePicker.class);
        datePicker1.setValue(LocalDate.of(2020, 9, LocalDate.now().getDayOfMonth()+1));
        robot.clickOn("#btnReservationConfirm");
        robot.clickOn("OK");
        assertTrue(theStage.isShowing());
        try{
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.clickOn("#menuReservations");
        robot.clickOn("#menuItemEditReservation");
        robot.clickOn("OK");
        robot.lookup("#lblHeader").tryQuery().isPresent();
        Label label=robot.lookup("#lblHeader").queryAs(Label.class);
        assertNotNull(label);
        robot.clickOn("#checkBoxNow");
        robot.clickOn("#fldNmbCard");
        robot.write("1234123412341234");
        robot.clickOn("#fldCode");
        robot.write("123");
        robot.clickOn("#fldYear");
        robot.write("2023");
        robot.clickOn("#btnReservationConfirm");
        robot.clickOn("OK");
        assertTrue(theStage.isShowing());
        /*tableView.getSelectionModel().selectFirst();
        robot.clickOn("#btnReservationConfirm");
        robot.clickOn("OK");*/
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }
    @Test
    void reservationButton13(FxRobot robot) {
        TableView tableView = robot.lookup("#tableViewCars").queryAs(TableView.class);
        tableView.getSelectionModel().selectFirst();
        robot.clickOn("#btnReservation");
        robot.lookup("#lblHeader").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblHeader").queryAs(Label.class);
        assertNotNull(lbl);
       /* robot.clickOn("#datePickup");
        robot.write("9/3/2020");
        robot.clickOn("#dateReturn");
        robot.write("9/20/2020");*/
        DatePicker datePicker=robot.lookup("#datePickup").queryAs(DatePicker.class);
        datePicker.setValue(LocalDate.now());
        DatePicker datePicker1=robot.lookup("#dateReturn").queryAs(DatePicker.class);
        datePicker1.setValue(LocalDate.of(2020, 9, LocalDate.now().getDayOfMonth()+1));
        robot.clickOn("#checkBoxNow");
        robot.clickOn("#fldNmbCard");
        robot.write("1234123412341234");
        robot.clickOn("#fldCode");
        robot.write("123");
        robot.clickOn("#choiceMonth");
        robot.clickOn("FEB");
        robot.clickOn("#fldYear");
        robot.write("2023");
        robot.clickOn("#btnReservationConfirm");
        robot.clickOn("OK");
        assertTrue(theStage.isShowing());
        /*tableView.getSelectionModel().selectFirst();
        robot.clickOn("#btnReservationConfirm");
        robot.clickOn("OK");*/
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }
    @Test
    void reservationButton15(FxRobot robot) {
        TableView tableView = robot.lookup("#tableViewCars").queryAs(TableView.class);
        tableView.getSelectionModel().selectFirst();
        robot.clickOn("#btnPrice");
        robot.lookup("#lblText").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblText").queryAs(Label.class);
        assertNotNull(lbl);
        robot.clickOn("#dateReturn");
        robot.write("9/7/2020");
        robot.clickOn("#datePickup");
        robot.write("9/7/2020");
        robot.clickOn("#btnReservationFromPrice");
        robot.clickOn("OK");

        robot.clickOn("#dateReturn");
        KeyCode ctrl = KeyCode.CONTROL;
        if (System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("9/6/2020");
        robot.clickOn("#btnReservationFromPrice");
        robot.clickOn("OK");

        robot.clickOn("#dateReturn");
        if (System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("9/8/2020");
        robot.clickOn("#datePickup");
        if (System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("9/7/2020");
        robot.lookup("#fldPrice").tryQuery().isPresent();
        TextField textField=robot.lookup("#fldPrice").queryAs(TextField.class);
        assertEquals("140.0 KM", textField.getText());
        robot.clickOn("#btnReservationFromPrice");
        robot.lookup("#lblHeader").tryQuery().isPresent();
        Label label1=robot.lookup("#lblHeader").queryAs(Label.class);
        assertNotNull(label1);
        robot.clickOn("#btnBack");
        assertTrue(theStage.isShowing());

        /*tableView.getSelectionModel().selectFirst();
        robot.clickOn("#btnPrice");
        robot.lookup("#lblText").tryQuery().isPresent();
        Label label2=robot.lookup("#lblText").queryAs(Label.class);
        assertNotNull(label2);
        datePicker.setValue(LocalDate.now());
        datePicker1.setValue(LocalDate.now());
        robot.clickOn("OK");
        datePicker.setValue(LocalDate.of(2020, 5, 10));
        datePicker1.setValue(LocalDate.now());
        robot.clickOn("OK");
        robot.clickOn("#btnBack");
        assertTrue(theStage.isShowing());*/

        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }
}