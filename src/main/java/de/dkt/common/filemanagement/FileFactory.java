package de.dkt.common.filemanagement;

import java.io.File;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.UrlResource;

import de.dkt.common.exceptions.FileNotFoundException;

public class FileFactory {

	
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
							UrlResource ur = new UrlResource(path);
							if(ur!=null && ur.exists()){
								return ur.getFile();
							}
							else{
								throw new IOException("Network file not found or not accesible.");
							}
						}
						throw new IOException("File not found.");
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
			if(f==null || !f.exists()){
				throw new FileNotFoundException("File not found");
			}
			return f;
		}
		catch(Exception e){
			
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
				parentPath = path.substring(0,path.lastIndexOf(File.separator)+1);
			}
			else{
				parentPath = path.substring(0,path.lastIndexOf(File.separator)+1);
			}
			
			if(path.startsWith("http")){
				//TODO HTTP file generation not implemented yet.
				throw new IOException("HTTP file generation not implemented yet.");
			}
			else if(path.startsWith("ftp")){
				//TODO FTP file generation not implemented yet.
				throw new IOException("FTP file generation not implemented yet.");
			}
			else{
				//The rest of the possibilities: classpath, filesystem or network storage
				ClassPathResource cpr = new ClassPathResource(parentPath);
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
						//Network storage
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
						else{
							throw new IOException("Network file not found or not accesible.");
						}
					}
				}
			}
		}
	}
}
