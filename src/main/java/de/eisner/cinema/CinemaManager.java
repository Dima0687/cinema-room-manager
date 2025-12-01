package de.eisner.cinema;

import java.util.Scanner;

public class CinemaManager {
    private static Cinema cinema;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Create an Cinema Room:");
        int rows = askForInput("Enter the number of rows", "question");
        int seats = askForInput("Enter the number of seats in each row", "question");
        cinema = new Cinema(rows, seats);

        while (true) {
            int choice = askForInput(getMenuOptions());
            if (choice == 0) break;

            run(choice);
        }

        scanner.close();
    }

    private static void run(int choice) {
        switch (choice) {
            case 1 -> showSeats();
            case 2 -> buyTicket();
            case 3 -> showStatistics();
        }
    }

    private static void showSeats() {
        System.out.println("Cinema:");
        cinema.showSeats();
    }

    private static void buyTicket() {
        System.out.println();
        int row = askForInput("Enter a row number", "question");
        int seatNo = askForInput("Enter a seat number in that row", "question");
        String alreadyTakenMsg = "That ticket has already been purchased!";
        String wrongInputMsg = "\nWrong input!";

        cinema.buyTicket(
                row,
                seatNo,
                (warnMsg) -> {
                    System.out.println(warnMsg);
                    buyTicket();
                },
                alreadyTakenMsg,
                wrongInputMsg
        );
    }

    private static void showStatistics() {
        cinema.showStatistics();
    }

    private static String getMenuOptions() {
        return """
                
                1. Show the seats
                2. Buy a ticket
                3. Statistics
                0. Exit
                """;
    }

    private static int askForInput(String message) {
        return askForInput(message, "menu");
    }

    private static int askForInput(String message, String mode) {
        int min = 0;
        int max = "question".equalsIgnoreCase(mode) ? Integer.MAX_VALUE : 3;

        String hint = "Please enter a valid number";
        while (true) {
            System.out.println(message + ("menu".equalsIgnoreCase(mode) ? "" : ":"));

            if (!scanner.hasNextInt()) {
                System.out.println(hint + ":");
                scanner.next();
                continue;
            }

            int input = scanner.nextInt();

            if (input >= min && input <= max) {
                return input;
            }
            System.out.printf("%s between %d and %d.%n", hint, min, max);
        }

    }
}
