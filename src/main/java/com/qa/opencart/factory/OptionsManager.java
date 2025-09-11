package com.qa.opencart.factory;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {
	private Properties prop;

	private ChromeOptions co;
	private FirefoxOptions fo;
	private EdgeOptions eo;
	
    private static final Logger log = LogManager.getLogger(OptionsManager.class);


	public OptionsManager(Properties prop) {
		this.prop = prop;
	}

	public ChromeOptions getChromeOptions() {
		co = new ChromeOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless"))) {
			//System.out.println("==Running in HeadLess Mode===");
			log.info("==Running in HeadLess Mode===");
			co.addArguments("--headless");
		}
		if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
			//System.out.println("==Running in Incognito Mode===");
			log.info("==Running in Incognito Mode===");
			co.addArguments("--incognito");
		}
		if(Boolean.parseBoolean(prop.getProperty("remote"))) {
			co.setCapability("browserName", "chrome");
		}
		return co;
	}

	public FirefoxOptions getFirefoxOptions() {
		fo = new FirefoxOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless"))) {
			System.out.println("==Running in HeadLess Mode===");
			fo.addArguments("--headless");
		}
		if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
			System.out.println("==Running in Incognito Mode===");
			fo.addArguments("--incognito");
		}
		if(Boolean.parseBoolean(prop.getProperty("remote"))) {
			fo.setCapability("browserName", "firefox");
		}
		return fo;
	}

	public EdgeOptions getEdgeOptions() {
		eo = new EdgeOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless"))) {
			System.out.println("==Running in HeadLess Mode===");
			eo.addArguments("--headless");
		}
		if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
			System.out.println("==Running in Incognito Mode===");
			eo.addArguments("--inprivate");
		}
		if(Boolean.parseBoolean(prop.getProperty("remote"))) {
			eo.setCapability("browserName", "edge");
		}
		return eo;
	}

}