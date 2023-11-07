import java.util.*;
import java.io.*;

public class Main {
    public static void menu(){
        System.out.println("1. Reserve Seats");
        System.out.println("2. Exit");
    }
    public static int[] getRowsAndColumns(String fileName) throws Exception {
        FileInputStream inFile = new FileInputStream(new File(fileName));
        Scanner fileScanner = new Scanner(inFile);
    
        int rows = 0;
        int columns = 0;
    
        while(fileScanner.hasNextLine()){
            String line = fileScanner.nextLine();
            rows++;
            columns = line.length();
        }
    
        fileScanner.close();
    
        return new int[]{rows, columns};
    }

    // Checks if the user input is valid for the row and tickets
    public static int getValidInput(Scanner scnr, String prompt, int minValue, int maxValue) {
        int userInput;
        boolean valid = false;
        while (!valid) {
            System.out.print(prompt);
            userInput = scnr.nextInt();
            if (userInput >= minValue && userInput <= maxValue) { // If the input falls below the min value or above the max value
                valid = true;
                return userInput;
            }
        }
        return -1; // This line should never be reached
    }

    // This is the main method that will be executed
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int rows = 0, columns = 0;
        int userSeatInt = 0, userRow = 0, userAdultTickets = 0, userChildTickets = 0, userSeniorTickets = 0;
        char userSeat = ' ';

        // Create auditorium object
        Auditorium<Seat> auditorium = new Auditorium<>();

        // Ask user for file name and creste file object
        System.out.print("Enter file name: ");
        try{
            String fileName = scanner.nextLine();
            FileInputStream inFile = new FileInputStream(new File(fileName));

            // Create scanner object to read from file
            Scanner fileScanner = new Scanner(inFile);

            // Get rows and columns from file
            int[] rowsAndColumns = getRowsAndColumns(fileName);
            rows = rowsAndColumns[0];
            columns = rowsAndColumns[1];
            
            // Fill auditorium with seats
            auditorium.fillAuditorium(fileScanner, rows, columns);

            int choice = 0;
            boolean valid = false;

            while(choice != 2){
                menu(); // Output the menu
                System.out.print("Enter choice: ");
                choice = scanner.nextInt();
                if(choice == 2) break;
                if(choice != 1){
                    System.out.println("Invalid choice");
                    continue;
                }

                auditorium.outputToConsole(fileScanner, rows, columns);

                userRow = getValidInput(scanner, "Enter row: ", 1, rows);

                while(!valid){
                    System.out.print("Enter seat: ");
                    userSeat = scanner.next().charAt(0);
                    // Convert the seat to uppercase
                    userSeat = Character.toUpperCase(userSeat);
                    // convert the char to an int. For example, A = 0, B = 1, C = 2, etc.
                    userSeatInt = (userSeat - 65);
                    // check if the seat is within the range of the auditorium
                    if(userSeatInt >= 0 && userSeatInt < columns){
                        valid = true;
                    }
                }

                userAdultTickets = getValidInput(scanner, "Enter number of adult tickets: ", 0, Integer.MAX_VALUE);
                userChildTickets = getValidInput(scanner, "Enter number of child tickets: ", 0, Integer.MAX_VALUE);
                userSeniorTickets = getValidInput(scanner, "Enter number of senior tickets: ", 0, Integer.MAX_VALUE);

                // Check if the seats are available
                if(auditorium.areSeatsAvailable(userRow - 1, userSeatInt, userAdultTickets + userChildTickets + userSeniorTickets, columns)){
                    System.out.println("Seats available");
                    // Reserve the seats
                    auditorium.reserveSeats(userRow - 1, userSeatInt, userAdultTickets, userChildTickets, userSeniorTickets, columns);
                    break;
                }
                else{
                    System.out.println("Seats not available");
                }
                valid = false;
            }

            // Close file and scanner objects
            scanner.close();
            fileScanner.close();
        }
        catch(Exception e){
            System.out.println("File not found");
            System.exit(0);
        }
        try{
            PrintStream out = new PrintStream(new FileOutputStream("A1.txt"));
            auditorium.outputToFile(out, rows, columns);
        }
        catch(Exception e){
            System.out.println("Error writing to file");
            System.exit(0);
        }
    }
}