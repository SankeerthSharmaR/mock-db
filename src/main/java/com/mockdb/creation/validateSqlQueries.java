package com.mockdb.creation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class validateSqlQueries {

	public static void validateSQL(Scanner sc, String filepath, String username, String password, String securityAnswer)
			throws InterruptedException, IOException {
		try {
		Scanner sct = new Scanner(System.in);
		System.out.println("Start writing the SQL queries with either create db or use db command");
		Logger.log("Start writing the SQL queries with either create db or use db command" + username);

		// System.out.println("");
		String fquery = sct.nextLine();
		String firstQuery = fquery.replaceAll("( )+", " ");
		// System.out.println(firstQuery);
		String capturedExistingDatabaseName = "";
		String newDatabasename = "";
		// first check condition is for
		// create database database_name ;
		// use database_name;
		if (firstQuery.trim().split(" ")[0].equals("use")) {
			if (firstQuery.trim().split(" ").length == 3 || firstQuery.trim().split(" ").length == 2) {

				if (firstQuery.trim().split(" ").length == 2) {
					if (firstQuery.trim().split(" ")[1].endsWith((";"))) {
						capturedExistingDatabaseName = firstQuery.trim().split(" ")[1].substring(0,
								firstQuery.trim().split(" ")[1].length() - 1);
						Logger.log("DBANEM IS :" + capturedExistingDatabaseName);
						if (!checkIfDbExists(sc, capturedExistingDatabaseName, filepath, username, password,
								securityAnswer)) {
							validateSqlQueries.validateSQL(sc, filepath, username, password, securityAnswer);
						}
						else
						{
							Logger.log("starting crud opration as db name matches:");
							crudOperation.start(sc, filepath, username, password, securityAnswer, capturedExistingDatabaseName);
						}

					} else {
						Logger.log("INVALID SYNTAX!!!!" + "QUERY SHOULD END with:SEMICOLUMN" + ";" + username);

						System.out.println("INVALID SYNTAX!!!!" + "QUERY SHOULD END with:SEMICOLUMN" + ";");
						validateSQL(sc, filepath, username, password, securityAnswer);
					}
				} else if (firstQuery.trim().split(" ").length == 3) {
					if (firstQuery.trim().split(" ")[2].equals(";")) {
						capturedExistingDatabaseName = firstQuery.trim().split(" ")[1];
						// System.out.println("DBANEM IS :"+ ""+capturedExistingDatabaseName);
						if (!checkIfDbExists(sc, capturedExistingDatabaseName, filepath, username, password,
								securityAnswer)) {
							validateSqlQueries.validateSQL(sc, filepath, username, password, securityAnswer);
						}
						else
						{
							Logger.log("starting crud opration as db name matches:");

							crudOperation.start(sc, filepath, username, password, securityAnswer, capturedExistingDatabaseName);
						}
					} else {
						Logger.log("INVALID SYNTAX!!!!" + "QUERY SHOULD END with:SEMICOLUMN" + ";" + username);
						System.out.println("INVALID SYNTAX!!!!" + "QUERY SHOULD END with:SEMICOLUMN" + ";");
						validateSQL(sc, filepath, username, password, securityAnswer);
					}

				}

			}
		} else if (firstQuery.trim().split(" ").length == 3 || firstQuery.trim().split(" ").length == 4) {
			if (firstQuery.trim().split(" ")[0].equals("create")
					&& firstQuery.trim().split(" ")[1].equals("database")) {

				if (firstQuery.trim().split(" ").length == 3) {
					if (firstQuery.trim().split(" ")[2].endsWith((";"))) {
						newDatabasename = firstQuery.trim().split(" ")[2].substring(0,
								firstQuery.trim().split(" ")[2].length() - 1);
						// System.out.println("DBNAME IS :"+capturedExistingDatabaseName);

						createNewDB(sc, filepath, newDatabasename, username, password, securityAnswer);

						// validateSqlQueries.validateSQL(sc,filepath,username,password,securityAnswer);

					} else {
						System.out.println("INVALID SYNTAX!!!!" + "QUERY SHOULD END with:SEMICOLUMN" + ";");
						validateSQL(sc, filepath, username, password, securityAnswer);
					}
				} else if (firstQuery.trim().split(" ").length == 4) {
					if (firstQuery.trim().split(" ")[3].equals(";")) {
						newDatabasename = firstQuery.trim().split(" ")[3];
						System.out.println("DBNAME IS :" + capturedExistingDatabaseName);
						createNewDB(sc, filepath, newDatabasename, username, password, securityAnswer);

						validateSqlQueries.validateSQL(sc, filepath, username, password, securityAnswer);

					} else {
						System.out.println("INVALID SYNTAX!!!!" + "QUERY SHOULD END with:SEMICOLUMN" + ";");
						Logger.log("INVALID SYNTAX!!!!\"+\"QUERY SHOULD END with:SEMICOLUMN");
						validateSQL(sc, filepath, username, password, securityAnswer);
					}
				}
			}

			else {
				System.out.println("Invalid Syntax in creating db!!!" + "\n" + "create a new database");
				Logger.log("Invalid Syntax!!!\"+\"\\n\"+\"Either user an existing database or create a new database");

				validateSQL(sc, filepath, username, password, securityAnswer);
			}
		}

		else

		{
			System.out
					.println("Invalid Syntax!!!" + "\n" + "Either user an existing database or create a new database");
			Logger.log("Invalid Syntax!!!\"+\"\\n\"+\"Either user an existing database or create a new database");
			validateSQL(sc, filepath, username, password, securityAnswer);
		}
		}
		 catch (IOException e) {
				Logger.log("exception in validiating SQL" + username);
				System.err.println("exception in validiating SQL!" + e.getMessage());
			}

	}
	/*
	 * else { System.out.println("Invalid Syntax!!!"
	 * +"\n"+"Either use an existing database or create a new database");
	 * validateSQL(sc, filepath, username, password, securityAnswer); }
	 */
	// checkIfDbExists(capturedExistingDatabaseName,filepath,username,password,securityAnswer);

	public static boolean checkIfDbExists(Scanner sc, String capturedExistingDatabaseName, String filepath,
			String username, String password, String securityAnswer) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		try {
		BufferedReader br = new BufferedReader(new FileReader(filepath));
		String st;

		while ((st = br.readLine()) != null) {

			// System.out.println("String is:"+st);

			if (st.split("789")[0].equals(username) && st.split("789")[1].equals(password)
					&& st.split("789")[2].equals(securityAnswer)) {

				if (st.split("789").length == 3) {
					System.out.println("Database doesnt exists!!! create one before Using");
					Logger.log("Database doesnt exists!!! create one before Using" + username);

					return false;

				} else if (st.split("789")[3].equals(capturedExistingDatabaseName)) {
					// System.out.println("");
					// validateSQL(sc, filepath, username, password, securityAnswer);
					// System.out.println("");
					
					return true;
					// createNewDB(filepath,capturedExistingDatabaseName,username,password,securityAnswer);
				} else {
					System.out.println("Sorry!!!databse doesnt match with the cretaed one!!" + "\n"
							+ "Use the database which is cretaed by you:");
					Logger.log("Sorry!!!databse doesnt match with the cretaed one!!");
					validateSQL(sc, filepath, username, password, securityAnswer);
				}

			}
		}
		}
		
			catch (IOException e) {
				Logger.log("Database createion failed for user" + username);
				System.err.println("Failed to create directory!" + e.getMessage());
			}
		
		return false;
	}

	public static void createNewDB(Scanner sc, String filepath, String newDbName, String username, String password,
			String securityAnswer) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		try {
		updateDBNameinCredentails(sc, filepath, newDbName, username, password, securityAnswer);
		}
		catch(IOException e)
		{
			System.out.println("Error in updating userdatabase name in credentials file !!!");
			Logger.log("Error in updating userdatabase name in credentials file !!!" + username);
		}

	}

	public static boolean updateDBNameinCredentails(Scanner sc, String filepath, String newDbName, String username,
			String password, String securityAnswer) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		try {
			List<String> l1 = new ArrayList<String>();
			List<String> l2 = new ArrayList<String>();

			File file = new File(filepath);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			String i = "";
			StringBuilder sb = new StringBuilder();
			while ((i = bufferedReader.readLine()) != null) {
				l1.add(i);
			}
			for (String st : l1) {
				if (st.split("789")[0].equals(username) && st.split("789")[1].equals(password)
						&& st.split("789")[2].equals(securityAnswer)) {
					if (st.split("789").length == 3) {
						sb.append(st + "789" + newDbName);
						// System.out.println("No databse With this name exists!!!Create db first");
						createNewFolderForDatabase(constants.databases_direc, username, newDbName);
						l2.add(sb.toString());
					} else if (st.split("789").length == 4) {
						System.out.println("Database already exists for your account cant create anew one!!!!!");
						Logger.log("Database already exists for your account cant create anew one!!!!!!!" + username);
						validateSQL(sc, filepath, username, password, securityAnswer);
					}
				} else {
					l2.add(st);
				}
			}

			// System.out.println("print sb"+sb.toString());
			FileWriter fileWriter = new FileWriter(file);
			for (String w : l2)
				fileWriter.write(w + "\n");
			bufferedReader.close();
			fileReader.close();
			fileWriter.close();
			l1.clear();
			l2.clear();
			System.out.println("One Database creation succesful !!!");
			Logger.log("One Database creation succesful !!" + username);
			crudOperation.start(sc, filepath, username, password, securityAnswer, newDbName);
			// validateSQL(sc, filepath, username, password, securityAnswer);
		} catch (IOException e) {
			System.out.println("Error in creating Database!!!"+e.getMessage());
			Logger.log("Error in creating Database!!!"+"or inappending data to log file"+e.getMessage());
		}
		
		return false;
	}

	private static void createNewFolderForDatabase(String databases_direc, String username, String newDbName)
			throws IOException {
		// TODO Auto-generated method stub
		try {

			Path path = Paths.get(databases_direc + username + "//" + newDbName);

			Files.createDirectories(path);
			Logger.log("Database sucesffully created for user" + ";" + username);
			//System.out.println("Directory is created!");

			// Files.createDirectory(path);

		} catch (IOException e) {
			Logger.log("Database createion failed for user" + username);
			System.err.println("Failed to create directory!" + e.getMessage());
		}
	}

}
