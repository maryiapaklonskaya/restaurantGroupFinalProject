package Sasha;

import java.sql.*;
import java.util.HashSet;
import java.util.Scanner;

public class Sasha {

    public static void scannerAddMeal(Connection connection) throws SQLException {
        HashSet<Integer> mealId = getAllMealsId(connection);

        Scanner helloWorld = new Scanner(System.in);
        System.out.println("\nEnter meal name: ");
        String mealName = helloWorld.nextLine();

        int mealTypeId = scannerChooseMealType(connection);
        if(!mealId.contains(mealTypeId)) { System.out.println("Please enter an existing id"); return;}

        System.out.println("\nEnter price: ");
        int mealPrice = helloWorld.nextInt();
//        тут можно проверку на ввод сделать, но я пока думаю над этим

        addMeal(connection, mealName, mealPrice, mealTypeId);
    }
    public static void theMostPopularMealsDESC(Connection connection) throws SQLException{
        String mostPopularMeal = "SELECT meal_title, count(quantity_of_meals) FROM restaurant.orders_items \n" +
                "INNER JOIN restaurant.meals ON orders_items.meal_id = meals.id\n" +
                "GROUP BY meal_id ORDER BY count(quantity_of_meals) DESC LIMIT 5;";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(mostPopularMeal);

        while (resultSet.next()) {
            if(resultSet.getInt(2) > 1) {
                System.out.print("\""+resultSet.getString(1)+"\"");
                System.out.println(", amount of orders: " + resultSet.getInt(2));
            }
        }
    }
    public  static  void getMeanClosedOrders(Connection connection) throws SQLException{
        String getClosedOrders = "SELECT ROUND(SUM(price*quantity_of_meals),2) " +
                " FROM restaurant.orders_items INNER JOIN restaurant.meals ON orders_items.meal_id = meals.id INNER JOIN restaurant.orders " +
                " ON orders.id = orders_items.order_id WHERE status=\"closed\" GROUP BY order_id";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(getClosedOrders);

        double countRow = 0;
        double amount = 0;

        while (resultSet.next()) {
            amount += resultSet.getDouble(1);
            countRow ++;
        }

        System.out.println("Average closed check "+amount/countRow);
    }
    public static void sortWaitersBySumRevenueDESC(Connection connection) throws SQLException{
        String sortWaitersByRevenueDESC = "SELECT waiters.name, waiters.surname, ROUND(SUM(price*quantity_of_meals),2)\n" +
                " FROM restaurant.waiters INNER JOIN restaurant.tables ON waiters.id = tables.waiter_id\n" +
                " INNER JOIN restaurant.orders ON orders.tables_id = tables.id \n" +
                " INNER JOIN restaurant.orders_items ON orders_items.order_id = orders.id\n" +
                " INNER JOIN restaurant.meals ON orders_items.meal_id = meals.id\n" +
                " GROUP BY waiters.id ORDER BY ROUND(SUM(price*quantity_of_meals),2) DESC;";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sortWaitersByRevenueDESC);

        while (resultSet.next()) {
            System.out.print(resultSet.getString(1));
            System.out.print(" " + resultSet.getString(2));
            System.out.println("   Total amount: " + resultSet.getDouble(3));
        }
    }

    public static void getTop10ClosedOrdersDesc(Connection connection) throws SQLException{
        String Top10ClosedOrdersDesc = "SELECT order_id, restaurant.orders.tables_id, restaurant.orders.status, ROUND(SUM(price*quantity_of_meals),2) " +
                " FROM restaurant.orders_items INNER JOIN restaurant.meals ON orders_items.meal_id = meals.id INNER JOIN restaurant.orders " +
                " ON orders.id = orders_items.order_id WHERE status=\"closed\" GROUP BY order_id ORDER BY ROUND(SUM(price*quantity_of_meals),2) DESC LIMIT 10";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(Top10ClosedOrdersDesc);

        while (resultSet.next()) {
            System.out.print("Total amount: " + resultSet.getDouble(4));
            System.out.print("   Order ID: " + resultSet.getInt(1));
            System.out.print("  Table ID: " + resultSet.getInt(2));
            System.out.println("  Status: " + resultSet.getString(3));
        }
    }
    public static void main(String[] args) throws SQLException {
        Connection connection =
                DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "sadomazo911");

//        addMeal(connection, "lazania", 5, MealType.MAIN_COURSES);
//        findMeal(connection, "lazania");
//        setNewPrice(connection, "lazania", 100);
//        findMeal(connection, "lazania");
//        renameMeal(connection, "lazania", "lasagna");
//        findMeal(connection, "lazania");
//        findMeal(connection, "lasagna");
//        removeMeal(connection, "lazania");
//        removeMeal(connection, "lasagna");

//        getTop10ClosedOrdersDesc(connection);
//        sortWaitersBySumRevenueDESC(connection);
//        getMeanClosedOrders(connection);
        theMostPopularMealsDESC(connection);

