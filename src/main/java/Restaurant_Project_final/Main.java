package Restaurant_Project_final;

import java.sql.*;
import java.util.Scanner;

public class Main {

    public static int authID = 0;
    public static String userName = "";

    //EDITORDER NEEDS REVIEW

    public static void main(String[] args){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant", "root", "rootroot");

            welcomeScreen(connection);
//            checkWaiterExists(connection, 2);
//            welcomeWaitersScreen(connection, "Maryia");
//            viewAllMealsByType(connection);
//            waitersChoice(connection, authID);
//            getAllOrdersByWaiterID(connection, authID);
//            createNewOrder(connection);
//            addMealsToTheOrder(connection, orderID);
//            reserveTable(connection);
//            unreserveTable(connection);
//            editOrder(connection, orderID); !!!!! MAYBE DELETE???
//            cancelOrder(connection, orderID);
//            closeOrder(connection, orderID);
//            viewOrderByID(connection, orderID);

//            welcomeAdminScreen();
//            --- MEALS ---
//                1. View All Meals
//                2. View All Meals by Category
//                3. Add Meal
//                4. Remove Meal
//                5. Set new price for the Meal
//                6. Rename Meal
//
//                --- WAITERS ---
//                11. View All Waiters
//                12. Add New Waiter
//                13. Remove Waiter
//
//                --- ANALYTICS ---
//                21. View All Orders
//                22. View Top Popular Meals
//                23. View the Most Productive Waiters
//                24. Show Mean Revenue of Closed Orders;
//

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

        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(checkUserID);

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
        if(userName == null){
            System.out.println(" ---> There is no such user, please try again <--- ");
            welcomeScreen(connection);
        } else {
            waitersChoice(connection, authID);
        }
    }

