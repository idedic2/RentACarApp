package sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class RentACarDAO {
    private static RentACarDAO instance;
    private Connection conn;
    private PreparedStatement getUsersQuery,getUserQuery;
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

}
