package com.qa.opencart.page;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {

	private WebDriver driver;

	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	// private By locator
	private By logoutLink = By.linkText("Logout");
	private By headers = By.cssSelector("div#content >h2");
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("div#search button");

	// public page action

	public String getHomePageTitle() {

		String title = driver.getTitle();
		System.out.println("Home Page title==> " + title);
		return title;

	}

	public String getHomePageUrl() {

		String url = driver.getCurrentUrl();
		System.out.println("Home Page tirltle==> " + url);
		return url;

	}

	public boolean isLogoutLinkExist() {
		return driver.findElement(logoutLink).isDisplayed();
	}
public void logout() {
	if(isLogoutLinkExist()) {
		driver.findElement(logoutLink).click();
	}
}
	public List<String> getHeaderList() {
		List<WebElement> heasersList = driver.findElements(headers);
		List<String> headersValList = new ArrayList<String>();
		for (WebElement e : heasersList) {
			String text = e.getText();
			headersValList.add(text);

		}
		return headersValList;
	}
	
	public SearchResultPage doSearch(String searchKey) {
		System.out.println("search key" + searchKey);
		driver.findElement(search).sendKeys(searchKey);
		driver.findElement(searchIcon).click();
		return new SearchResultPage(driver);
		
	}
}
