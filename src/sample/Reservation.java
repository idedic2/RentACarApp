package sample;


import java.time.LocalDate;
import java.util.Date;

public class Reservation {
    private int id;
    private Vehicle vehicle;
    private User user;
    private LocalDate pickUpDate;
    private LocalDate returnDate;
    private String pickupTime;
    private String returnTime;

    public Reservation(int id, Vehicle vehicle, User user, LocalDate pickUpDate, LocalDate returnDate, String pickupTime, String returnTime) {
        this.id = id;
        this.vehicle = vehicle;
        this.user = user;
        this.pickUpDate = pickUpDate;
        this.returnDate = returnDate;
        this.pickupTime = pickupTime;
        this.returnTime = returnTime;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }


    public Reservation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(LocalDate pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
