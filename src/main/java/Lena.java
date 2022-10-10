import java.sql.*;
import java.util.Scanner;

public class Lena {

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant", "root", "jelenaandrejs");
           viewAllMealsByType(connection);

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

        System.out.println("Table No" + tableNum + " was reserved.");

    }

    public static void unreserveTable(Connection connection) {

        System.out.println("Enter number of table you want to unreserve: ");

        Scanner scanner = new Scanner(System.in);
        int tableNum = scanner.nextInt();

        String reserve = "UPDATE restaurant.tables SET table_reserved = false WHERE id = ?";

        try {
            PreparedStatement pStatement = connection.prepareStatement(reserve);
            pStatement.setInt(1, tableNum);

            pStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Table No" + tableNum + " was unreserved.");

    }

    public static void  getAllMeals (Connection connection) {
        String getMeals = "SELECT id, meal_title, price FROM restaurant.meals;";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getMeals);

            while (resultSet.next()) {
                System.out.print(resultSet.getString(1));
                System.out.print(". " + resultSet.getString(2));
                System.out.println(", price: " + resultSet.getString(3));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void  viewAllMealsByType (Connection connection) {
        String viewMeals = "SELECT meal_type, meal_title FROM restaurant.meals INNER JOIN restaurant.meal_type ON meal_type.id = meals.meal_type_id ORDER BY meal_type";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(viewMeals);

            while (resultSet.next()) {
                System.out.print(resultSet.getString(1));
                System.out.println(". " + resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
