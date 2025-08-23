package com.qa.opencart.factory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;


import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.exception.FrameworkException;

import io.qameta.allure.Step;

public class DriverFactory {

	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	public static String highlight;;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	// WHEN WE WANT TO USE THREAD LOCAL WEBDRIVER
	// MEANS THAT WHEN WE WANT TO RUN TESTS IN PARALLEL
	// WE USE THREAD LOCAL WEBDRIVER
	// WE HAVE THE COPY OF THE DRIVER FOR EACH THREAD
@Step("Initializing the driver using properties: {0}")
	public WebDriver initDriver(Properties prop) {
		String browserName = prop.getProperty("browser");

		highlight = prop.getProperty("highlight");
		optionsManager = new OptionsManager(prop);

		switch (browserName.trim().toLowerCase()) {

		case "chrome": {
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
//			driver = new ChromeDriver(optionsManager.getChromeOptions());
			break;
		}
		case "firefox": {
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));//
//			driver = new FirefoxDriver(optionsManager.getFirefoxOptions());	
			break;
		}
		case "edge": {
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
//			driver = new EdgeDriver(optionsManager.getEdgeOptions());//
			break;
		}
		case "safari": {
			tlDriver.set(new SafariDriver());
//			driver = new SafariDriver()
			break;
		}
		default:
			System.out.println("please pass the valid browser " + browserName);
			throw new FrameworkException("===invalid browser name===");

		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		return getDriver();
	}

	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	// * mvn clean install -Denv="qa"
	// * mvn clean install -Denv="dev"
	// * mvn clean install -Denv="stage"
	// * mvn clean install -Denv="uat"
	// * mvn clean install -Denv="prod"

	/**
	 * This method is used to initialize the properties from config file
	 * 
	 * @return prop
	 */

	public Properties initProp() {

		String envName = System.getProperty("env");
		System.out.println("Running tests suite on environment: " + envName);
		FileInputStream ip = null;
		prop = new Properties();
		try {
			if (envName == null) {
				System.out.println("No environment is specified, running tests on QA environment...");
				ip = new FileInputStream(AppConstants.CONFIG_QA_PROP_FILE_PATH);
			} else {
				switch (envName.toLowerCase()) {
				case "qa":
					ip = new FileInputStream(AppConstants.CONFIG_QA_PROP_FILE_PATH);
					break;
				case "dev":
					ip = new FileInputStream(AppConstants.CONFIG_DEV_PROP_FILE_PATH);
					break;
				case "stage":
					ip = new FileInputStream(AppConstants.CONFIG_STAGE_PROP_FILE_PATH);
					break;
				case "uat":
					ip = new FileInputStream(AppConstants.CONFIG_UAT_PROP_FILE_PATH);
					break;
				case "prod":
					ip = new FileInputStream(AppConstants.CONFIG_PROD_PROP_FILE_PATH);
					break;
				default:
					System.out.println("Please pass a valid environment name: qa/dev/stage/uat");
					throw new FrameworkException("===invalid environment name===");
				}

			}
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
//			throw new FrameworkException("File not found exception: " + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop;

	}
	
		public static String getScreenshot() {
		    File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE); // temp dir
		    String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		    File destination = new File(path);
		    try {
		        FileHandler.copy(srcFile, destination);
		    } catch (IOException e) {
		        e.printStackTrace();
		    }

		    return path;
		}
		
		public static File getScreenshotFile() {
			 File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
			 return srcFile;
		}
	
	

}
