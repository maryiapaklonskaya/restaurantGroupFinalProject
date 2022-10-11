import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Maryia {


//    если ок ---> 0. viewAllMealsByType()
//					1. создать заказ() + бронировать столик вместе
//
//					2. редактировать заказ
//                        - getOrdersbyTableID()
//						- veiwOrder()
//								- изменить заказ
//								- отменить заказ (статус кансселлед) + разбронировать столик
//								- закрыть ордер  (статус closed) + разбронировать столик
//									- createСheсk()
//								* вернуться назад

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant", "root", "rootroot");
            welcomeScreen(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void welcomeScreen(Connection connection) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Welcome to the Restaurant System made by Sasha, Lena and Masha. \nFOR WAITERS: Please insert your personal ID. \nFOR ADMINS: Press 0. ");
        int authID = scan.nextInt();

        if (authID == 0) {
            welcomeAdminScreen(connection);
        } else {
            checkWaiterExists(connection, authID);
        }


        // SHOW LIST OF AVAILABLE (NOT BOOKED) TABLES


    }

    public static String checkWaiterExists(Connection connection, int authID) {
        String checkUserID = "SELECT restaurant.checkUserID(" + authID + ");";
        String userName = "";

         //resultSet = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(checkUserID);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!(resultSet == null)){

        }

        while (resultSet.next()) {
            userName = resultSet.getString(1);
            if (!(resultSet == null)) {
                welcomeWaitersScreen(connection, userName);
            } else {
                System.out.print("There is no such user, please try again");
                welcomeScreen(connection);
            }

            return userName;
        }
    }

    public static void welcomeWaitersScreen(Connection connection, String userName){
        Scanner scan = new Scanner(System.in);

        System.out.println(userName + ", welcome to the Waiter Side of the Restaurant System. \nChoose what do you want to do:");
        int authID = scan.nextInt();
    }

    public static void welcomeAdminScreen(Connection connection) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Welcome to the Admin Side of the Restaurant System. \nChoose what do you want to do:");
        int authID = scan.nextInt();

    }
}

