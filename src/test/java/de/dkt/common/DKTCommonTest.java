package de.dkt.common;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.context.ApplicationContext;

import com.mashape.unirest.http.exceptions.UnirestException;

import de.dkt.common.authentication.UserAuthentication;
import de.dkt.common.filemanagement.FileFactory;
import eu.freme.bservices.testhelper.TestHelper;
import eu.freme.bservices.testhelper.ValidationHelper;
import eu.freme.bservices.testhelper.api.IntegrationTestSetup;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DKTCommonTest {

	TestHelper testHelper;
	ValidationHelper validationHelper;
//	UserAuthentication userAuth;

	@Before
	public void setup() {
		ApplicationContext context = IntegrationTestSetup
				.getContext(TestConstants.pathToPackage);
		testHelper = context.getBean(TestHelper.class);
		validationHelper = context.getBean(ValidationHelper.class);
//		userAuth = context.getBean(UserAuthentication.class);
	}

	
	@Test
	public void testSanityCheck() throws IOException,Exception {
	}
	
	/**
	 * TEST FOR FILEMANAGEMENET
	 */

//	/**
//	 * Check classpath file instance generation
//	 * @throws UnirestException
//	 * @throws IOException
//	 * @throws Exception
//	 */
//	@Test
//	public void testDKTCommonFileFactoryClasspathfile() throws IOException,
//			Exception {
//		String filePath = "documents/doc1.txt";
//		File f = FileFactory.generateFileInstance(filePath);
//		Assert.assertNotNull("The generated file instance is null and should not be.", f);
//		Assert.assertTrue("The generated file instance does not exists and it should do.", f.exists());
//
//		String filePath2 = "documents/doc2.txt";
//		try{
//			File f2 = FileFactory.generateFileInstance(filePath2);
//		}
//		catch(IOException e){
//			Assert.assertEquals("File ["+filePath2+"] not found.", e.getMessage());
//		}
////		Assert.assertNotNull("The generated file instance is null and should not be.", f2);
////		Assert.assertTrue("The generated file instance does not exists and it should do.", f2.exists());
//
//		String filePath3 = "documents/doc3.txt";
//		File f3 = FileFactory.generateOrCreateFileInstance(filePath3);
//		Assert.assertNotNull("The generated file instance is null and should not be.", f3);
//		Assert.assertTrue("The generated file instance does not exists and it should do.", f3.exists());
//
//		String filePath4 = "documents/testfolder";
//		File f4 = FileFactory.generateOrCreateDirectoryInstance(filePath4);
//		Assert.assertNotNull("The generated file instance is null and should not be.", f4);
//		Assert.assertTrue("The generated file instance does not exists and it should do.", f4.exists());
//		Assert.assertTrue("The generated file instance is not a directory and it should be.", f4.isDirectory());
//	}
//	
//	/**
//	 * Check MAC/Linux filesystem file instance generation
//	 * @throws UnirestException
//	 * @throws IOException
//	 * @throws Exception
//	 */
//	@Test
//	public void testDKTCommonFileFactoryFilesystemfile() throws IOException,
//			Exception {
//		
//		String OS = System.getProperty("os.name");
//		System.out.println(OS);
//		
//		String filePath = "/Users/jumo04/Documents/DFKI/DKT/dkt-test/docs/prueba101.txt";
//		String filePath2 = "/Users/jumo04/Documents/DFKI/DKT/dkt-test/docs/prueba102.txt";
//		String filePath3 = "/Users/jumo04/Documents/DFKI/DKT/dkt-test/docs/prueba103.txt";
//		String filePath4 = "/Users/jumo04/Documents/DFKI/DKT/dkt-test/docs/testfolder";
//		
//		if(OS.startsWith("Mac")){
//		}
//		else if(OS.startsWith("Windows")){
//			filePath = "C:/";
//			filePath2 = "";
//			filePath3 = "";
//			filePath4 = "";
//		}
//		else if(OS.startsWith("Linux")){
//			filePath = "/tmp/storage/documents/prueba101.txt";
//			filePath2 = "/tmp/storage/documents/prueba102.txt";
//			filePath3 = "/tmp/storage/documents/prueba103.txt";
//			filePath4 = "/tmp/storage/documents/testfolder";
//		}
//
//		File f = FileFactory.generateFileInstance(filePath);
//		Assert.assertNotNull("The generated file instance is null and should not be.", f);
//		Assert.assertTrue("The generated file instance does not exists and it should do.", f.exists());
//		
//		try{
//			File f2 = FileFactory.generateFileInstance(filePath2);
//		}
//		catch(IOException e){
//			Assert.assertEquals("File ["+filePath2+"] not found.", e.getMessage());
//		}
//
//		File f3 = FileFactory.generateOrCreateFileInstance(filePath3);
//		Assert.assertNotNull("The generated file instance is null and should not be.", f3);
//		Assert.assertTrue("The generated file instance does not exists and it should do.", f3.exists());
//
//		File f4 = FileFactory.generateOrCreateDirectoryInstance(filePath4);
//		Assert.assertNotNull("The generated file instance is null and should not be.", f4);
//		Assert.assertTrue("The generated file instance does not exists and it should do.", f4.exists());
//		Assert.assertTrue("The generated file instance is not a directory and it should be.", f4.isDirectory());
//	}
//	
	/**
	 * Check http filesystem file instance generation
	 * @throws UnirestException
	 * @throws IOException
	 * @throws Exception
	 */
//	@Test
//	public void test9_DKTCommonFileFactoryHttpFile() throws IOException,
//			Exception {
//		String filePath = "http://dev.digitale-kuratierung.de/data/test.html";
//		//Check classpath resources
//		File f = FileFactory.generateFileInstance(filePath);
//		Assert.assertNotNull("The generated file instance is null and should not be.", f);
//		Assert.assertTrue("The generated file instance does not exists and it should do.", f.exists());
//		
//		
//	}

	/**
	 * TEST FOR AUTHENTICATION
	 */

//	@Test(expected=BadRequestException.class)
//	public void test11_Authentication1() throws Exception {
//		AuthenticationService as = UserAuthentication.getAuthenticationService("file", "lucene", "indexName", true);
//		Assert.assertTrue("The generated authentication service instance is not FileAuthenticationService and it should do.", as instanceof FileAuthenticationService);
//		
//		AuthenticationService as2 = UserAuthentication.getAuthenticationService("database", "", "", true);
//	}
//	
//	@Test
//	public void test12_Authentication2() throws Exception {
//		boolean au1 = UserAuthentication.authenticateUser("user", "password", "file", "lucene", "indexName", true);
//		Assert.assertFalse("The user should not be authenticated.", au1);
//
//		boolean ua2 = UserAuthentication.addCredentials("max", "mustermann", "", "file", "lucene", "indexName",true);
//		Assert.assertTrue("User credentials should be added.", ua2);
//
//		boolean ua3 = UserAuthentication.checkAndAddCredentials("max", "mustermann", "max2", "mustermann2", "", "file", "lucene", "indexName");
//		Assert.assertTrue("User credentials should be added.", ua3);
//	}

//	@Test
//	public void test13_AuthenticationDDBB_RightAccess() throws Exception {
//		boolean ua2 = userAuth.authenticateUser("dkttest", "dkttest", "database", "lucene", "lucenetest", false);
//		Assert.assertTrue("User credentials should be authenticated.", ua2);
//	}
//
//	@Test
//	public void test13_AuthenticationDDBB_WrongAccess() throws Exception {
//		boolean ua2 = userAuth.authenticateUser("dkttest", "dkttest2", "database", "lucene", "lucenetest", false);
//		Assert.assertFalse("User credentials should be authenticated.", ua2);
//	}

}
