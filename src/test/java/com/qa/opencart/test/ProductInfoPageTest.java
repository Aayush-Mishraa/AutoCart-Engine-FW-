package com.qa.opencart.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class ProductInfoPageTest extends BaseTest {
	@BeforeClass
	public void homePageSetup() {
		homePage = loginPage.doLogin("septbatch2024@open.com", "Selenium@12345");
	}
	
	@Test
	public void productHeaderTest() {
		searchResultPage = homePage.doSearch("Macbook");
		int resultCount = searchResultPage.getProductResultCount();
		System.out.println("product result count ===>" + resultCount);
		if (resultCount > 0) {
			productInfoPage = searchResultPage.selectProduct("MacBook Pro");
			String actutalProductheader = productInfoPage.getProductHeader();
			System.out.println("product header is ===>" + actutalProductheader);
			Assert.assertEquals(actutalProductheader, "MacBook Pro", "Product header does not match!");
		} else {
			System.out.println("No product found for the given search term.");
		}
	}

}
