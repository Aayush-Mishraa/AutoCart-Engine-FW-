package com.qa.opencart.page;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.util.ElementUtil;

public class CommonsPage {
	
	private WebDriver driver;
	private ElementUtil elementUtil;

	// constructor
	public CommonsPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}
	
	private By logo = By.cssSelector(".img-responsive");
	private By footer = By.xpath("//footer//a");
	
	public boolean isLogoExist() {
		return elementUtil.doIsElementDisplayed(logo);
	}
	
  public List<String> getFooterList() {
	  List<WebElement> footerList = elementUtil.waitForElementsPresence(footer, AppConstants.DEFAULT_TIMEOUT);
	  System.out.println("Total number of fotter link"+ footerList.size());
	  List<String> footerTextList = new ArrayList<String>();
	  for(WebElement e : footerList) {
		 String text = e.getText();
		 footerTextList.add(text);
	  }
	  return footerTextList;
	  
  }
  
  public boolean checkFooterLink(String footerLink) {
	  return getFooterList().contains(footerLink);
  }

}
