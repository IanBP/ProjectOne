import java.io.BufferedReader;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by christianbartram on 5/11/17.
 */
public class Run {


    private int command = 0; //Command corresponds to the number in the menu

    /**
     * Main Method
     * @param args
     */
    public static void main(String[] args) {
        //Create new class instance and call the NON static method
        Run r = new Run();

        r.printMenu();
        r.processInput();
    }


    /**
     * Prints a menu on the client side for a user to make a selection from
     */
    private void printMenu()
    {
        LinkedList<String> commands = new LinkedList<>();

        //Add commands to the linked list
        commands.add("1 - Current Date & Time");
        commands.add("2 - Uptime");
        commands.add("3 - Memory Use");
        commands.add("4 - Network Statistics");
        commands.add("5 - Current Users");
        commands.add("6 - Hosts Running Processes");
        commands.add("7 - Quit");


        System.out.println("------------------------------------------------------------");
        System.out.println("Please type the numbers 1-7 to request data from the server!");


        //Iterate through the commands
        commands.forEach(System.out::println);

        System.out.println("------------------------------------------------------------");

    }

    private void processInput()
    {
        Scanner s = new Scanner(System.in);

        try {

            int input = s.nextInt();

            //Ensure valid input
            switch(input) {
                case 1:
                    command = 1;
                    break;
                case 2:
                    command = 2;
                    break;
                case 3:
                    command = 3;
                    break;
                case 4:
                    command = 4;
                    break;
                case 5:
                    command = 5;
                    break;
                case 6:
                    command = 6;
                    break;
                case 7:
                    command = 7;
                    System.out.println("Thank you exiting!");
                    System.exit(0);
                    break;
                default:
                    System.err.println("That is not valid input please enter a number 1-7");
            }

        } catch(InputMismatchException e) {
            System.err.println("That input is invalid please enter a number between 1 and 7");
        }

        s.close();

    }




}
