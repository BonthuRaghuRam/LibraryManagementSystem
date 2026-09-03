package com.library;

import com.library.console.ConsoleInputReader;
import com.library.console.LibraryConsoleApp;
import com.library.model.Library;
import com.library.model.PatronManager;
import com.library.service.LendingService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        PatronManager patronManager = new PatronManager();
        LendingService lendingService = new LendingService(patronManager, library);

        Scanner scanner = new Scanner(System.in);
        ConsoleInputReader inputReader = new ConsoleInputReader(scanner);

        LibraryConsoleApp app = new LibraryConsoleApp(library, patronManager, lendingService, inputReader);
        app.run();
    }
}