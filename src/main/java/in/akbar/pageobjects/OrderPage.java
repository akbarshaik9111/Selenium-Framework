package in.akbar.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import in.akbar.abstractmethods.AbstractMethod;

public class OrderPage extends AbstractMethod {
	
	WebDriver driver;
	
	public OrderPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="tr td:nth-child(3)")
	List<WebElement> prodNames;
	
	public Boolean getProdcutsFromOrders(String prodName) {
		boolean match = prodNames.stream().anyMatch(prod -> prod.getText().equalsIgnoreCase(prodName));
		return match;
	}

}
