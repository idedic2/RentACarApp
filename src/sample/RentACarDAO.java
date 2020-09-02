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
    private PreparedStatement existEmailQuery, getEmployeePerUsernameQuery, getClientPerUsernameQuery, addEmployeeQuery, deleteEmployeeQuery, editEmployeeQuery, getEmployeesQuery, editReservationQuery, getVehiclesPerAvailabilityQuery, deleteCardQuery, deleteReservationQuery, getClientPerIdQuery, getReservationsQuery, deleteUserQuery, deleteClientQuery, editUserQuery, editClientQuery, getClientsQuery,doesExistCardQuery, getClientQuery, getEmployeeQuery, maxClientIdQuery, getUsersQuery,getUserQuery,addUserQuery,maxIdUserQuery,getVehiclesQuery,addVehicleQuery,maxIdVehicleQuery,editVehicleQuery,deleteVehicleQuery,getVehiclesPerTypeQuery,getVehiclePerIdQuery, getUserPerUsername, addReservationQuery, maxReservationIdQuery, maxIdCardQuery, addCardQuery, addClientQuery, getCardQuery, getUserPerId;

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
            getEmployeeQuery=conn.prepareStatement("SELECT u.username FROM employee e, user u WHERE e.id=u.id AND u.username=? AND u.password=? AND e.admin=?");
            addUserQuery=conn.prepareStatement("INSERT INTO user VALUES(?,?,?,?,?,?)");
            maxIdUserQuery=conn.prepareStatement("SELECT MAX(id)+1 FROM user");
            getVehiclesQuery=conn.prepareStatement("SELECT * FROM vehicle");
            addVehicleQuery=conn.prepareStatement("INSERT INTO vehicle VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            maxIdVehicleQuery=conn.prepareStatement("SELECT MAX(id)+1 FROM vehicle");
            editVehicleQuery=conn.prepareStatement("UPDATE vehicle SET name=?,brand=?,model=?,type=?,year=?,seats_number=?,doors_number=?,engine=?,transmission=?,fuel_consumption=?,color=?,price_per_day=?,availability=?, image=? WHERE id=?");
            deleteVehicleQuery=conn.prepareStatement("DELETE FROM vehicle WHERE id=?");
            getVehiclesPerTypeQuery=conn.prepareStatement("SELECT * FROM vehicle WHERE type=?");
            getVehiclePerIdQuery=conn.prepareStatement("SELECT * FROM vehicle WHERE id=?");
            getUserPerUsername=conn.prepareStatement("SELECT id, first_name, last_name, email, username, password FROM user  WHERE username=?");
            maxReservationIdQuery=conn.prepareStatement("SELECT MAX(id)+1 FROM reservation");
            addReservationQuery=conn.prepareStatement("INSERT INTO reservation VALUES(?,?,?,?,?,?,?,?)");
            maxIdCardQuery=conn.prepareStatement("SELECT MAX(id)+1 FROM card");
            addCardQuery=conn.prepareStatement("INSERT INTO card VALUES(?,?,?,?)");
            addClientQuery=conn.prepareStatement("INSERT INTO client VALUES(?,?,?)");
            getCardQuery=conn.prepareStatement("SELECT * FROM card WHERE id=?");
            //getUserPerId=conn.prepareStatement("SELECT * FROM user WHERE id=?");
            maxClientIdQuery=conn.prepareStatement("SELECT MAX(id)+1 FROM client");
            doesExistCardQuery=conn.prepareStatement("SELECT * FROM card WHERE card_number=?");
            getClientsQuery=conn.prepareStatement("SELECT u.id, u.first_name, u.last_name, u.email, u.username, u.password, c.address, c.telephone FROM user u, client c WHERE c.id=u.id");
            editClientQuery=conn.prepareStatement("UPDATE client SET address=?, telephone=? WHERE id=?");
            editUserQuery=conn.prepareStatement("UPDATE user SET first_name=?, last_name=?, email=?, username=?, password=? WHERE id=?");
            deleteClientQuery=conn.prepareStatement("DELETE FROM client WHERE id=?");
            deleteUserQuery=conn.prepareStatement("DELETE FROM user WHERE id=?");
            getReservationsQuery=conn.prepareStatement("SELECT * FROM reservation");
            getClientPerIdQuery=conn.prepareStatement("SELECT u.id, u.first_name, u.last_name, u.email, u.username, u.password, c.address, c.telephone FROM user u, client c WHERE c.id=u.id AND u.id=?");
            deleteCardQuery=conn.prepareStatement("DELETE FROM card WHERE id=?");
            deleteReservationQuery=conn.prepareStatement("DELETE FROM reservation WHERE id=?");
            getVehiclesPerAvailabilityQuery=conn.prepareStatement("SELECT * FROM vehicle WHERE availability=?");
            editReservationQuery=conn.prepareStatement("UPDATE reservation SET vehicle_id=?, client_id=?, pickup_date=?, return_date=?, pickup_time=?, return_time=?, card_id=? WHERE id=?");
            getEmployeesQuery=conn.prepareStatement("SELECT u.id, u.first_name, u.last_name, u.email, u.username, u.password, e.admin FROM user u, employee e WHERE u.id=e.id AND e.admin=?");
            editEmployeeQuery=conn.prepareStatement("UPDATE employee SET admin=? WHERE id=?");
            deleteEmployeeQuery=conn.prepareStatement("DELETE FROM employee WHERE id=?");
            addEmployeeQuery=conn.prepareStatement("INSERT INTO employee VALUES(?,?)");
            getClientPerUsernameQuery=conn.prepareStatement("SELECT u.id, u.first_name, u.last_name, u.email, u.username, u.password, c.address, c.telephone FROM user u, client c WHERE c.id=u.id AND u.username=?");
            getEmployeePerUsernameQuery=conn.prepareStatement("SELECT u.id, u.first_name, u.last_name, u.email, u.username, u.password, e.admin FROM user u, employee e WHERE e.id=u.id AND u.username=?");
            existEmailQuery=conn.prepareStatement("SELECT * FROM user WHERE email=?");
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

    public Connection getConn() {
        return conn;
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
        if (radioText.equals("Klijent")) {
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
        if (radioText.equals("Zaposlenik")) {
            try {
                getEmployeeQuery.setString(1, username);
                getEmployeeQuery.setString(2, password);
                getEmployeeQuery.setString(3, "no");
                ResultSet rsEmployee = getEmployeeQuery.executeQuery();
                if (rsEmployee.next()) return true;
                return false;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            getEmployeeQuery.setString(1, username);
            getEmployeeQuery.setString(2, password);
            getEmployeeQuery.setString(3, "yes");
            ResultSet rsAdmin=getEmployeeQuery.executeQuery();
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
    public ArrayList<Vehicle> getVehicles() {
        ArrayList<Vehicle>vehicles=new ArrayList<>();
        try{
            ResultSet rs=getVehiclesQuery.executeQuery();
            while(rs.next()) {
                try {
                    vehicles.add(new Vehicle(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getString(9), rs.getString(10), rs.getDouble(11), rs.getString(12), rs.getDouble(13), rs.getString(14), rs.getString(15)));
                } catch (NegativeNumberException e) {
                    e.printStackTrace();
                }
            }
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
            addVehicleQuery.setString(15, vehicle.getImage());
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
            editVehicleQuery.setString(14, vehicle.getImage());
            editVehicleQuery.setInt(15, vehicle.getId());
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
            while(rs.next()) {
                try {
                    vehicles.add(new Vehicle(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getString(9), rs.getString(10), rs.getDouble(11), rs.getString(12), rs.getDouble(13), rs.getString(14), rs.getString(15)));
                }
                catch (NegativeNumberException e){
                    e.printStackTrace();
                }
            }

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
            try {
                vehicle = new Vehicle(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getString(9), rs.getString(10), rs.getDouble(11), rs.getString(12), rs.getDouble(13), rs.getString(14), rs.getString(15));
            }
            catch (NegativeNumberException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicle;
    }
    public boolean getUserPerUsername(String username){
        try{
            getUserPerUsername.setString(1, username);
            ResultSet rs=getUserPerUsername.executeQuery();
            if(rs.next())return true;

            } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Client getClientPerId(int id){
        Client client=null;
        try{
            getClientPerIdQuery.setInt(1, id);
            ResultSet rs=getClientPerIdQuery.executeQuery();
            if(!rs.next())return null;
            try {
                client = new Client(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
            }
            catch (NegativeNumberException e){
                e.printStackTrace();
            }
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
            System.out.println(reservation.getVehicle().getId());
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
    public void editReservation(Reservation reservation){
        int cardId=0;
        if(reservation.getCard()!=null)cardId=reservation.getCard().getId();
        try{
            editReservationQuery.setInt(1, reservation.getVehicle().getId());
            editReservationQuery.setInt(2, reservation.getClient().getId());
            editReservationQuery.setString(3, reservation.getPickUpDate().getDayOfMonth()+"/"+reservation.getPickUpDate().getMonth()+"/"+reservation.getPickUpDate().getYear());
            editReservationQuery.setString(4, reservation.getReturnDate().getDayOfMonth()+"/"+reservation.getReturnDate().getMonth()+"/"+reservation.getReturnDate().getYear());
            editReservationQuery.setString(5, reservation.getPickupTime());
            editReservationQuery.setString(6, reservation.getReturnTime());
            editReservationQuery.setInt(7, cardId);
            editReservationQuery.setInt(8, reservation.getId());
            editReservationQuery.executeUpdate();
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
            addCardQuery.setString(4, card.getExpirationDate().getDayOfMonth()+"/"+getMonth(card.getExpirationDate().getMonth().toString())+"/"+card.getExpirationDate().getYear());
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
    public void addEmployee(Employee employee){
        try{
            int id=1;
            ResultSet rs=maxIdUserQuery.executeQuery();
            if(rs.next())id=rs.getInt(1);
            addUserQuery.setInt(1, id);
            addUserQuery.setString(2, employee.getFirstName());
            addUserQuery.setString(3, employee.getLastName());
            addUserQuery.setString(4, employee.getEmail());
            addUserQuery.setString(5, employee.getUsername());
            addUserQuery.setString(6, employee.getPassword());
            addUserQuery.executeUpdate();
            addEmployeeQuery.setInt(1, id);
            addEmployeeQuery.setString(2, employee.getAdmin());
            addEmployeeQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private int getMonth(String month){
        if(month.equals("JANUARY"))return 1;
        if(month.equals("FEBRUARY"))return 2;
        if(month.equals("MARCH"))return 3;
        if(month.equals("APRIL"))return 4;
        if(month.equals("MAY"))return 5;
        if(month.equals("JUNE"))return 6;
        if(month.equals("JULY"))return 7;
        if(month.equals("AUGUST"))return 8;
        if(month.equals("SEPTEMBER"))return 9;
        if(month.equals("OCTOBER"))return 10;
        if(month.equals("NOVEMBER"))return 11;
        return 12;
    }
    public Card getCard(String cardNumber){
        Card card=null;
        try{
            doesExistCardQuery.setString(1, cardNumber);
            ResultSet rs=doesExistCardQuery.executeQuery();
            if(!rs.next())return null;
            LocalDate expireDate=stringToDate(rs.getString(4));
            try {
                card = new Card(rs.getInt(1), rs.getString(2), rs.getString(3), expireDate);
            }
            catch (NegativeNumberException e){
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return card;
    }
    public Card getCardPerId(int id){
        Card card=null;
        try{
            getCardQuery.setInt(1, id);
            ResultSet rs=getCardQuery.executeQuery();
            if(!rs.next())return null;
            LocalDate expireDate=stringToDate(rs.getString(4));
            try {
                card = new Card(rs.getInt(1), rs.getString(2), rs.getString(3), expireDate);
            }
            catch (NegativeNumberException e){
                e.printStackTrace();
            }
            } catch (SQLException e) {
            e.printStackTrace();
        }
        return card;
    }
    public ArrayList<Client>getClients(){
        ArrayList<Client>clients=new ArrayList<>();
        try{
            ResultSet rs=getClientsQuery.executeQuery();
            while(rs.next()) {
                try {
                    clients.add(new Client(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
                }
                catch (NegativeNumberException e){
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }
    private boolean allLetters(String str){
        return  str.chars().allMatch(Character::isLetter);
    }
    public LocalDate stringToDate(String date){
        String []temp=date.split("/");
        if(allLetters(temp[1])){
            int pom=getMonth(temp[1]);
            temp[1]=Integer.toString(pom);
        }
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("d/MM/yyyy");
        String d="";
        if(Integer.parseInt(temp[0])>=1 && Integer.parseInt(temp[0])<=9)d+="0";
        d+=temp[0]+"/";
        if(Integer.parseInt(temp[1]) >= 1 && Integer.parseInt(temp[1])<=9)d+="0";
        d+=temp[1]+"/"+temp[2];
        LocalDate localDate=LocalDate.parse(d, formatter);
        return localDate;
    }
    public void editClient(Client client){
        try{
            editUserQuery.setString(1, client.getFirstName());
            editUserQuery.setString(2, client.getLastName());
            editUserQuery.setString(3, client.getEmail());
            editUserQuery.setString(4, client.getUsername());
            editUserQuery.setString(5, client.getPassword());
            editUserQuery.setInt(6, client.getId());
            editUserQuery.executeUpdate();
            editClientQuery.setString(1, client.getAddress());
            editClientQuery.setString(2, client.getTelephone());
            editClientQuery.setInt(3, client.getId());
            editClientQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteClient(Client client){
        int id=client.getId();
        try{
            deleteClientQuery.setInt(1, id);
            deleteClientQuery.executeUpdate();
            deleteUserQuery.setInt(1, id);
            deleteUserQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void editEmployee(Employee employee){
        try{
            editUserQuery.setString(1, employee.getFirstName());
            editUserQuery.setString(2, employee.getLastName());
            editUserQuery.setString(3, employee.getEmail());
            editUserQuery.setString(4, employee.getUsername());
            editUserQuery.setString(5, employee.getPassword());
            editUserQuery.setInt(6, employee.getId());
            editUserQuery.executeUpdate();
            //editEmployeeQuery.setString(1, employee.getAdmin());
            //editEmployeeQuery.setInt(2, employee.getId());
            //editEmployeeQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteEmployee(Employee employee){
        int id=employee.getId();
        try{
            deleteEmployeeQuery.setInt(1, id);
            deleteEmployeeQuery.executeUpdate();
            deleteUserQuery.setInt(1, id);
            deleteUserQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Reservation> getReservations(){
        ArrayList<Reservation>reservations=new ArrayList<>();
        try{
            ResultSet rs=getReservationsQuery.executeQuery();
            while(rs.next()) {
                Vehicle vehicle = getVehiclePerId(rs.getInt(2));
                if (vehicle == null) return null;
                Client client = getClientPerId(rs.getInt(3));
                if (client == null) return null;
                Card card = null;
                if (rs.getInt(8) != 0) card = getCardPerId(rs.getInt(8));
                try {
                    reservations.add(new Reservation(rs.getInt(1), vehicle, client, stringToDate(rs.getString(4)), stringToDate(rs.getString(5)), rs.getString(6), rs.getString(7), card));
                }
                catch (NegativeNumberException e){
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    //vraca listu rezervacija jer jedan klijent moze imati vise rezervacija
    public ArrayList<Reservation> getClientReservations(Client client){
        ArrayList<Reservation>reservations=new ArrayList<>();
        ArrayList<Reservation>clientReservations=new ArrayList<>();
        try{
            reservations=getReservations();
            for(Reservation reservation: reservations){
                if(reservation.getClient().getId()==client.getId())
                    clientReservations.add(reservation);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientReservations;
    }
    public void deleteCard(Card card){
        try{
            deleteCardQuery.setInt(1, card.getId());
            deleteCardQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteReservation(Reservation reservation){
        try{
            //obrisati karticu, reci da je vozilo dostupno, klijenta ne brisemo
            if(reservation.getCard()!=null)deleteCard(reservation.getCard());
            editVehicle(reservation.getVehicle());
            deleteReservationQuery.setInt(1, reservation.getId());
            deleteReservationQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Vehicle>getVehiclesPerAvailability(){
        ArrayList<Vehicle>vehicles=new ArrayList<>();
        try{
            getVehiclesPerAvailabilityQuery.setString(1, "DA");
            ResultSet rs=getVehiclesPerAvailabilityQuery.executeQuery();
            //if(!rs.next())return null;
            while(rs.next()) {
                try {
                    vehicles.add(new Vehicle(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getString(9), rs.getString(10), rs.getDouble(11), rs.getString(12), rs.getDouble(13), rs.getString(14), rs.getString(15)));
                } catch (NegativeNumberException e) {
                    e.printStackTrace();
                }
            }
                } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }
    public boolean isVehicleReserved(Vehicle vehicle){
        ArrayList<Reservation>reservations=new ArrayList<>();
        try{
            reservations=getReservations();
            for(Reservation r:reservations){
                if(r.getVehicle().getId()==vehicle.getId())
                    return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean clientInReservations(Client client){
        ArrayList<Reservation>reservations=new ArrayList<>();
        try{
            reservations=getReservations();
            for(Reservation r:reservations){
                if(r.getClient().getId()==client.getId())
                    return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public ArrayList<Employee> getEmployees(){
        ArrayList<Employee>employees=new ArrayList<>();
        try {
            getEmployeesQuery.setString(1, "no");
            ResultSet rs = getEmployeesQuery.executeQuery();
            while (rs.next()) {
                try {
                    employees.add(new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
                }
                catch (NegativeNumberException e) {
                    e.printStackTrace();
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
    public Client getClientPerUsername(String username){
        Client client=null;
        try{
            getClientPerUsernameQuery.setString(1, username);
            ResultSet rs=getClientPerUsernameQuery.executeQuery();
            if(!rs.next())return null;
            try {
                client = new Client(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
            }
            catch (NegativeNumberException e){
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }
    public Employee getEmployeePerUsername(String username){
        Employee employee=null;
        try{
            getEmployeePerUsernameQuery.setString(1, username);
            ResultSet rs=getEmployeePerUsernameQuery.executeQuery();
            if(!rs.next())return null;
            try {
                employee = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
            }
            catch (NegativeNumberException e){
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }
    public boolean existEmail(String email){
        try{
            existEmailQuery.setString(1, email);
            ResultSet rs=existEmailQuery.executeQuery();
            if(rs.next())return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
