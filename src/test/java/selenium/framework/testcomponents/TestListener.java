package selenium.framework.testcomponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import in.akbar.resources.ExtentReportGeneration;

public class TestListener extends BaseTest implements ITestListener {

	ExtentReports extent = ExtentReportGeneration.generateExtentReport();
	ExtentTest test;
	ThreadLocal<ExtentTest> local = new ThreadLocal<>();

	
	@Override
	public void onTestStart(ITestResult result) {
		test = extent.createTest(result.getMethod().getMethodName());
		local.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		local.get().log(Status.PASS, result.getMethod().getMethodName()+" - Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		local.get().fail(result.getThrowable());
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver")
					.get(result.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String filePath = null;
		try {
			filePath = takesScreenshot(result.getMethod().getMethodName(), driver);
		} catch(Exception e) {
			e.printStackTrace();
		}
		local.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();

	}

}
