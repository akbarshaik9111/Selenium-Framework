package in.akbar.abstractmethods;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import in.akbar.pageobjects.CheckoutPage;
import in.akbar.pageobjects.OrderPage;

public class AbstractMethod {
	
	WebDriver driver;
	
	public AbstractMethod(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="button[routerlink*='cart']")
	WebElement cartBtn;
	
	@FindBy(css="button[routerlink='/dashboard/myorders']")
	WebElement orderBtn;
	
	public void waitUntilElementVisibleByLocator(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public void waitUntilElementVisibleByWebElement(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void waitUntilElementInVisibleByWebElement(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.invisibilityOf(element));
	}
	
	public void waitUntilElementClickableByWebElement(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public CheckoutPage clickOnCart() {
		cartBtn.click();
		return new CheckoutPage(driver); 
	}
	
	public OrderPage clickOrderButton() {
		orderBtn.click();
		OrderPage orderPage = new OrderPage(driver);
		return orderPage;
	}
}
