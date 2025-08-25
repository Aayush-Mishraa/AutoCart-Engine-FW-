package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.aventstack.chaintest.service.ChainPluginService;
//import org.testng.annotations.Listeners;
//import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.page.CommonsPage;
import com.qa.opencart.page.HomePage;
import com.qa.opencart.page.LoginPage;
import com.qa.opencart.page.ProductInfoPage;
import com.qa.opencart.page.SearchResultPage;

import jdk.jfr.Description;

//@Listeners(ChainTestListener.class) -> commenting this as it is creating issue with testng.xml file

@Description("This is the base test class which will be extended by all the test classes")
public class BaseTest {
	WebDriver driver;
	DriverFactory df;
	protected Properties prop;
	protected LoginPage loginPage;
	protected HomePage homePage;
	protected SearchResultPage searchResultPage;
	protected ProductInfoPage productInfoPage;
	protected CommonsPage commonsPage;
	
	
	
	@Description("Setup: initialize the driver and launch the browser")
	@Parameters("browser")
	@BeforeClass
	public void setup(String browserName) {

		df = new DriverFactory();
		prop = df.initProp();
		
		if(browserName!=null) {
			System.out.println("Browser name is: "+ browserName);
			prop.setProperty("browser", browserName);
		}

//		ChainPluginService.getInstance().addSystemInfo("Build#", "v1.0.0");
//		ChainPluginService.getInstance().addSystemInfo("Headless", prop.getProperty("headless"));
//		ChainPluginService.getInstance().addSystemInfo("Incognito", prop.getProperty("incognito"));
//		ChainPluginService.getInstance().addSystemInfo("Owner", "Aayush Mishra");

		driver = df.initDriver(prop);
		loginPage = new LoginPage(driver);
		commonsPage = new CommonsPage(driver);

	}
	@Description("Take screenshot on test failure")
	@AfterMethod
	public void attachScreenshot(ITestResult result) {
		if (!result.isSuccess()) {// only for failed test cases
			// take screenshot and attach to report
			ChainTestListener.embed(DriverFactory.getScreenshotFile(), "image/png");
		}
	}

	@Description("Teardown: close the browser")
	@AfterClass
	public void tearDown() {
		driver.quit();
	}

}