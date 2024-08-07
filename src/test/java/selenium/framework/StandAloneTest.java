package selenium.framework;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import in.akbar.pageobjects.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTest {

	public static void main(String[] args) throws InterruptedException {
		
		String prodName = "ZARA COAT 3";
		WebDriverManager.chromedriver().setup();
		//System.setProperty("webdriver.edge.driver", "J:\\selenium\\drivers\\edgedriver_win64\\msedgedriver.exe");
		WebDriver driver = new EdgeDriver();
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
		
		driver.get("https://rahulshettyacademy.com/client");
		LoginPage loginPage = new LoginPage(driver);
		WebElement username = driver.findElement(By.id("userEmail"));
		WebElement password = driver.findElement(By.id("userPassword"));
		WebElement login = driver.findElement(By.id("login"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		username.clear();
		username.sendKeys("akbarshaik@gmail.com");
		
		password.clear();
		password.sendKeys("Akbar@0326");
		login.click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		WebElement prod = products.stream().filter(product -> 
			product.findElement(By.cssSelector("b")).getText().equals(prodName)).findFirst().orElse(null);
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#toast-container"))));
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		driver.findElement(By.cssSelector("button[routerlink*='cart']")).click();
		
		List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
		boolean status = cartProducts.stream().anyMatch(cartpProd -> cartpProd.getText().equalsIgnoreCase(prodName));
		Assert.assertTrue(status);
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
		driver.findElement(By.xpath("//input[@class='input txt' and @type='text']")).sendKeys("302");
		//driver.findElement(By.xpath("(//div[@class='field']/input)[2]")).sendKeys("Akbar");
		WebElement nameOnCard = driver.findElement(By.xpath("//div[text()='Name on Card ']"));
		driver.findElement(with(By.tagName("input")).below(nameOnCard)).sendKeys("Akbar");
		
		Actions action = new Actions(driver);
		WebElement countryText = driver.findElement(By.xpath("//input[@placeholder='Select Country']"));
		action.sendKeys(countryText, "india").build().perform();
		
		List<WebElement> listOfCountries = driver.findElements(By.cssSelector(".ta-results .ta-item"));
		for(WebElement country : listOfCountries) {
			String countryName = country.getText();
			if(countryName.equalsIgnoreCase("india")) {
				country.click();
			}
		}
		
		Thread.sleep(2000);
		/*WebElement element = driver.findElement(By.cssSelector(".action__submit"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		element.click();*/
		
		driver.findElement(By.cssSelector("div[class='actions'] a")).click();
		String confirmation = driver.findElement(By.xpath("//h1[@class='hero-primary']")).getText();
		Assert.assertTrue(confirmation.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		Thread.sleep(2000);
		driver.close();
	}
}
