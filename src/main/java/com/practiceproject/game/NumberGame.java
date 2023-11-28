package com.practiceproject.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Random;

public class NumberGame {
	
	// This function control the game	
	static void startGame() {
		BufferedReader bReader = App.bReader;
   
    	try {
        	boolean isTerminate = false;
        	
        	do {
        		System.out.print("\nPress:\n 1)To play\n 2)To exit\n ==> ");
        		String userCoice = bReader.readLine();
        		
        		switch (userCoice) {
				case "1":
					playGame();		        	
					break;
					
				case "2":
					isTerminate = true;
					break;

				default:
					System.out.println("Wrong input!");
					break;
				}
				
			} while (!isTerminate);
        	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// This method have actual logic of the game
	static void playGame() {
		BufferedReader bReader = App.bReader;
		
		// Generate random number		
		Random random = new Random();
    	int randomNum = random.nextInt(10)+1;
    	while(true) {
    		try {
        		
        		// Take number from user    		
        		System.out.print("\nEnter any Number ==> ");
            	int userNum = Integer.parseInt(bReader.readLine());
            	
            	// Check the user number and random are equal or not            	
        		if(randomNum > userNum) { System.out.println("Number is too small!"); }
        		else if(randomNum < userNum) { System.out.println("Number is too big!"); }
        		else { 
        			System.out.println("\nYOU WIN THE GAME!!!"); 
        			break;
        		}
    		} 
        	catch (NumberFormatException e) {System.out.println("Please enter valid number between (1 to 10).");}
        	catch (IOException e) {e.printStackTrace();}
        	catch (Exception e) {e.printStackTrace();}
    	}
    	
	}
}
