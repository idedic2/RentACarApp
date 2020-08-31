package sample;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;


import java.awt.*;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class LoginControllerTest {
    Stage theStage;
    LoginController ctrl;


    @Start
    public void start (Stage stage) throws Exception {
        RentACarDAO dao = RentACarDAO.getInstance();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        //ctrl = new LoginController();
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Login");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        theStage = stage;
    }


    @Test
    void loginConfirmAction(FxRobot robot) {
        // Polje fieldNaziv je nevalidno jer je prazno
        TextField username = robot.lookup("#fldUsername").queryAs(TextField.class);
        robot.clickOn("#fldUsername");
        robot.press(KeyCode.SPACE);
        robot.clickOn("#btnLoginConfirm");
        Background bg = username.getBackground();
        boolean colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("ff7f50"))
                colorFound = true;
        assertTrue(colorFound);
        robot.clickOn("OK");
        robot.clickOn("#fldUsername");
        KeyCode ctrl = KeyCode.CONTROL;
        if (System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("idedic2");
        robot.clickOn("#fldPassword");
        robot.write("password");

        robot.clickOn("#radioAdmin");
        // Klik na dugme ok
        robot.clickOn("#btnLoginConfirm");
        robot.lookup("#lblWelcome").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblWelcome").queryAs(Label.class);
        assertNotNull(lbl);
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());


    }

    @Test
    void loginConfirmAction2(FxRobot robot) {
        TextField username = robot.lookup("#fldUsername").queryAs(TextField.class);
        robot.clickOn("#fldUsername");
        robot.write("idedic2");
        PasswordField password = robot.lookup("#fldPassword").queryAs(PasswordField.class);
        robot.clickOn("#fldPassword");
        robot.write("password");
        Background bg = username.getBackground();
        boolean colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("9acd32"))
                colorFound = true;
        assertTrue(colorFound);
        Background bg2 = password.getBackground();
        boolean colorFound2 = false;
        for (BackgroundFill bf : bg2.getFills())
            if (bf.getFill().toString().contains("9acd32"))
                colorFound2 = true;
        assertTrue(colorFound2);
        robot.clickOn("#radioClient");
        robot.clickOn("#btnLoginConfirm");
        robot.clickOn("OK");
        robot.clickOn("#radioEmployee");
        robot.clickOn("#btnLoginConfirm");
        robot.clickOn("OK");
        robot.clickOn("#radioAdmin");
        robot.clickOn("#btnLoginConfirm");
        assertFalse(theStage.isShowing());
        robot.lookup("#lblWelcome").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblWelcome").queryAs(Label.class);
        assertNotNull(lbl);
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }

    @Test
    void loginConfirmAction3(FxRobot robot) {
        TextField username = robot.lookup("#fldUsername").queryAs(TextField.class);
        robot.clickOn("#fldUsername");
        robot.write("suljo");
        PasswordField password = robot.lookup("#fldPassword").queryAs(PasswordField.class);
        robot.clickOn("#fldPassword");
        robot.write("password");
        Background bg = username.getBackground();
        boolean colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("9acd32"))
                colorFound = true;
        assertTrue(colorFound);
        Background bg2 = password.getBackground();
        boolean colorFound2 = false;
        for (BackgroundFill bf : bg2.getFills())
            if (bf.getFill().toString().contains("9acd32"))
                colorFound2 = true;
        assertTrue(colorFound2);
        robot.clickOn("#radioAdmin");
        robot.clickOn("#btnLoginConfirm");
        robot.clickOn("OK");
        robot.clickOn("#radioEmployee");
        robot.clickOn("#btnLoginConfirm");
        robot.clickOn("OK");
        robot.clickOn("#radioClient");
        robot.clickOn("#btnLoginConfirm");
        assertFalse(theStage.isShowing());
        robot.lookup("#lblWelcome").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblWelcome").queryAs(Label.class);
        assertNotNull(lbl);
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }
    @Test
    void loginConfirmAction4(FxRobot robot) {
        TextField username = robot.lookup("#fldUsername").queryAs(TextField.class);
        robot.clickOn("#fldUsername");
        robot.write("john");
        PasswordField password = robot.lookup("#fldPassword").queryAs(PasswordField.class);
        robot.clickOn("#fldPassword");
        robot.write("password");
        Background bg = username.getBackground();
        boolean colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("9acd32"))
                colorFound = true;
        assertTrue(colorFound);
        Background bg2 = password.getBackground();
        boolean colorFound2 = false;
        for (BackgroundFill bf : bg2.getFills())
            if (bf.getFill().toString().contains("9acd32"))
                colorFound2 = true;
        assertTrue(colorFound2);
        robot.clickOn("#radioAdmin");
        robot.clickOn("#btnLoginConfirm");
        robot.clickOn("OK");
        robot.clickOn("#radioClient");
        robot.clickOn("#btnLoginConfirm");
        robot.clickOn("OK");
        robot.clickOn("#radioEmployee");
        robot.clickOn("#btnLoginConfirm");
        assertFalse(theStage.isShowing());
        robot.lookup("#lblWelcome").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblWelcome").queryAs(Label.class);
        assertNotNull(lbl);
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }
    @Test
    void loginConfirmAction5(FxRobot robot) {
        // Polje fieldNaziv je nevalidno jer je prazno
        PasswordField password = robot.lookup("#fldPassword").queryAs(PasswordField.class);
        robot.clickOn("#fldPassword");
        robot.press(KeyCode.SPACE);
        robot.clickOn("#btnLoginConfirm");
        Background bg = password.getBackground();
        boolean colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("ff7f50"))
                colorFound = true;
        assertTrue(colorFound);
        robot.clickOn("OK");
        robot.clickOn("#fldPassword");
        KeyCode ctrl = KeyCode.CONTROL;
        if (System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("password");
        robot.clickOn("#fldUsername");
        robot.write("idedic2");

        robot.clickOn("#radioAdmin");
        // Klik na dugme ok
        robot.clickOn("#btnLoginConfirm");
        robot.lookup("#lblWelcome").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblWelcome").queryAs(Label.class);
        assertNotNull(lbl);
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());


    }
    @Test
    void loginConfirmAction6(FxRobot robot) {
        TextField username = robot.lookup("#fldUsername").queryAs(TextField.class);
        robot.clickOn("#fldUsername");
        robot.write("idedic2");
        PasswordField password = robot.lookup("#fldPassword").queryAs(PasswordField.class);
        robot.clickOn("#fldPassword");
        robot.write("password");
        robot.clickOn("#btnLoginConfirm");
        robot.clickOn("OK");
        robot.clickOn("#radioAdmin");
        robot.clickOn("#btnLoginConfirm");
        assertFalse(theStage.isShowing());
        robot.lookup("#lblWelcome").tryQuery().isPresent();
        Label lbl=robot.lookup("#lblWelcome").queryAs(Label.class);
        assertNotNull(lbl);
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }
    @Test
    void backLoginAction(FxRobot robot) {
        robot.clickOn("#btnBackLogin");
        assertFalse(theStage.isShowing());
        robot.lookup("#rent").tryQuery().isPresent();
        Label lbl=robot.lookup("#rent").queryAs(Label.class);
        assertNotNull(lbl);
        Stage stage= (Stage) lbl.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }


}