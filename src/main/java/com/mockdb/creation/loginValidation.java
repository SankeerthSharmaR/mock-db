package com.mockdb.creation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
public class loginValidation {

	public loginValidation() {
		// TODO Auto-generated constructor stub
	}
	public static void loginValidation(Scanner sc) throws IOException, InterruptedException
	{
		//boolean ifExists=false;
		//sc=null;
		constants cs = null;
		System.out.println("Enter your Username:");
		String username=sc.next();
		System.out.println("Enter the password:");
		String password=sc.next();
		String md5=captureUserDetails.md5(password);
		if(validateUsernameAndPassword(cs.filepath,username,md5))
		{
			System.out.println("Enter the Security answer for authentication:");
			System.out.println("What is your favourite sport:");
			String securityAnswer=sc.next();
			if(validateUsernameAndPasswordAndSecurity(cs.filepath,username,md5,securityAnswer))
			{
				System.out.println("LOGIN SUCCESSFULL!!!!");
				Path path = Paths.get(constants.loggers_direc + username);
				Files.createDirectories(path);
				//Logger.path=constants.loggers_direc+username+"//log.txt";
				Logger.log("Loggined username is:"+username+"paswword is:"+password);
				createDatabaseRunner.runSqlQueries(sc,constants.filepath,username,md5,securityAnswer);
			}
			else
			{
				Logger.log("Security Answer Is Incorrect!!!! PLease login with correct credentails for loggin user"+"Loggined username is:"+username+"paswword is:"+password);

				System.out.println("Security Answer Is Incorrect!!!! PLease login with correct credentails");
				loginValidation(sc);
			}
		}
		else
		{
			System.out.println("Invalid Username or password!!!!!PLease enter proper username and password..or User Doesnt EXISTS!!!");
			createDatabaseRunner.startApplication(sc);
		}
	}
	public static boolean validateUsernameAndPassword(String filepath,String username, String password) throws IOException {
		// TODO Auto-generated method stub
		
		BufferedReader br= new BufferedReader(new FileReader(filepath));
		 String st="";
		 

	        while ((st = br.readLine()) != null)
	        {
	        	
	        	if(st.split("789")[0].equals(username) && st.split("789")[1].equals(password)){
	        		
	        			return true;
	        		
	        	}
        }
	        br.close();
		return false;
	}
	
	public static boolean validateUsernameAndPasswordAndSecurity(String filepath,String username, String password,String securityAnser) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br= new BufferedReader(new FileReader(filepath));
		 String st;
		 

	        while ((st = br.readLine()) != null)
	        {
	        	
	        	if(st.split("789")[0].equals(username) && st.split("789")[1].equals(password) && st.split("789")[2].equals(securityAnser)){
	        		
	        			return true;
	        		
	        	}
        }
	        br.close();
		return false;
	}
	
	
}
