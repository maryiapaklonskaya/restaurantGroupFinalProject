import java.sql.*;
import java.util.Scanner;

public class Maryia {
    public static int authID = 0;
    public static String userName = "";



    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant", "root", "rootroot");
            welcomeScreen(connection);
            //getAllOrdersByWaiterID(connection, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void welcomeScreen(Connection connection) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to the Restaurant System made by Sasha, Lena and Masha. \nFOR WAITERS: Please insert your personal ID. \nFOR ADMINS: Press 0. ");
        authID = scan.nextInt();
        if (authID == 0) {
            welcomeAdminScreen(connection);
        } else {
            checkWaiterExists(connection, authID);
        }
    }

    public static String checkWaiterExists(Connection connection, int authID) {
        String checkUserID = "SELECT restaurant.checkUserID(" + authID + ");";

        //resultSet = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(checkUserID);

            if (!(resultSet == null)) {
                while (resultSet.next()) {
                    userName = resultSet.getString(1);
                    System.out.println("\n" + userName + ", welcome to the Waiter Side of the Restaurant System.");
                    welcomeWaitersScreen(connection, userName);
                }
            } else {
                System.out.print("---> There is no such user, please try again <---");
                userName = null;
                welcomeScreen(connection);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userName;
    }

    public static void welcomeWaitersScreen(Connection connection, String userName){
        Scanner scan = new Scanner(System.in);
        if(userName == null){
            System.out.println(" ---> There is no such user, please try again <--- ");
            welcomeScreen(connection);
        } else {
            waitersChoice(connection, authID);
            }
        }

    public static void welcomeAdminScreen(Connection connection) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Welcome to the Admin Side of the Restaurant System. \nChoose what do you want to do:");

        //Дополнить тем, что делает админ

        int authID = scan.nextInt();

    }

    public static void  viewAllMealsByType (Connection connection) {
        String viewMeals = "SELECT meal_type, meal_title, price FROM restaurant.meals INNER JOIN restaurant.meal_type ON meal_type.id = meals.meal_type_id ORDER BY meal_type";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(viewMeals);

            while (resultSet.next()) {
                System.out.print(resultSet.getString(1));
                System.out.print(". " + resultSet.getString(2));
                System.out.println(". === " + resultSet.getDouble(3) + " euro");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void waitersChoice(Connection connection, int authID) {
        Scanner scan = new Scanner(System.in);

        System.out.println("\nWhat do you want to do next?\n1. View Menu\n2. Create order or edit order");
        int waitersChoice = scan.nextInt();
        if (waitersChoice == 1) {
            System.out.println("Here is a menu:\nMealName\t\t\t\tCategory\t\t\t\tPrice");
            viewAllMealsByType(connection);
            welcomeWaitersScreen(connection, userName);
        } else if (waitersChoice == 2) {
            System.out.println("Here is a list of all your orders. Choose one and insert Order_id below.\nID   Table\t\t\tStatus");
            getAllOrdersByWaiterID(connection, authID);
            checkOrders(connection);
        } else {
            System.out.println(" ---> There is no such option, please choose another one <--- ");
            waitersChoice(connection, authID);
        }
    }

    public static void getAllOrdersByWaiterID(Connection connection, int authID) {
        String viewMeals = "SELECT tables.waiter_id, orders.status, orders.id, orders.tables_id, meals.meal_title, orders_items.quantity_of_meals, meals.price FROM orders INNER JOIN orders_items ON orders.id = orders_items.order_id INNER JOIN meals ON orders_items.meal_id = meals.id INNER JOIN tables ON orders.tables_id = tables.id WHERE tables.waiter_id = " + authID + " ORDER BY orders.id, orders.status DESC";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(viewMeals);
            System.out.println("Here is all your orders:\nwaiter_id\t | status\t | order_id\t | table_id\t | meal_title\t\t\t | quantity\t | price_per_each");

            while (resultSet.next()) {
                System.out.print(resultSet.getInt(1) + "\t\t\t");
                System.out.print(" | " + resultSet.getString(2) + "\t\t");
                System.out.print(" | " + resultSet.getInt(3) + "\t\t");
                System.out.print(" | " + resultSet.getInt(4) + "\t\t");
                System.out.print(" | " + resultSet.getString(5) + "\t\t\t");
                System.out.print(" | " + resultSet.getInt(6) + "\t\t");
                System.out.println(" | " + resultSet.getDouble(7));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void checkOrders(Connection connection) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Insert Order_id:");
        int order_id_inserted = scan.nextInt();
        System.out.println("What do you want to do with order " + order_id_inserted +
                "?\n1. View full order by ID (check)\n" +
                "//2. Edit order by ID\n" +
                "//3. Cancel order отменить заказ (статус кансселлед) + разбронировать столик\n" +
                "//4. Close order закрыть ордер  (статус closed) + разбронировать столик\n");

        "1. Create an order  2. Edit existing order  3. Cancel order  4. Close order  " +
                "5. Create a checque \n 6. Reserve table  7. Take off reservation  8. View all meals  9. View all meals by type" );

//        switch (order_id_inserted) {
//            case 1:
//                viewOrderByID(connection, order_id_inserted); //название + цена + общая сумма и назвать ChecquePreview
//                waitersChoice(connection, authID);
//                break;
//            case 2:
//                editOrderByID(connection, order_id_inserted);
//                waitersChoice(connection, authID);
//                break;
//            case 3:
//                cancelOrderByID(connection, order_id_inserted);
//                waitersChoice(connection, authID);
//                break;
//            case 4:
//                closeOrderByID(connection, order_id_inserted);
//                waitersChoice(connection, authID);
//                break;
//
//            default:
//                waitersChoice(connection, authID);
//                break;
//        }
    }
}

