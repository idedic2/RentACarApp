package sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class RentACarDAO {
    private static RentACarDAO instance;
    private Connection conn;
    private PreparedStatement doesExistCardQuery, getClientQuery, getAdminQuery, maxClientIdQuery, getUsersQuery,getUserQuery,addUserQuery,maxIdUserQuery,getVehiclesQuery,addVehicleQuery,maxIdVehicleQuery,editVehicleQuery,deleteVehicleQuery,getVehiclesPerTypeQuery,getVehiclePerIdQuery, getClientPerUsername, addReservationQuery, maxReservationIdQuery, maxIdCardQuery, addCardQuery, addClientQuery, getCardQuery, getUserPerId;

    private RentACarDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:rentacar.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            getUsersQuery = conn.prepareStatement("SELECT * FROM user");
        } catch (SQLException e) {
            regenerisiBazu();
            try {
                getUsersQuery = conn.prepareStatement("SELECT * FROM user");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        try{
            getClientQuery=conn.prepareStatement("SELECT u.username FROM client c, user u WHERE c.id=u.id AND u.username=? AND u.password=?");
            getAdminQuery=conn.prepareStatement("SELECT u.username FROM admin a, user u WHERE a.id=u.id AND u.username=? AND u.password=?");
            addUserQuery=conn.prepareStatement("INSERT INTO user VALUES(?,?,?,?,?,?)");
            maxIdUserQuery=conn.prepareStatement("SELECT MAX(id)+1 FROM user");
            getVehiclesQuery=conn.prepareStatement("SELECT * FROM vehicle");
            addVehicleQuery=conn.prepareStatement("INSERT INTO vehicle VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            maxIdVehicleQuery=conn.prepareStatement("SELECT MAX(id)+1 FROM vehicle");
            editVehicleQuery=conn.prepareStatement("UPDATE vehicle SET name=?,brand=?,model=?,type=?,year=?,seats_number=?,doors_number=?,engine=?,transmission=?,fuel_consumption=?,color=?,price_per_day=?,availability=? WHERE id=?");
            deleteVehicleQuery=conn.prepareStatement("DELETE FROM vehicle WHERE id=?");
            getVehiclesPerTypeQuery=conn.prepareStatement("SELECT * FROM vehicle WHERE type=?");
            getVehiclePerIdQuery=conn.prepareStatement("SELECT * FROM vehicle WHERE id=?");
            getClientPerUsername=conn.prepareStatement("SELECT u.id, u.first_name, u.last_name, u.email, u.username, u.password, c.address, c.telephone FROM user u, client c WHERE c.id=u.id AND u.username=?");
            maxReservationIdQuery=conn.prepareStatement("SELECT MAX(id)+1 FROM reservation");
            addReservationQuery=conn.prepareStatement("INSERT INTO reservation VALUES(?,?,?,?,?,?,?,?)");
            maxIdCardQuery=conn.prepareStatement("SELECT MAX(id)+1 FROM card");
            addCardQuery=conn.prepareStatement("INSERT INTO card VALUES(?,?,?,?)");
            addClientQuery=conn.prepareStatement("INSERT INTO client VALUES(?,?,?)");
            getCardQuery=conn.prepareStatement("SELECT * FROM card WHERE id=?");
            //getUserPerId=conn.prepareStatement("SELECT * FROM user WHERE id=?");
            maxClientIdQuery=conn.prepareStatement("SELECT MAX(id)+1 FROM client");
            doesExistCardQuery=conn.prepareStatement("SELECT * FROM card WHERE card_number=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
        public static RentACarDAO getInstance() {
        if (instance == null) instance = new RentACarDAO();
        return instance;
    }
    public static void removeInstance() {
        if (instance == null) return;
        instance.close();
        instance = null;
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void regenerisiBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("rentacar.db.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if ( sqlUpit.length() > 1 && sqlUpit.charAt( sqlUpit.length()-1 ) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    //metode koje izvrsavaju upite

    public boolean doesExistUser(String username, String password, String radioText) {
        boolean temp=false;
        if (radioText.equals("Client")) {
            try {
                getClientQuery.setString(1, username);
                getClientQuery.setString(2, password);
                ResultSet rsClient = getClientQuery.executeQuery();
                if (rsClient.next()) return true;
                return false;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            getAdminQuery.setString(1, username);
            getAdminQuery.setString(2, password);
            ResultSet rsAdmin=getAdminQuery.executeQuery();
            if(rsAdmin.next())temp=true;
            else temp=false;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }
    /*public void addUser(User user){
        try{
            int id=1;
            ResultSet rs=maxIdUserQuery.executeQuery();
            if(rs.next())id=rs.getInt(1);
            addUserQuery.setInt(1,id);
            addUserQuery.setString(2,user.getFirstName());
            addUserQuery.setString(3,user.getLastName());
            addUserQuery.setString(4,user.getEmail());
            addUserQuery.setString(5,user.getUsername());
            addUserQuery.setString(6,user.getPassword());
            addUserQuery.setInt(7,2);
            addUserQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
    public ArrayList<Vehicle> getVehicles(){
        ArrayList<Vehicle>vehicles=new ArrayList<>();
        try{
            ResultSet rs=getVehiclesQuery.executeQuery();
            while(rs.next())
            vehicles.add(new Vehicle(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getString(9), rs.getString(10), rs.getDouble(11), rs.getString(12), rs.getDouble(13), rs.getString(14)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }
    public void addVehicle(Vehicle vehicle){
        try{
            int id=1;
            ResultSet rs=maxIdVehicleQuery.executeQuery();
            if(rs.next())id=rs.getInt(1);
            addVehicleQuery.setInt(1, id);
            addVehicleQuery.setString(2, vehicle.getName());
            addVehicleQuery.setString(3, vehicle.getBrand());
            addVehicleQuery.setString(4, vehicle.getModel());
            addVehicleQuery.setString(5, vehicle.getType());
            addVehicleQuery.setInt(6, vehicle.getYear());
            addVehicleQuery.setInt(7, vehicle.getSeatsNumber());
            addVehicleQuery.setInt(8, vehicle.getDoorsNumber());
            addVehicleQuery.setString(9, vehicle.getEngine());
            addVehicleQuery.setString(10, vehicle.getTransmission());
            addVehicleQuery.setDouble(11, vehicle.getFuelConsumption());
            addVehicleQuery.setString(12, vehicle.getColor());
            addVehicleQuery.setDouble(13, vehicle.getPricePerDay());
            addVehicleQuery.setString(14, vehicle.getAvailability());
            addVehicleQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void editVehicle(Vehicle vehicle){
        try{
            editVehicleQuery.setString(1, vehicle.getName());
            editVehicleQuery.setString(2,vehicle.getBrand());
            editVehicleQuery.setString(3,vehicle.getModel());
            editVehicleQuery.setString(4, vehicle.getType());
            editVehicleQuery.setInt(5, vehicle.getYear());
            editVehicleQuery.setInt(6, vehicle.getSeatsNumber());
            editVehicleQuery.setInt(7, vehicle.getDoorsNumber());
            editVehicleQuery.setString(8, vehicle.getEngine());
            editVehicleQuery.setString(9, vehicle.getTransmission());
            editVehicleQuery.setDouble(10, vehicle.getFuelConsumption());
            editVehicleQuery.setString(11, vehicle.getColor());
            editVehicleQuery.setDouble(12, vehicle.getPricePerDay());
            editVehicleQuery.setString(13, vehicle.getAvailability());
            editVehicleQuery.setInt(14, vehicle.getId());
            editVehicleQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteVehicle(Vehicle vehicle){
        try{
            deleteVehicleQuery.setInt(1, vehicle.getId());
            deleteVehicleQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Vehicle>getVehiclesPerType(String type){
        ArrayList<Vehicle>vehicles=new ArrayList<>();
        try{
            getVehiclesPerTypeQuery.setString(1, type);
            ResultSet rs=getVehiclesPerTypeQuery.executeQuery();
            while(rs.next())
                vehicles.add(new Vehicle(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getString(9), rs.getString(10), rs.getDouble(11), rs.getString(12), rs.getDouble(13), rs.getString(14)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }
    public Vehicle getVehiclePerId(int id){
        Vehicle vehicle=null;
        try{
            getVehiclePerIdQuery.setInt(1, id);
            ResultSet rs=getVehiclePerIdQuery.executeQuery();
            if(!rs.next())return null;
            vehicle=new Vehicle(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getString(9), rs.getString(10), rs.getDouble(11), rs.getString(12), rs.getDouble(13), rs.getString(14));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicle;
    }
    public Client getClientPerUsername(String username){
        Client client=null;
        try{
            getClientPerUsername.setString(1, username);
            ResultSet rs=getClientPerUsername.executeQuery();
            if(!rs.next())return null;
            client=new Client(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }
    public void addReservation(Reservation reservation){
        int cardId=0;
        if(reservation.getCard()!=null)cardId=reservation.getCard().getId();
        try{
            int id=1;
            ResultSet rs=maxReservationIdQuery.executeQuery();
            if(rs.next())id=rs.getInt(1);
            addReservationQuery.setInt(1, id);
            addReservationQuery.setInt(2, reservation.getVehicle().getId());
            addReservationQuery.setInt(3, reservation.getClient().getId());
            addReservationQuery.setString(4, reservation.getPickUpDate().getDayOfMonth()+"/"+reservation.getPickUpDate().getMonth()+"/"+reservation.getPickUpDate().getYear());
            addReservationQuery.setString(5, reservation.getReturnDate().getDayOfMonth()+"/"+reservation.getReturnDate().getMonth()+"/"+reservation.getReturnDate().getYear());
            addReservationQuery.setString(6, reservation.getPickupTime());
            addReservationQuery.setString(7, reservation.getReturnTime());
            addReservationQuery.setInt(8, cardId);
            addReservationQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addCard(Card card){
        try{
            doesExistCardQuery.setString(1, card.getCardNumber());
            ResultSet rsCard=doesExistCardQuery.executeQuery();
            if(rsCard.next())return;
            int id=1;
            ResultSet rs=maxIdCardQuery.executeQuery();
            if(rs.next())id=rs.getInt(1);
            addCardQuery.setInt(1, id);
            addCardQuery.setString(2, card.getCardNumber());
            addCardQuery.setString(3, card.getCode());
            addCardQuery.setString(4, card.getExpirationDate().getMonth()+"/"+card.getExpirationDate().getYear());
            addCardQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addClient(Client client){
        try{
            int id=1;
            ResultSet rs=maxIdUserQuery.executeQuery();
            if(rs.next())id=rs.getInt(1);
            addUserQuery.setInt(1, id);
            addUserQuery.setString(2, client.getFirstName());
            addUserQuery.setString(3, client.getLastName());
            addUserQuery.setString(4, client.getEmail());
            addUserQuery.setString(5, client.getUsername());
            addUserQuery.setString(6, client.getPassword());
            addUserQuery.executeUpdate();
            addClientQuery.setInt(1, id);
            addClientQuery.setString(2, client.getAddress());
            addClientQuery.setString(3, client.getTelephone());
            addClientQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Card getCard(String cardNumber){
        Card card=null;
        try{
            doesExistCardQuery.setString(1, cardNumber);
            ResultSet rs=doesExistCardQuery.executeQuery();
            if(!rs.next())return null;
            LocalDate expireDate=stringToDate(rs.getString(4));
            card=new Card(rs.getInt(1), rs.getString(2), rs.getString(3), expireDate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return card;
    }
    public LocalDate stringToDate(String date){
        String []temp=date.split("\\.");
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("d/MM/yyyy");
        String d="";
        if(Integer.parseInt(temp[0])>=1 && Integer.parseInt(temp[0])<=9)d+="0";
        d+=temp[0]+"/";
        if(Integer.parseInt(temp[1]) >= 1 && Integer.parseInt(temp[1])<=9)d+="0";
        d+=temp[1]+"/"+temp[2];
        LocalDate localDate=LocalDate.parse(d, formatter);
        return localDate;
    }
    /*public Client getClient(int id){
        Client client=null;
        try{
            getClientQuery.setInt(1, id);
            ResultSet rs=getClientQuery.executeQuery();
            if(!rs.next())return null;
            getUserPerId.setInt(1, id);
            ResultSet userRs=getUserPerId.executeQuery();
            if(!userRs.next())return null;
            User user=new User(userRs.getInt(1), userRs.getString(2), userRs.getString(3), userRs.getString(4), userRs.getString(5), userRs.getString(6));
            if(rs.getInt(4)==0)
                return new Client(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getUsername(), user.getPassword(), rs.getString(1), rs.getString(2), null);
            else {
                getCardQuery.setInt(1, rs.getInt(4));
                ResultSet cardRs=getCardQuery.executeQuery();
                if(!cardRs.next())return null;
                LocalDate expireDate=stringToDate(cardRs.getString(4));
                Card card=new Card(cardRs.getInt(1), cardRs.getString(2), cardRs.getString(3), expireDate);
                client=new Client(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getUsername(), user.getPassword(), rs.getString(1), rs.getString(2), card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }*/

}
