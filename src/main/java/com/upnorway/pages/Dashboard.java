package com.upnorway.pages;

import com.upnorway.base.TestBase;

public class Dashboard extends TestBase {
	
	public void loginEmail(String email) {
		type("loginEmail_XPATH",email);
	}
	
	public void loginPassword(String pwd) {
		type("loginPassword_XPATH",pwd);
	}
	
	public void clickEnter() {
		click("fpButton_Enter_XPATH");
	}

	public void goToProfileMenu() {
		click("Menu_XPATH");

	}

	public void goToMyProfile() {
		goToProfileMenu();
		click("profile_XPATH");
	}

	public void goToSettings() {
		goToProfileMenu();
		click("settings_XPATH");
	}

	public void enterFirstName(String name) {
		type("firstName_XPATH", name);
	}

	public void enterLastName(String name) {
		type("lastName_XPATH", name);
	}

	public void saveProfile() {
		click("save_XPATH");

	}

	public void logOut() {

	}

	public void ChangePassword() {
		click("changePassword_XPATH");

	}

	public void enterCurrentPwd(String pwd) {
		type("currentPassword_XPATH", pwd);
	}

	public void enterNewPwd(String pwd) {
		type("newPassword_XPATH", pwd);
	}

	public void enterConfirmPwd(String pwd) {
		type("confirmPassword_XPATH", pwd);
	}

	public void changePwd() {
		click("buttonChangepwd_XPATH");
	}

}
