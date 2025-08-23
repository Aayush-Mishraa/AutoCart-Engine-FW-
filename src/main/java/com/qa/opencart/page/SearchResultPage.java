package com.qa.opencart.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.util.ElementUtil;

public class SearchResultPage {

	private WebDriver driver;
	private ElementUtil elementUtil;
	public SearchResultPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}

	private By productResults = By.cssSelector("div.product-thumb");

	public int getProductResultCount() {
		int resultcount = elementUtil.waitForElementsVisible(productResults, 10).size();
		System.out.println("product result count ===>" + resultcount);
		return resultcount;
	}

	public ProductInfoPage selectProduct(String productName ){
		System.out.println("product name:"+productName);
		elementUtil.doClick(By.linkText(productName));
		return new ProductInfoPage(driver);
		
	}

}
