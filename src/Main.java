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

            System.out.println("Rows: " + rows);
            System.out.println("Columns: " + columns);

            scanner.close();
            fileScanner.close();
        }
        catch(Exception e){
            System.out.println("File not found");
            System.exit(0);
        }
    }
}
