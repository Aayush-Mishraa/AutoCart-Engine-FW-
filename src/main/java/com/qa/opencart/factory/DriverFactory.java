package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	public static String highlight;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	private static final Logger log = LogManager.getLogger(DriverFactory.class);

	@Step("init the driver using properties : {0}")
	public WebDriver initDriver(Properties prop) {

		String browserName = prop.getProperty("browser");
		// System.out.println("browser name is :" + browserName);
		log.info("browser name is : " + browserName);

		highlight = prop.getProperty("highlight");
		optionsManager = new OptionsManager(prop);

		boolean remoteExecution = Boolean.parseBoolean(prop.getProperty("remote"));

		switch (browserName.trim().toLowerCase()) {
		case "chrome":
			if (remoteExecution) {
				// run tcs on remote/grid:
				initRemoteDriver("chrome");
			} else {
				// local execution:
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			}
			break;
		case "firefox":
			if (remoteExecution) {
				// run tcs on remote/grid:
				initRemoteDriver("firefox");
			} else {
				// local execution:
				tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			}
			break;
		case "edge":
			if (remoteExecution) {
				// run tcs on remote/grid:
				initRemoteDriver("edge");
			} else {
				// local execution:
				tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			}
			break;
		case "safari":
			tlDriver.set(new SafariDriver());
			break;
		default:
			log.error("plz pass the valid browser name.." + browserName);
			throw new FrameworkException("===invalid browser name===");
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		return getDriver();

	}

	/**
	 * init the remote driver with selenium grid
	 * 
	 * @param browserName
	 */
	private void initRemoteDriver(String browserName) {
		System.out.println("Running tests on grid: " + browserName);

		try {
			switch (browserName.toLowerCase().trim()) {
			case "chrome":
				tlDriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getChromeOptions()));
				break;
			case "firefox":
				tlDriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getFirefoxOptions()));
				break;
			case "edge":
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getEdgeOptions()));
				break;

			default:
				System.out.println("browser is not supported on GRID... " + browserName);
				break;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * get driver using threadlocal
	 */
	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	/**
	 * This method is used to init the properties from .properties file
	 * 
	 * @return
	 */

	// supply env name using maven command line
	// mvn clean install -Denv="qa"
	// mvn clean install
	public Properties initProp() {
		String envName = System.getProperty("env");
		// System.out.println("running test suite on env: " + envName);
		log.info("running test suite on env: " + envName);
		FileInputStream ip = null;
		prop = new Properties();

		try {
			if (envName == null) {
				// System.out.println("no env is passed, hence running test suite on qa env..");
				log.warn("no env is passed, hence running test suite on qa env..");
				ip = new FileInputStream(AppConstants.CONFIG_QA_PROP_FILE_PATH);
			} else {
				switch (envName.trim().toLowerCase()) {
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
					// System.out.println("plz pass the right env name..."+ envName);
					log.error("plz pass the right env name..." + envName);
					throw new FrameworkException("===INVALID ENV===");
				}
			}

			prop.load(ip);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}

	/**
	 * takescreenshot
	 */

	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp dir
		String path = System.getProperty("user.dir") + "/screenshot/" + "_" + System.currentTimeMillis() + ".png";
		File destination = new File(path);

		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}

	public static File getScreenshotFile() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp dir
		return srcFile;
	}

	public static byte[] getScreenshotByte() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);// temp dir

	}

	public static String getScreenshotBase64() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);// temp dir

	}

}