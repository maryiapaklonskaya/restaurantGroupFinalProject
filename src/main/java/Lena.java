import java.sql.*;
import java.util.Scanner;

public class Lena {

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant", "root", "jelenaandrejs");
            //reserveTable(connection);
            //unreserveTable(connection);
            //getAllMeals(connection);
            //viewAllMealsByType(connection);
            //getAllWaiters(connection);
            //addWaiter(connection);
            //removeWaiter(connection);
            //getAllOrders(connection);

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

    public static void  getAllWaiters (Connection connection) {
        String getWaiters = "SELECT * FROM restaurant.waiters";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getWaiters);

            while (resultSet.next()) {
                System.out.print(resultSet.getString(1));
                System.out.print(". " + resultSet.getString(2));
                System.out.println(" " + resultSet.getString(3));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addWaiter(Connection connection){

        System.out.println("Please enter waiter name: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();

        System.out.println("Please enter waiter surname: ");
        String surname = scanner.nextLine();

        String addWaiter = "INSERT INTO restaurant.waiters (name, surname) VALUES (?, ?)" ;

        try{
            PreparedStatement pStatement = connection.prepareStatement(addWaiter);
            pStatement.setString(1, name);
            pStatement.setString(2, surname);

            pStatement.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }

        System.out.println("Waiter " + name + " " + surname + " has been added.");

    }

    public static void removeWaiter(Connection connection){

        System.out.println("Please enter waiter id: ");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();

        String addWaiter = "DELETE FROM restaurant.waiters WHERE id = ?" ;

        try{
            PreparedStatement pStatement = connection.prepareStatement(addWaiter);
            pStatement.setInt(1, id);


            pStatement.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }

        System.out.println("Waiter with ID " + id + " has been removed.");

    }

    public static void  getAllOrders (Connection connection) {
        String getOrders = "SELECT restaurant.orders.id AS 'Order_id', restaurant.orders.tables_id, restaurant.orders.status, SUM(meals.price*quantity_of_meals) AS 'Total_Amount' \n" +
                "FROM restaurant.orders LEFT JOIN restaurant.orders_items ON orders.id = restaurant.orders_items.id" +
                " INNER JOIN restaurant.meals ON orders_items.meal_id = meals.id ORDER BY orders.id;";


        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getOrders);

            while (resultSet.next()) {
                System.out.print("Order ID: " + resultSet.getInt(1));
                System.out.print("  Table ID: " + resultSet.getInt(2));
                System.out.print("  Status: " + resultSet.getString(3));
                System.out.println("  Total amount: " + resultSet.getDouble(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
