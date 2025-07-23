package com.qa.opencart.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.page.HomePage;
import com.qa.opencart.page.LoginPage;
import com.qa.opencart.page.ProductInfoPage;
import com.qa.opencart.page.SearchResultPage;

public class BaseTest {
	WebDriver driver;
	DriverFactory df;
	
	protected LoginPage loginPage;
	protected HomePage homePage;
	protected SearchResultPage searchResultPage;
	protected ProductInfoPage productInfoPage;

	@BeforeClass
	public void setup() {
		df = new DriverFactory();
		driver = df.initDriver("chrome");
	    loginPage = new LoginPage(driver);
	    

	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

}
