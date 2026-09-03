package com.library.console;

import java.util.Scanner;

public class ConsoleInputReader {
    private final Scanner scanner;

    public ConsoleInputReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public int readInt(String prompt) {
        System.out.print(prompt);
        int value = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return value;
    }

    /**
     * Repeatedly prompts until the user enters a non-blank value.
     */
    public String readNonBlank(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (!input.isBlank()) {
                return input;
            }
            System.out.println("This field cannot be blank. Please try again.");
        }
    }

    /**
     * Repeatedly prompts until the user enters a valid integer year.
     */
    public int readYear(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number for the year.");
            }
        }
    }
}