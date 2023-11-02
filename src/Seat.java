public class Seat {
    private int row;
    private char seat;
    private char ticketType;

    public Seat() {
        this.row = 0;
        this.seat = ' ';
        this.ticketType = ' ';
    }

    public Seat(int row, char seat, char ticketType) {
        this.row = row;
        this.seat = seat;
        this.ticketType = ticketType;
    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public char getSeat() {
        return this.seat;
    }

    public void setSeat(char seat) {
        this.seat = seat;
    }

    public char getTicketType() {
        return this.ticketType;
    }

    public void setTicketType(char ticketType) {
        this.ticketType = ticketType;
    }
}