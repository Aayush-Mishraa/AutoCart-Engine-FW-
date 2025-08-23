package com.qa.opencart.constants;

public class AppConstants {
	//********** Timeouts Constants **********//
	
	public final static int DEFAULT_TIMEOUT = 5;
	public final static int SHORT_TIMEOUT = 10;
	public final static int MEDIUM_TIMEOUT = 15;
	public final static int MAX_TIMEOUT = 20;
	
	//********** Login Page Constants **********//
	public static final String LOGIN_PAGE_TITLE = "Account Login";
	public static final String LOGIN_PAGE_URL_FRACTION = "route=account/login";
	
	public static final String HOME_PAGE_TITLE = "My Account";
	public static final String HOME_PAGE_URL_FRACTIONL = "route=account/account";
	
	
	//********** Config File Paths **********//
	public static final String CONFIG_PROD_PROP_FILE_PATH = "./src/test/resources/config/config.properties";
	public static final String CONFIG_QA_PROP_FILE_PATH = "./src/test/resources/config/qa.config.properties";
	public static final String CONFIG_DEV_PROP_FILE_PATH = "./src/test/resources/config/dev.config.properties";
	public static final String CONFIG_STAGE_PROP_FILE_PATH = "./src/test/resources/config/stage.config.properties";
	public static final String CONFIG_UAT_PROP_FILE_PATH = "./src/test/resources/config/uat.config.properties";
	
	//********** Excel Sheet Constants **********//
	public static final String PRODUCT_SHEET_NAME = "product";
	
	
}
