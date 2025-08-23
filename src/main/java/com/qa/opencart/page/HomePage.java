package com.qa.opencart.page;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.util.ElementUtil;

public class HomePage {

	private WebDriver driver;
	private ElementUtil elementUtil;

	// constructor
	public HomePage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}

	// private By locator
	private By logoutLink = By.linkText("Logout");
	private By headers = By.cssSelector("div#content >h2");
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("div#search button");

	// public page action

	public String getHomePageTitle() {

		String title = elementUtil.waitForTitleIs(AppConstants.HOME_PAGE_TITLE, AppConstants.DEFAULT_TIMEOUT);
//		String title = driver.getTitle();
		System.out.println("Home Page title==> " + title);
		return title;

	}

	public String getHomePageUrl() {
		String url = elementUtil.waitForURLContains(AppConstants.HOME_PAGE_URL_FRACTIONL, AppConstants.DEFAULT_TIMEOUT);
//		String url = driver.getCurrentUrl();
		System.out.println("Home Page tirltle==> " + url);
		return url;

	}

	public boolean isLogoutLinkExist() {
		return elementUtil.doIsElementDisplayed(logoutLink);
	}

	public void logout() {
		if (isLogoutLinkExist()) {
			elementUtil.doClick(logoutLink);
		}
		// pending
	}

	public List<String> getHeaderList() {
		List<WebElement> heasersList = elementUtil.waitForElementsVisible(headers, AppConstants.SHORT_TIMEOUT);
		List<String> headersValList = new ArrayList<String>();
		for (WebElement e : heasersList) {
			String text = e.getText();
			headersValList.add(text);

		}
		return headersValList;
	}

	public SearchResultPage doSearch(String searchKey) {
		System.out.println("search key" + searchKey);
		WebElement searchEle = elementUtil.waitForElementVisible(search, AppConstants.DEFAULT_TIMEOUT);
		elementUtil.doSendKeys(searchEle, searchKey);
		elementUtil.doClick(searchIcon);
		return new SearchResultPage(driver);

	}
}
