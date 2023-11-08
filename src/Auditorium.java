import java.util.*;
import java.io.*;

public class Auditorium<T> {
   private Node<T> first;

   public Auditorium() {
      this.first = null;
   }

   public Node<T> getFirst() {
      return first;
   }

   public void setFirst(Node<T> first) {
      this.first = first;
   }

   // This method will fill the auditorium with seats
   public void fillAuditorium(Scanner fileScanner, int rows, int columns){
      Node<T> current = null; // This node will be used to keep track of the current node that we are at
      Node<T> firstNodeOfRow = null;

      int currentRow = 0;
      int currentColumn = 0;

      while(fileScanner.hasNext()){
         String line = fileScanner.nextLine();
         if(line.length() == 0){ // If the line is empty
            continue;
         }
         // Create a node that will always be pointing to the first node of the row so that we can later set its down pointer to the first node of the next row

         for(int i = 0; i < line.length(); i++){
            // current seat is going to be the column converted to a char letter Ex: 0 = A, 1 = B, 2 = C
            Seat seat = new Seat(currentRow, (char)(currentColumn + 65), line.charAt(i));
            
            @SuppressWarnings("unchecked")
            Node<T> newNode = new Node<T>((T)seat); // (T) is a cast so that the compiler knows that the object is of type T
            if(currentRow == 0 && currentColumn == 0){ // If we are at the first node of the auditorium
               this.first = newNode;
               current = newNode;
               newNode.setPrevious(null); // Set the previous pointer of the first node to null because there is no previous node
               firstNodeOfRow = newNode;
            }
            else{
               if(currentColumn == 0 && currentRow != 0){ // If we are at the first node of a row but not the first row
                  firstNodeOfRow.setDown(newNode); // Set the down pointer of the first node of the row to the first node of the next row
                  firstNodeOfRow = newNode; // Set the first node of the row to the first node of the next row
                  newNode.setPrevious(null); // Set the previous pointer of the first node of the row to null because there is no previous node in the row
                  current = newNode;
               }
               else{ // If we are not at the first node of a row
                  current.setNext(newNode);
                  newNode.setPrevious(current);
                  current = newNode;
               }
            }
            currentColumn++;
         }
         currentRow++;
         currentColumn = 0;
      }
   }
   public void outputToConsole(Scanner fileScanner, int rows, int columns){
      Node<T> current = this.first;
      Node<T> firstNodeOfRow = this.first;
      int currentRow = 0;
      int currentColumn = 0;
      
      System.out.print("  ");
      for(int i = 0; i < columns; i++){
         System.out.print((char)(i + 65));
      }
      System.out.println();

      while(currentRow < rows){
         System.out.print((currentRow + 1) + " ");

         while(currentColumn < columns){
            Seat seat = (Seat)current.getPayload();
            char ticketType = seat.getTicketType();
            if(ticketType == '.'){
               System.out.print(".");
            }
            else{
               System.out.print("#");
            }
            current = current.getNext();
            currentColumn++;
         }
         System.out.println();
         currentRow++;
         currentColumn = 0;
         firstNodeOfRow = firstNodeOfRow.getDown();
         current = firstNodeOfRow;
      }
   }
   public void outputToFile(PrintStream out, int rows, int columns){
      Node<T> current = this.first;
      Node<T> firstNodeOfRow = this.first;
      int currentRow = 0;
      int currentColumn = 0;
      
      while(currentRow < rows){
         while(currentColumn < columns){
            Seat seat = (Seat)current.getPayload();
            char ticketType = seat.getTicketType();
            out.print(ticketType);
            current = current.getNext();
            currentColumn++;
         }
         out.println();
         currentRow++;
         currentColumn = 0;
         firstNodeOfRow = firstNodeOfRow.getDown();
         current = firstNodeOfRow;
      }

   }
   public boolean areSeatsAvailable(int userRow, int userSeatInt, int totalTickets, int totColumn){
      Node<T> current = this.first;
      Node<T> firstNodeOfRow = this.first;
      int currentRow = 0;
      int currentColumn = 0;
      int currentTicket = 0;

      // Move to the row that the user wants to reserve seats in
      while(currentRow < userRow){
         firstNodeOfRow = firstNodeOfRow.getDown();
         current = firstNodeOfRow;
         currentRow++;
      }
      // Check if the seats exceed the number of columns
      if(userSeatInt + totalTickets > totColumn){
         return false;
      }
      // Move to the seat that the user wants to reserve seats in
      while(currentColumn < userSeatInt){
         current = current.getNext();
         currentColumn++;
      }
      // Check if the seats are available
      while(currentTicket < totalTickets){
         Seat seat = (Seat)current.getPayload();
         char ticketType = seat.getTicketType();
         if(ticketType == '.'){
            currentTicket++;
            current = current.getNext();
         }
         else{
            return false;
         }
      }
      return true;
   }
   public void reserveSeats(int userRow, int userSeatInt, int userAdultTickets, int userChildTickets, int userSeniorTickets, int totColumn){
      Node<T> current = this.first;
      Node<T> firstNodeOfRow = this.first;
      int currentRow = 0;
      int currentColumn = 0;

      // Move to the row that the user wants to reserve seats in
      while(currentRow < userRow){
         firstNodeOfRow = firstNodeOfRow.getDown();
         current = firstNodeOfRow;
         currentRow++;
      }
      // Move to the seat that the user wants to reserve seats in
      while(currentColumn < userSeatInt){
         current = current.getNext();
         currentColumn++;
      }
      // Reserve the seats
      for(int i = currentColumn; i < currentColumn + userAdultTickets; i++){
         Seat seat = (Seat)current.getPayload();
         seat.setTicketType('A');
         current = current.getNext();
      }
      for(int i = currentColumn + userAdultTickets; i < currentColumn + userAdultTickets + userChildTickets; i++){
         Seat seat = (Seat)current.getPayload();
         seat.setTicketType('C');
         current = current.getNext();
      }
      for(int i = currentColumn + userAdultTickets + userChildTickets; i < currentColumn + userAdultTickets + userChildTickets + userSeniorTickets; i++){
         Seat seat = (Seat)current.getPayload();
         seat.setTicketType('S');
         current = current.getNext();
      }
   }
}