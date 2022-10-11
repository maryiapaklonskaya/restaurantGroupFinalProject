import java.util.Scanner;

public class TestByLena {

    public static void main(String[] args) {
        System.out.println("Welcome to the Restaurant System made by Sasha, Lena and Masha. \nFOR WAITERS: Press 1. \nFOR ADMINS: Press 0. ");

        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();

        if (input == 1) {
            System.out.println("Welcome to the Waiter Side of the Restaurant System! Please enter number of operation to do:");
            System.out.println("");
            System.out.println("1. Create an order  2. Edit existing order  3. Cancel order  4. Close order  " +
                    "5. Create a checque \n 6. Reserve table  7. Take off reservation  8. View all meals  9. View all meals by type" );
            int waiterChoice = scanner.nextInt();

            switch (waiterChoice){
                case 1:
                    //Here we will input function 1
                    break;
                case 2:
                    //Here we will input function 2
                    break;
                case 3:
                    //Here we will input function 3
                    break;
                case 4:
                    //Here we will input function 4
                    break;
                case 5:
                    //Here we will input function 5
                    break;
                case 6:
                    //Here we will input function 6
                    break;
                case 7:
                    //Here we will input function 7
                    break;
                case 8:
                    //Here we will input function 8
                    break;
                case 9:
                    //Here we will input function 9
                    break;
                default:
                    System.out.println("Wrong input. Please try again!");

            }


        } else if (input == 0) {
            System.out.println("Welcome to the Admin Side of the Restaurant System. Please enter number of operation to do:");
            System.out.println("");
            System.out.println("1. View all meals  2. View all meals by type  3. View all waiters  4. Add waiter  " +
                    "5. Remove waiter  6. View all orders  \n 7. Top 10 closed orders  8. Waiter rating by revenue  " +
                    "9. Average amount of closed orders  10. Most popular meals" );
            int adminChoice = scanner.nextInt();

            switch (adminChoice){
                case 1:
                    //Here we will input function 1
                    break;
                case 2:
                    //Here we will input function 2
                    break;
                case 3:
                    //Here we will input function 3
                    break;
                case 4:
                    //Here we will input function 4
                    break;
                case 5:
                    //Here we will input function 5
                    break;
                case 6:
                    //Here we will input function 6
                    break;
                case 7:
                    //Here we will input function 7
                    break;
                case 8:
                    //Here we will input function 8
                    break;
                case 9:
                    //Here we will input function 9
                    break;
                case 10:
                    //Here we will input function 9
                    break;
                default:
                    System.out.println("Wrong input. Please try again!");

            }

        } else {
            System.out.println("Your input is incorrect. Please try to log in again.");
        }
    }
}
