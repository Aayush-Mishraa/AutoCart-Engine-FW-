package com.qa.opencart.test;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.constants.AppError;

public class HomePageTest extends BaseTest {

	@BeforeClass
	public void homePageSetup() {
		homePage = 	loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@Test
	public void homePageTitileTest() {
		Assert.assertEquals(homePage.getHomePageTitle(), AppConstants.HOME_PAGE_TITLE, AppError.TITLE_NOT_MATCHED_ERROR);
	}
	@Test
	public void homePageUrlTest() {
		Assert.assertTrue(homePage.getHomePageUrl().contains(AppConstants.HOME_PAGE_URL_FRACTIONL), AppError.URL_NOT_MATCHED_ERROR);
	}

	@Test
	public void logoutLinkExitsTest() {
		Assert.assertTrue(homePage.isLogoutLinkExist(), AppError.ELEMENT_NOT_FOUND_ERROR);

	}
	@Test
	public void headerTest() {
		List<String> actualHeader = homePage.getHeaderList();
		System.out.println("Home page Headers: ==> "+ actualHeader);
		
	}
	@DataProvider
	public Object[][] getSearchData() {
		return new Object[][] {
			{"macbook", 3},
			{"imac", 1},
			{"samsung", 2},
			{"canon", 1},
			{"nokia", 0},
			{"apple", 1}
		};
		
	}
	@Test(dataProvider = "getSearchData")
	public void searchTest(String searchKey, int resultCount) {
		searchResultPage= homePage.doSearch(searchKey);
		Assert.assertEquals(searchResultPage.getProductResultCount(),resultCount);
	}
	
}
