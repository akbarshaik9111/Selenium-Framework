package selenium.framework;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import in.akbar.pageobjects.CheckoutPage;
import in.akbar.pageobjects.ConfirmationPage;
import in.akbar.pageobjects.OrderPage;
import in.akbar.pageobjects.PaymentPage;
import in.akbar.pageobjects.ProductsCatlogPage;
import selenium.framework.testcomponents.BaseTest;

public class SubmitOrderTest extends BaseTest {

	String prodName = "ZARA COAT 3";
	
	@Test(dataProvider= "getData", groups = "purchase")
	public void submitOrder(HashMap<String, String> input) throws IOException {
		
		ProductsCatlogPage productsCatlogPage = loginPage.loginApplication(input.get("email"), input.get("password"));
		
		List<WebElement> products = productsCatlogPage.getProducts();
		productsCatlogPage.addProductToTheCart(input.get("product"));
		CheckoutPage checkoutPage = productsCatlogPage.clickOnCart();

		Boolean status = checkoutPage.verifyProductAvailability(input.get("product"));
		Assert.assertTrue(status);
		PaymentPage paymentPage = checkoutPage.clickOnCheckOutBtn();
		
		paymentPage.selectTheCountry("india", "302", "Akbar");
		ConfirmationPage confimMsg = paymentPage.submitDetails();
		
		String confirmation = confimMsg.getConfirmationTextMsg();
		Assert.assertTrue(confirmation.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	}
	
	@Test(dependsOnMethods = {"submitOrder"})
	public void verifyOrderList() {
		ProductsCatlogPage productsCatlogPage = loginPage.loginApplication("akbarshaik@gmail.com", "Akbar@0326");
		OrderPage ordersPage = productsCatlogPage.clickOrderButton();
		Boolean match = ordersPage.getProdcutsFromOrders(prodName);
		Assert.assertTrue(match);
	}
	
	// DRIVEN THE DATA FROM OBJECT ARRAY
	/*
	@DataProvider
	public Object[][] getData() {
		return new Object[][] {{"akbarshaik@gmail.com", "Akbar@0326", "ZARA COAT 3"}, {"shetty@gmail.com", "Iamking@000", "IPHONE 13 PRO"}};
	}
	
	// DRIVEN THE DATA FROM HASHMAP
	@DataProvider
	public Object[][] getData() {
		HashMap<String, String> map = new HashMap<>();
		map.put("email", "akbarshaik@gmail.com");
		map.put("password", "Akbar@0326");
		map.put("product", "ZARA COAT 3");
		
		HashMap<String, String> map1 = new HashMap<>();
		map1.put("email", "shetty@gmail.com");
		map1.put("password", "Iamking@000");
		map1.put("product", "IPHONE 13 PRO");
		
		return new Object[][]{{map}, {map1}};
	}*/
	
	// DRIVEN THE DATA FROM JSON FILE
	@DataProvider
	public Object[][] getData() throws IOException {
		List<HashMap<String, String>> data = getJsonData(System.getProperty("user.dir")+"\\src\\test\\java\\selenium\\framework\\data\\PurchaseOrder.json");
		return new Object[][]{{data.get(0)}, {data.get(1)}};
	}
}
