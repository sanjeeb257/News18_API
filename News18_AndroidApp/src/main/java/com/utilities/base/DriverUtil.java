package com.utilities.base;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.github.bonigarcia.wdm.WebDriverManager;



public class DriverUtil {

	public static final String IE = "IE";
	public static final String REMOTE = "Remote";
	public static final String EDGE = "edge";
	public static final String CHROME = "Chrome";
	private static Map<String, WebDriver> drivers = new HashMap<>();
	public static WebDriver driver = null;
	private static HashMap<String, String> checkLogin = new HashMap<>();
	
	
	public static WebDriver getChromeBrowser(String browserName) {
		WebDriver browser = null;
		try {
				if (browserName.equalsIgnoreCase(CHROME)) {
					// Write code for chrome
					ChromeOptions options = new ChromeOptions();

					options.addArguments("--window-size=1920,1200");
					options.setPageLoadStrategy(PageLoadStrategy.NONE);
					options.addArguments("--no-sandbox");
					options.addArguments("--disable-dev-shm-usage");
					 options.addArguments("--disable-web-security");
					 options.addArguments("--disable-gpu");
					 options.addArguments("--disable-popup-blocking");
				     options.addArguments("--allow-running-insecure-content");
				     options.setExperimentalOption("useAutomationExtension", false);
				     options.setExperimentalOption("excludeSwitches",Collections.singletonList("enable-automation"));    
					options.addArguments("--ignore-certificate-errors");
//					options.addArguments("--headless");
					options.addArguments("--disable-notifications");
					Map<String, String> mobileEmulation = new HashMap<>();
					mobileEmulation.put("deviceName", "Pixel 2");

					options.setExperimentalOption("mobileEmulation", mobileEmulation);

					browser = drivers.get(browserName);
					WebDriverManager.chromedriver().setup();
					browser = new ChromeDriver(options);

				} 

			LogUtil.infoLog("TestStarted", browserName + " : Browser Launched and Maximized.");
		} catch (Exception e) {
			LogUtil.errorLog(DriverUtil.class, "Browser not launched. Please check the configuration ", e);
			e.printStackTrace();
		}
		return browser;
	}

	/**
	 * will get browser type and version
	 * 
	 * @param browser
	 * @param cap
	 * @return
	 */
	public static String getBrowserAndVersion(WebDriver browser, DesiredCapabilities cap) {
		String browserversion;
		String windows = "Windows";
		String browsername = cap.getBrowserName();
		// This block to find out IE Version number
		if ("IE".equalsIgnoreCase(browsername)) {
			String uAgent = (String) ((JavascriptExecutor) browser).executeScript("return navigator.userAgent;");
			LogUtil.infoLog(DriverUtil.class, uAgent);
			// uAgent return as "MSIE 8.0 Windows" for IE8
			if (uAgent.contains("MSIE") && uAgent.contains(windows)) {
				browserversion = uAgent.substring(uAgent.indexOf("MSIE") + 5, uAgent.indexOf(windows) - 2);
			} else if (uAgent.contains("Trident/7.0")) {
				browserversion = "11.0";
			} else {
				browserversion = "0.0";
			}
		} else {
			// Browser version for Firefox and Chrome
			browserversion = cap.getVersion();
		}
		String browserversion1 = browserversion.substring(0, browserversion.indexOf('.'));
		return browsername + " " + browserversion1;
	}
	

}// End class
