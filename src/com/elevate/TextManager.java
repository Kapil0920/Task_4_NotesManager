package com.elevate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TextManager {
	public static final  String FILENAME = "notes.txt";
	
	public static void addNote(Scanner sc) {
		System.out.println("Enter your note: ");
		String note =sc.nextLine();
		try(
		FileWriter writer = new FileWriter(FILENAME, true)){
			writer.write(note + System.lineSeparator());
			System.out.println("Note Saved");
			
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void viewNotes() {
		try(
		BufferedReader buffered  = new BufferedReader(new FileReader(FILENAME))){
			String line ;
			boolean isEmpty = true;
			int count = 1;
			while ((line = buffered.readLine()) != null) {
			    System.out.println(count++ + ". " + line);
			    isEmpty = false;
			}
			if(isEmpty) {
				 System.out.println("No notes found");
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (true) {
			try {
				System.out.println("Choose an option: [S]ave, [V]iew, [E]xit");
				String answer = sc.nextLine().trim();
				
				if (answer.equalsIgnoreCase("S") || answer.equalsIgnoreCase("s")) {
					TextManager.addNote(sc); 
				} else if (answer.equalsIgnoreCase("V") || answer.equalsIgnoreCase("v")) {

					TextManager.viewNotes();
				} else if (answer.equalsIgnoreCase("E") || answer.equalsIgnoreCase("e")) {
					System.out.println("Exit");
					break;
				} else {
					System.err.println("Invalid Input ");
				}
			} catch (IllegalArgumentException exception) {
				exception.printStackTrace();

			}
		}
		sc.close();
	}

}
