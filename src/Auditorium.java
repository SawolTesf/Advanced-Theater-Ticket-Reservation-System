import java.util.*;

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
}