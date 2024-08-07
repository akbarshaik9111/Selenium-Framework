package in.akbar.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import in.akbar.abstractmethods.AbstractMethod;

public class ConfirmationPage extends AbstractMethod {
	
	WebDriver driver;
	
	public ConfirmationPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//h1[@class='hero-primary']")
	WebElement confirmatinText;
	
	// GET THE CONFIRMATION MESSAGE AFTER PLACING THE ORDER
	public String getConfirmationTextMsg() {
		String confirmMsg = confirmatinText.getText();
		return confirmMsg;
	}
}
