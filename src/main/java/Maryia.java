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

    public static void main(String[] args){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "rootroot");
            createUser(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //welcomeScreen();

    }

    public static void welcomeScreen(){
        Scanner scan = new Scanner(System.in);

        System.out.println("Welcome to the Restaurant System made by Sasha, Lena and Masha. \n FOR WAITERS: Please insert your personal ID. \n FOR ADMINS: Press 0. ");
        int authID = scan.nextInt();

        checkWaiterExists(authID);
        public static String checkWaiterExists(Connection connection, int authID){
            String checkUserID = "SELECT restaurant.checkUserID(" + authID + ");";

            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(checkUserID);
                if(!(resultSet == null)){
                    String userName = resultSet.getString(1);
                }

                while (resultSet.next()) {
                    System.out.print(resultSet.getInt(1) + "   ");
                System.out.print(resultSet.getString(2) + "\t");
                System.out.println(resultSet.getInt(3));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        GET ALL USERS BY ID AND NAME

        switch(firstChoice){
            case(0): ;
            case(): ;
            case(0): ;
            default
        }


        System.out.println("Select table number, if the order to go - press 0");

        // SHOW LIST OF AVAILABLE (NOT BOOKED) TABLES


    }
}
