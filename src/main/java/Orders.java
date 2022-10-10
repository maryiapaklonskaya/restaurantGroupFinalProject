import java.sql.*;
import java.util.Scanner;

public class Orders {

    public static void main(String[] args){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant", "root", "rootroot");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}





