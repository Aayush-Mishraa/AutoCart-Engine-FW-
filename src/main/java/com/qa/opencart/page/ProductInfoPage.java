package com.qa.opencart.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductInfoPage {
	private WebDriver driver;

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;

	}
	
	private By  productHeader = By.tagName("h1");

	
	public String getProductHeader() {
		String header = driver.findElement(productHeader).getText();
		System.out.println("Product header ==> "+header);
		return header;
		
	}
}
