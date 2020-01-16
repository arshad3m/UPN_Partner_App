package com.upnorway.pages;

import com.upnorway.base.TestBase;

public class LoginPage extends TestBase {
	
	public void loginEmail(String email) {
		type("loginEmail_XPATH",email);
	}
	
	public void loginPassword(String pwd) {
		type("loginPassword_XPATH",pwd);
	}

	public void goToForgotPassword() {
		click("clickHere_XPATH");
	}
	
	public void enterEmail(String email) {
		type("fpEmail_XPATH", email);
	}
	
	public void clickEnter() {
		click("fpButton_Enter_XPATH");
	}
	
	public void enterCode(String code) {
		type("fpCode_XPATH", code);
	}
	
	public void enterNewPassword(String npwd) {
		type("fpNewPassword_XPATH", npwd);
	}
	
	public void enterConfirmPassword(String cpwd) {
		type("fpConfirmPassword_XPATH",cpwd);
	}
	
	public void clickChange() {
		click("cpButtonChange_XPATH");
	}
}
