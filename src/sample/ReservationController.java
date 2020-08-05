package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class ReservationController {

    public DatePicker datePickup;
    public DatePicker dateReturn;
    public TextField fldName;
    public TextField fldAddress;
    public TextField fldTelephone;
    public TextField fldEmail;
    public CheckBox checkBoxNow;
    public CheckBox checkBoxShop;
    private Vehicle vehicle;

    public ReservationController(Vehicle vehicle) {
        this.vehicle=vehicle;
    }

    public void reservationConfirmAction(ActionEvent actionEvent) {
    }
}
