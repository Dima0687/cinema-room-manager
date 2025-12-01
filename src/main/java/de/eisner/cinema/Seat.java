package de.eisner.cinema;

public class Seat {
    private final int row;
    private final int seatNo;
    private double seatPrice;
    private boolean reserved = false;

    public Seat(int row, int seatNo, double seatPrice) {
        this.row = row;
        this.seatNo = seatNo;
        this.seatPrice = seatPrice; 
    }

    public int getRow() {
        return row;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public double getSeatPrice() {
        return seatPrice;
    }

    public void setSeatPrice(double seatPrice) {
        this.seatPrice = seatPrice;
    }

    @Override
    public String toString() {
       if (isReserved()) {
           return "\u001B[31mB\u001B[0m";
       } else {
           return "S";
       }
    }
}
