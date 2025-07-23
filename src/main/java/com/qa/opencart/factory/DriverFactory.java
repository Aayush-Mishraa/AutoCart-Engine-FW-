package com.qa.opencart.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.qa.opencart.exception.FrameworkException;

public class DriverFactory {

	WebDriver driver;

	public WebDriver initDriver(String browserName) {
		switch (browserName.trim().toLowerCase()) {
		case "chrome": {

			driver = new ChromeDriver();
			break;
		}
		case "firefox": {

			driver = new ChromeDriver();
			break;
		}
		case "edge": {

			driver = new ChromeDriver();
			break;
		}
		case "safari": {

			driver = new ChromeDriver();
			break;
		}
		default:
			System.out.println("please pass the valid browser " + browserName);
			throw new FrameworkException("===invalid browser name===");

		}
		
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get("https://naveenautomationlabs.com/opencart/index.php?route=account/login");
		return driver;
	}

		
	

}
