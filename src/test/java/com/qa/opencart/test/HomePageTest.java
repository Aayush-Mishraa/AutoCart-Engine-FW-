package com.qa.opencart.test;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class HomePageTest extends BaseTest {

	@BeforeClass
	public void homePageSetup() {
		homePage = loginPage.doLogin("septbatch2024@open.com", "Selenium@12345");
	}

	@Test
	public void homePageTitileTest() {
		String title = homePage.getHomePageTitle();
		Assert.assertEquals(homePage.getHomePageTitle(), "My Account", "==Title is not match==");
	}
	@Test
	public void homePageUrlTest() {
		Assert.assertTrue(homePage.getHomePageUrl().contains("route=account/login"), "==url is not matched");
	}

	@Test
	public void logoutLinkExitsTest() {
		Assert.assertTrue(homePage.isLogoutLinkExist(), "===Logout link is not  present===");

	}
	@Test
	public void headerTest() {
		List<String> actualHeader = homePage.getHeaderList();
		System.out.println("Home page Headers: ==> "+ actualHeader);
		
	}
	@Test
	public void searchTest() {
		searchResultPage= homePage.doSearch("macbook");
		Assert.assertEquals(searchResultPage.getProductResultCount(), 3);
	}
	
}
