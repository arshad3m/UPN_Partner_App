package com.upnorway.base;

import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.upnorway.utilities.ExcelReader;
import com.upnorway.utilities.ExtentManager;
import com.upnorway.utilities.TestUtil;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * @author ArshadM
 *
 */
public class TestBase {

	/*
	 * WebDriver - done Properties - done Logs - log4j jar, .log, log4j.properties,
	 * Logger ExtentReports DB Excel Mail ReportNG, ExtentReports Jenkins
	 * 
	 */

	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\testdata.xlsx");
	public static WebDriverWait wait;
	public ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;
	public static String browser;
	public static Actions action;

	/**
	 * @author ArshadM initiating file input streams initiating browser driver
	 *         inititating configurations initiating reports intitiating logs
	 * @throws ClassNotFoundException 
	 * @throws InterruptedException 
	 * @throws IOException 
	 */

	@BeforeSuite
	public void setUp() throws ClassNotFoundException, InterruptedException, IOException {

		if (driver == null) {

			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
				log.debug("Config file loaded !!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				log.debug("OR file loaded !!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			


			if (System.getenv("browser") != null && !System.getenv("browser").isEmpty()) {

				browser = System.getenv("browser");
			} else {

				browser = config.getProperty("browser");

			}

			config.setProperty("browser", browser);

			if (config.getProperty("browser").equals("firefox")) {

				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") +"\\src\\test\\resources\\executables\\geckodriver.exe");
				driver = new FirefoxDriver();

			} else if (config.getProperty("browser").equals("chrome")) {

				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\chromedriver.exe");
				driver = new ChromeDriver();
				log.debug("Chrome Launched !!!");
			} else if (config.getProperty("browser").equals("ie")) {

				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\msedgedriver.exe");
				
				ChromeOptions chromeOptions = new ChromeOptions();
			    chromeOptions.setBinary(
			            "C:\\Program Files (x86)\\Microsoft\\Edge Beta\\Application\\msedge.exe");
			    MutableCapabilities edgeOptions = new EdgeOptions().merge(chromeOptions);
				
				driver = new ChromeDriver(edgeOptions);

			}

			driver.get(config.getProperty("testsiteurl"));
			log.debug("Navigated to : " + config.getProperty("testsiteurl"));
			
			
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")),
					TimeUnit.SECONDS);
			

			//login();

			
			wait = new WebDriverWait(driver, 25);
			action = new Actions(driver);
			
	//		login();
		}

	}	
	/**
	 * @author ArshadM Wrapper mehtod to click on an element
	 */

