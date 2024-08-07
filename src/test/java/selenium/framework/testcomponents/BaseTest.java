package selenium.framework.testcomponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.akbar.pageobjects.LoginPage;

public class BaseTest {
	
	public WebDriver driver;
	public LoginPage loginPage;
	
	// INITIALIZE THE DRIVER
	public WebDriver initializeDriver() throws IOException {
		Properties props = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\in\\akbar\\resources\\GlobalData.properties");
		props.load(fis);
		String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : props.getProperty("browser");
		//String browserName = props.getProperty("browser");
		if(browserName.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
		} else if(browserName.equalsIgnoreCase("edge")) {
			EdgeOptions options = new EdgeOptions();
			if(browserName.contains("headless")) {
				options.addArguments("--headless");
			}
			driver = new EdgeDriver(options);
			driver.manage().window().setSize(new Dimension(1440, 900));
			driver.manage().deleteAllCookies();
		} else if(browserName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "J:\\selenium\\drivers\\geckodriver-v0.34.0-win64\\geckodriver.exe");			
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
		
		return driver;
	}
	
	// LAUNCHING THE APPLICATION
	@BeforeMethod(alwaysRun = true)
	public LoginPage launchApplication() throws IOException {
		driver = initializeDriver();
		loginPage = new LoginPage(driver);
		loginPage.goToLanuchUrl();
		return loginPage;
	}
	
	// CLOSE THE BROWSER
	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.close();
	}
	
	// CONVERT THE JSONDATA TO STRING AND STRING TO HASHMAP
	public List<HashMap<String, String>> getJsonData(String filePath) throws IOException {
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
		});
		return data;
	}
	
	// CREATE AN UTILITY TO TAKE SCREENSHOT
	public String takesScreenshot(String testcasename, WebDriver driver) throws IOException {
		// Take screenshot and store as a file format
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
					
		// Now to copy the screenshot to desired location using copyFile method
		File file = new File(System.getProperty("user.dir")+"//reports"+testcasename+".png");
		FileUtils.copyFile(src, file);
			
		// RETURN THE LOCATION OF THE SCREENSHOT 
		return System.getProperty("user.dir")+"//reports"+testcasename+".png";
	}
}
