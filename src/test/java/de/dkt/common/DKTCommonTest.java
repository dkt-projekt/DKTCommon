package de.dkt.common;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.dkt.common.filemanagement.FileFactory;

public class DKTCommonTest {

	@Before
	public void setup() {
	}
	
	/**
	 * Check classpath file instance generation
	 * @throws UnirestException
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void testDKTCommonFileFactoryClasspathfile() throws IOException,
			Exception {
		String filePath = "documents/doc1.txt";
		File f = FileFactory.generateFileInstance(filePath);
		Assert.assertNotNull("The generated file instance is null and should not be.", f);
		Assert.assertTrue("The generated file instance does not exists and it should do.", f.exists());

		String filePath2 = "documents/doc2.txt";
		try{
			File f2 = FileFactory.generateFileInstance(filePath2);
		}
		catch(IOException e){
			Assert.assertEquals("File ["+filePath2+"] not found.", e.getMessage());
		}
//		Assert.assertNotNull("The generated file instance is null and should not be.", f2);
//		Assert.assertTrue("The generated file instance does not exists and it should do.", f2.exists());

		String filePath3 = "documents/doc3.txt";
		File f3 = FileFactory.generateOrCreateFileInstance(filePath3);
		Assert.assertNotNull("The generated file instance is null and should not be.", f3);
		Assert.assertTrue("The generated file instance does not exists and it should do.", f3.exists());

		String filePath4 = "documents/testfolder";
		File f4 = FileFactory.generateOrCreateDirectoryInstance(filePath4);
		Assert.assertNotNull("The generated file instance is null and should not be.", f4);
		Assert.assertTrue("The generated file instance does not exists and it should do.", f4.exists());
		Assert.assertTrue("The generated file instance is not a directory and it should be.", f4.isDirectory());
	}
	
	/**
	 * Check MAC/Linux filesystem file instance generation
	 * @throws UnirestException
	 * @throws IOException
	 * @throws Exception
	 */
	@Test
	public void testDKTCommonFileFactoryFilesystemfile() throws IOException,
			Exception {
		
		String OS = System.getProperty("os.name");
		System.out.println(OS);
		
		String filePath = "/Users/jumo04/Documents/DFKI/DKT/dkt-test/docs/prueba101.txt";
		String filePath2 = "/Users/jumo04/Documents/DFKI/DKT/dkt-test/docs/prueba102.txt";
		String filePath3 = "/Users/jumo04/Documents/DFKI/DKT/dkt-test/docs/prueba103.txt";
		String filePath4 = "/Users/jumo04/Documents/DFKI/DKT/dkt-test/docs/testfolder";
		
		if(OS.startsWith("Mac")){
		}
		else if(OS.startsWith("Windows")){
			filePath = "";
			filePath2 = "";
			filePath3 = "";
			filePath4 = "";
		}
		else if(OS.startsWith("Linux")){
		}

		File f = FileFactory.generateFileInstance(filePath);
		Assert.assertNotNull("The generated file instance is null and should not be.", f);
		Assert.assertTrue("The generated file instance does not exists and it should do.", f.exists());
		
		try{
			File f2 = FileFactory.generateFileInstance(filePath2);
		}
		catch(IOException e){
			Assert.assertEquals("File ["+filePath2+"] not found.", e.getMessage());
		}

		File f3 = FileFactory.generateOrCreateFileInstance(filePath3);
		Assert.assertNotNull("The generated file instance is null and should not be.", f3);
		Assert.assertTrue("The generated file instance does not exists and it should do.", f3.exists());

		File f4 = FileFactory.generateOrCreateDirectoryInstance(filePath4);
		Assert.assertNotNull("The generated file instance is null and should not be.", f4);
		Assert.assertTrue("The generated file instance does not exists and it should do.", f4.exists());
		Assert.assertTrue("The generated file instance is not a directory and it should be.", f4.isDirectory());
	}
	
//	/**
//	 * Check http filesystem file instance generation
//	 * @throws UnirestException
//	 * @throws IOException
//	 * @throws Exception
//	 */
//	@Test
//	public void testDKTCommonFileFactoryHttpFile() throws IOException,
//			Exception {
//		String filePath = "http://v35731.1blu.de/resources/documents/prueba101.txt";
//		//Check classpath resources
//		File f = FileFactory.generateFileInstance(filePath);
//		Assert.assertNotNull("The generated file instance is null and should not be.", f);
//		Assert.assertTrue("The generated file instance does not exists and it should do.", f.exists());
//	}
	
}
