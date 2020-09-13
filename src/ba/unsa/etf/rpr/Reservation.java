package ba.unsa.etf.rpr;


import java.time.DateTimeException;
import java.time.LocalDate;

public class Reservation {
    private int id;
    private Vehicle vehicle;
    private Client client;
    private Card card;
    private LocalDate pickUpDate;
    private LocalDate returnDate;
    private String pickupTime;
    private String returnTime;

    private boolean validateTime(String time){
        if(time.length()!=5)return false;
        if(!(time.charAt(0)>='0' && time.charAt(0)<='9')){
            System.out.println("1");
            return false;
        }
        if(!(time.charAt(1)>='0' && time.charAt(1)<='9')){
            System.out.println("2");
            return false;
        }
        if(time.charAt(2)!=':'){
            System.out.println("3");
            return false;
        }
        if(!(time.charAt(3)>='0' && time.charAt(3)<='9')){
            System.out.println("4");
            return false;
        }
        if(!(time.charAt(4)>='0' && time.charAt(4)<='9')){
            System.out.println("5");
            return false;
        }
        return true;
    }
    public Reservation(int id, Vehicle vehicle, Client client, LocalDate pickUpDate, LocalDate returnDate, String pickupTime, String returnTime, Card card) throws NegativeNumberException, InvalidTimeFormatException {
        if(id<0) throw new NegativeNumberException("Unijeli ste negativan broj");
        if(pickUpDate.isEqual(returnDate) || pickUpDate.isAfter(returnDate))throw new DateTimeException("Nevalidan format datuma");
        if(!validateTime(pickupTime))throw new InvalidTimeFormatException("Nevalidno vrijeme preuzimanja vozila");
        if(!validateTime(returnTime))throw new InvalidTimeFormatException("Nevalidno vrijeme vraćanja vozila");
        this.id = id;
        this.vehicle = vehicle;
        this.client = client;
        this.pickUpDate = pickUpDate;
        this.returnDate = returnDate;
        this.pickupTime = pickupTime;
        this.returnTime = returnTime;
        this.card=card;
    }
    public  boolean isOnline(){
        if(card!=null)return true;
        return false;
    }
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) throws InvalidTimeFormatException {
        if(!validateTime(pickupTime))throw new InvalidTimeFormatException("Nevalidno vrijeme preuzimanja vozila");
        this.pickupTime = pickupTime;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) throws InvalidTimeFormatException {
        if(!validateTime(returnTime))throw new InvalidTimeFormatException("Nevalidno vrijeme preuzimanja vozila");
        this.returnTime = returnTime;
    }


    public Reservation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) throws NegativeNumberException {
        if(id<0) throw new NegativeNumberException("Unijeli ste negativan broj");
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }


    public LocalDate getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(LocalDate pickUpDate) {
        if(this.returnDate!=null){
            if(pickUpDate.isEqual(returnDate) || pickUpDate.isAfter(returnDate))throw new DateTimeException("Nevalidni datumi");
        }
        this.pickUpDate = pickUpDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        if(this.pickUpDate!=null){
            if(pickUpDate.isEqual(returnDate) || pickUpDate.isAfter(returnDate))throw new DateTimeException("Nevalidni datumi");
        }
        this.returnDate = returnDate;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
