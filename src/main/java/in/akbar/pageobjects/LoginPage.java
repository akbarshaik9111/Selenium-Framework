package in.akbar.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import in.akbar.abstractmethods.AbstractMethod;

public class LoginPage extends AbstractMethod {

	WebDriver driver;

	public LoginPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id = "userEmail")
	WebElement username;

	@FindBy(id = "userPassword")
	WebElement password;

	@FindBy(id = "login")
	WebElement login;
	
	@FindBy(css="[class*='flyInOut']")
	WebElement errorMsg;
	
	// LAUNCH THE URL
	public void goToLanuchUrl() {
		driver.get("https://rahulshettyacademy.com/client");
	}

	// LOGIN INTO THE APPLICATION
	public ProductsCatlogPage loginApplication(String userName, String pwd) {
		username.sendKeys(userName);
		password.sendKeys(pwd);
		login.click();
		return new ProductsCatlogPage(driver);
	}
	
	// GET THE ERROR MESSAGE IF LOGIN FAILS
	public String getErrorMessage() {
		waitUntilElementVisibleByWebElement(errorMsg);
		String errMsg = errorMsg.getText();
		return errMsg;
	}
}
