package de.eisner.cinema;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Statistics {
    private int purchasedTickets;
    private double percentage;
    private int currentIncome;
    private final int totalIncome;
    private final int seats;

    public Statistics(int seats, int totalIncome) {
        this.seats = seats;
        this.totalIncome = totalIncome;
    }

    private int getCurrentIncome() {
        return currentIncome;
    }

    private void setCurrentIncome(int currentIncome) {
        this.currentIncome = currentIncome;
    }

    private double getPercentage() {
        return percentage;
    }

    private void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    private int getPurchasedTickets() {
        return purchasedTickets;
    }

    private void setPurchasedTickets(int purchasedTickets) {
        this.purchasedTickets = purchasedTickets;
    }

    private int getTotalIncome() {
        return totalIncome;
    }

    private int getSeats() {
        return seats;
    }

    public void buyTicket(int ticketPrice) {
        int current = getPurchasedTickets();
        setPurchasedTickets(++current);

        if (ticketPrice < 0) {
            System.out.println("Ticket Price can't be negative!");
            return;
        }

        int currentIncome = getCurrentIncome();
        setCurrentIncome(currentIncome + ticketPrice);

        double percentage = (double) getPurchasedTickets() / getSeats() * 100;
        BigDecimal bd = new BigDecimal(percentage).setScale(2, RoundingMode.HALF_UP);
        double rounded = bd.doubleValue();
        setPercentage(rounded) ;
    }

    @Override
    public String toString() {
        return """
                
                Number of purchased tickets: %d
                Percentage: %.2f%%
                Current income: $%d
                Total income: $%d""".formatted(
                getPurchasedTickets(),
                getPercentage(),
                getCurrentIncome(),
                getTotalIncome()
        );
    }
}
