package in.akbar.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import in.akbar.abstractmethods.AbstractMethod;

public class ProductsCatlogPage extends AbstractMethod {
	
	WebDriver driver;
	
	public ProductsCatlogPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	By byLocator = By.cssSelector(".mb-3");
	By addToCart = By.cssSelector(".card-body button:last-of-type");
	
	@FindBy(css=".mb-3")
	List<WebElement> products;
	
	@FindBy(css="#toast-container")
	WebElement toastContainer;
	
	@FindBy(css=".ng-animating")
	WebElement animating;
	
	public List<WebElement> getProducts() {
		waitUntilElementVisibleByLocator(byLocator);
		return products;
	}
	
	// GET THE PRODUCT NAME FROM THE LIST OF PRODUCTS
	public WebElement getProdcutByName(String prodName) {
		WebElement prod = getProducts().stream().filter(product -> 
		product.findElement(By.cssSelector("b")).getText().equals(prodName)).findFirst().orElse(null);
		return prod;
	}
	
	// ADD THE PRODUCT TO THE CART
	public void addProductToTheCart(String prodName) {
		WebElement prod = getProdcutByName(prodName);
		prod.findElement(addToCart).click();
		waitUntilElementVisibleByWebElement(toastContainer);
		waitUntilElementInVisibleByWebElement(animating);
	}	
}
