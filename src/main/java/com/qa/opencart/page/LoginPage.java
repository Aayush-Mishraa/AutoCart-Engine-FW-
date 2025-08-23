package com.qa.opencart.page;

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.util.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	private WebDriver driver;
	private ElementUtil elementUtil;
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}

	
	//
	private By email = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgetPwdLink = By.linkText("Forgotten Password");
	
	@Step("Getting login page title...")
	public String getLoginPageTitle() {
		String  title = elementUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, AppConstants.DEFAULT_TIMEOUT);
//		String  title =driver.getTitle();
		System.out.println("Login Page title==> "+title );
		ChainTestListener.log("Login Page title==> "+title );
		return title;
		
	}
	@Step("	Getting login page url...")	
	public String getLoginPageUrl() {
		
		String  url = elementUtil.waitForURLContains(AppConstants.LOGIN_PAGE_URL_FRACTION, AppConstants.DEFAULT_TIMEOUT);
		System.out.println("Login Page tirltle==> "+url );
		return url;
		
	}
	@Step("Checking forget pwd link exist...")	
	public boolean isForgetPwdLinkExist() {
		return elementUtil.doIsElementDisplayed(forgetPwdLink);
		
	}
	@Step("Login with username: {0} and password: {1}")
	public @Nullable HomePage  doLogin(String username, String pwd) {
		System.out.println("App card are==>" + username+ " : " + pwd);
		elementUtil.waitForElementVisible(email, AppConstants.DEFAULT_TIMEOUT).sendKeys(username);
		elementUtil.waitForElementVisible(password, AppConstants.DEFAULT_TIMEOUT).sendKeys(pwd);
		elementUtil.doClick(loginBtn);
		
//		driver.findElement(email).sendKeys(username);
//		driver.findElement(password).sendKeys(pwd);
//		driver.findElement(loginBtn).click();
		return new HomePage(driver);
		// when ever landing on the other page make the object of that page
		
	}
}
