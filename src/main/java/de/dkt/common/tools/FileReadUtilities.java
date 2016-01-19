package de.dkt.common.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

import java.io.StringWriter;

import de.dkt.common.filemanagement.FileFactory;

public class FileReadUtilities {
	
    
	public static String readFile2String(String fileName) throws IOException {
		File f = FileFactory.generateFileInstance(fileName);
	    BufferedReader br = new BufferedReader(new FileReader(f));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}

}