	public static void click(String locator) {

		try {
		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_XPATH")) {
			Actions action = new Actions(driver);
			action.moveToElement(driver.findElement(By.xpath(OR.getProperty(locator)))).perform();
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(locator)))).click();
			// driver.findElement(By.xpath(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).click();
		}
		test.log(LogStatus.INFO, "Clicking on : " + locator.toString().replace("_XPATH", ""));
		
		}catch(Throwable t) {
			WebElement element = driver.findElement(By.xpath(OR.getProperty(locator)));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", element);
			test.log(LogStatus.INFO, "Clickingg on : " + element.toString().replace("_XPATH", ""));		
		}
	}
	
	
	

	public static void click(WebElement element) {
		wait.until(ExpectedConditions.elementToBeClickable(element));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
		test.log(LogStatus.INFO, "Clicking on : " + element.toString().replace("_XPATH", ""));
	}

	/**
	 * @author ArshadM Wrapper method to enter a value in textbox field
	 */
	public static void type(String locator, String value) {

		if (locator.endsWith("_CSS")) {

			// wait.until(ExpectedConditions.elementToBeClickable(element)).click();
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_XPATH")) {
			// action.moveToElement(driver.findElement(By.xpath(OR.getProperty(locator)))).sendKeys(value).build().perform();;;
			// wait.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(locator)))).clear();

			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(OR.getProperty(locator))))).clear();
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		}

		test.log(LogStatus.INFO,
				"Typing in : " + locator.toString().replace("_XPATH", "") + " entered value as " + value);

	}

	/**
	 * @author ArshadM Method to enter a key
	 */

	public void type(String locator, Keys key) {

		if (locator.endsWith("_CSS")) {

			// wait.until(ExpectedConditions.elementToBeClickable(element)).click();
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(key);
		} else if (locator.endsWith("_XPATH")) {
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(OR.getProperty(locator)))));
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(key);
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(key);
		}

		test.log(LogStatus.INFO, "Pressed the key: " + key + " in : " + locator.toString().replace("_XPATH", ""));

	}

	static WebElement dropdown;

	/**
	 * @author ArshadM Wrapper method for selecting a value from a drop down
	 */
	public void select(String locator, String value) {

		if (locator.endsWith("_CSS")) {
			dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		} else if (locator.endsWith("_XPATH")) {
			dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
		} else if (locator.endsWith("_ID")) {
			dropdown = driver.findElement(By.id(OR.getProperty(locator)));
		}

		Select select = new Select(dropdown);
		select.selectByVisibleText(value);

		test.log(LogStatus.INFO,
				"Selecting from dropdown : " + locator.toString().replace("_XPATH", "") + " value as " + value);

	}

	/**
	 * @author ArshadM Check if an element is present
	 */
	public boolean isElementPresent(By by) {

		try {

			driver.findElement(by);
			return true;

		} catch (NoSuchElementException e) {

			return false;

		}

	}

	public static void verifyEqualsIgnoreCase(String expected, String actual) throws IOException {

		try {

			Assert.assertEquals(actual.toLowerCase(), expected.toLowerCase());
			test.log(LogStatus.INFO, "Verified the expected text: " + expected);

		} catch (Throwable t) {

			TestUtil.captureScreenshot();
			// ReportNG
			Reporter.log("<br>" + "Verification failure : " + t.getMessage() + "<br>");
			Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + "><img src=" + TestUtil.screenshotName
					+ " height=200 width=200></img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			// Extent Reports
			test.log(LogStatus.FAIL, " Verification failed with exception : " + t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));

		}

	}

	/**
	 * @author ArshadM Verify two strings
	 */
	public static void verifyEquals(String expected, String actual) throws IOException {

		try {

			Assert.assertEquals(actual, expected);
			test.log(LogStatus.INFO, "Verified the expected text: " + expected);

		} catch (Throwable t) {

			TestUtil.captureScreenshot();
			// ReportNG
			Reporter.log("<br>" + "Verification failure : " + t.getMessage() + "<br>");
			Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + "><img src=" + TestUtil.screenshotName
					+ " height=200 width=200></img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			// Extent Reports
			test.log(LogStatus.FAIL, " Verification failed with exception : " + t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));

		}

	}

	public static void verifyGreaterThan(int value, int shouldBeGreaterThanThis) throws IOException {

		try {

			boolean state = false;
			if (value > shouldBeGreaterThanThis) {
				state = true;
			}

			assertTrue(state);
			test.log(LogStatus.INFO, "Verified " + value + " is greater than " + shouldBeGreaterThanThis);

		} catch (Throwable t) {

			TestUtil.captureScreenshot();
			// ReportNG
			Reporter.log("<br>" + "Verification failure : " + t.getMessage() + "<br>");
			Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + "><img src=" + TestUtil.screenshotName
					+ " height=200 width=200></img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			// Extent Reports
			test.log(LogStatus.FAIL, " Verification failed with exception : " + t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));

		}
	}
	
	
	public static void verifyLessThan(int value, int shouldBeLessThanThis) throws IOException {

		try {

			boolean state = false;
			if (value < shouldBeLessThanThis) {
				state = true;
			}

			assertTrue(state);
			test.log(LogStatus.INFO, "Verified " + value + " is less than " + shouldBeLessThanThis);

		} catch (Throwable t) {

			TestUtil.captureScreenshot();
			// ReportNG
			Reporter.log("<br>" + "Verification failure : " + t.getMessage() + "<br>");
			Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + "><img src=" + TestUtil.screenshotName
					+ " height=200 width=200></img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			// Extent Reports
			test.log(LogStatus.FAIL, " Verification failed with exception : " + t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));

		}
	}
	
	

	public static void verifyContains(String text, String word) throws IOException {
		try {

			assertTrue(text.toLowerCase().contains(word.toLowerCase()));
			test.log(LogStatus.INFO, "Asserting " + text + " contains: " + word);

		} catch (Throwable t) {

			TestUtil.captureScreenshot();
			// ReportNG
			Reporter.log("<br>" + "Verification failure : " + t.getMessage() + "<br>");
			Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + "><img src=" + TestUtil.screenshotName
					+ " height=200 width=200></img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			// Extent Reports
			test.log(LogStatus.FAIL, " Assertion failed for " + text + " with exception : " + t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));

		}

	}

	public void verifyElementExists(String xpath) throws IOException {

		try {
			List<WebElement> element = driver.findElements(By.xpath(OR.getProperty(xpath)));
			int val = element.size();
			if (val > 0) {
				assertTrue(true);
				test.log(LogStatus.INFO, "Asserting element " + xpath + " exists");
			}

			else {
				assertTrue(false);
				test.log(LogStatus.INFO, "Asserting element " + xpath + " does not exist");
			}

		} catch (Throwable t) {

			TestUtil.captureScreenshot();
			// ReportNG
			Reporter.log("<br>" + "Verification failure : " + t.getMessage() + "<br>");
			Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + "><img src=" + TestUtil.screenshotName
					+ " height=200 width=200></img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			// Extent Reports
			test.log(LogStatus.FAIL, " Assertion failed for " + xpath + " with exception : " + t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));

		}
	}

	
	/**
	 * @author ArshadM Navigate to newly openened tab
	 * Verify the element is not available
	 */
	public void verifyElementExistNot(String xpath) throws IOException {

		try {
			List<WebElement> element = driver.findElements(By.xpath(OR.getProperty(xpath)));
			int val = element.size();
			if (val == 0) {
				assertTrue(true);
				test.log(LogStatus.INFO, "Asserting element " + xpath + " does not exist");
			}

			else {
				assertTrue(false);
				test.log(LogStatus.INFO, "Asserting element " + xpath + " exists");
			}

		} catch (Throwable t) {

			TestUtil.captureScreenshot();
			// ReportNG
			Reporter.log("<br>" + "Verification failure : " + t.getMessage() + "<br>");
			Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + "><img src=" + TestUtil.screenshotName
					+ " height=200 width=200></img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			// Extent Reports
			test.log(LogStatus.FAIL, " Assertion failed for " + xpath + " with exception : " + t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));

		}
	}
	
	

	/**
	 * @author ArshadM Close newly opened tab
	 */

	public void closeNewTab() {
		String originalHandle = driver.getWindowHandle();

		// Do something to open new tabs

		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(originalHandle)) {
				driver.switchTo().window(handle);
				driver.close();
			}
		}

		driver.switchTo().window(originalHandle);
	}

	/**
	 * @author ArshadM Navigate to newly openened tab
	 * 
	 */
	public void swithToNewTab() {
		String originalHandle = driver.getWindowHandle();

		// Do something to open new tabs

		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(originalHandle)) {
				driver.switchTo().window(handle);
				// driver.close();
			}
		}

		test.log(LogStatus.INFO, "Switching to new tab ");

		// driver.switchTo().window(originalHandle);
	}

	
	
	/**
	 * @author ArshadM 
	 * Get text attribute of the element
	 * 
	 */
	public static String getTextOfElement(String xpath) {
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(OR.getProperty(xpath)))));
		String text = driver.findElement(By.xpath(OR.getProperty(xpath))).getText();
		test.log(LogStatus.INFO, "Reading value of " + xpath.toString().replace("_XPATH", " "));
		return text;
	}
	
	
	
	/**
	 * @author ArshadM 
	 * Get text attribute of the element
	 * 
	 */
	public static String getTextAttribute(String xpath) {
		
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(OR.getProperty(xpath)))));
		String text = driver.findElement(By.xpath(OR.getProperty(xpath))).getAttribute("value");
		test.log(LogStatus.INFO, "Reading value of " + xpath.toString().replace("_XPATH", " "));
		
		if(text==null) {
			text=driver.findElement(By.xpath(OR.getProperty(xpath))).getAttribute("innerText");
		}
		
	
		return text;	
		
	}
	/*
	public void login() throws InterruptedException {


		


		driver.findElement(By.xpath(OR.getProperty("email_XPATH"))).sendKeys(config.getProperty("username"));

		driver.findElement(By.xpath(OR.getProperty("password_XPATH"))).sendKeys(config.getProperty("password"));
		Thread.sleep(2000);

		//driver.findElement(By.xpsssssath(OR.getProperty("login_XPATH"))).click();
		
		//click("login_XPATH");
		
		WebElement element = driver.findElement(By.xpath(OR.getProperty("login_XPATH")));
		wait.until(ExpectedConditions.elementToBeClickable(element));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
		
		Thread.sleep(4000);





		
	}*/


	@AfterSuite
	public void tearDown() throws InterruptedException, IOException {

		//logout();
		


		log.debug("test execution completed !!!");
		
		Thread.sleep(10000);
		
		copyLogFiles();
		
		if (driver != null) {
			driver.quit();
		}
	}
	
	
	public void copyLogFiles() throws IOException {
		
		String timeStamp = new SimpleDateFormat("y-M-dd, E 'at' h.mm a").format(new java.util.Date());
		
		File srcDir = new File(System.getProperty("user.dir") + "\\target\\surefire-reports\\html");
		File destDir = new File(System.getProperty("user.dir") + "\\target\\surefire-reports\\"+timeStamp);
		FileUtils.copyDirectory(srcDir, destDir);
		System.out.println("Created reports directory with timestamp");
	}
	
	
	@BeforeMethod
	public void beforeTest() throws InterruptedException {
		closeNewTab();

		driver.get(config.getProperty("testsiteurl"));
	}

	@AfterTest
	public void afterTest() {
		wait = new WebDriverWait(driver, 30);
	}
}