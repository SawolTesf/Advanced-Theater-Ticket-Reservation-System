import java.util.*;
import java.io.*;

public class Main {
    public static void menu(){
        System.out.println("1. Reserve Seats");
        System.out.println("2. Exit");
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

            scanner.close();
            fileScanner.close();
        }
        catch(Exception e){
            System.out.println("File not found");
            System.exit(0);
        }
    }
}
