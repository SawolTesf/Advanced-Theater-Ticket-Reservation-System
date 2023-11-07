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

        // Ask user for file name and creste file object
        System.out.print("Enter file name: ");
        try{
            String fileName = scanner.nextLine();
            FileInputStream inFile = new FileInputStream(new File(fileName));

            // Create auditorium object
            Auditorium<Seat> auditorium = new Auditorium<>();
            // Create scanner object to read from file
            Scanner fileScanner = new Scanner(inFile);

            // Get rows and columns from file
            int[] rowsAndColumns = getRowsAndColumns(fileName);
            int rows = rowsAndColumns[0];
            int columns = rowsAndColumns[1];
            
            // Fill auditorium with seats
            auditorium.fillAuditorium(fileScanner, rows, columns);

            int choice = 0;

            while(choice != 2){
                menu(); // Output the menu
                auditorium.outputToConsole(fileScanner, rows, columns);
                System.out.println("Enter choice: ");
                choice = scanner.nextInt();
                if(choice == 2) break;
            }

            // Close file and scanner objects
            scanner.close();
            fileScanner.close();
        }
        catch(Exception e){
            System.out.println("File not found");
            System.exit(0);
        }
    }
}
