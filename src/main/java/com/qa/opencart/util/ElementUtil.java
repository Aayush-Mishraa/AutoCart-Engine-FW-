package com.qa.opencart.util;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 
 * @author naveenautomationlabs
 *
 */
public class ElementUtil {

	private WebDriver driver;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
	}

	private void nullCheck(CharSequence... value) {
		if (value == null) {
			throw new RuntimeException("===value/prop/attribute can not be null====");
		}
	}

	public void doClick(By locator) {
		getElement(locator).click();
	}

	public void doClick(String locatorType, String locatorValue) {
		getElement(getLocator(locatorType, locatorValue)).click();
	}

	public void doSendKeys(By locator, CharSequence... value) {
		nullCheck(value);
		getElement(locator).sendKeys(value);
	}

	public void doSendKeys(String locatorType, String locatorValue, CharSequence... value) {
		nullCheck(value);
		getElement(getLocator(locatorType, locatorValue)).sendKeys(value);
	}

	public String doElementGetText(By locator) {
		return getElement(locator).getText();
	}

	public String doElementGetText(String locatorType, String locatorValue) {
		return getElement(getLocator(locatorType, locatorValue)).getText();
	}

	public String doGetDomAttribute(By locator, String attrName) {
		nullCheck(attrName);
		return getElement(locator).getDomAttribute(attrName);
	}

	public String doGetDomProperty(By locator, String propName) {
		nullCheck(propName);
		return getElement(locator).getDomProperty(propName);
	}

	public boolean doIsElementDisplayed(By locator) {
		try {
			return getElement(locator).isDisplayed();
		} catch (NoSuchElementException e) {
			System.out.println("element is not displayed");
			return false;
		}
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public boolean isElementDisplayed(By locator) {
		if (getElements(locator).size() == 1) {
			System.out.println("element is available on the page one time");
			return true;
		} else {
			System.out.println("element is not available on the page");
			return false;
		}
	}

	public boolean isElementDisplayed(By locator, int elementCount) {
		if (getElements(locator).size() == elementCount) {
			System.out.println("element is available on the page " + elementCount + " times");
			return true;
		} else {
			System.out.println("element is not available on the page");
			return false;
		}
	}

	public WebElement getElement(By locator) {
		//return waitForelementVisible(locator, 10);//default time = 10 secs
		return driver.findElement(locator);
	}

	public By getLocator(String locatorType, String locatorValue) {

		By locator = null;

		switch (locatorType.toUpperCase().trim()) {
		case "ID":
			locator = By.id(locatorValue);
			break;
		case "NAME":
			locator = By.name(locatorValue);
			break;
		case "CLASSNAME":
			locator = By.className(locatorValue);
			break;
		case "XPATH":
			locator = By.xpath(locatorValue);
			break;
		case "CSS":
			locator = By.cssSelector(locatorValue);
			break;
		case "LINKTEXT":
			locator = By.linkText(locatorValue);
			break;
		case "PARTIALLINKTEXT":
			locator = By.partialLinkText(locatorValue);
			break;
		case "TAGNAME":
			locator = By.tagName(locatorValue);
			break;

		default:
			System.out.println("invalid locator, please use the right locator...");
			break;
		}

		return locator;

	}

	
	public List<String> fetchElementsText(By locator) {
		List<String> eleTextList = new ArrayList<String>();
		getElements(locator)
				.stream()
					.filter(e -> !e.getText().isEmpty())
						.forEach(e -> eleTextList.add(e.getText()));
		return eleTextList;
	}
	
	public void printElementText(By locator) {
		getElements(locator)
				.stream()
					.filter(e -> !e.getText().isEmpty())
						.forEach(e -> System.out.println(e.getText()));
	}
	
	
	
	
	
	// *****************Select tag drop down utils***************//

	public void doSelectDropDownByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	public void doSelectDropDownByVisibleText(By locator, String visibleText) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(visibleText);
	}

	public void doSelectDropDownByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}

	public void doSelectDropDownByContainsText(By locator, String partialText) {
		Select select = new Select(getElement(locator));
		select.selectByContainsVisibleText(partialText);
	}

	public void printDropDownOptionsText(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		for (WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);
		}
	}

	public List<String> getDropDownOptionsTextList(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		System.out.println("options size: " + optionsList.size());

		List<String> optionsValueList = new ArrayList<String>();// []
		for (WebElement e : optionsList) {
			String text = e.getText();
			optionsValueList.add(text);
		}
		return optionsValueList;
	}

	public int getDropDownOptionsCount(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> countryOptionsList = select.getOptions();
		System.out.println("options size: " + countryOptionsList.size());
		return countryOptionsList.size();
	}

	public void selectValueFromDropDown(By locator, String value) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		boolean flag = false;

		for (WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);
			if (text.contains(value)) {
				e.click();
				flag = true;
				break;
			}
		}

		if (flag) {
			System.out.println(value + " is available and selected");
		} else {
			System.out.println(value + " is not available");
		}
	}

	/**
	 * 
	 * @param searchField
	 * @param suggestions
	 * @param searchKey
	 * @param actualValue
	 * @throws InterruptedException
	 */
	public void doSearch(By searchField, By suggestions, String searchKey, String actualValue)
			throws InterruptedException {

		doSendKeys(searchField, searchKey);
		Thread.sleep(2000);

		List<WebElement> suggList = getElements(suggestions);
		System.out.println(suggList.size());
		boolean flag = false;

		for (WebElement e : suggList) {
			String text = e.getText();
			System.out.println(text);
			if (text.contains(actualValue)) {
				e.click();
				flag = true;
				break;
			}
		}
		if (flag) {
			System.out.println(actualValue + " is available and clicked");
		} else {
			System.out.println(actualValue + " is not available");
		}

	}

	/**
	 * this method is handling single/multiple and all choices selection. Please
	 * pass "all" to select all the choices. selectChoice(choiceDropDown, choices,
	 * "All");
	 * 
	 * @param choiceDropDown
	 * @param choices
	 * @param choiceValue
	 * @throws InterruptedException
	 */
	public void selectChoice(By choiceDropDown, By choices, String... choiceValue) throws InterruptedException {
		doClick(choiceDropDown);
		Thread.sleep(3000);
		List<WebElement> choicesList = getElements(choices);

		System.out.println(choicesList.size());

		if (choiceValue[0].equalsIgnoreCase("all")) {
			// select all the choice one by one:
			for (WebElement e : choicesList) {
				e.click();
			}
		}

		else {
			for (WebElement e : choicesList) {
				String text = e.getText();
				System.out.println(text);

				for (String ch : choiceValue) {
					if (text.equals(ch)) {
						e.click();
					}
				}

			}
		}
	}

	// **************************Actions utils***************//

	public void doActionsSendKeys(By locator, CharSequence... value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).build().perform();
	}

	public void doActionsClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).build().perform();
	}

	public void handleTwoLevelMenuSubMenuHandling(By parentMenuLocator, By childMenuLocator) throws InterruptedException {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(parentMenuLocator)).build().perform();
		Thread.sleep(2000);
		getElement(childMenuLocator).click();
	}

	public void handleFourLebelMenuSubMenuHandling(By level1Menu, By level2Menu, By level3Menu, By level4Menu)
			throws InterruptedException {
		Actions act = new Actions(driver);
		driver.findElement(level1Menu).click();
		Thread.sleep(2000);
		act.moveToElement(driver.findElement(level2Menu)).build().perform();
		Thread.sleep(2000);
		act.moveToElement(driver.findElement(level3Menu)).build().perform();
		Thread.sleep(2000);
		driver.findElement(level4Menu).click();

	}
	
	
	public void doSendKeysWithPause(By locator, String value, long pauseTime) {		
		Actions act = new Actions(driver);
		char val[] = value.toCharArray();		
		for(char ch : val) {
			act.sendKeys(getElement(locator), String.valueOf(ch))
					.pause(pauseTime).perform();
		}		
	}
	

	
	//**********************Wait for elements************//
	
	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement waitForElementPresence(By locator, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	public WebElement waitForElementPresence(By locator, long timeOut, long pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(pollingTime));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	
	/**
	 * An expectation for checking that an element is present on the DOM of a page and visible. 
	 * Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement waitForElementVisible(By locator, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	
	public WebElement waitForElementVisible(By locator, long timeOut, long pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(pollingTime));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	
	/**
	 * An expectation for checking an element is visible and enabled such that you can click it.
	 * @param locator
	 * @param timeOut
	 */
	public void clickElementWhenReady(By locator, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}
	
	
	/**
	 * An expectation for checking that there is at least one element present on a web page.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForElementsPresence(By locator, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}
	
	
	/**
	 * An expectation for checking that all elements present on the web page that match the locator are visible. 
	 * Visibility means that the elements are not only displayed but also have a height and width that is greater than 0.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForElementsVisible(By locator, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
	
	
	public WebElement waitForElementVisibleUsingFluentFeatures(By locator, long timeOut, long pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime))
				.ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class)
				.withMessage("====element is not found====");
		
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

	}
	
	
	
	//***********wait for Title and URL*********//

	/**
	 * An expectation for checking that the title contains a case-sensitive substring.
	 * @param fractionTitle
	 * @param timeOut
	 * @return
	 */
	public String waitForTitleContains(String fractionTitle, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {
			if (wait.until(ExpectedConditions.titleContains(fractionTitle))) {
				return driver.getTitle();
			}
		} catch (TimeoutException e) {
			System.out.println("title is not found after " + timeOut + " seconds");
		}

		return null;

	}

	
	/**
	 * An expectation for checking the title of a page.
	 * @param title
	 * @param timeOut
	 * @return
	 */
	public String waitForTitleIs(String title, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {
			if (wait.until(ExpectedConditions.titleIs(title))) {
				return driver.getTitle();
			}
		} catch (TimeoutException e) {
			System.out.println("title is not found after " + timeOut + " seconds");
		}

		return null;

	}
	
	
	public String waitForURLContains(String fractionURL, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {
			if (wait.until(ExpectedConditions.urlContains(fractionURL))) {
				return driver.getCurrentUrl();
			}
		} catch (TimeoutException e) {
			System.out.println("URL is not found after " + timeOut + " seconds");
		}

		return null;
	}
	
	
	public String waitForURLToBe(String url, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {
			if (wait.until(ExpectedConditions.urlToBe(url))) {
				return driver.getCurrentUrl();
			}
		} catch (TimeoutException e) {
			System.out.println("URL is not found after " + timeOut + " seconds");
		}

		return null;
	}
	
	//***********wait for alert*********//

	
	public Alert waitForAlertUsingFluentFeatures(long timeOut, long pollingTime) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime))
				.ignoring(NoAlertPresentException.class)
				.withMessage("====alert is not found====");
									
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
	
	public Alert waitForAlert(long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public String getAlertText(long timeOut) {
		return waitForAlert(timeOut).getText();
	}

	public void acceptAlert(long timeOut) {
		waitForAlert(timeOut).accept();
	}

	public void dismissAlert(long timeOut) {
		waitForAlert(timeOut).dismiss();
	}

	public void alertSendKeys(String text, long timeOut) {
		waitForAlert(timeOut).sendKeys(text);
	}
	
	//***********wait for frame*********//
	
	
	public void waitForFrameByLocatorAndSwitchToItUsingFluentFeatures(By frameLocator, long timeOut, long pollingTime) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime))
				.ignoring(NoSuchFrameException.class)
				.withMessage("====Frame is not found====");
		
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));		
	}
	
	
	public void waitForFrameByLocatorAndSwitchToIt(By frameLocator, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));		
	}
	
	public void waitForFrameByIndexAndSwitchToIt(int frameIndex, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));		
	}
	
	public void waitForFrameByLocatorAndSwitchToIt(String frameIDOrName, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIDOrName));		
	}
	
	public void waitForFrameByLocatorAndSwitchToIt(WebElement frameElement, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));		
	}
	
	//***********wait for window*******//
	public boolean waitForWindow(int numberOfWindows, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			return wait.until(ExpectedConditions.numberOfWindowsToBe(numberOfWindows));
		} catch (TimeoutException e) {
			System.out.println("number of windows are not matched...");
			return false;
		}
	}
	
	
	public boolean isPageLoaded(long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		String isPageLoaded = 
					wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete'")).toString();
		return Boolean.parseBoolean(isPageLoaded);
	}
	
	
}
