import java.sql.*;
import java.util.Scanner;

public class Orders {

    public static void main(String[] args){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant", "root", "rootroot");
            //createUser(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
