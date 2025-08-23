package com.qa.opencart.test;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.util.ExcelUtil;

public class ProductInfoPageTest extends BaseTest {
	@BeforeClass
	public void homePageSetup() {
		homePage = loginPage.doLogin("septbatch2024@open.com", "Selenium@12345");
	}

	@DataProvider
	public Object[][] getProductData() {
		return new Object[][] { 
			{ "Macbook", "MacBook Pro" },
			{ "iMac", "iMac" },
			{ "Samsung", "Samsung SyncMaster 941BW" },
			{ "Samsung", "Samsung Galaxy Tab 10.1" } };

	}

	@Test(dataProvider = "getProductData")
	public void productHeaderTest(String searchKey, String ProductName) {
		ChainTestListener.log(searchKey + " : " + ProductName);
		searchResultPage = homePage.doSearch(searchKey);
		productInfoPage = searchResultPage.selectProduct(ProductName);
		String actutalProductheader = productInfoPage.getProductHeader();
		System.out.println("product header is ===>" + actutalProductheader);
		Assert.assertEquals(actutalProductheader, ProductName, "Product header does not match!");

	}

	@DataProvider
	public Object[][] getProductImageData() {
		return new Object[][] { 
			{ "Macbook", "MacBook Pro", 4 }, 
			{ "iMac", "iMac", 3 },
			{ "Samsung", "Samsung SyncMaster 941BW", 1 }, 
			{ "Samsung", "Samsung Galaxy Tab 10.1", 7 } };

	}

	@DataProvider
	public Object[][] getProductImageSheetData() {
		Object productdata[][] = ExcelUtil.getTestData(AppConstants.PRODUCT_SHEET_NAME);
		return productdata;

	}

	@Test(dataProvider = "getProductImageSheetData")
	public void productHeaderImageTest(String searchKey, String productName, String expectedImageCount) {
		searchResultPage = homePage.doSearch(searchKey);
		productInfoPage = searchResultPage.selectProduct(productName);
		int actualImageCount = productInfoPage.getProductImagesCount();
		assert actualImageCount > 0 : "Product images count is zero!";
		Assert.assertEquals(actualImageCount, Integer.parseInt(expectedImageCount), "Product image count does not match!");
	}

	@Test
	public void productInfoTest() {
		searchResultPage = homePage.doSearch("Macbook");
		productInfoPage = searchResultPage.selectProduct("MacBook Pro");

		Map<String, String> productInfoMap = productInfoPage.getProductInfo();
		productInfoMap.forEach((k, v) -> System.out.println(k + " : " + v));

		SoftAssert softAssert = new SoftAssert();

		softAssert.assertEquals(productInfoMap.get("header"), "MacBook Pro");
		softAssert.assertEquals(productInfoMap.get("Brand"), "Apple");

		softAssert.assertEquals(productInfoMap.get("Availability"), "Out Of Stock");
		softAssert.assertEquals(productInfoMap.get("Product Code"), "Product 18");

		softAssert.assertEquals(productInfoMap.get("Reward Points"), "800");
		softAssert.assertEquals(productInfoMap.get("price"), "$2,000.00");

		softAssert.assertEquals(productInfoMap.get("extax"), "$2,000.00");
		softAssert.assertAll();
	}

}
