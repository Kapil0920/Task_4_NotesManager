package com.elevate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextManager {

    // Constant filename to store all notes
    public static final String FILENAME = "notes.txt";

    /**
     * Adds a new note provided by the user to the file.
     * @param sc Scanner object to read user input.
     */
    public static void addNote(Scanner sc) {
        System.out.println("Enter your note: ");
        String note = sc.nextLine(); // Read the full line input as note

        // Try-with-resources ensures FileWriter is closed automatically
        try (FileWriter writer = new FileWriter(FILENAME, true)) {
            // Append the note to the file with a newline
            writer.write(note + System.lineSeparator());
            System.out.println("Note Saved");
        } catch (IOException ex) {
            // If any error occurs during file writing
            ex.printStackTrace();
        }
    }

    /**
     * Displays all saved notes from the file with numbering.
     */
    public static void viewNotes() {
        try (BufferedReader buffered = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            boolean isEmpty = true;
            int count = 1; // Counter for numbering notes

            // Read and print each line from the file
            while ((line = buffered.readLine()) != null) {
                System.out.println(count++ + ". " + line);
                isEmpty = false;
            }

            // If the file is empty or has no content
            if (isEmpty) {
                System.out.println("No notes found");
            }

        } catch (FileNotFoundException e) {
            // File does not exist yet
            System.out.println("No notes found (file does not exist yet).");
        } catch (IOException e) {
            // Handle other I/O errors
            e.printStackTrace();
        }
    }

    /**
     * Allows the user to delete a specific note by selecting its number.
     * @param sc Scanner object to read user input.
     */
    public static void deleteNote(Scanner sc) {
        List<String> notes = new ArrayList<>(); // List to hold all notes

        // Step 1: Read notes from file into the list
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                notes.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No notes to delete (file does not exist).");
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Step 2: Check if any notes are available
        if (notes.isEmpty()) {
            System.out.println("No notes found to delete.");
            return;
        }

        // Step 3: Display all notes with their index
        System.out.println("Your Notes:");
        for (int i = 0; i < notes.size(); i++) {
            System.out.println((i + 1) + ". " + notes.get(i));
        }

        // Step 4: Ask user which note to delete
        System.out.print("Enter the note number to delete: ");
        int index;
        try {
            index = Integer.parseInt(sc.nextLine()) - 1; // Convert to 0-based index
            if (index < 0 || index >= notes.size()) {
                System.out.println("Invalid note number.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return;
        }

        // Step 5: Delete the selected note from the list
        String removed = notes.remove(index);
        System.out.println("Deleted note: " + removed);

        // Step 6: Rewrite the file with the remaining notes
        try (FileWriter writer = new FileWriter(FILENAME)) {
            for (String note : notes) {
                writer.write(note + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Main method - menu driven program that allows user to
     * Save, View, Delete, or Exit note-taking operations.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // Scanner for user input

        // Menu loop to interact with user
        while (true) {
            try {
                // Show user options
                System.out.println("Choose an option: [S]ave, [V]iew, [D]elete, [E]xit");
                String answer = sc.nextLine().trim();

                // Call appropriate method based on input
                if (answer.equalsIgnoreCase("S")) {
                    addNote(sc);
                } else if (answer.equalsIgnoreCase("V")) {
                    viewNotes();
                } else if (answer.equalsIgnoreCase("D")) {
                    deleteNote(sc);
                } else if (answer.equalsIgnoreCase("E")) {
                    System.out.println("Exit");
                    break; // End the loop
                } else {
                    // If input doesn't match any option
                    System.err.println("Invalid Input");
                }
            } catch (IllegalArgumentException exception) {
                // Catch and display any unexpected errors
                exception.printStackTrace();
            }
        }

        // Close the scanner to release resources
        sc.close();
    }
}
