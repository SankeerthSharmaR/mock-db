package com.mockdb.creation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class crudOperation {

	/*@param sc: a Scanner object to read user input from the console.
	@param filepath: a String representing the file path of the user's database directory.
	@param username: a String representing the name of the user.
	@param password: a String representing the password of the user.
	@param securityAnswer: a String representing the security answer of the user.
	@param ExistingDatabaseName: a String representing the name of the existing database.
	@return Nothing (void).*/

	
	
	public static void start(Scanner sc, String filepath, String username, String password, String securityAnswer,
			String ExistingDatabaseName) throws IOException {
		// TODO Auto-generated method stub
		File f=new File(constants.databases_direc+username+"//"+ExistingDatabaseName);
		System.out.println("Select the list of operations you want to perform inside your DB"+"\n"+"1.create a table"+"\n"+"2.insert into  a table");
		System.out.println("3.select from a table"+"\n"+"4.update a table"+"\n"+"5.delete a table"+"\n"+"6 to exit");
		int selectOption=sc.nextInt();
		sc.nextLine();
		if(selectOption!=1 && selectOption!=2 && selectOption!=3 && selectOption!=4 && selectOption!=5)
		{
			System.out.println("Please enter value from selected options");
			start(sc,filepath,username,password,securityAnswer,ExistingDatabaseName);
		}
		else 
		{
			startOperations(selectOption,sc,filepath,username,password,securityAnswer,ExistingDatabaseName,f);
		}
		
	}
	public static void startOperations(int selectOption,Scanner sc, String filepath, String username, String password,
			String securityAnswer, String ExistingDatabaseName,File f) throws IOException {
		// TODO Auto-generated method stub
		
		switch (selectOption) {
		  case 1:
		    System.out.println("Enter the proper SQl query for create table");
		    String create_table=sc.nextLine();
		    createTableInDb(sc,create_table,filepath,username,password,securityAnswer,ExistingDatabaseName);
		    start(sc,filepath,username,password,securityAnswer,ExistingDatabaseName);
		    break;
		  case 2:
		    System.out.println("Enter the proper SQl query for inserting into table");
		    String insert_table=sc.nextLine();
		    inserTableInDb(sc,insert_table,filepath,username,password,securityAnswer,ExistingDatabaseName,f);
		    start(sc,filepath,username,password,securityAnswer,ExistingDatabaseName);
		    break;
		  case 3:
		    System.out.println("Enter the proper SQl query for selecting into table");
		    String selectQuery=sc.nextLine();
		    selectTableInDb( sc,selectQuery,filepath,username,password,securityAnswer,ExistingDatabaseName,f);
		    start(sc,filepath,username,password,securityAnswer,ExistingDatabaseName);
		    break;
		    
		  case 4:
			  System.out.println("Enter the proper SQl query for updating into table");
			  String updateQuery=sc.nextLine();
			  updateTableInDb( sc,updateQuery,filepath,username,password,securityAnswer,ExistingDatabaseName,f);
			  start(sc,filepath,username,password,securityAnswer,ExistingDatabaseName);
		    break;
		  case 5:
			  	System.out.println("Enter the table you want to delete:");
			    String deletQuery=sc.nextLine();
			    deleteTableInDb( sc,deletQuery,filepath,username,password,securityAnswer,ExistingDatabaseName,f);
			    start(sc,filepath,username,password,securityAnswer,ExistingDatabaseName);
			    break;
		  case 6:
			  	System.exit(0);
			    break;	    
		}
		
	}

	
	private static void updateTableInDb(Scanner sc, String updateQuery, String filepath, String username,
			String password, String securityAnswer, String existingDatabaseName, File f) throws IOException {
		// TODO Auto-generated method stub
		String selectTableName="";
		selectTableName=updateQuery.trim().split(" ")[1];
		if(!checkIfTableExists(sc,selectTableName,filepath,username,password,securityAnswer,existingDatabaseName,constants.databases_direc))
		{
			System.out.println("TABLE NAME DOES NOT EXISTS TO UPDATE!!!!");
			start(sc,filepath,username,password,securityAnswer,existingDatabaseName);
		}
		else
		{
			String updateColumnname=updateQuery.split(" ")[3];
			String updateColumnValue=updateQuery.substring(updateQuery.indexOf("=")+1,updateQuery.indexOf("where")).trim();
			String whereColumnName1=updateQuery.substring(updateQuery.indexOf("where"),updateQuery.indexOf(";"));
			String whereColumnName=whereColumnName1.split(" ")[1];
			String whereColumnValue=whereColumnName1.split(" ")[3];

			updateTable(updateColumnname, updateColumnValue, whereColumnName,whereColumnValue,selectTableName,f);
		}
	}
	
/*	@param updateColumnname: A string representing the name of the column that needs to be updated.
	@param updateColumnValue: A string representing the new value that needs to be updated for the specified column.
	@param whereColumnName: A string representing the name of the column that is used as a condition in the WHERE clause.
	@param whereColumnValue: A string representing the value of the column that is used as a condition in the WHERE clause.
	@param selectTableName: A string representing the name of the table that needs to be updated.
	@param f: A File object representing the file path of the database where the table exists.*/

	//@return: This method does not return any value. It updates the specified table with the new value for the specified column where the condition in the WHERE clause is satisfied.
	private static void updateTable(String updateColumnname,String updateColumnValue, String whereColumnName, String whereColumnValue,
			String selectTableName, File f) throws IOException {
		// TODO Auto-generated method stub
		if(!getExistingColumnNames(selectTableName,f).contains(updateColumnname) && !getExistingColumnNames(selectTableName,f).contains(whereColumnName))
		{
			System.out.println("Either update column_name or where column_name are not part of table columns!!");
			return;
		}	
		else
		{
			int updateColumnIndex=getExistingColumnNames(selectTableName,f).indexOf(updateColumnname);
			int whereColumnIndex=getExistingColumnNames(selectTableName,f).indexOf(whereColumnName);
			//updateNewValuesInTable(updateColumnname,updateColumnValue,whereColumnName,whereColumnValue);*/
			List<String> l1=new ArrayList<String>();
		  	List<String> l2=new ArrayList<String>();
		  	String filename=f+"//"+selectTableName+".txt";
            File file = new File(filename);
            String whereColVal="";
            FileReader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            if(whereColumnValue.contains("'"))
            {
            	whereColVal=whereColumnValue.substring(1, whereColumnValue.length()-1);
            	System.out.println(whereColVal);
            }
            else
            {
            	whereColVal=whereColumnValue;
            }
            
            String i="";
            StringBuilder sb=new StringBuilder();
            String updateColVal="";
            
            if(updateColumnValue.contains("'"))
            {
            	updateColVal=updateColumnValue.substring(1, updateColumnValue.length()-1);
            }
            else
            {
            	updateColVal=updateColumnValue;
            }
           
            while ((i = br.readLine()) != null)
	        {
            	l1.add(i);
	        }
            
            for(String st:l1)
	        {
	        	if(st.split("\\*\\*\\*")[whereColumnIndex].trim().equals(whereColVal))
	        	{
	        		String mod=st;
	        		String[] x=mod.split("\\*\\*\\*");
	        		int count=0;
	        		for(String k:x)
	        		{
	        			if(count==updateColumnIndex)
	        			{
	        				sb.append(updateColVal+"***");
	        				
	        			}
	        			else
	        			{
	        				sb.append(k+"***");
	        			}
	        			count++;
	        		}
	        		l2.add(sb.toString());
	        	}
	        	else
	        	{
	        		l2.add(st);
	        	}
	        }
            
            FileWriter fileWriter = new FileWriter(file);
            for(String w:l2)
            fileWriter.write(w+"\n");
            br.close();
            fileReader.close();
            fileWriter.close();
            l1.clear();
            l2.clear();
			
		}
	}

	
	
	public static void deleteTableInDb(Scanner sc, String deletQuery, String filepath, String username,
			String password, String securityAnswer, String existingDatabaseName, File f) throws IOException {
		// TODO Auto-generated method stub
		String selectTableName="";
		selectTableName=deletQuery.trim().split(" ")[2];
		if(selectTableName.endsWith(";"))
				{
			selectTableName=selectTableName.substring(0, selectTableName.length()-1).trim();
				}
		if(!checkIfTableExists(sc,selectTableName,filepath,username,password,securityAnswer,existingDatabaseName,constants.databases_direc))
		{
			System.out.println("TABLE NAME DOES NOT EXISTS!!!! PLEASE DELETE A TABLE WHICH ALREADY EXISTS");
			start(sc,filepath,username,password,securityAnswer,existingDatabaseName);
		}
		else
		{
			deleteTableFromDb(sc,selectTableName,filepath,username,password,securityAnswer,existingDatabaseName,f);
			//System.out.println("TABLE DELTED FROM DB!!!!");
			start(sc,filepath,username,password,securityAnswer,existingDatabaseName);
		}
	}

	public static void deleteTableFromDb(Scanner sc, String selectTableName, String filepath, String username,
			String password, String securityAnswer, String existingDatabaseName, File f) throws IOException {
		// TODO Auto-generated method stub
		String deletefile=f+"//"+selectTableName+".txt";
		System.out.println("selete file name is"+deletefile);
		File f1=new File(deletefile);
		f1.delete();
		if(!checkIfTableExists(sc,selectTableName,filepath,username,password,securityAnswer,existingDatabaseName,constants.databases_direc))
				{
			System.out.println("TABLE SUCCESFULLy DELETED!!!");
				}
		else
		{
			System.out.println("UNABLE TO DELETED THE TABLE AT THIS MOMENT");
		}
		
	}

	private static void selectTableInDb(Scanner sc, String selectQuery, String filepath, String username,
			String password, String securityAnswer, String existingDatabaseName, File f) throws IOException {
		// TODO Auto-generated method stub
		String selectTableName="";
		selectTableName=selectQuery.trim().substring(selectQuery.indexOf("from")+4, selectQuery.length());
		if(selectTableName.endsWith(";"))
				{
			selectTableName=selectTableName.substring(0, selectTableName.length()-1).trim();
				}
		//System.out.println("select table is:"+selectTableName);
		//String tableName=select_table.split(" ")[2];
		if(!checkIfTableExists(sc,selectTableName,filepath,username,password,securityAnswer,existingDatabaseName,constants.databases_direc))
		{
			System.out.println("TABLE NAME DOES NOT EXISTS!!!! PLEASE SELECT FROM ALREADY EXISTING TABLE");
			start(sc,filepath,username,password,securityAnswer,existingDatabaseName);
		}
		else
		{
			parseSlectQuery(sc,selectQuery,selectTableName,filepath,username,password,securityAnswer,existingDatabaseName,f);
			
		}
	}

	public static void parseSlectQuery(Scanner sc, String selectQuery,String tableName, String filepath, String username,
			String password, String securityAnswer, String existingDatabaseName, File f) throws IOException {
		// TODO Auto-generated method stub
		
		List<String> columnNamesFromSelectQuery=new ArrayList<String>();
		List<String> existingcolumns;
		if(selectQuery.trim().split(" ")[1].equals("*")) {
			
			readAllColumnsFromFile(f,username,existingDatabaseName,tableName);
		}
		else
		{
			String colname=selectQuery.trim().substring(selectQuery.indexOf("select")+7,selectQuery.indexOf("from")).trim().concat(",").trim();
			int selectColCount=0;
			for(int i=0;i<colname.length();i++)
			{
				if(colname.charAt(i)==',')
				{
					selectColCount++;
				}
			}
			for(int i=0;i<selectColCount;i++) {
			columnNamesFromSelectQuery.add(colname.split(",")[i]);
			}
			existingcolumns=getExistingColumnNames(tableName,f);
			String s1="";
			String outPutIndex;
			StringBuilder sb=new StringBuilder(s1);
			boolean checkcolumns=true;
			for(String q:columnNamesFromSelectQuery) {
				if(!getExistingColumnNames(tableName,f).contains(q))
				{
					System.out.println("Select columns do not match with the columns of create table!!");
					checkcolumns=false;
					break;
					
				}	
			}
			if(checkcolumns)
			{
				for(String i:columnNamesFromSelectQuery)
					{
						
								sb.append(existingcolumns.indexOf(i)+"|");
							
						
					}
					outPutIndex=sb.toString();
					readFilesForSelectedColumns(outPutIndex,f,tableName);
			}
			
			
		}
		
	}

	public static void readFilesForSelectedColumns(String outPutIndex, File f, String tableName) throws IOException {
		// TODO Auto-generated method stub
		String filename=f+"//"+tableName+".txt";
		//System.out.println(filename);
		 BufferedReader br= new BufferedReader(new FileReader(filename));
		// System.out.println(outPutIndex);
		 String[] x=outPutIndex.split("\\|");
		// System.out.println("erros");
		 String st;
		 int count=0;
		
		 while ((st = br.readLine()) != null)
	        {
			 
			 String output="";
			 StringBuilder sb=new StringBuilder(output);
			 	if(count==0)
			 	{
			 		count++;
			 	}
			 	else
			 	{
			 		for(String i:x) {
			 			sb.append(st.split("\\*\\*\\*")[Integer.parseInt(i)]).append("|");
			 			//System.out.println(st.split("\\*\\*\\*")[Integer.parseInt(i)]);
			 			
			 		}
			 		System.out.println(sb.toString().substring(0,sb.toString().length()-1 ));
			 	}
			 	//System.out.println(output.substring(0,sb.toString().length()-1).toString());
			 	//System.out.println(sb.toString());
	        }
		 br.close();
		 }
	

	public static void inserTableInDb(Scanner sc, String insert_table, String filepath, String username,
			String password, String securityAnswer, String existingDatabaseName,File f) throws IOException {
		// TODO Auto-generated method stub
		//System.out.println(insert_table);
		String tableName=insert_table.split(" ")[2];
		if(!checkIfTableExists(sc,tableName,filepath,username,password,securityAnswer,existingDatabaseName,constants.databases_direc))
		{
			System.out.println("TABLE NAME DOES NOT EXISTS!!!! PLEASE CREATE AND INSERT");
			start(sc,filepath,username,password,securityAnswer,existingDatabaseName);
		}
		else
		{
			int count=0;
			for(int i=0;i<insert_table.length();i++)
			{
				
				if(insert_table.charAt(i)==')')
				{
					count++;
				}
			}
			if(count==1) {
				String extractValues=(String) insert_table.subSequence(insert_table.indexOf("(")+1, insert_table.length()-2);
				String insertValues=extractValues.replaceAll(",", "***").replace('\'', ' ').replaceAll("\\s", "").concat("***").toString();
				writeSingleValuestoDb(insertValues,tableName,username,existingDatabaseName,f);
				}
			else if(count>1)
			{
				String[] x1=new String[count];
				List<String> l1=new ArrayList<String>();
				l1.clear();
				Arrays.fill(x1, null);
				//System.out.println("hello");
				String extractValues=(String) insert_table.subSequence(insert_table.indexOf("("), insert_table.length()-1)+",";
				//System.out.println("more values:"+"\n"+extractValues);
				for(int i=0;i<count;i++)
				{
					x1[i]=extractValues.split("\\),")[i];
				}
				for(int i=0;i<count;i++)
				{
					l1.add(x1[i].substring(1, x1[i].length()).replaceAll(",", "***").replace('\'', ' ').replaceAll("\\s", "").concat("***").toString());
				}
				writeMingleValuestoDb(l1,tableName,username,existingDatabaseName,f);
			}
		}
	}

	private static void writeMingleValuestoDb(List<String> l1, String tableName, String username,
			String existingDatabaseName, File f) throws IOException {
		// TODO Auto-generated method stub
		String filename=f+"//"+tableName+".txt";
		FileWriter f1 = new FileWriter(filename,true);
		for(String j:l1) {
		f1.write(j+"\n");
		}
        f1.close();
        System.out.println("Values inserted to TAble:"+tableName);
	}

	private static void writeSingleValuestoDb(String insertValues, String tableName, String username,
			String existingDatabaseName,File f) throws IOException {
		// TODO Auto-generated method stub
		String filename=f+"//"+tableName+".txt";
		FileWriter f1 = new FileWriter(filename,true);
		f1.write(insertValues+"\n");
        f1.close();
        System.out.println("Values inserted to TAble:"+tableName);
	}

	public static void createTableInDb(Scanner sc,String create_table, String filepath, String username, String password,
			String securityAnswer, String existingDatabaseName) throws IOException {
		// TODO Auto-generated method stub
		System.out.println(create_table);
		String tableName="";
		String tableName_Split=create_table.split(" ")[2];
		if(tableName_Split.contains("\\("))
		{
			tableName=tableName_Split.split("(")[0];
		}
		else
		{
			tableName=tableName_Split;
		}
		File f=new File(constants.databases_direc+username+"//"+existingDatabaseName);
		List<String> colnames=new ArrayList<String>();
		if(checkIfTableExists(sc,tableName,filepath,username,password,securityAnswer,existingDatabaseName,constants.databases_direc))
		{
			System.out.println("TABLE NAME EXISTS!!!!");
			start(sc,filepath,username,password,securityAnswer,existingDatabaseName);
		}
		else
		{
			createFileWithTableName(f,tableName);
			colnames=extractColumnNamesFromTable(create_table);
			writeColumnNamesTotable(colnames,f,tableName);
			start(sc,filepath,username,password,securityAnswer,existingDatabaseName);
		}
		
	}

	public static void writeColumnNamesTotable(List<String> colnames, File f, String tableName) throws IOException {
		// TODO Auto-generated method stub
		String filename=f+"//"+tableName+".txt";
		File f1=new File(filename);
		String final_str="";
		StringBuilder sb=new StringBuilder(final_str);
		//String str = name+"789"+signInPassword+"789"+securityAnswer;
        FileWriter fw = new FileWriter(filename);
        for(String s:colnames) {
        	sb.append(s+"***");
        }
        fw.write(sb.substring(0, sb.length()-4).concat("\n").toString());
        fw.close();
        System.out.println("Column names captured!!!!!!");
	}

	public static List<String> extractColumnNamesFromTable(String create_table) {
		// TODO Auto-generated method stub
		String colnames,exccolnames=null;
		ArrayList<String> colnames_al=new ArrayList<String>();
		colnames_al.clear();
		int cnt=0;
		colnames=(String) create_table.subSequence(create_table.indexOf('('), create_table.indexOf(';'));
		exccolnames=colnames.substring(1, colnames.length()-1);
		for(int i=0;i<exccolnames.length();i++)
		{
			if(exccolnames.charAt(i)==',')
				cnt++;
		}
		String[] x=new String[cnt+1];
		for(int i=0;i<cnt+1;i++)
		{
			x[i]=exccolnames.split(",")[i];
			
		}
		for(int i1=0;i1<cnt+1;i1++)
		{
			colnames_al.add(x[i1].split(" ")[0]);
		}
		Arrays.fill( x, null );
		return colnames_al;
	}

	public static boolean checkIfTableExists(Scanner sc,String tableName, String filepath, String username,String password,String securityAnswer,
			String existingDatabaseName, String databases_direc) throws IOException {
		// TODO Auto-generated method stub
		List<String> tablesInDb=new ArrayList<String>();
		File f=new File(databases_direc+username+"//"+existingDatabaseName);
		tablesInDb=listFilesForFolder(f);
		if(tablesInDb.contains(tableName+".txt"))
		{
			return true;
		}
		else if(tablesInDb.size()==0 || !tablesInDb.contains(tableName+".txt"))
		{
			return false;
			
		}
		return false;
	}

	public static void createFileWithTableName(File f, String tableName) throws IOException {
		// TODO Auto-generated method stub
		String textfile=f+"//"+tableName+".txt";
		File file =new File(textfile);
		file.createNewFile();
		//file.close();
		System.out.println("Table creation successful!!");
	}

	public static List<String> listFilesForFolder(File folder) throws IOException {
		// TODO Auto-generated method stub
		List<String> l1=new ArrayList<String>();
		for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	            //
	        	//System.out.println(fileEntry.getName());
	            l1.add(fileEntry.getName());
	        }
	    }
		return l1;
	}
	
	public static void readAllColumnsFromFile(File f,String username,String existingDatabaseName,String tableName) throws IOException {
		// TODO Auto-generated method stub
		String filename=f+"//"+tableName+".txt";
		File f1=new File(filename);
		 //File file = new File(folder+filename);
		 BufferedReader br= new BufferedReader(new FileReader(f1));
		 String st;
		 //String[] x=new String[10];
		 //int i=0;
		 

	        while ((st = br.readLine()) != null)
	        {
	     
	        	System.out.println(st.replace("***","|"));
	        }
	        br.close();
	     }
	
	/*public static void writefile(File folder,String name, String signInPassword, String securityAnswer,String filename) throws IOException {
		// TODO Auto-generated method stub
		
			
			String textfile=folder+filename;
			File file =new File(textfile);
			if(file.exists())
			{
				if(checkIfUserNameExists( folder, name,filename))
					{
						System.out.println("username already exists!!!!");
						System.out.println("please take another username...");
						authentication.start();
					}
			}
			else
			{
				String str = name+"789"+signInPassword+"789"+securityAnswer;
	            FileWriter fw = new FileWriter(textfile);
	            fw.write(str);
	            fw.close();
	            System.out.println("succesfully signed in!!!!!!");
			}
			
            
	    
	}*/
	public static List<String> getExistingColumnNames(String table,File f) throws IOException
	{
		ArrayList<String> existingColNames=new ArrayList<String>();
		String text=f+"//"+table+".txt";
		File f1=new File(text);
		 int cnt=0;
		 String colnames="";
		 String findStr = "***";
		 
		 BufferedReader br= new BufferedReader(new FileReader(f1));
		 String st;
		 while ((st = br.readLine()) != null)
	        {
			 	if(cnt==0)
	        	colnames=st.concat("***");
			 	break;
	        }
		 br.close();
		 int appereanceCount=findStringAppereanceCount(colnames,findStr);
		 for(int i=0;i<appereanceCount;i++)
				existingColNames.add(colnames.split("\\*\\*\\*")[i]);
			//System.out.println(l1);
		 return existingColNames;
		
	     }

	private static int findStringAppereanceCount(String str, String findStr) {
		// TODO Auto-generated method stub
		int lastIndex = 0;
		int count = 0;

		while (lastIndex != -1) {

		    lastIndex = str.indexOf(findStr, lastIndex);

		    if (lastIndex != -1) {
		        count++;
		        lastIndex += findStr.length();
		    }
		}
		return count;
	}
	}