//        scannerAddMeal(connection);
    }

    public static void addMeal(Connection connection, String mealTitle, int price, int mealTypeId) throws SQLException{
        if(isMealPresent(connection, mealTitle)) {System.out.println("This meal already exist");  return;}

        Meal meal = new Meal(mealTitle, price, mealTypeId);

        addToDatabase(connection, meal, mealTypeId);
    }

    public static void setNewPrice(Connection connection, String mealTitle, int newPrice) throws SQLException {
        if(!isMealPresent(connection, mealTitle)) {System.out.println("This meal isn't exist"); return;}

        String setNewPrice = "UPDATE restaurant.meals SET restaurant.meals.price = ? WHERE meal_title=?;";
        PreparedStatement pStatement = connection.prepareStatement(setNewPrice);
        pStatement.setInt(1, newPrice);
        pStatement.setString(2, mealTitle);
        pStatement.executeUpdate();
        pStatement.close();

//        UPDATE Restaurant.Meals SET Restaurant.Meals.Price = 100 WHERE Meal_id=70;
    }

    public static void renameMeal(Connection connection, String mealTitle, String newTitle) throws SQLException {
        if(!isMealPresent(connection, mealTitle)) {System.out.println("This meal isn't exist"); return;}

        int mealId = getMealId(connection, mealTitle);

        String setNewPrice = "UPDATE restaurant.meals SET restaurant.meals.meal_title = ? WHERE id=?;";
        PreparedStatement pStatement = connection.prepareStatement(setNewPrice);
        pStatement.setString(1, newTitle);
        pStatement.setInt(2, mealId);
        pStatement.executeUpdate();
        pStatement.close();
    }

    public static void findMeal(Connection connection, String mealTitle) throws SQLException{
        if(!isMealPresent(connection, mealTitle)) {System.out.println("This meal isn't exist"); return;}

        ResultSet rs = searchOfMeal(connection, mealTitle);
        onDisplayMeal(rs);
        rs.close();
    }

    public static void removeMeal(Connection connection, String mealTitle) throws SQLException{
        if(!isMealPresent(connection, mealTitle)) {System.out.println("This meal isn't exist"); return;}

        int mealIndex = getMealId(connection, mealTitle);

        String removeMeal = "DELETE FROM restaurant.meals WHERE id=?";
        PreparedStatement pStatement = connection.prepareStatement(removeMeal);
        pStatement.setInt(1, mealIndex);
        pStatement.executeUpdate();
        pStatement.close();
    }

    public static void addToDatabase(Connection connection, Meal meal, int mealTypeId) throws SQLException {
        String addNewMeal = "INSERT INTO Restaurant.Meals(meal_title, price, meal_type_id) VALUES (?, ?, ?);";
        PreparedStatement pStatement = connection.prepareStatement(addNewMeal);
        pStatement.setString(1, meal.getMealTitle());
        pStatement.setInt(2, meal.getPrice());
        pStatement.setInt(3, (mealTypeId));
        pStatement.executeUpdate();
        pStatement.close();

        System.out.println("Meal add to Database");
    }

    public static int getMealId(Connection connection, String mealTitle) throws SQLException{
        int mealIndex = 0;
        ResultSet rs = searchOfMeal(connection, mealTitle);
        while (rs.next()){
            mealIndex = rs.getInt("id");
        }

        return mealIndex;
    }

    public static int getMealPrice(Connection connection, String mealTitle) throws SQLException{
        int mealPrice = 0;
        ResultSet rs = searchOfMeal(connection, mealTitle);
        while (rs.next()){
            mealPrice = rs.getInt("price");
        }

        return mealPrice;
    }

    public static ResultSet searchOfMeal(Connection connection, String MealTitle) throws SQLException {
        String searchOfMeal = "SELECT * FROM restaurant.meals WHERE meal_title=\""+MealTitle+"\"";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(searchOfMeal);

        return resultSet;
    }

    public static boolean isMealPresent(Connection connection, String MealTitle) throws SQLException{
        boolean result = true;
        while(!searchOfMeal(connection, MealTitle).next()) {

            return result = false;
        }

        return result;
    }

    public static void onDisplayMeal(ResultSet rs) throws SQLException{
        while (rs.next()){
            System.out.print("Meal Title: "+rs.getString("meal_title"));
            System.out.println("Price: "+rs.getInt("price"));
        }
    }

    public static void onDisplayMealType(ResultSet rs) throws SQLException{
        System.out.println("\n");
        while (rs.next()){
            System.out.print("Meal Id: "+rs.getString(1));
            System.out.println("   Meal type: "+rs.getString(2));
        }
    }

    public static HashSet getAllMealsId(Connection connection) throws SQLException{
        HashSet<Integer> mealId = new HashSet<>();

        String id = "SELECT id FROM restaurant.meal_type";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(id);

        while (resultSet.next()) {
            mealId.add(resultSet.getInt(1));
        }
        return mealId;
    }

    public static int scannerChooseMealType(Connection connection) throws SQLException{
        String mealType = "SELECT id, meal_type FROM restaurant.meal_type";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(mealType);
        onDisplayMealType(resultSet);

        Scanner helloWorld = new Scanner(System.in);

        System.out.println("\nEnter the Id the dish belongs to: ");
        int mealTypeId = helloWorld.nextInt();
        helloWorld.nextLine();

        return mealTypeId;
    }
}
