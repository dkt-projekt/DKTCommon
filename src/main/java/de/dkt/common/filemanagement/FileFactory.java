package de.dkt.common.filemanagement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.UrlResource;

import de.dkt.common.exceptions.FileNotFoundException;

public class FileFactory {

	public static void main(String[] args) throws Exception {
		File f = FileFactory.generateFileInstance("/Users/jumo04/Documents/DFKI/DKT/dkt-test/testComplete/sesameStorage/testTimelining");
	}
	
	public static File generateFileInstance(String path) throws IOException {
		
		try{
			//Test if it is http file
			if(path.startsWith("http")){
				UrlResource ur = new UrlResource(path);
				if(ur!=null && ur.exists()){
					return ur.getFile();
				}
				else{
					throw new IOException("HTTP file not found or not accesible.");
				}
			}
			else if(path.startsWith("ftp")){
				UrlResource ur = new UrlResource(path);
				//TODO Authentication needed
				if(ur!=null && ur.exists()){
					return ur.getFile();
				}
				else{
					throw new IOException("FTP file not found or not accesible.");
				}
			}
			else if(path.startsWith("file:")){
				UrlResource ur = new UrlResource(path);
				//TODO Authentication needed
				if(ur!=null && ur.exists()){
					return ur.getFile();
				}
				else{
					throw new IOException("Network file not found or not accesible.");
				}
			}
			else{
				//The rest of the possibilities: classpath, filesystem or network storage
				ClassPathResource cpr = new ClassPathResource(path);
				if(cpr!=null && cpr.exists()){
					return cpr.getFile();
				}
				else{
					FileSystemResource fsr = new FileSystemResource(path);
					if(fsr!=null && fsr.exists()){
						return fsr.getFile();
					}
					else{
						if(path.startsWith("/") || path.charAt(1)==':'){
							//Network storage
							try{
								UrlResource ur = new UrlResource(path);
								if(ur!=null && ur.exists()){
									return ur.getFile();
								}
							}
							catch(Exception e){
							}
						}
						throw new IOException("File ["+path+"] not found.");
					}
				}
			}
		}
		catch(IOException e){
			e.printStackTrace();
			throw e;
		}
	}
	
	public static File generateOrCreateFileInstance(String path) throws IOException {
		try{
			File f = generateFileInstance(path);
			if(f!=null && f.exists()){
				System.out.println("Using existing file, not generating anything new");
				return f; 
			}
		}
		catch(IOException e){
			//System.out.println("/////////////////////////Fucking bitch");
		}
		//Parent folder
		String parentPath = path.substring(0,path.lastIndexOf(File.separator)+1);
		
		if(path.startsWith("http")){
			//TODO HTTP file generation not implemented yet.
			throw new IOException("HTTP file generation not implemented yet.");
		}
		else if(path.startsWith("ftp")){
			//TODO FTP file generation not implemented yet.
			throw new IOException("FTP file generation not implemented yet.");
		}
		else if(path.startsWith("file:")){
			//TODO FTP file generation not implemented yet.
			throw new IOException("NETWORK file generation not implemented yet.");
		}
		else{
//				System.out.println(parentPath);
			//The rest of the possibilities: classpath, filesystem or network storage
			ClassPathResource cpr = new ClassPathResource(parentPath);
			if(cpr!=null && cpr.exists()){
//					System.out.println(path.substring(path.lastIndexOf(File.separator)));
				File newFile = new File(cpr.getFile(),path.substring(path.lastIndexOf(File.separator)));
				if(newFile.createNewFile()){
					return newFile;
				}
				else{
					throw new IOException("Unable to generate file: "+newFile.getAbsolutePath());
				}
			}
			else{
				FileSystemResource fsr = new FileSystemResource(parentPath);
				if(fsr!=null && fsr.exists()){
					File newFile = new File(fsr.getFile(),path.substring(path.lastIndexOf(File.separator)));
					if(newFile.createNewFile()){
						return newFile;
					}
					else{
						throw new IOException("Unable to generate file: "+newFile.getAbsolutePath());
					}
				}
				else{
					if(path.startsWith("/") || path.charAt(1)==':'){
						throw new IOException("Parent folder not found for creating the file.");
					}
					//Network storage
					UrlResource ur = new UrlResource(parentPath);
					if(ur!=null && ur.exists()){
						File newFile = new File(ur.getFile(),path.substring(path.lastIndexOf(File.separator)));
						if(newFile.createNewFile()){
							return newFile;
						}
						else{
							throw new IOException("Unable to generate file: "+newFile.getAbsolutePath());
						}
					}
					else{
						throw new IOException("Network file not found or not accesible.");
					}
				}
			}
		}
	}

