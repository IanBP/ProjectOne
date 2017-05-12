import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by christianbartram on 5/11/17.
 */
public class Run {


    private int command = 0; //Command corresponds to the number in the menu
    private String hostname;


    /**
     * Main Method
     * @param args
     */
    public static void main(String[] args) {
        //Create new class instance and call the NON static method
        Run r = new Run();
        Server s = new Server();

        r.printMenu();
        r.processInput();
        s.listen();
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
        System.out.println("Please type the numbers 1-7 to request data from the server.");
        System.out.println("Use CLI arguments -hostname to specify a desired hostname!");
        System.out.println("For example: 1 -hostname 127.0.0.1");
        System.out.println();



        //Iterate through the commands
        commands.forEach(System.out::println);

        System.out.println("------------------------------------------------------------");

    }


    /**
     * Validates that the input matches the selected criteria
     * @param input String input from scanner
     * @return true if the input is valid false otherwise
     */
    private boolean isValidInput(String input)
    {
        char[] valid = {'1', '2', '3', '4', '5', '6', '7'};

        for (char c : valid) {
            if (c == input.charAt(0)) {
                //The number is valid
                if(input.contains("-hostname")) {
                    //Contains the hostname argument
                    return true;
                }
            }
        }

        return false;
    }


    /**
     * Validates Input given from the scanner
     * sets hostname and command variables
     */
    private void processInput()
    {
        Scanner s = new Scanner(System.in);
        String input;

        try {

            input = s.nextLine();

            //Ensure number is between 1 and 7
            while(!isValidInput(input)) {
                System.err.println("Your input is invalid please enter a number between 1 and 7 and ensure that the -hostname flag is set");
                input = s.nextLine();
            }

            //We have our validated command
            command = Character.getNumericValue(input.charAt(0));

            char[] arr = input.toCharArray();
            int spaceCounter = 0;

            for(int i = 0; i < arr.length; i++) {
                if(arr[i] == ' ') {
                    spaceCounter++;

                    //The second space is the hostname value
                    if(spaceCounter == 2) {
                        hostname = input.substring(i + 1, arr.length);
                    }
                }
            }

        } catch(Exception e) {
            System.err.println("That input is invalid please enter a number between 1 and 7");
            e.printStackTrace();

        }

        s.close();

    }

}
