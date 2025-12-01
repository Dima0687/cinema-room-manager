package de.eisner.cinema;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.Optional;
import java.util.function.Consumer;

public class Cinema {

    private final Seat[][] seatsList;
    private final int rows;
    private final int seats;
    private final Statistics statistics;

    public Cinema(int rows, int seats) {
        this(rows, seats, 10, 8);
    }

    public Cinema(int rows, int seats, int defaultPrice, int backHalfPrice) {
        if (rows < 1) throw new IllegalArgumentException("Rows can't be less than one!");
        this.rows = rows + 1;
        if (seats < 1) throw new IllegalArgumentException("Seats can't be less than one!");
        this.seats = seats + 1;
        if (defaultPrice < 0 || backHalfPrice < 0) throw new IllegalArgumentException("Price can't be negative");

        int totalSeats = rows * seats;
        seatsList = new Seat[rows][seats];

        Arrays.setAll(seatsList, r -> {
            Seat[] row = new Seat[seats];
            Arrays.setAll(row, sN -> {
                int seatPrice = defaultPrice;
                if (totalSeats > 60) {
                    seatPrice = calculatePrice(r, rows, defaultPrice, backHalfPrice);
                }
                return new Seat(r, sN, seatPrice);
            });
            return row;
        });

        IntSummaryStatistics stats = Arrays.stream(seatsList)
                .flatMap(Arrays::stream)
                .map(Seat::getSeatPrice)
                .mapToInt(Double::intValue)
                .summaryStatistics();

        this.statistics = new Statistics(Math.toIntExact(stats.getCount()), Math.toIntExact(stats.getSum()));
    }

    private Optional<Seat> getSeat(int row, int seatNo) {
        return Arrays.stream(seatsList)
                .flatMap(Arrays::stream)
                .filter(s -> s.getRow() == row && s.getSeatNo() == seatNo)
                .findFirst();
    }

    public void buyTicket(int row, int seatNo, Consumer<String> onErrFn, String alreadyTakenMsg, String wrongInputMsg) {
        Optional<Seat> seat = getSeat(row - 1, seatNo - 1);

        seat.ifPresentOrElse(
                (s) -> {
                    if (s.isReserved()) {
                        onErrFn.accept(alreadyTakenMsg);
                        return;
                    }
                    int seatPrice = (int) s.getSeatPrice();
                    System.out.println("\nTicket price: $" + seatPrice);

                    s.setReserved(true);
                    statistics.buyTicket(seatPrice);
                },
                () -> onErrFn.accept(wrongInputMsg)
        );
    }

    public void showSeats() {
        System.out.println();
        for (int row = 0; row < this.rows; row++) {
            printFirstRow(row, this.seats);
            printRow(row, this.seats);
        }
    }

    public void showStatistics() {
        System.out.println(statistics);
    }

    private void printFirstRow(int row, int rowLength) {
        int lastColInRow = rowLength - 1;
        if (row == 0) {
            for (int col = 0; col < rowLength; col++) {
                if (col == 0) {
                    System.out.print(" ");
                } else {
                    System.out.print(col);
                }

                if (col != lastColInRow) {
                    System.out.print(" ");
                } else {
                    System.out.println();
                }
            }
        }
    }

    private void printRow(int row, int rowLength) {
        int lastColInRow = rowLength - 1;
        if (row >= 1) {
            for (int col = 0; col < rowLength; col++) {
                if (col == 0) {
                    System.out.print(row);
                } else {
                    var seat = seatsList[row - 1][col - 1]; // starting point for list 0, 0
                    System.out.print(seat);
                }

                if (col != lastColInRow) {
                    System.out.print(" ");
                } else {
                    System.out.println();
                }
            }
        }
    }

    private static int calculatePrice(int row, int rows, int defaultPrice, int backHalfPrice) {
        int backHalf = Math.ceilDiv(rows, 2) - 1;
        int price = defaultPrice;

        if (row >= backHalf) {
            price = backHalfPrice;
        }

        return price;
    }
}