package sample;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HorizontalDirection;
import javafx.geometry.VerticalDirection;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.awt.event.KeyEvent;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

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
        try{
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RentACarDAO dao = RentACarDAO.getInstance();
        TableView tableView = robot.lookup("#tableViewClients").queryAs(TableView.class);
        assertEquals(dao.getClients().size(), tableView.getItems().size());
    }

    @Test
    void editClientAction(FxRobot robot) {
        robot.clickOn("#tabClients");
        robot.clickOn("Klijent");
        robot.clickOn("#btnEditClient");
        robot.lookup("#lblRegistrationText").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblRegistrationText").queryAs(Label.class);
        assertNotNull(lbl);
        assertEquals("Podaci o klijentu", lbl.getText());
        robot.clickOn("#fldLastName");
        KeyCode ctrl = KeyCode.CONTROL;
        if (System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("Prezimenković");
        robot.clickOn("#btnRegistrationConfirm");
        robot.clickOn("OK");
        RentACarDAO dao = RentACarDAO.getInstance();
        Client client=dao.getClientPerUsername("klijent");
        assertEquals("Prezimenković", client.getLastName());
    }
    @Test
    void editClientAction2(FxRobot robot) {
        robot.clickOn("#tabClients");
        TableView tableView = robot.lookup("#tableViewClients").queryAs(TableView.class);
        tableView.getSelectionModel().selectFirst();
        robot.clickOn("Izmijeni");
        robot.lookup("#lblRegistrationText").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblRegistrationText").queryAs(Label.class);
        assertNotNull(lbl);
        assertEquals("Podaci o klijentu", lbl.getText());
        robot.clickOn("#fldLastName");
        KeyCode ctrl = KeyCode.CONTROL;
        if (System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("Prezimenković");
        robot.clickOn("#btnRegistrationConfirm");
        robot.clickOn("OK");
        RentACarDAO dao = RentACarDAO.getInstance();
        Client client=dao.getClientPerUsername("klijent");
        assertEquals("Prezimenković", client.getLastName());
    }
    @Test
    void deleteClientAction(FxRobot robot) {
        robot.clickOn("#tabClients");
        TableView tableView = robot.lookup("#tableViewClients").queryAs(TableView.class);
        robot.clickOn("Klijent");
        robot.clickOn("#btnDeleteClient");
        robot.clickOn("OK");
        try{
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RentACarDAO dao = RentACarDAO.getInstance();
        assertEquals(dao.getClients().size(), tableView.getItems().size());
        assertEquals(0, tableView.getItems().size());
    }
    @Test
    void deleteClientAction2(FxRobot robot) {
        robot.clickOn("#tabClients");
        TableView tableView = robot.lookup("#tableViewClients").queryAs(TableView.class);
        tableView.getSelectionModel().selectFirst();
        robot.clickOn("Obriši");
        robot.clickOn("OK");
        try{
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
    void addEmployee(FxRobot robot) {
        robot.clickOn("#tabEmployees");
        robot.clickOn("#btnAddEmployee");
        robot.lookup("#lblRegistrationText").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblRegistrationText").queryAs(Label.class);
        assertNotNull(lbl);
        assertEquals("Dodajte novog zaposlenika", lbl.getText());
        robot.clickOn("#fldFirstName");
        robot.write("Zaposlenik");
        robot.clickOn("#fldLastName");
        robot.write("Zaposleniković");
        robot.clickOn("#fldEmail");
        robot.write("klijent@gmail.com");
        robot.clickOn("#fldUsername");
        robot.write("zaposlenik123");
        robot.clickOn("#fldPassword");
        robot.write("password");
        robot.clickOn("#fldRetypePassword");
        robot.write("password");
        robot.clickOn("#btnRegistrationConfirm");
        robot.clickOn("OK");
        try{
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RentACarDAO dao = RentACarDAO.getInstance();
        TableView tableView = robot.lookup("#tableViewEmployees").queryAs(TableView.class);
        assertEquals(dao.getEmployees().size(), tableView.getItems().size());
    }

    @Test
    void editEmployeeAction(FxRobot robot) {
        robot.clickOn("#tabEmployees");
        robot.clickOn("Zaposlenik");
        robot.clickOn("#btnEditEmployee");
        robot.lookup("#lblRegistrationText").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblRegistrationText").queryAs(Label.class);
        assertNotNull(lbl);
        assertEquals("Podaci o zaposleniku", lbl.getText());
        robot.clickOn("#fldLastName");
        KeyCode ctrl = KeyCode.CONTROL;
        if (System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("Prezimenković");
        robot.clickOn("#btnRegistrationConfirm");
        robot.clickOn("OK");
        try{
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RentACarDAO dao = RentACarDAO.getInstance();
        Employee employee=dao.getEmployeePerUsername("zaposlenik");
        assertEquals("Prezimenković", employee.getLastName());
    }
    @Test
    void editEmployeeAction2(FxRobot robot) {
        robot.clickOn("#tabEmployees");
        TableView tableView = robot.lookup("#tableViewEmployees").queryAs(TableView.class);
        tableView.getSelectionModel().selectFirst();
        robot.clickOn("Izmijeni");
        robot.lookup("#lblRegistrationText").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblRegistrationText").queryAs(Label.class);
        assertNotNull(lbl);
        assertEquals("Podaci o zaposleniku", lbl.getText());
        robot.clickOn("#fldLastName");
        KeyCode ctrl = KeyCode.CONTROL;
        if (System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("Prezimenković");
        robot.clickOn("#btnRegistrationConfirm");
        robot.clickOn("OK");
        try{
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RentACarDAO dao = RentACarDAO.getInstance();
        Employee employee=dao.getEmployeePerUsername("zaposlenik");
        assertEquals("Prezimenković", employee.getLastName());
    }
    @Test
    void deleteEmployeeAction(FxRobot robot) {
        robot.clickOn("#tabEmployees");
        TableView tableView = robot.lookup("#tableViewEmployees").queryAs(TableView.class);
        robot.clickOn("Zaposlenik");
        robot.clickOn("#btnDeleteEmployee");
        robot.clickOn("OK");
        try{
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RentACarDAO dao = RentACarDAO.getInstance();
        assertEquals(dao.getEmployees().size(), tableView.getItems().size());
        assertEquals(0, tableView.getItems().size());
    }
    @Test
    void deleteEmployeeAction2(FxRobot robot) {
        robot.clickOn("#tabEmployees");
        TableView tableView = robot.lookup("#tableViewEmployees").queryAs(TableView.class);
        tableView.getSelectionModel().selectFirst();
        robot.clickOn("Obriši");
        robot.clickOn("OK");
        try{
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RentACarDAO dao = RentACarDAO.getInstance();
        assertEquals(dao.getEmployees().size(), tableView.getItems().size());
        assertEquals(0, tableView.getItems().size());
    }

    @Test
    void editProfilAction(FxRobot robot) {
        robot.clickOn("#profil");
        robot.clickOn("#btnEditProfilEmployee");
        robot.lookup("#lblRegistrationText").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblRegistrationText").queryAs(Label.class);
        assertNotNull(lbl);
        robot.clickOn("#fldLastName");
        KeyCode ctrl = KeyCode.CONTROL;
        if (System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("Prezimenković");
        robot.clickOn("#btnRegistrationConfirm");
        robot.clickOn("OK");
        try{
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RentACarDAO dao = RentACarDAO.getInstance();
        Employee employee=dao.getEmployeePerUsername("idedic2");
        assertEquals("Prezimenković", employee.getLastName());

    }
    @Test
    void addVehicle(FxRobot robot) {
        robot.clickOn("#tabVehicles");
        robot.clickOn("#btnAddVehicle");
        robot.lookup("#lblNameVehicle").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblNameVehicle").queryAs(Label.class);
        assertNotNull(lbl);
        robot.clickOn("#fldName");
        robot.write("Audi A6");
        robot.clickOn("#fldBrand");
        robot.write("Audi");
        robot.clickOn("#fldModel");
        robot.write("A6 Quattro");
        ComboBox combo=robot.lookup("#comboYear").queryAs(ComboBox.class);
        assertNotNull(combo);
        robot.clickOn("#comboYear");
        robot.write("2019");
        ComboBox comboBox=robot.lookup("#comboFuelConsumption").queryAs(ComboBox.class);
        assertNotNull(comboBox);
        robot.clickOn("#comboFuelConsumption");
        robot.write("15");
        robot.clickOn("#fldColor");
        robot.write("Plava");
        ComboBox comboBox1=robot.lookup("#comboPrice").queryAs(ComboBox.class);
        assertNotNull(comboBox1);
        robot.clickOn("#comboPrice");
        robot.write("140");
        robot.clickOn("#checkAvailability");
        robot.clickOn("#btnChooseImage");
        robot.lookup("#lblImageVehicle").tryQuery().isPresent();
        Label label=robot.lookup("#lblImageVehicle").queryAs(Label.class);
        assertNotNull(label);
        ListView<String> list=robot.lookup("#listViewImages").queryAs(ListView.class);
        list.getSelectionModel().selectFirst();
        robot.clickOn("#btnConfirmImage");
        robot.clickOn("#btnAddConfirm");
        //RentACarDAO dao = RentACarDAO.getInstance();
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
        TableView tableView = robot.lookup("#tableViewVehicles").queryAs(TableView.class);
        assertEquals(7, tableView.getItems().size());
    }

    @Test
    void deleteVehicleAction(FxRobot robot) {
        robot.clickOn("#tabVehicles");
        TableView tableView = robot.lookup("#tableViewVehicles").queryAs(TableView.class);
        tableView.getSelectionModel().selectFirst();
        robot.clickOn("#btnDeleteVehicle");
        robot.clickOn("OK");
        try{
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RentACarDAO dao = RentACarDAO.getInstance();
        assertEquals(5, tableView.getItems().size());
    }
    /*@Test
    void deleteVehicleAction2(FxRobot robot) {
        robot.clickOn("#tabVehicles");
        TableView tableView = robot.lookup("#tableViewVehicles").queryAs(TableView.class);
        tableView.getSelectionModel().selectFirst();
        robot.clickOn("#btnDeleteVehicle2");
        robot.clickOn("OK");
        try{
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RentACarDAO dao = RentACarDAO.getInstance();
        assertEquals(5, tableView.getItems().size());
    }*/
    @Test
    void editVehicleAction(FxRobot robot) {
        robot.clickOn("#tabVehicles");
        TableView tableView = robot.lookup("#tableViewVehicles").queryAs(TableView.class);
        tableView.getSelectionModel().selectFirst();
        robot.clickOn("#btnEditVehicle");
        robot.lookup("#lblNameVehicle").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblNameVehicle").queryAs(Label.class);
        assertNotNull(lbl);
        RentACarDAO dao = RentACarDAO.getInstance();
        assertEquals(140, dao.getVehiclePerId(0).getPricePerDay());
        robot.clickOn("#comboPrice");
        KeyCode ctrl = KeyCode.CONTROL;
        if (System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("100");
        robot.clickOn("#btnAddConfirm");
        assertEquals(100, dao.getVehiclePerId(0).getPricePerDay());
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }
    @Test
    void addReservationAction(FxRobot robot) {
        robot.clickOn("#tabReservations");
        TableView tableView = robot.lookup("#tableViewReservations").queryAs(TableView.class);
        robot.clickOn("#btnAddReservation");
        robot.lookup("#lblVehicle").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblVehicle").queryAs(Label.class);
        assertNotNull(lbl);
        robot.lookup("#listVehicles").tryQuery().isPresent();
        ListView<Vehicle> vehicleListView=robot.lookup("#listVehicles").queryAs(ListView.class);
        vehicleListView.getSelectionModel().selectFirst();
        robot.lookup("#listClients").tryQuery().isPresent();
        ListView<Client> clientListView=robot.lookup("#listClients").queryAs(ListView.class);
        clientListView.getSelectionModel().selectFirst();
        DatePicker datePicker=robot.lookup("#datePickup").queryAs(DatePicker.class);
        datePicker.setValue(LocalDate.now());
        DatePicker datePicker1=robot.lookup("#dateReturn").queryAs(DatePicker.class);
        datePicker1.setValue(LocalDate.of(2020, 9, LocalDate.now().getDayOfMonth()+1));
        robot.clickOn("#btnConfirmAddReservation");
        assertTrue(theStage.isShowing());
        RentACarDAO dao = RentACarDAO.getInstance();
        assertEquals(1, dao.getReservations().size());
        assertEquals(1, tableView.getItems().size());
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }
    @Test
    void addReservationAction2(FxRobot robot) {
        robot.clickOn("#tabReservations");
        TableView tableView = robot.lookup("#tableViewReservations").queryAs(TableView.class);
        robot.clickOn("#btnAddReservation");
        robot.lookup("#lblVehicle").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblVehicle").queryAs(Label.class);
        assertNotNull(lbl);
        robot.lookup("#listVehicles").tryQuery().isPresent();
        ListView<Vehicle> vehicleListView=robot.lookup("#listVehicles").queryAs(ListView.class);
        vehicleListView.getSelectionModel().selectFirst();
        robot.lookup("#listClients").tryQuery().isPresent();
        ListView<Client> clientListView=robot.lookup("#listClients").queryAs(ListView.class);
        clientListView.getSelectionModel().selectFirst();
        DatePicker datePicker=robot.lookup("#datePickup").queryAs(DatePicker.class);
        datePicker.setValue(LocalDate.now());
        DatePicker datePicker1=robot.lookup("#dateReturn").queryAs(DatePicker.class);
        datePicker1.setValue(LocalDate.of(2020, 9, LocalDate.now().getDayOfMonth()+1));
        robot.clickOn("#btnConfirmAddReservation");
        assertTrue(theStage.isShowing());
        RentACarDAO dao = RentACarDAO.getInstance();
        assertEquals(1, dao.getReservations().size());
        assertEquals(1, tableView.getItems().size());
        tableView.getSelectionModel().selectFirst();
        robot.clickOn("#btnEditReservation");
        ListView<Vehicle> vehicleListView1=robot.lookup("#listVehicles").queryAs(ListView.class);
        vehicleListView1.getSelectionModel().selectFirst();
        robot.clickOn("#btnConfirmAddReservation");
        assertTrue(theStage.isShowing());
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }
    @Test
    void addReservationAction3(FxRobot robot) {
        robot.clickOn("#tabReservations");
        TableView tableView = robot.lookup("#tableViewReservations").queryAs(TableView.class);
        robot.clickOn("#btnAddReservation");
        robot.lookup("#lblVehicle").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblVehicle").queryAs(Label.class);
        assertNotNull(lbl);
        robot.lookup("#listVehicles").tryQuery().isPresent();
        ListView<Vehicle> vehicleListView=robot.lookup("#listVehicles").queryAs(ListView.class);
        vehicleListView.getSelectionModel().selectFirst();
        robot.lookup("#listClients").tryQuery().isPresent();
        ListView<Client> clientListView=robot.lookup("#listClients").queryAs(ListView.class);
        clientListView.getSelectionModel().selectFirst();
        DatePicker datePicker=robot.lookup("#datePickup").queryAs(DatePicker.class);
        datePicker.setValue(LocalDate.now());
        DatePicker datePicker1=robot.lookup("#dateReturn").queryAs(DatePicker.class);
        datePicker1.setValue(LocalDate.of(2020, 9, LocalDate.now().getDayOfMonth()+1));
        robot.clickOn("#btnConfirmAddReservation");
        assertTrue(theStage.isShowing());
        robot.clickOn("#btnEditReservation");
        robot.clickOn("OK");
        tableView.getSelectionModel().selectFirst();
        robot.clickOn("#btnEditReservation");
        robot.lookup("#lblVehicle").tryQuery().isPresent();
        Label label=robot.lookup("#lblVehicle").queryAs(Label.class);
        assertNotNull(label);
        robot.clickOn("#btnCancelAddReservation");
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }
    @Test
    void addReservationAction4(FxRobot robot) {
        robot.clickOn("#tabReservations");
        TableView tableView = robot.lookup("#tableViewReservations").queryAs(TableView.class);
        robot.clickOn("#btnDeleteReservation");
        robot.clickOn("OK");
        robot.clickOn("#btnAddReservation");
        robot.lookup("#lblVehicle").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblVehicle").queryAs(Label.class);
        assertNotNull(lbl);
        robot.lookup("#listVehicles").tryQuery().isPresent();
        ListView<Vehicle> vehicleListView=robot.lookup("#listVehicles").queryAs(ListView.class);
        vehicleListView.getSelectionModel().selectFirst();
        robot.lookup("#listClients").tryQuery().isPresent();
        ListView<Client> clientListView=robot.lookup("#listClients").queryAs(ListView.class);
        clientListView.getSelectionModel().selectFirst();
        DatePicker datePicker=robot.lookup("#datePickup").queryAs(DatePicker.class);
        datePicker.setValue(LocalDate.now());
        DatePicker datePicker1=robot.lookup("#dateReturn").queryAs(DatePicker.class);
        datePicker1.setValue(LocalDate.of(2020, 9, LocalDate.now().getDayOfMonth()+1));
        robot.clickOn("#btnConfirmAddReservation");
        assertTrue(theStage.isShowing());
        robot.clickOn("#btnDeleteReservation");
        robot.clickOn("OK");
        tableView.getSelectionModel().selectFirst();
        robot.clickOn("#btnDeleteReservation");
        robot.clickOn("OK");
        RentACarDAO dao = RentACarDAO.getInstance();
        assertEquals(0, dao.getReservations().size());
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }
    @Test
    void deleteReservation(FxRobot robot) {
        robot.clickOn("#tabReservations");
        TableView tableView = robot.lookup("#tableViewReservations").queryAs(TableView.class);
        robot.clickOn("#btnAddReservation");
        robot.lookup("#lblVehicle").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblVehicle").queryAs(Label.class);
        assertNotNull(lbl);
        robot.lookup("#listVehicles").tryQuery().isPresent();
        ListView<Vehicle> vehicleListView=robot.lookup("#listVehicles").queryAs(ListView.class);
        vehicleListView.getSelectionModel().selectFirst();
        robot.lookup("#listClients").tryQuery().isPresent();
        ListView<Client> clientListView=robot.lookup("#listClients").queryAs(ListView.class);
        clientListView.getSelectionModel().selectFirst();
        DatePicker datePicker=robot.lookup("#datePickup").queryAs(DatePicker.class);
        datePicker.setValue(LocalDate.now());
        DatePicker datePicker1=robot.lookup("#dateReturn").queryAs(DatePicker.class);
        datePicker1.setValue(LocalDate.of(2020, 9, LocalDate.now().getDayOfMonth()+1));
        robot.clickOn("#btnConfirmAddReservation");
        assertTrue(theStage.isShowing());
        tableView.getSelectionModel().selectFirst();
        tableView.scrollToColumnIndex(12);
        robot.clickOn("Obriši");
        robot.clickOn("OK");
        RentACarDAO dao = RentACarDAO.getInstance();
        assertEquals(0, dao.getReservations().size());
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }
    @Test
    void editReservation(FxRobot robot) {
        robot.clickOn("#tabReservations");
        TableView tableView = robot.lookup("#tableViewReservations").queryAs(TableView.class);
        robot.clickOn("#btnAddReservation");
        robot.lookup("#lblVehicle").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblVehicle").queryAs(Label.class);
        assertNotNull(lbl);
        robot.lookup("#listVehicles").tryQuery().isPresent();
        ListView<Vehicle> vehicleListView=robot.lookup("#listVehicles").queryAs(ListView.class);
        vehicleListView.getSelectionModel().selectFirst();
        robot.lookup("#listClients").tryQuery().isPresent();
        ListView<Client> clientListView=robot.lookup("#listClients").queryAs(ListView.class);
        clientListView.getSelectionModel().selectFirst();
        DatePicker datePicker=robot.lookup("#datePickup").queryAs(DatePicker.class);
        datePicker.setValue(LocalDate.now());
        DatePicker datePicker1=robot.lookup("#dateReturn").queryAs(DatePicker.class);
        datePicker1.setValue(LocalDate.of(2020, 9, LocalDate.now().getDayOfMonth()+1));
        robot.clickOn("#btnConfirmAddReservation");
        assertTrue(theStage.isShowing());
        tableView.getSelectionModel().selectFirst();
        tableView.scrollToColumnIndex(11);
        robot.clickOn("Izmijeni");
        robot.clickOn("#dateReturn");
        KeyCode ctrl = KeyCode.CONTROL;
        if (System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("10/7/2020");
        robot.clickOn("#btnConfirmAddReservation");
        RentACarDAO dao = RentACarDAO.getInstance();
        ArrayList<Reservation>reservations=dao.getReservations();
        Reservation reservation=reservations.get(0);
        assertEquals(10, reservation.getReturnDate().getMonth().getValue());
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }
    @Test
    void editVehicle(FxRobot robot) {
        robot.clickOn("#tabVehicles");
        TableView tableView = robot.lookup("#tableViewVehicles").queryAs(TableView.class);
        tableView.getSelectionModel().selectFirst();
        tableView.scrollToColumnIndex(14);
        robot.clickOn("Izmijeni");
        Label lbl=robot.lookup("#lblNameVehicle").queryAs(Label.class);
        assertNotNull(lbl);
        robot.clickOn("#comboPrice");
        KeyCode ctrl = KeyCode.CONTROL;
        if (System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("180");
        robot.clickOn("#btnAddConfirm");
        assertTrue(theStage.isShowing());
        RentACarDAO dao = RentACarDAO.getInstance();
        assertEquals(180.0, dao.getVehiclePerId(0).getPricePerDay());
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }
    @Test
    void deleteVehicle(FxRobot robot) {
        robot.clickOn("#tabVehicles");
        TableView tableView = robot.lookup("#tableViewVehicles").queryAs(TableView.class);
        tableView.getSelectionModel().selectFirst();
        tableView.scrollToColumnIndex(15);
        robot.clickOn("Obriši");
        robot.clickOn("OK");
        RentACarDAO dao = RentACarDAO.getInstance();
        assertEquals(5, dao.getVehicles().size());
        Stage stage= (Stage) tableView.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }
    /*@Test
    void addReservationAction4(FxRobot robot) {
        robot.clickOn("#tabReservations");
        TableView tableView = robot.lookup("#tableViewReservations").queryAs(TableView.class);
        robot.clickOn("#btnAddReservation");
        robot.lookup("#lblVehicle").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblVehicle").queryAs(Label.class);
        assertNotNull(lbl);
        robot.lookup("#listVehicles").tryQuery().isPresent();
        ListView<Vehicle> vehicleListView=robot.lookup("#listVehicles").queryAs(ListView.class);
        vehicleListView.getSelectionModel().selectFirst();
        robot.lookup("#listClients").tryQuery().isPresent();
        ListView<Client> clientListView=robot.lookup("#listClients").queryAs(ListView.class);
        clientListView.getSelectionModel().selectFirst();
        /*DatePicker datePicker=robot.lookup("#datePickup").queryAs(DatePicker.class);
        datePicker.setValue(LocalDate.now());
        DatePicker datePicker1=robot.lookup("#dateReturn").queryAs(DatePicker.class);
        datePicker1.setValue(LocalDate.now());
        robot.clickOn("OK");
        datePicker1.setValue(LocalDate.of(2020, 9, LocalDate.now().getDayOfMonth()-4));
        robot.clickOn("OK");
        datePicker1.setValue(LocalDate.of(2020, 9, LocalDate.now().getDayOfMonth()+14));
        robot.clickOn("#datePickup");
        robot.write("9/5/2020");
        robot.clickOn("#dateReturn");
        robot.write("9/5/2020");
        robot.clickOn("OK");
        robot.clickOn("#dateReturn");
        robot.write("8/5/2020");
        robot.clickOn("OK");
        robot.clickOn("#dateReturn");
        robot.write("9/15/2020");
        robot.clickOn("#btnConfirmAddReservation");
        assertTrue(theStage.isShowing());
        RentACarDAO dao = RentACarDAO.getInstance();
        assertEquals(1, dao.getReservations().size());
        assertEquals(1, tableView.getItems().size());
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }
    @Test
    void editVehicleAction2(FxRobot robot) {
        robot.clickOn("#tabVehicles");
        TableView tableView = robot.lookup("#tableViewVehicles").queryAs(TableView.class);
        tableView.getSelectionModel().selectFirst();
        robot.clickOn("#btnEditVehicle2");
        robot.lookup("#lblNameVehicle").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblNameVehicle").queryAs(Label.class);
        assertNotNull(lbl);
        RentACarDAO dao = RentACarDAO.getInstance();
        assertEquals(140, dao.getVehiclePerId(0).getPricePerDay());
        robot.clickOn("#comboPrice");
        KeyCode ctrl = KeyCode.CONTROL;
        if (System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("100");
        robot.clickOn("#btnAddConfirm");
        assertEquals(100, dao.getVehiclePerId(0).getPricePerDay());
    }*/

}