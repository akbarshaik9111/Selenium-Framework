package in.akbar.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportGeneration {
	
	public static ExtentReports generateExtentReport() {
		String path = System.getProperty("user.dir")+"\\reports\\index.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setDocumentTitle("AUTOMATION RESULTS");
		reporter.config().setReportName("WEB AUTOMATION RESULTS");
		
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("QA", "Shaik Akbar");
		return extent;
	}
}
