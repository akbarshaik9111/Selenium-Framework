package in.akbar.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import in.akbar.abstractmethods.AbstractMethod;

public class CheckoutPage extends AbstractMethod {
	
	WebDriver driver;
	
	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css=".cartSection h3")
	List<WebElement> cartProdcuts;
	
	@FindBy(css=".totalRow button")
	WebElement checkoutBtn;
	
	public List<WebElement> getListOfCartProducts() {
		return cartProdcuts;
	}
	
	// VERIFY THE ADDED PRODUCT IN CART PAGE
	public Boolean verifyProductAvailability(String prodName) {
		boolean match = getListOfCartProducts().stream().anyMatch(cartpProd -> cartpProd.getText().equalsIgnoreCase(prodName));
		return match;
	}
	
	// CLICK ON CHECKOUT BUTTON
	public PaymentPage clickOnCheckOutBtn() {
		checkoutBtn.click();
		return new PaymentPage(driver);
	}
}