    public static void  viewAllMealsByType (Connection connection) {
        String viewMeals = "SELECT id, meal_type, meal_title, price FROM restaurant.meals INNER JOIN restaurant.meal_type ON meal_type.id = meals.meal_type_id ORDER BY meal_type";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(viewMeals);

            while (resultSet.next()) {
                System.out.print(resultSet.getInt(1));
                System.out.print(". " + resultSet.getString(2));
                System.out.print(". " + resultSet.getString(3));
                System.out.println(". === " + resultSet.getDouble(4) + " euro");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void waitersChoice(Connection connection, int authID) {
        Scanner scan = new Scanner(System.in);
        int orderID;

        System.out.println("""

                What do you want to do next?

                1. View Menu
                2. Create order
                3. Edit order
                4. Cancel order
                5. Close order
                6. Create a cheque
                7. Reserve table
                8. Take off reservation""");


        int choice = scan.nextInt();
        switch (choice) {
            case 1 -> {
                //1. View Menu
                System.out.println("Here is a menu:\nMealName\t\t\t\tCategory\t\t\t\tPrice");
                viewAllMealsByType(connection);
                welcomeWaitersScreen(connection, userName);
            }
            case 2 ->
                //2. Create order
                    createNewOrder(connection);
            case 3 -> {
                //3. Edit order
                System.out.println("Here is a list of all your orders. " +
                        "Choose one and insert Order_id below.\nID   Table\t\t\tStatus");
                getAllOrdersByWaiterID(connection, authID);
                orderID = scan.nextInt();
                editOrder(connection, orderID);
            }
            case 4 -> {
                //4. Cancel order
                System.out.println("Here is a list of all your orders. " +
                        "Choose one and insert Order_id below.\nID   Table\t\t\tStatus");
                getAllOrdersByWaiterID(connection, authID);
                orderID = scan.nextInt();
                cancelOrder(connection, orderID);
            }
            case 5 -> {
                //5. Close order
                System.out.println("Here is a list of all your orders. " +
                        "Choose one and insert Order_id below.\nID   Table\t\t\tStatus");
                getAllOrdersByWaiterID(connection, authID);
                orderID = scan.nextInt();
                closeOrder(connection, orderID);
            }
            case 6 -> {
                //6. Create a cheque
                System.out.println("Here is a list of all your orders. " +
                        "Choose one and insert Order_id below.\nID   Table\t\t\tStatus");
                getAllOrdersByWaiterID(connection, authID);
                orderID = scan.nextInt();
                viewOrderByID(connection, orderID);
            }
            case 7 ->
                //7. Reserve table
                    reserveTable(connection);
            case 8 ->
                //8. Take off reservation
                    unreserveTable(connection);
            default -> {
                System.out.println(" ---> There is no such option, please choose another one <--- ");
                waitersChoice(connection, authID);
            }
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

    public static void createNewOrder(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        //FINDING AVAILABLE TABLES
        String displayAvailableTables = "SELECT id, table_reserved FROM tables WHERE tables.table_reserved = 0";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(displayAvailableTables);
            System.out.println("Here is all available tables:\nid\t | table_reserved");

            while (resultSet.next()) {
                System.out.print(resultSet.getInt(1) + "\t");
                System.out.println(" | " + resultSet.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Please enter table id:");
        int tables_id = scanner.nextInt();
        //END OF TABLE ID

        //TIPS PERCENTAGE
        System.out.println("Please enter wanted/deserved tips amount in format XX.XX: ");
        double tips = scanner.nextDouble();
        //END OF TIPS PERCENTAGE

        //<PAYMENT TYPE> IS DEFAULT WHILE OPENING ORDER
        //<STATUS> IS OPEN WHILE OPENING THE ORDER

        boolean payment_type = false;
        String status = "OPEN";

        String addNewOrder = "INSERT INTO restaurant.orders (tables_id, tips_percentage, status, payment_type) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement pStatement = connection.prepareStatement(addNewOrder);
            pStatement.setInt(1, tables_id);
            pStatement.setDouble(2, tips);
            pStatement.setString(3, status);
            pStatement.setBoolean(4, payment_type);
            pStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //GETTING ORDER_ID
        int latestOrderId = 0;
        String latestOrder = "SELECT max(id) FROM orders";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(latestOrder);
            while (resultSet.next()) {
                latestOrderId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //ADDING MEALS TO THE ORDER
        addMealsToTheOrder(connection, latestOrderId);
    }

    public static void addMealsToTheOrder(Connection connection, int orderID){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please view Menu and at the bottom enter meal id to order:");
        viewAllMealsByType(connection);
        System.out.println("Please enter meal id:");
        int meal_id = scanner.nextInt();
        System.out.println("Please enter quantity of the meal " +meal_id +":");
        int quantity_of_meals = scanner.nextInt();
        String addMeal = "INSERT INTO restaurant.orders_meals (meal_id, order_id, quantity_of_meals) VALUES (?, ?, ?)";

        try {
            PreparedStatement pStatement = connection.prepareStatement(addMeal);
            pStatement.setInt(1, meal_id);
            pStatement.setInt(2, orderID);
            pStatement.setInt(3, quantity_of_meals);

            pStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Order " + orderID + " has been created");
        System.out.println("Do you want to add more meals? Type 'yes' or 'no'");
        String repeatFunction = scanner.nextLine();
        if(repeatFunction.equalsIgnoreCase("yes")){
            addMealsToTheOrder(connection, orderID);
        } else {
            waitersChoice(connection, authID);
        }
    }

    public static void reserveTable(Connection connection) {
        String displayAvailableTables = "SELECT id, table_reserved FROM tables WHERE tables.table_reserved = 0";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(displayAvailableTables);
            System.out.println("Here is all available tables:\nid\t | table_reserved");

            while (resultSet.next()) {
                System.out.print(resultSet.getInt(1) + "\t");
                System.out.println(" | " + resultSet.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Enter number of the table to reserve: ");
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

        welcomeScreen(connection);

    }

    public static void unreserveTable(Connection connection) {
        String viewNotAvailableTables = "SELECT id, table_reserved FROM tables WHERE tables.table_reserved = 1";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(viewNotAvailableTables);
            System.out.println("Here is all reserved tables:\nid\t | table_reserved");

            while (resultSet.next()) {
                System.out.print(resultSet.getInt(1) + "\t");
                System.out.println(" | " + resultSet.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

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

        welcomeScreen(connection);

    }

    public static void editOrder(Connection connection, int orderID){
        System.out.println("IN PROGRESS");
        waitersChoice(connection, authID);
    }

    public static void cancelOrder(Connection connection, int orderID){
        int tableID = 0;
        String cancelOrder = "UPDATE restaurant.orders SET status = 'CANCELLED' WHERE id = ?";
        String selectOrderTable = "SELECT tables_id FROM orders WHERE id = " + orderID;
        String unreserveTable = "UPDATE restaurant.tables SET table_reserved = false WHERE id = ?";

        try {
            PreparedStatement pStatement = connection.prepareStatement(cancelOrder);
            pStatement.setInt(1, orderID);

            pStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectOrderTable);

            while (resultSet.next()) {
                tableID = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement pStatement = connection.prepareStatement(unreserveTable);
            pStatement.setInt(1, tableID);

            pStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Order No" + orderID + " was CANCELLED.");

        waitersChoice(connection, authID);

    }

    public static void closeOrder(Connection connection, int orderID){
        int tableID = 0;
        String closeOrder = "UPDATE restaurant.orders SET status = 'CLOSED' WHERE id = ?";
        String selectOrderTable = "SELECT tables_id FROM orders WHERE id = " + orderID;
        String unreserveTable = "UPDATE restaurant.tables SET table_reserved = false WHERE id = ?";

        try {
            PreparedStatement pStatement = connection.prepareStatement(closeOrder);
            pStatement.setInt(1, orderID);

            pStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectOrderTable);

            while (resultSet.next()) {
                tableID = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement pStatement = connection.prepareStatement(unreserveTable);
            pStatement.setInt(1, tableID);

            pStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Order No" + orderID + " was CLOSED.");

        waitersChoice(connection, authID);


    }

    public static void viewOrderByID(Connection connection, int orderID){
        String getOrder = "SELECT order_id, restaurant.orders.tables_id, restaurant.orders.status, ROUND(SUM(price*quantity_of_meals),2) " +
                " FROM restaurant.orders_items INNER JOIN restaurant.meals ON orders_items.meal_id = meals.id INNER JOIN restaurant.orders " +
                " ON orders.id = orders_items.order_id WHERE order_id = " + orderID + " GROUP BY order_id";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getOrder);

            while (resultSet.next()) {
                System.out.print("Order ID: " + resultSet.getInt(1));
                System.out.print("  Table ID: " + resultSet.getInt(2));
                System.out.print("  Status: " + resultSet.getString(3));
                System.out.println("  Total amount: " + resultSet.getDouble(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        waitersChoice(connection, authID);
    }

    public static void welcomeAdminScreen(Connection connection) {
        Scanner scan = new Scanner(System.in);

        System.out.println("""
                Welcome to the Admin Side of the Restaurant System.

                What do you want to do next?
                
                --- MEALS ---
                1. View All Meals
                2. View All Meals by Category
                3. Add Meal
                4. Remove Meal
                5. Set new price for the Meal
                6. Rename Meal
                
                --- WAITERS ---
                11. View All Waiters
                12. Add New Waiter
                13. Remove Waiter
                
                --- ANALYTICS --- 
                21. View All Orders
                22. View Top Popular Meals 
                23. View the Most Productive Waiters
                24. Show Mean Revenue of Closed Orders;
                
                """);

        int choice = scan.nextInt();

        switch (choice) {
            case 1 -> {
                getAllMeals(connection);
                welcomeAdminScreen(connection);
            }
            case 2 -> {
                viewAllMealsByType(connection);
                welcomeAdminScreen(connection);
            }
            case 3 -> {

            }
            case 4 -> {

            }
            case 5 -> {

            }
            case 6 -> {

            }
            case 11 -> {
                getAllWaiters(connection);
                welcomeAdminScreen(connection);
            }
            case 12 -> {
                addWaiter(connection);
                welcomeAdminScreen(connection);
            }
            case 13 -> {
                removeWaiter(connection);
                welcomeAdminScreen(connection);
            }

            case 21 -> {
                getAllOrders(connection);
                welcomeAdminScreen(connection);
            }
            case 22 -> {
                addWaiter(connection);
                welcomeAdminScreen(connection);
            }
            case 23 -> {
                removeWaiter(connection);
                welcomeAdminScreen(connection);
            }
            case 24 -> {
                removeWaiter(connection);
                welcomeAdminScreen(connection);
            }

//            22. View Top Popular Meals
//            23. View the Most Productive Waiters
//            24. Show Mean Revenue of Closed Orders;

            default -> {
                System.out.println(" ---> There is no such option, please choose another one <--- ");
                waitersChoice(connection, authID);
            }
        }

    }

    public static void getAllMeals (Connection connection) {
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
        String getOrders = "SELECT order_id, restaurant.orders.tables_id, restaurant.orders.status, ROUND(SUM(price*quantity_of_meals),2) " +
                " FROM restaurant.orders_items INNER JOIN restaurant.meals ON orders_items.meal_id = meals.id INNER JOIN restaurant.orders " +
                " ON orders.id = orders_items.order_id GROUP BY order_id";


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

