package in.akbar.pageobjects;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import in.akbar.abstractmethods.AbstractMethod;

public class PaymentPage extends AbstractMethod {
	
	WebDriver driver;
	
	public PaymentPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	By tagEle = By.tagName("input");
	
	@FindBy(xpath = "//input[@placeholder='Select Country']")
	WebElement country;
	
	@FindBy(xpath = "//input[@class='input txt' and @type='text']")
	WebElement cvv;
	
	@FindBy(xpath="(//div[@class='field']/input)[2]")
	WebElement nameOnCard;
	
	@FindBy(css = ".ta-results .ta-item")
	List<WebElement> listOfCountries;
	
	@FindBy(css=".action__submit")
	WebElement submit;
	
	// FILL THE PAYMENT DETAILS
	public void selectTheCountry(String countryName, String cvvNumber, String name) {
		cvv.sendKeys(cvvNumber);
		nameOnCard.sendKeys(name);
		Actions action = new Actions(driver);
		action.sendKeys(country, countryName).build().perform();
		for(WebElement country : listOfCountries) {
			String countryNameText = country.getText();
			if(countryNameText.equalsIgnoreCase(countryName)) {
				country.click();
			}
		}
	}
	
	// CLICK ON PLACE ORDER BUTTON
	public ConfirmationPage submitDetails() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click()", submit);
		return new ConfirmationPage(driver);
	}
}
