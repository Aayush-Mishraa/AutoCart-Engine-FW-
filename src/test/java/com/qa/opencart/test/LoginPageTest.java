package com.qa.opencart.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class LoginPageTest extends BaseTest {
	@Test
	public void loginPageTitleTest() {

		String actTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, "Account Login", "==title is not match==");
	}

	@Test
	public void loginPageUrlTest() {
		String actUrl = loginPage.getLoginPageUrl();
		Assert.assertTrue(actUrl.contains("route=account/login"), "==url is not matched");
	}
	
	@Test
	public void forgetLPwdLinkTest() {
		Assert.assertTrue(loginPage.isForgetPwdLinkExist(), "==Forget Link Is not Present");
	}
	@Test(priority = Integer.MAX_VALUE)
	public void loginTest (){
	homePage = 	loginPage.doLogin("septbatch2024@open.com", "Selenium@12345");
		Assert.assertEquals(homePage.getHomePageTitle(), "My Account", "==Title is not matched==");
	}
	
}
 