	public static File generateOrCreateDirectoryInstance(String path) throws IOException {
		try{ 
			File f = generateFileInstance(path);
			if(f==null || !f.exists()){
				throw new FileNotFoundException("File not found");
			}
			return f;
		}
		catch(Exception e){
			
			//Parent folder
			String parentPath;
			if(path.endsWith(File.separator)){
				path = path.substring(0, path.length()-1);
			}
			if(path.lastIndexOf(File.separator)!=-1){
				parentPath = path.substring(0,path.lastIndexOf(File.separator)+1);
			}
			else{
				parentPath = ".";
			}
			
			if(path.startsWith("http")){
				//TODO HTTP file generation not implemented yet.
				throw new IOException("HTTP file generation not implemented yet.");
			}
			else if(path.startsWith("ftp")){
				//TODO FTP file generation not implemented yet.
				throw new IOException("FTP file generation not implemented yet.");
			}
			else if(path.startsWith("file:")){
				//TODO FTP file generation not implemented yet.
				throw new IOException("NETWORK file generation not implemented yet.");
			}
			else{
				//The rest of the possibilities: classpath, filesystem or network storage
//				System.out.println("Working Directory = " +
//			              System.getProperty("user.dir"));
//				System.out.println("PARENTPATH: "+parentPath);
				ClassPathResource cpr = new ClassPathResource(parentPath);
//				System.out.println("CPR: "+cpr);
//				System.out.println("CPRPATH: "+cpr.getPath());
//				System.out.println("CPREXISTS: "+cpr.exists());
//				System.out.println("CPRURL: "+cpr.getURL());
//				System.out.println("CPRURL: "+cpr.getFile().getAbsolutePath());
				if(cpr!=null && cpr.exists()){
					File newFile = new File(cpr.getFile(),path.substring(path.lastIndexOf(File.separator)));
					if(newFile.mkdir()){
						return newFile;
					}
					else{
						throw new IOException("Unable to generate directory: "+newFile.getAbsolutePath());
					}
				}
				else{
					FileSystemResource fsr = new FileSystemResource(parentPath);
					if(fsr!=null && fsr.exists()){
						File newFile = new File(fsr.getFile(),path.substring(path.lastIndexOf(File.separator)));
						if(newFile.mkdir()){
							return newFile;
						}
						else{
							throw new IOException("Unable to generate directory: "+newFile.getAbsolutePath());
						}
					}
					else{
						if(path.startsWith("/") || path.charAt(1)==':'){
							throw new IOException("Parent folder not found for creating the file.");
						}
						try{
							UrlResource ur = new UrlResource(parentPath);
							if(ur!=null && ur.exists()){
								File newFile = new File(ur.getFile(),path.substring(path.lastIndexOf(File.separator)));
								if(newFile.mkdir()){
									return newFile;
								}
								else{
									throw new IOException("Unable to generate directory: "+newFile.getAbsolutePath());
								}
							}
						}
						catch(Exception ex){
						}
						throw new IOException("File ["+path+"] not found.");
					}
				}
			}
		}
	}
	
	public static BufferedWriter generateBufferedWriterInstance(String path, String encoding, boolean append) throws IOException {
		File f = generateFileInstance(path);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f,append), encoding));
		return bw;
	}
	public static BufferedReader generateBufferedReaderInstance(String path, String encoding) throws IOException {
		File f = generateFileInstance(path);
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), encoding));
		return br;
	}
	public static BufferedWriter generateOrCreateBufferedWriterInstance(String path, String encoding, boolean append) throws IOException {
		File f = generateOrCreateFileInstance(path);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f,append), encoding));
		return bw;
	}
	public static BufferedReader generateOrCreateBufferedReaderInstance(String path, String encoding) throws IOException {
		File f = generateOrCreateFileInstance(path);
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), encoding));
		return br;
	}
}
