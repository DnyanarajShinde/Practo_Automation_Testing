package test_practo;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.util.SystemOutLogger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.Select;
import com.google.common.collect.Table.Cell;
import eu.cedarsoft.devtools.DevTools;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

public class PractoAutomation {
	private WebDriver driver;
	private String baseURL;
	private String Username;
	private String Password;

	@Before
	public void setUp() throws Exception {
		
		//reading the text file (URL and Login Details)
		Properties prop=new Properties();
		prop.load(new FileInputStream(".\\Configuration\\LoginDetails.txt"));
		baseURL=prop.getProperty("sAppURL");
		Username=prop.getProperty("Username");
		Password=prop.getProperty("Password");
		
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\lenovo\\Desktop\\Drivers\\chromedriver.exe");		//chrome driver setup and path
		driver=new ChromeDriver();																					//create instance of chrome driver
//		System.setProperty("webdriver.gecko.driver", "C:\\Users\\lenovo\\Desktop\\Drivers\\geckodriver.exe");		//gecko-driver setup and path
//		driver=new FirefoxDriver();																					//create instance of firefox driver
		
		driver.manage().window().maximize();																		//maximize window size
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);											//timeout to load website
	}

	@Test
	public void test() throws Exception{
		driver.get(baseURL);	//open baseURL
		
		//Sign-Up
//		driver.findElement(By.xpath(".//*[@id=\'root\']/div/div/div[1]/div[1]/div[2]/div/div[3]/div[3]/span/a")).click();	//click on login/signup
//		driver.findElement(By.xpath(".//*[@id=\'registerLink\']")).click();		//click on register
//		driver.findElement(By.xpath(".//*[@id=\'name\']")).sendKeys("Dnyanaraj Gopal Shinde");		//write your full name
//		driver.findElement(By.xpath(".//*[@id=\'mobile\']")).sendKeys("9021370596");		//write your mobile number
//		driver.findElement(By.xpath(".//*[@id=\'password\']")).sendKeys("dnyanu5678");		//set password
//		driver.findElement(By.xpath(".//*[@id=\'EmailRegister\']")).click();		//send OTP
//		Thread.sleep(10000);	//write OTP manually 
//		driver.findElement(By.xpath(".//*[@id=\'patientregisterOTP\']")).click();	//click on login
		
		//Login
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.findElement(By.xpath(".//*[@id=\'root\']/div/div/div[1]/div[1]/div[2]/div/div[3]/div[3]/span/a")).click();	//click on login/signup
		driver.findElement(By.xpath(".//*[@id=\'username\']")).sendKeys(Username);											//writing username
		driver.findElement(By.xpath(".//*[@id=\'password\']")).sendKeys(Password);											//writing password
		driver.findElement(By.xpath(".//*[@id=\'login\']")).click();														//click on login
		
		String ModuleName="Module Name: Login\t";
		String Result="Result: Passed\t";
		String Comment="Comment: User Logged in Successfully\n\n";
		writeText(ModuleName, Result, Comment);																				//writing module result to text file
		
		//Iterating through cities
		for (int i=1;i<=3;i++) {
			PractoAutomation practo=new PractoAutomation();
			FileInputStream fis= new FileInputStream(new File("./DataFile/cities1.xls"));
			String[] cities=practo.Practo_readXL(2, "cities",fis);
			System.out.println(cities[i]);
		
		//Searching city
		driver.findElement(By.xpath(".//*[@id=\'c-omni-container\']/div/div[1]/div[1]/input")).clear();						//Clears default location
		Thread.sleep(2000);																									//time sleep for 2 seconds (2000 milliseconds)
		driver.findElement(By.xpath(".//*[@id=\'c-omni-container\']/div/div[1]/div[1]/input")).sendKeys(cities[i]);			//search city
		Thread.sleep(4000);
		driver.findElement(By.xpath(".//*[@id=\'c-omni-container\']/div/div[1]/div[1]/input")).sendKeys(Keys.ARROW_DOWN);	//press down key one time
		Thread.sleep(2000);
		driver.findElement(By.xpath(".//*[@id=\'c-omni-container\']/div/div[1]/div[1]/input")).sendKeys(Keys.ARROW_DOWN);	//press down key another one time
		Thread.sleep(2000);
		driver.findElement(By.xpath(".//*[@id=\'c-omni-container\']/div/div[1]/div[1]/input")).sendKeys(Keys.ENTER);		//press enter key
		Thread.sleep(2000);
		
		//Searching Hospitals
		driver.findElement(By.xpath(".//*[@id=\'c-omni-container\']/div/div[2]/div[1]/input")).clear();
		Thread.sleep(2000);
		driver.findElement(By.xpath(".//*[@id=\'c-omni-container\']/div/div[2]/div[1]/input")).sendKeys("Hospital");		//search hospital
		Thread.sleep(2000);
		driver.findElement(By.xpath(".//*[@id=\'c-omni-container\']/div/div[2]/div[1]/input")).sendKeys(Keys.ARROW_DOWN);	//press down key one time
		Thread.sleep(2000);
		driver.findElement(By.xpath(".//*[@id=\'c-omni-container\']/div/div[2]/div[1]/input")).sendKeys(Keys.ENTER);		//press enter key
		Thread.sleep(2000);
		
		//Accredited Hospitals
		driver.findElement(By.xpath(".//*[@id=\'container\']/div[3]/div/div[1]/div/div/header/div[1]/div/div[2]/label/div")).click();		//click on checkbox of Accredited
		
		//24*7 Pharmacy Hospitals
		try {
		driver.findElement(By.xpath(".//*[@id=\'container\']/div[3]/div/div[1]/div/div/header/div[1]/div/div[4]/span/span")).click();		//click on All Filters
		driver.findElement(By.xpath(".//*[@id=\'container\']/div[3]/div/div[1]/div/div/header/div[2]/div/div/div/label[3]/div")).click();	//click on checkbox of 24*7 Pharmacy
		}
		catch(org.openqa.selenium.StaleElementReferenceException ex) {																		//handles "org.openqa.selenium.StaleElementReferenceException ex" exception
			driver.findElement(By.xpath(".//*[@id=\'container\']/div[3]/div/div[1]/div/div/header/div[1]/div/div[4]/span/span")).click();
			driver.findElement(By.xpath(".//*[@id=\'container\']/div[3]/div/div[1]/div/div/header/div[2]/div/div/div/label[3]/div")).click();
		}
		
		//printing hospitals names to the console
		System.out.println("\nTop five hospitals in "+cities[i]+" are listed below: ");
		System.out.println("1: "+driver.findElement(By.xpath(".//*[@id=\'container\']/div[3]/div/div[2]/div[1]/div/div[3]/div[2]/div/div[1]/div[1]/div/div[2]/div/a/h2")).getText());	//getting text from element
		System.out.println("2: "+driver.findElement(By.xpath(".//*[@id=\'container\']/div[3]/div/div[2]/div[1]/div/div[3]/div[3]/div/div[1]/div[1]/div/div[2]/div/a/h2")).getText());
		System.out.println("3: "+driver.findElement(By.xpath(".//*[@id=\'container\']/div[3]/div/div[2]/div[1]/div/div[3]/div[4]/div/div[1]/div[1]/div/div[2]/div/a/h2")).getText());
		System.out.println("4: "+driver.findElement(By.xpath(".//*[@id=\'container\']/div[3]/div/div[2]/div[1]/div/div[3]/div[5]/div/div[1]/div[1]/div/div[2]/div/a/h2")).getText());
		System.out.println("5: "+driver.findElement(By.xpath(".//*[@id=\'container\']/div[3]/div/div[2]/div[1]/div/div[3]/div[6]/div/div[1]/div[1]/div/div[2]/div/a/h2")).getText()+"\n");
		}
				
		//Logout
		try {
		driver.findElement(By.xpath(".//*[@id=\'container\']/div[2]/div[1]/div[1]/div[2]/div/div[3]/div[3]/span/span[1]")).click();					//click on your account
		driver.findElement(By.xpath(".//*[@id=\'container\']/div[2]/div[1]/div[1]/div[2]/div/div[3]/div[3]/span/div/div[10]/a")).click();			//click on logout
		}
		catch(org.openqa.selenium.StaleElementReferenceException ex) {
			driver.findElement(By.xpath(".//*[@id=\'container\']/div[2]/div[1]/div[1]/div[2]/div/div[3]/div[3]/span/span[1]")).click();	
			driver.findElement(By.xpath(".//*[@id=\'container\']/div[2]/div[1]/div[1]/div[2]/div/div[3]/div[3]/span/div/div[10]/a")).click();
			}
		
		String ModuleName1="Module Name: Logout\t";
		String Result1="Result: Passed\t";
		String Comment1="Comment: User Logged out Successfully\n\n";
		appendText(ModuleName1, Result1, Comment1);																									//appending data to existing text file
		
		System.out.println("Result: Test Case Passed!");
		
	}
	
	
	//reading data from excel file
	public static String[] Practo_readXL (int row, String column,FileInputStream fs) 
	{
	jxl.Cell c= null;
	int reqCol=0;
	int reqRow=0;
	WorkbookSettings ws = null;
	Workbook workbook = null;
	Sheet sheet = null;
	try{
	ws = new WorkbookSettings();
	ws.setLocale(new Locale("en", "EN"));
	String[] data = null;

	                                                                       // opening the work book and sheet for reading data
	workbook = Workbook.getWorkbook(fs, ws);
	sheet = workbook.getSheet(0);
	data = new String[sheet.getRows()];

	                                                                      // Sanitise given data
	String col = column.trim();

	 
	                                                                      //loop for going through the given row
	for(int j=0; j<sheet.getColumns(); j++)
	{
	jxl.Cell cell = sheet.getCell(j,0);
	if((cell.getContents().trim()).equalsIgnoreCase(col))
	{ 
	reqCol= cell.getColumn();
	                                                                       //System.out.println("column No:"+reqCol);  
	for (int i = 0; i < sheet.getRows(); i++)
	             {
	c = sheet.getCell(reqCol, reqRow);
	data[i] = c.getContents();
	//System.out.println(data[i]);
	fs.close();
	reqRow=reqRow+1;
	}
	return data;
	}

	}

	}
	catch(BiffException be)
	{

	System.out.println("The given file should have .xls extension.");
	}
	catch(Exception e)
	{
	e.printStackTrace();

	}
	System.out.println("NO MATCH FOUND IN GIVEN FILE: PROBLEM IS COMING FROM DATA FILE");

	return null;
	}
	
	//writing to text file
	public static void writeText(String ModuleName, String Result, String Comment) throws IOException {		
		FileWriter fw=new FileWriter("log.txt",true);
		
		fw.write(ModuleName + Result + Comment);
		fw.close();	
	}
	
	//append data to existing text file
	public static void appendText(String ModuleName1, String Result1, String Comment1) throws IOException {
		FileWriter fw=new FileWriter("log.txt",true);
		
		fw.append(ModuleName1 + Result1 + Comment1);
		fw.close();	
	}
	
	
	@After
	public void tearDown() throws Exception {
		driver.close();
	}

}
