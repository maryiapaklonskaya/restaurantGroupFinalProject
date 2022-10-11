package Sasha;

import java.sql.*;

public class Sasha {
//                      - getTop10ClosedOrdersDesc()
//						- SortWaitersBySumRevenueDESC()
//						- getMeanClosedOrders() \\ avg
//						* theMostPopularMealsDESC() - count

    public static void getTop10ClosedOrdersDesc(){
        String findCloseOrders = "SELECT orders.id, orders_items.meal_id, orders_items.quantity_of_meals FROM orders LEFT JOIN orders_items ON orders.id = orders_items.order_id WHERE orders.status=\"Close\";";
    }
    public static void main(String[] args) throws SQLException {
        Connection connection =
                DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "sadomazo911");

        addMeal(connection, "lazania", 5, MealType.MAIN_COURSES);
        findMeal(connection, "lazania");
        setNewPrice(connection, "lazania", 100);
        findMeal(connection, "lazania");
        renameMeal(connection, "lazania", "lasagna");
        findMeal(connection, "lazania");
        findMeal(connection, "lasagna");
        removeMeal(connection, "lazania");
        removeMeal(connection, "lasagna");
    }

    public static void addMeal(Connection connection, String mealTitle, int price, MealType mealType) throws SQLException{
        if(isMealPresent(connection, mealTitle)) {System.out.println("This meal already exist");  return;}

        Meal meal = new Meal(mealTitle, price, mealType);
        int mealTypeId = getMealTypeId(connection, mealType);

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
        outputOnDisplay(rs);
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

    public static int getMealTypeId(Connection connection, MealType mealType) throws SQLException {
        String getMealTypeIndex = "SELECT id FROM restaurant.meal_type WHERE meal_type='"+mealType.getReadableName()+"';";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(getMealTypeIndex);
        resultSet.next();

        return resultSet.getInt(1);
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

    public static void outputOnDisplay(ResultSet rs) throws SQLException{
        while (rs.next()){
            System.out.println("Meal Title: "+rs.getString("meal_title")+
                    "\nPrice: "+rs.getInt("price")+"\n");
        }
    }
}
