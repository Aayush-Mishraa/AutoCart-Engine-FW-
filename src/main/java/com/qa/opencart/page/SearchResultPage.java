package com.qa.opencart.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchResultPage {

	private WebDriver driver;

	public SearchResultPage(WebDriver driver) {
		this.driver = driver;

	}

	private By productResults = By.cssSelector("div.product-thumb");

	public int getProductResultCount() {
		int resultcount = driver.findElements(productResults).size();
		System.out.println("product result count ===>" + resultcount);
		return resultcount;
	}

	public ProductInfoPage selectProduct(String productName ){
		System.out.println("product name:"+productName);
		driver.findElement(By.linkText(productName)).click();
		return new ProductInfoPage(driver);
		
	}

}
