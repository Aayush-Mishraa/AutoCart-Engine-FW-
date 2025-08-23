package com.qa.opencart.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.util.ElementUtil;

public class ProductInfoPage {
	private WebDriver driver;
	private ElementUtil elementUtil;
	private Map<String, String> productMap;

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}

	private By productImages = By.cssSelector("ul.thumbnails img");
	private By productHeader = By.tagName("h1");
	private By productMetaData = By.xpath("(//div[@id=\"content\"]//ul[@class=\"list-unstyled\"])[1]/li");
	private By productPriceData = By.xpath("(//div[@id=\"content\"]//ul[@class=\"list-unstyled\"])[2]/li");

	public String getProductHeader() {

		String header = elementUtil.doElementGetText(productHeader);
		System.out.println("Product header ==> " + header);
		return header;
	}

	public int getProductImagesCount() {
		int imageCount = elementUtil.waitForElementsPresence(productImages, AppConstants.SHORT_TIMEOUT).size();
		System.out.println(getProductHeader() + " product image count is: " + imageCount);
		return imageCount;

	}

	public Map<String, String> getProductInfo() {
		productMap = new HashMap<String, String>();
		productMap.put("header", getProductHeader());
		productMap.put("imageCount", getProductImagesCount() + "");
		getProductMetaData();
		getProductPriceData();
		return productMap;
	}

//	Brand: Apple
//	Product Code: Product 17
//	Reward Points: 700
//	Availability: Out Of Stock

	public void getProductMetaData() {
		List<WebElement> metaList = elementUtil.waitForElementsPresence(productMetaData, AppConstants.SHORT_TIMEOUT);
		for (WebElement e : metaList) {
			String metaText = e.getText();
			String meta[] = metaText.split(":");
			String metaKey = meta[0].trim();
			String metaValue = meta[1].trim();
			productMap.put(metaKey, metaValue);

		}
	}

	// $1,202.00
	// Ex Tax: $1,000.00

	public void getProductPriceData() {
		List<WebElement> priceList = elementUtil.waitForElementsPresence(productPriceData, getProductImagesCount());
		String priceText = priceList.get(1).getText();
		String price[] = priceText.split(":");
		String priceValue = price[1].trim();
		productMap.put("price", priceValue);
		productMap.put("extax", priceValue);

	}

}
