package sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class RentACarDAO {
    private static RentACarDAO instance;
    private Connection conn;
    private PreparedStatement getUsersQuery,getUserQuery,addUserQuery,maxIdUserQuery,getVehiclesQuery,addVehicleQuery,maxIdVehicleQuery,editVehicleQuery,deleteVehicleQuery,getVehiclesPerTypeQuery,getVehiclePerIdQuery, getUserPerUsername;

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
            getUserQuery=conn.prepareStatement("SELECT * FROM user WHERE username=? AND password=? AND type_user=?");
            addUserQuery=conn.prepareStatement("INSERT INTO user VALUES(?,?,?,?,?,?,?)");
            maxIdUserQuery=conn.prepareStatement("SELECT MAX(id)+1 FROM user");
            getVehiclesQuery=conn.prepareStatement("SELECT * FROM vehicle");
            addVehicleQuery=conn.prepareStatement("INSERT INTO vehicle VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            maxIdVehicleQuery=conn.prepareStatement("SELECT MAX(id)+1 FROM vehicle");
            editVehicleQuery=conn.prepareStatement("UPDATE vehicle SET name=?,brand=?,model=?,type=?,year=?,seats_number=?,doors_number=?,engine=?,transmission=?,fuel_consumption=?,color=?,price_per_day=?,availability=? WHERE id=?");
            deleteVehicleQuery=conn.prepareStatement("DELETE FROM vehicle WHERE id=?");
            getVehiclesPerTypeQuery=conn.prepareStatement("SELECT * FROM vehicle WHERE type=?");
            getVehiclePerIdQuery=conn.prepareStatement("SELECT * FROM vehicle WHERE id=?");
            getUserPerUsername=conn.prepareStatement("SELECT * FROM user WHERE username=?");
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
    public ArrayList<User> getUsers(){
        ArrayList<User>users=new ArrayList<>();
        try{
            ResultSet rs=getUsersQuery.executeQuery();
            while(rs.next())
                users.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    public User getUser(String username, String password, String radioText){
        User user=null;
        int userType=1;
        if(radioText.equals("Client"))userType=2;
        try{
            getUserQuery.setString(1, username);
            getUserQuery.setString(2, password);
            getUserQuery.setInt(3, userType);
            ResultSet rs=getUserQuery.executeQuery();
            if(!rs.next())return null;
            user=new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void addUser(User user){
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
    }
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
            addVehicleQuery.setString(4, vehicle.getType());
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
    public User getUserPerUsername(String username){
        User user=null;
        try{
            getUserPerUsername.setString(1, username);
            ResultSet rs=getUserPerUsername.executeQuery();
            if(!rs.next())return null;
            user=new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
