import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Lena {

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant", "root", "jelenaandrejs");
            reserveTable(connection);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void reserveTable(Connection connection) {

        System.out.println("Enter number of table you want to reserve: ");

        Scanner scanner = new Scanner(System.in);
        int tableNum = scanner.nextInt();

        String reserve = "UPDATE restaurant.tables SET table_reserved = true WHERE id = ?";

        try {
            PreparedStatement pStatement = connection.prepareStatement(reserve);
            pStatement.setInt(1, tableNum);

            pStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
