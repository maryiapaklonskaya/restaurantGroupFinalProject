import java.sql.*;
import java.util.Scanner;

public class Orders {

    public static void main(String[] args){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant", "root", "rootroot");
            //createUser(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    showAllOrders();
    //showAllOpenedOrders();
    //showAllClosedOrders();

//    newOrder()
//    addMealsToExistingOrder()
//    removeMeals()
//    createPrechek(calls for method "add tips %")
//    addTipsToTheWaiterSum()
//    closeOrder()
//    cancelOrder()
//    veiwOrder()



    public static void newOrder(Connection connection){
        Scanner scan = new Scanner(System.in);
        System.out.println("Select table number, if the order to go - press 0");

       // SHOW LIST OF AVAILABLE (NOT BOOKED) TABLES

        int tableNumber = scan.nextInt();
        if(!=0){
            System.out.println("Select WAITER ID");






        }


        1. getCheck();
        2. revenue();
        3 bestWaiter();
        4. Other;

        11. countWters

        4

          case(4) {
            print(5) "1. countWters()"
            print(6)

            scanner = 1 + 10

                    function(11);


        }




        waiterID FK
        tableID FK
        totalMealSum
                tipsPercentage
        totalTipsMoney
                paymentType
        status
            String userName = scan.nextLine();
            System.out.println("Enter USER AGE");
            int userAge = scan.nextInt();

            String createUserQuery = "INSERT INTO users (name, age) VALUES (?, ?);";
            String fetchUsers = "SELECT * FROM library.users;";

            System.out.println("ID   UserName \t UserAge");

            try {
                Statement statement = connection.createStatement();
                PreparedStatement pStatement = connection.prepareStatement(createUserQuery);
                pStatement.setString(1, userName);
                pStatement.setInt(2, userAge);

                pStatement.executeUpdate();
                ResultSet resultSet = statement.executeQuery(fetchUsers);

                while (resultSet.next()) {
                    System.out.print(resultSet.getInt(1) + "   ");
                    System.out.print(resultSet.getString(2) + "\t");
                    System.out.println(resultSet.getInt(3));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
