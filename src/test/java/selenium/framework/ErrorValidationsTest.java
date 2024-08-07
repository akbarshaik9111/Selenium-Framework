package selenium.framework;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import in.akbar.pageobjects.CheckoutPage;
import in.akbar.pageobjects.ConfirmationPage;
import in.akbar.pageobjects.LoginPage;
import in.akbar.pageobjects.PaymentPage;
import in.akbar.pageobjects.ProductsCatlogPage;
import selenium.framework.testcomponents.BaseTest;
import selenium.framework.testcomponents.RetryListener;

public class ErrorValidationsTest extends BaseTest {

	@Test(groups = {"errorvalidtion"}, retryAnalyzer = RetryListener.class)
	public void errorMessageValidation() throws IOException {
		ProductsCatlogPage productsCatlogPage = loginPage.loginApplication("akbarshaik@gmail.com", "kbar@0326");
		String errMsg = loginPage.getErrorMessage();
		Assert.assertEquals(errMsg, "Incorrect email or password.");
	}
	
	@Test
	public void productCatelogErrorValidation() {
		String prodName = "ZARA COAT 3";
		ProductsCatlogPage productsCatlogPage = loginPage.loginApplication("akbarshaik@gmail.com", "Akbar@0326");
	
		List<WebElement> products = productsCatlogPage.getProducts();
		productsCatlogPage.addProductToTheCart(prodName);
		CheckoutPage checkoutPage = productsCatlogPage.clickOnCart();

		Boolean status = checkoutPage.verifyProductAvailability("ZARA COAT 33");
		Assert.assertFalse(status);
	}
}