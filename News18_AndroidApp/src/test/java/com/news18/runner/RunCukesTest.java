package com.news18.runner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import com.api.base.ApplicationFuncs;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.utilities.base.LogUtil;

import cucumber.api.CucumberOptions;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(features = {"features/A_News18_English.feature"
//		"features/B_News18_Hindi.feature","features/C_News18_Bengali.feature","features/D_News18_Marathi.feature",
//		"features/E_News18_Tamil.feature","features/F_News18_Telugu.feature","features/G_News18_Kannada.feature",
//		"features/H_News18_Malayalam.feature","features/I_News18_Gujarati.feature","features/J_News18_Punjab.feature","features/K_News18_Urdu.feature",
//		"features/L_News18_Assam.feature","features/M_News18_Odia.feature"
		}, plugin = { "pretty", "html:target/cucumber-html-report",
		"json:target/cucumber.json",
"com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-html-report/index.html" }, 
glue={"com.news18.stepdefinition"},tags = {"@English"}, monochrome = true)
public class RunCukesTest extends AbstractTestNGCucumberTests {

	static ExtentReports extent;
	public static ExtentTest logger;
	String imagePath;
	String pathForLogger;
	String testCaseDescription;
	public Scenario scenario;
	public FileOutputStream fio;

	private static final String NEW_LINE = "\n";
	private static final String HTML_FILE_EXTENSION = ".html";
	private static final String TEMP_FILE_EXTENSION = ".tmp";
	private static final String HTML_SNNIPET_1 = "<!DOCTYPE html><html><head><title>";
	private static final String HTML_SNNIPET_2 = "<table cellspacing='0' cellpadding='0' width='640' border='1'>";
	private static final String HTML_SNNIPET_3 = "</table></body></html>";
	private static final String HTML_TR_S = "<tr>";
	private static final String HTML_TR_E = "</tr>";
	private static final String HTML_TD_S = "<td align='center' valign='top' style='border-left: solid 1px'>";
	private static final String HTML_TD_S_C = "<td align='center' valign='top' style='color:red;font-weight: bold;' width='50%' style='font-size:25px'>";
	private static final String HTML_TD_E = "</td>";
	private static String[] sheetnames;
	@Before
	public void beforeMethod(Scenario scenario) {
		this.scenario = scenario;
		if (scenario.getName().contains("_"))
			testCaseDescription = scenario.getName().split("_")[1];
		else
			testCaseDescription = scenario.getName();
		System.out.println("CURRENTLY EXECUTING TC IS: " +testCaseDescription);

		LogUtil.infoLog(getClass(),
				"\n+----------------------------------------------------------------------------------------------------------------------------+");
		LogUtil.infoLog(getClass(), "Test Started: " + scenario.getName());

	}

	@BeforeTest
	public void cleanUp() {
		File fileName = new File("htmlemail.html");
		if (fileName.exists()) {
			fileName.delete();
		}
	}
	@BeforeClass
	public void onStart() throws FileNotFoundException {
		try {

			// Get all the common setting from excel file that are required for
			String	uri = ApplicationFuncs.baseURI;

			LogUtil.infoLog(getClass(),
					"\n\n+===========================================================================================================+");
			LogUtil.infoLog(getClass(), " Suite started" + " at " + new Date());

			LogUtil.infoLog(getClass(),
					"Suite Execution For RestAPI on URL: " + uri);
			LogUtil.infoLog(getClass(),
					"\n\n+===========================================================================================================+");
			File fileName = new File("ExecutionReport.xlsx");
			if(fileName.exists()) {
				fileName.delete();
			}
			FileOutputStream fileOut = new FileOutputStream(fileName); 
			Workbook wb=new XSSFWorkbook();
			wb.write(fileOut); 
			fileOut.close(); 
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.errorLog(getClass(), "Common Settings not properly set may not run the scripts properly");
		}

	}

	@AfterClass
	public void onFinish() throws IOException {
		BufferedWriter writer;
		Workbook workbook;
		String sheetName = null;
		FileInputStream file = new FileInputStream(new File("ExecutionReport.xlsx"));
		workbook = new XSSFWorkbook(file);
		File tempFile = File.createTempFile("tr" + '-', HTML_FILE_EXTENSION + TEMP_FILE_EXTENSION,
				new File(System.getProperty("user.dir")));
		writer = new BufferedWriter(new FileWriter(tempFile));
		writer.write("<!DOCTYPE html><html xmlns='http://www.w3.org/1999/xhtml'>");
		writer.write("<body>");
		int numOfSheets = workbook.getNumberOfSheets();
		sheetnames = new String[numOfSheets];

			for (int i = 0; i < numOfSheets; i++) {
				writer.write("<head>");
				writer.write("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>");
				Sheet sheet = workbook.getSheetAt(i);
				sheetName = sheet.getSheetName();
				Iterator<Row> rows = sheet.rowIterator();
				Iterator<Cell> cells = null;
				DataFormatter formatter = new DataFormatter();
				writer.write("<h1 style='background-color:#00FA9A;width:640px;font-size: 20px;'>");
				writer.write(sheetName);
				writer.write("</h1>");
				writer.write("</head>");
				writer.write(HTML_SNNIPET_2);
				writer.write("<thead bgcolor='#FFD700'><tr>");
				writer.write("<th>Section Name</th>");
				writer.write("<th>Number Of Failures</th>");
				writer.write("</tr></thead>");
				Map<String, Integer> map = map = new HashMap<>();
				while (rows.hasNext()) {
					Row row = rows.next();
					cells = row.cellIterator();
					Cell valll = row.getCell(4);
					String value = formatter.formatCellValue(valll);
					if (value.equalsIgnoreCase("FAIL")) {
						Cell secName = row.getCell(0);
						String name = secName.toString();
						if (map.containsKey(name)) {
							int count = map.get(name); 
							map.put(name, count + 1); 
						} else {
							map.put(name, 1); 
						}
					}
				}
				Set<String> keys = map.keySet();
				TreeSet<String> sortedKeys = new TreeSet<>(keys);
				if (sortedKeys.size() == 0) {
					writer.write(HTML_TR_S);
					writer.write("<th colspan='2'>No failure  - Test Passed</th>");
					writer.write(HTML_TD_E);
					writer.write(HTML_TR_E);
				} else {
					for (String str : sortedKeys) {
						writer.write(NEW_LINE);
						writer.write(HTML_TR_S);
						writer.write(HTML_TD_S);
						writer.write(str);
						writer.write(HTML_TD_E);
						writer.write(HTML_TD_S_C);
						writer.write(map.get(str).toString());
						writer.write(HTML_TD_E);
						writer.write(HTML_TR_E);
					}
				}
				writer.write("</table>");
			}
		//}
		writer.write(NEW_LINE);
		writer.write(HTML_SNNIPET_3);
		writer.write("</body>");
		writer.write("</html>");
		writer.close();
		File newFile = new File(System.getProperty("user.dir") + '/' + "htmlemail" + HTML_FILE_EXTENSION);
		System.out.println(newFile);
		tempFile.renameTo(newFile);

		LogUtil.infoLog(getClass(), " suite finished" + " at " + new Date());
		LogUtil.infoLog(getClass(),
				"\n\n+===========================================================================================================+");
	}


}
