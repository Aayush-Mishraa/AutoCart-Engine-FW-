package com.qa.opencart.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.constants.AppError;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic - 101 : Design Login Page for Open Cart Application....")
@Story("US - 201 : Login Page Features with title, url, forget pwd link, login test and logo...")
@Feature("Feature - 201 : Login Page Test....")
public class LoginPageTest extends BaseTest {

	@Description("Login Page Title Test...")
	@Severity(SeverityLevel.MINOR)
	@Owner("Aayush Mishra")
	@Test
	public void loginPageTitleTest() {
		ChainTestListener.log("Verifying login page title...");

		String actTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE, AppError.TITLE_NOT_MATCHED_ERROR);
	}
	@Description("Login Page URL Test...")
	@Severity(SeverityLevel.MINOR)
	@Test(enabled = true)
	public void loginPageUrlTest() {
		String actUrl = loginPage.getLoginPageUrl();
		Assert.assertTrue(actUrl.contains(AppConstants.LOGIN_PAGE_URL_FRACTION), AppError.URL_NOT_MATCHED_ERROR);
	}
	@Description("	Forget Password Link Test...")	
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = true)
	public void forgetLPwdLinkTest() {
		Assert.assertTrue(loginPage.isForgetPwdLinkExist(), AppError.FORGET_LINK_NOT_PRESENT_ERROR);
	}
	@Description("Login Test with correct username and password...")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = Integer.MAX_VALUE, enabled = true)
	public void loginTest() {
		homePage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertEquals(homePage.getHomePageTitle(), AppConstants.HOME_PAGE_TITLE,
				AppError.TITLE_NOT_MATCHED_ERROR);
	}
	@Description("	Logo Test...")	
	@Severity(SeverityLevel.MINOR)
	@Test(enabled = true)
	public void logoTest() {
		Assert.assertTrue(commonsPage.isLogoExist(), AppError.LOGO_NOT_FOUND_ERROR);
	}

	@DataProvider
	public Object[][] getFooterData() {
		return new Object[][] { { "About Us" }, { "Brands" }, { "Gift Certificates" }, { "Contact Us" }, { "Site Map" },
				{ "Specials" }, { "Affiliate" } };
	}
	@Description("Footer Test with multiple links...")
	@Severity(SeverityLevel.NORMAL)
	@Test(dataProvider = "getFooterData", enabled = true)
	public void footerTest(String footerLink) {
		Assert.assertTrue(commonsPage.checkFooterLink(footerLink));
	}
}
