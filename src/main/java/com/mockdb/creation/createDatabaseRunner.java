package com.mockdb.creation;

import java.io.IOException;
import java.util.*;

public class createDatabaseRunner {

	public createDatabaseRunner() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IllegalArgumentException, IOException, InterruptedException {
		// TODO Auto-generated method stub
		Scanner sc=new Scanner(System.in);
		try
		{
		
		startApplication(sc);
		}
		catch(IllegalArgumentException e)
		{
			Logger.log(e.toString());
			Logger.log("Enter Valid Input or valid Arguments!!!!"+"\n"+"Expecting integer values...");
			System.out.println("Enter Valid Input or valid Arguments!!!!"+"\n"+"Expecting integer values...");
			startApplication(sc);
			
		}
		catch(IOException e)
		{
			Logger.log(e.toString());
			Logger.log("Exception while accessing the file!!!!");
			System.out.println("Exception while accessing the file!!!!");
			startApplication(sc);
		}
		catch(InterruptedException e)
		{
			Logger.log(e.toString());
			Logger.log("Facing interuption in accessing the file in the system restarting the application!!!! ");
			System.out.println("Facing interuption in accessing the file in the system restarting the application!!!! ");
			startApplication(sc);
		}
		
		
		
	}

	public static void startApplication(Scanner sc) throws IllegalArgumentException, IOException, InterruptedException {
		// TODO Auto-generated method stub
		System.out.println("Welcome !!!!"+ '\n' +"For New User Registration Enter: 1" + '\n' +"For Login Enter: 2");
		int userType=sc.nextInt();
		if(userType!=1 && userType!=2)
		{
			System.out.println("Please enter either 1 or 2");
			startApplication(sc);
		}
		else 
		{
			captureUserDetails cud=new captureUserDetails();
			captureUserDetails.captureUserDetails(sc,userType);
		}
		
	}
	

	/**
	@param sc: a Scanner object to take user input
	@param filepath: a String representing the filepath of the SQL file to run queries from
	@param username: a String representing the username of the database to connect to
	@param password: a String representing the password of the database to connect to
	@param securityAnswer: a String representing the security answer for the user
	@return: void
	**/
	
	public static void runSqlQueries(Scanner sc,String filepath,String username,String password,String securityAnswer) throws IOException, InterruptedException {
		System.out.println("Enter 3 to exit:");
		System.out.println("Enter 4 to continue writing sql queries");
		int stype=sc.nextInt();
		if(stype==3)
		{
			System.out.println("Thank You Vist again!!!");
			//System.out.flush(); 
		
		}
		if(stype==4)
		{
			
			validateSqlQueries.validateSQL(sc,filepath,username,password,securityAnswer);
		}
	}

	
	

}
