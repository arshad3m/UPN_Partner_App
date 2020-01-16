package com.upnorway.testcases;

import java.util.Hashtable;

import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.upnorway.base.TestBase;
import com.upnorway.pages.LoginPage;
import com.upnorway.utilities.TestUtil;

public class Login_test extends TestBase{

	LoginPage db = new LoginPage();
	
	//Verify the email field in the forgot password section
	
	@Test(enabled = true, dataProviderClass = TestUtil.class, dataProvider = "dp", priority = 1)
	public void emailValidation_ForgotPassword(Hashtable<String, String> data) throws Exception {
		
		if (!data.get("runmode").equals("Y")) {
			throw new SkipException("Skipping the test case as the Run mode for data set is NO");
		}

		db.goToForgotPassword();
		db.enterEmail(data.get("email"));
		db.clickEnter();
		
		Thread.sleep(3000);

		String validationMessage = driver.findElement(By.xpath(OR.getProperty("fpemailValidationmsg_XPATH"))).getText();

		verifyEquals(data.get("message"),validationMessage);
		
	}
	
	//Attempt to add an invalid email for 6 times to verify attempt email error
	
	@Test(enabled = true, dataProviderClass = TestUtil.class, dataProvider = "dp", priority = 2)
	public void attemptError_ForgotPassword(Hashtable<String, String> data) throws Exception {
		
		if (!data.get("runmode").equals("Y")) {
			throw new SkipException("Skipping the test case as the Run mode for data set is NO");
		}

		db.goToForgotPassword();
		//Enter error email 6 times
		for (int i = 1; i <= 6; i++) {
		db.enterEmail(data.get("email"));
		db.clickEnter();
		
		Thread.sleep(3000);
		}
		String validationMessage = driver.findElement(By.xpath(OR.getProperty("fpattemptEmailmsg_XPATH"))).getText();

		verifyEquals(data.get("message"),validationMessage);
		
	}
	
	//Verify the error message which gets after provide empty/invalid code for Change password
	
	@Test(enabled = true, dataProviderClass = TestUtil.class, dataProvider = "dp", priority = 3)
	public void emptyCode_ForgotPassword(Hashtable<String, String> data) throws Exception {
		
		if (!data.get("runmode").equals("Y")) {
			throw new SkipException("Skipping the test case as the Run mode for data set is NO");
		}

		db.goToForgotPassword();
		db.enterEmail(data.get("email"));
		db.clickEnter();
		
		Thread.sleep(3000);
		
		db.enterCode(data.get("code"));
		db.enterNewPassword(data.get("npwd"));
		db.enterConfirmPassword(data.get("cpwd"));
		db.clickChange();
		
		Thread.sleep(3000);
		
		String validationMessage = driver.findElement(By.xpath(OR.getProperty("cpValidationmsg_XPATH"))).getText();

		verifyEquals(data.get("message"),validationMessage);
		
	}
	
	//Verify the error message when the email is null in the login page
	
	@Test(enabled = true, dataProviderClass = TestUtil.class, dataProvider = "dp", priority = 4)
	public void nullEmail_Login(Hashtable<String, String> data) throws Exception {
		
		if (!data.get("runmode").equals("Y")) {
			throw new SkipException("Skipping the test case as the Run mode for data set is NO");
		}

		db.loginEmail(data.get("email"));
		db.loginPassword(data.get("password"));
		db.clickEnter();
		
		Thread.sleep(3000);
		
		String validationMessage = driver.findElement(By.xpath(OR.getProperty("nullEmailValidationmsg_XPATH"))).getText();

		verifyEquals(data.get("message"),validationMessage);
		
	}
	//Verify the error message when the password is null in the login page
	
	@Test(enabled = true, dataProviderClass = TestUtil.class, dataProvider = "dp", priority = 5)
	public void nullPassword_Login(Hashtable<String, String> data) throws Exception {
		
		if (!data.get("runmode").equals("Y")) {
			throw new SkipException("Skipping the test case as the Run mode for data set is NO");
		}

		db.loginEmail(data.get("email"));
		db.loginPassword(data.get("password"));
		db.clickEnter();
		
		Thread.sleep(3000);
		
		String validationMessage = driver.findElement(By.xpath(OR.getProperty("nullPwdValidationmsg_XPATH"))).getText();

		verifyEquals(data.get("message"),validationMessage);
		
	}
	
	////Verify the error message when the invalid email/password is provided in the login page
	
	@Test(enabled = true, dataProviderClass = TestUtil.class, dataProvider = "dp", priority = 6)
	public void validation_Login(Hashtable<String, String> data) throws Exception {
		
		if (!data.get("runmode").equals("Y")) {
			throw new SkipException("Skipping the test case as the Run mode for data set is NO");
		}

		db.loginEmail(data.get("email"));
		db.loginPassword(data.get("password"));
		db.clickEnter();
		
		Thread.sleep(3000);
		
		String validationMessage = driver.findElement(By.xpath(OR.getProperty("errorValidationmsg_XPATH"))).getText();

		verifyEquals(data.get("message"),validationMessage);
		
	}
	
	//Successfully login into the login page
	
	@Test(enabled = true, dataProviderClass = TestUtil.class, dataProvider = "dp", priority = 7)
	public void successfully_Login(Hashtable<String, String> data) throws Exception {
		
		if (!data.get("runmode").equals("Y")) {
			throw new SkipException("Skipping the test case as the Run mode for data set is NO");
		}

		db.loginEmail(data.get("email"));
		db.loginPassword(data.get("password"));
		db.clickEnter();
		
		Thread.sleep(3000);
		
		String validationMessage = driver.findElement(By.xpath(OR.getProperty("dashBoardTitle_XPATH"))).getText();

		verifyEquals(data.get("title"),validationMessage);
		
	}
}
