package com.mockdb.creation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public  class Logger {
	static LocalDateTime now = LocalDateTime.now();
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    static String formattedDateTime = now.format(formatter);
   // System.out.println("Current Date and Time: " + formattedDateTime);
   // static String path="";
    
	public static void log(String msg) throws IOException {
			
		try {
			
			
			
		    FileWriter f=new FileWriter(constants.loggers_direc,true);
		    f.write("["+formattedDateTime+"]:"+msg+"\n");
		    f.close();
		}
		catch(Exception e)
		{
			throw new IOException("Unable to create or read the file at this moment!!!");
		}
		
	}
	
	

}
