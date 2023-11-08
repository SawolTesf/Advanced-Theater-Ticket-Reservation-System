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
    public static int[] findTotalTickets(int rows, int columns, Auditorium<Seat> auditorium){
        // Iterate through auditorium and find the total number of tickets
        Node<Seat> current = auditorium.getFirst();
        Node<Seat> firstNodeOfRow = auditorium.getFirst();
        int[] totalTickets = new int[4]; // 0 = total adult tickets, 1 = total child tickets, 2 = total senior tickets, 3 = total tickets

        for(int r = 0; r < rows; r++){ // r is the current row
            // Loop through the row
            for(int i = 0; i < columns; i++){
                // Avoid null pointer exception
                if(current == null){
                    break;
                }
                // If the seat is available, increment the total tickets of that type
                if(current.getPayload().getTicketType() == 'A'){
                    totalTickets[0]++;
                    totalTickets[3]++;
                }
                else if(current.getPayload().getTicketType() == 'C'){
                    totalTickets[1]++;
                    totalTickets[3]++;
                }
                else if(current.getPayload().getTicketType() == 'S'){
                    totalTickets[2]++;
                    totalTickets[3]++;
                }

                current = current.getNext(); // Move the current node to the next node
            }
            // Move to the next row
            firstNodeOfRow = firstNodeOfRow.getDown();
            current = firstNodeOfRow;
        }


        return totalTickets;
    }

    // This method will find the best seat for the user
    public static int[] bestAvailable(int rows, int columns, Auditorium<Seat> auditorium, int totAdultTickets, int totChildTickets, int totSeniorTickets){
        Node<Seat> current = auditorium.getFirst(); // This node will be used to keep track of the current node that we are at
        Node<Seat> firstNodeOfRow = auditorium.getFirst();
        Node<Seat> checkAhead = current; // This node will be used to check ahead of the current node to see if the conswcutive seats are available

        int totalTickets = totAdultTickets + totChildTickets + totSeniorTickets;
  
        int[] best = new int[2]; // 0 = best row, 1 = best seat
        float dis = Integer.MAX_VALUE;
        
        boolean seatsAvailable = true;
        
        for(int r = 0; r < rows; r++){ // r is the current row
            // Loop through the row
            for(int i = 0; i < columns; i++){
                // Check if consecutive seats are available
                for(int j = i; j < totalTickets + i; j++){
                    if(checkAhead == null || checkAhead.getPayload().getTicketType() != '.'){
                        seatsAvailable = false;
                        break;
                    }
                    checkAhead = checkAhead.getNext(); // Move the checkAhead node to the next node
                }
                if(seatsAvailable){
                    float midOfSeats = 0;
                    
                    // Get the middle of the seats
                    midOfSeats = (float)(i + totalTickets / 2.0);

                    // Get the middle of the row
                    float midOfRow = 0;

                    if(columns % 2 == 1){ // If the number of columns is odd
                        midOfRow = (float)columns / 2;
                    }
                    else{ // If the number of columns is even
                        midOfRow = (float)(columns / 2) + 0.5f;
                    }

                    // Get distance between the middle of the seats and the middle of the row
                    float currentDis = Math.abs(midOfSeats - midOfRow);

                    // If the distance is less than the current distance, set the best seat to the current seat
                    if(currentDis < dis){
                        dis = currentDis;
                        best[1] = i;
                        best[0] = r;
                    }
                    // Check if the distance is equal to the current distance
                    else if(currentDis == dis){
                        // check if the current row is equal to the best row. If so, the best seat will be the smaller seat number
                        if(r == best[0]){
                            if(i < best[1]){
                                best[1] = i;
                            }
                        }
                        // If not, the best seat is the row with the smaller row number
                        else{
                            if(r < best[0]){
                                best[1] = i;
                                best[0] = r;
                            }
                        }
                    }
                        
                }
                // Move the current node to the next node and reset the checkAhead node
                current = current.getNext();
                checkAhead = current;
                seatsAvailable = true;

            }
            // Move to the next row
            firstNodeOfRow = firstNodeOfRow.getDown();
            current = firstNodeOfRow;
            checkAhead = current;
        }

        return best;
     }

    // This is the main method that will be executed
    public static void main(String[] args) throws Exception {
        // Create scanner object
        Scanner scanner = new Scanner(System.in);
        
        // Declare variables
        int rows = 0, columns = 0;
        int userSeatInt = 0, userRow = 0, userAdultTickets = 0, userChildTickets = 0, userSeniorTickets = 0;
        int totAdultTickets = 0, totChildTickets = 0, totSeniorTickets = 0, totTickets = 0;
        char userSeat = ' ';
        int bestRow = -1, bestSeat = -1;

        // Create auditorium object
        Auditorium<Seat> auditorium = new Auditorium<>();

        // Ask user for file name and creste file object
        System.out.print("Enter file name: ");
        //try{
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
                int[] totalTickets = findTotalTickets(rows, columns, auditorium);
                totAdultTickets = totalTickets[0];
                totChildTickets = totalTickets[1];
                totSeniorTickets = totalTickets[2];
                totTickets = totalTickets[3];

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
                    continue;
                }
                else{
                    int best[] = bestAvailable(rows, columns, auditorium, userAdultTickets, userChildTickets, userSeniorTickets);
                    bestRow = best[0];
                    bestSeat = best[1];
                    if(bestSeat == -1){
                        System.out.println("No new seats available");
                        valid = false;
                        continue;
                    }
                    // Output the best seat in format: <row><starting seat> - <row><ending seat>
                    System.out.println("Best available seats: " + (bestRow + 1) + (char)(bestSeat + 65) + " - " + (bestRow + 1) + (char)(bestSeat + userAdultTickets + userChildTickets + userSeniorTickets + 64));
                    // Ask user if they want to reserve the seats
                    System.out.print("Would you like to reserve the seats? (Y/N): ");
                    String input = scanner.next();
                    if(input.equalsIgnoreCase("Y")){
                        // Reserve the seats
                        auditorium.reserveSeats(bestRow, bestSeat, userAdultTickets, userChildTickets, userSeniorTickets, columns);
                        
                    }
                    else{
                        valid = false;
                        continue;
                    }
                }
                valid = false;
            }

            // Close file and scanner objects
            scanner.close();
            fileScanner.close();
        //}
/* 
        catch(Exception e){
            System.out.println("File not found");
            System.exit(0);
        }
*/
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