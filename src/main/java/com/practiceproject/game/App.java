package com.practiceproject.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App 
{
	static BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));

    public static void main( String[] args )
    {    	
    	boolean isTerminate = false;
    	
    	try {
    		do {
            	System.out.println("\n\nWhich game you want to play?");
            	System.out.println("1) Number game");
            	System.out.println("2) Questions Answers game");
            	System.out.print("3) EXIT \n==> ");
            	
            	// Take game choice from user            	
            	String userChoice = bReader.readLine();
            	
            	switch (userChoice) {
            		// Number Game            	
    				case "1":
    					NumberGame.startGame();	
    					break;
    					
    				// Question Answer game    					
    				case "2":
    					
    					break;
    					
    				// Exit from game    				
    				case "3":
    					isTerminate = true;
    					System.out.println("Thank you!");
    					break;

    				default:
    					System.out.println("Wrong input!");
    					break;
    			}
        	}while(!isTerminate);
		} 
    	catch (IOException e) {e.printStackTrace();}
    	catch (Exception e) {e.printStackTrace();}
    	
    }
}
