package com.mockdb.creation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class captureUserDetails {
	
	
	/**

	Captures user details for sign up or login process.

	@param sc Scanner object to capture user input.

	@param userType An integer value representing the user type (1 for sign up and 2 for login).

	@throws IOException If an I/O error occurs while writing to the file.

	@throws InterruptedException If the current thread is interrupted while sleeping.
	*/

	public static void  captureUserDetails(Scanner sc,int userType) throws IOException, InterruptedException{
		// TODO Auto-generated constructor stub
		try {
		createDatabaseRunner db=new createDatabaseRunner();
		constants cs = null;
		boolean isExists=false;
		if(userType==1)
		{
			System.out.println("Enter your username:");
			String signUpUsername=sc.next();
			//step 1:check if there is an existing user with the username entered.
			//step 2:To check we need to save the user details in a file.
			//step 3:check if autherization file exists. For the first time create autherization file if not exists.
			if(checkUsernameExists(signUpUsername))
			{
				System.out.println("Sorry!!!"+ '\n'+"Username with this exists please select another user name");
				createDatabaseRunner.startApplication(sc);
			}
			else
			{
				System.out.println("Enter your password:");
				String signUpPassword=sc.next();
				String hashed_password=md5(signUpPassword);
				System.out.println("Establishing two way authentication!!!!! please answer the below securtiy question.");
				System.out.println("What is your favourite sport:");
				String securityAnswer=sc.next();
				
				Logger.log("Writing new user details to db username is:"+signUpUsername+":password is:"+signUpPassword);
				writeDetailstoDb(constants.filepath,signUpUsername,hashed_password,securityAnswer);
				
			}
		}
		else
		{
			//loginValidation lv=new loginValidation()
			loginValidation.loginValidation(sc);
		}
		}
		catch (IOException e) {
			Logger.log("exception in capturing user details:" );
			System.err.println("exception in capturing user details" + e.getMessage());
		}
	}

	
	/**

	Hashes the input password using MD5 algorithm.

	@param signUpPassword The password to be hashed.

	@return A String representing the hashed password.
	*/
	public static String md5(String signUpPassword)  {
		// TODO Auto-generated method stub
		try {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(signUpPassword.getBytes());
		byte[] bytes = md.digest();
		StringBuilder sb = new StringBuilder();
	      for (int i = 0; i < bytes.length; i++) {
	        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	      }
	       String generatedPassword = sb.toString();
	       return generatedPassword;
		}
		catch (NoSuchAlgorithmException e) {
		      e.printStackTrace();
		    }
		return null;
	}

	/**

	Writes the user details to the database file.
	@param filepath A String representing the path of the database file.
	@param signUpUsername A String representing the username of the user signing up.
	@param hashed_password A String representing the hashed password of the user signing up.
	@param securityAnswer A String representing the answer to the security question.
	@throws IOException If an I/O error occurs while writing to the file.
	@throws IllegalArgumentException If the file path is invalid.
	@throws InterruptedException If the current thread is interrupted while sleeping.
	*/
	
	private static void writeDetailstoDb(String filepath,String signUpUsername, String hashed_password, String securityAnswer) throws IOException, IllegalArgumentException, InterruptedException {
		// TODO Auto-generated method stub
		try {
		Scanner sc=new Scanner(System.in);
		FileWriter f = new FileWriter(filepath,true);
		String str = signUpUsername+"789"+hashed_password+"789"+securityAnswer+"\n";
		f.write(str);
        f.close();
        Logger.log("user details which are stored in db are:"+str);
        Logger.log("Sign Up Successfull!!!!!!");
        System.out.println("Sign Up Successfull!!!!!!");
        createDatabaseRunner.startApplication(sc);
		}
		catch (IOException e) {
			Logger.log("exception in writing userdetails to file" + signUpUsername);
			System.err.println("exception in writing userdetails to file" + e.getMessage());
		}
	}

	/**
	 * Checks if the given username exists in the system.
	 * @param signUpUsername the username to check
	 * @return true if the username exists, false otherwise
	 * @throws IOException if an I/O error occurs
	 */
	public static boolean checkUsernameExists(String signUpUsername) throws IOException {
		// TODO Auto-generated method stub
		//boolean ifExists=false;
		try {
		constants cs = null;
		//create a file if not exists 
		//if exists check the SIgnupUsername exists
		if(checkInFile(cs,signUpUsername))
		{
			return true;
		}
		else
		{
		return false;
		}
		}
		catch (IOException e) {
			Logger.log("exception in checking username in file" + signUpUsername);
			System.err.println("exception in username checking in file" + e.getMessage());
		}
		return false;
		
	}
	
	/**
	 * Checks if the given username exists in the specified file.
	 * @param cs the constants object containing the file path
	 * @param signUpUsername the username to check
	 * @return true if the username exists in the file, false otherwise
	 * @throws IOException if an I/O error occurs
	 */

	public static boolean checkInFile(constants cs,String signUpUsername) throws IOException {
		// TODO Auto-generated method stub
		try {
		File f = new File(cs.filepath);
		if(f.exists() && !f.isDirectory()) { 
		   return checkSignUpUsername(f,signUpUsername);
		}
		else
		{
			f.createNewFile();
			return false;
			
		}
		}
		catch (IOException e) {
			Logger.log("exception in checking in file" + signUpUsername);
			System.err.println("exception in checking in file" + e.getMessage());
		}
		return false;
	}
	
	/**
	 * Checks if the given username exists in the specified file.
	 * @param f the file to check
	 * @param signUpUsername the username to check
	 * @return true if the username exists in the file, false otherwise
	 * @throws IOException if an I/O error occurs
	 */

	public static boolean checkSignUpUsername(File f,String signUpUsername) throws IOException {
		// TODO Auto-generated method stub
		try {
		BufferedReader br= new BufferedReader(new FileReader(f));
		 String st;
		 

	        while ((st = br.readLine()) != null)
	        {
	        	
	        	if(st.split("789")[0].equals(signUpUsername)){
	        		
	        			return true;
	        		
	        	}
	        }
	        br.close();
		}
		 catch (IOException e) {
				Logger.log("exception in checking the signup user SQL" + signUpUsername);
				System.err.println("exception in validiating SQL!" + e.getMessage());
			}
		return false;
	}
	
	

}
