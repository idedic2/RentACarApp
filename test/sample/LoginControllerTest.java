package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
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
        // Klik na dugme ok

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
        // Upisujemo državu
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

        // Sada je naziv validan, forma se zatvorila
        assertFalse(theStage.isShowing());
    }

}