package com.news18.stepdefinition;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.api.base.ApplicationFuncs;
import com.cucumber.listener.Reporter;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.news18.locators.Urdu_Homepage;
import com.utilities.base.ConfigReader;
import com.utilities.base.GlobalUtil;
import com.utilities.base.KeywordUtil;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Stepdefinitions_Urdu {
	List<String> mobileWebList = new ArrayList<String>();
	List<String> list = new ArrayList<String>();
	String sectionName;
	@When("^Gets all top stories list in Urdu language$")
	public void gets_all_top_stories_list_in_Urdu_language() throws Throwable {

		KeywordUtil.waitForVisible(Urdu_Homepage.topStory);
		String title = KeywordUtil.getElementText(Urdu_Homepage.topStory);
		mobileWebList.add(title);
		KeywordUtil.scroll();
		List<WebElement> list =  KeywordUtil.getListElements(Urdu_Homepage.storyList);
		for(int i=1;i<=8;i++) {
			KeywordUtil.waitForVisible(By.xpath("(//a[text()='تازہ خبریں']/ancestor::div[contains(@class,'globalhd')]/following-sibling::*//a)["+i+"]"));
			title = KeywordUtil.getAttribute("title", By.xpath("(//a[text()='تازہ خبریں']/ancestor::div[contains(@class,'globalhd')]/following-sibling::*//a)["+i+"]"));
			boolean status = KeywordUtil.isWebElementVisible(By.xpath("(//a[text()='تازہ خبریں']/ancestor::div[contains(@class,'globalhd')]/following-sibling::*//a)["+i+"]"));
			if(status) {
				mobileWebList.add(title);
			}
		}
		for(String str:mobileWebList) {
			System.out.println(str+"\n");
		}
		System.out.println("---------------------------------------------------");
		//GlobalUtil.getDriver().close();


	}

	@When("^User hits News(\\d+) mobile app Urdu language api$")
	public void user_hits_News_mobile_app_Urdu_language_api(int arg1) throws Throwable {


		Response response;
		sectionName = "Top Stories";
		RestAssured.baseURI = ConfigReader.getValue("News18_EndPoint");
		response = RestAssured.given().when().get("ur/get/URDN18:ur_V1_APP_homefeed/android/1/92/");
		JsonPath path = response.jsonPath();
		List<String> storysectionList =path.get("node");

		for (int i = 0; i <storysectionList.size(); i++) {
			String storysection = path.get("node[" + i + "].dimension").toString();
			if (storysection.equalsIgnoreCase("top_stories")) {
				List<String> l2 = path.get("node[" + i + "].data");
				for (int j = 0; j < l2.size(); j++) {
					List<String> l3 = path.get("node[" + i + "].data[" + j + "].data_node");
					for (int k = 0; k < l3.size(); k++) {
						String childSection = path.get("node[" + i + "].data[" + j + "].data_node.child_layout_type["+k+"]").toString();
						if(childSection.equalsIgnoreCase("top_stories") || childSection.equalsIgnoreCase("medium_horizontal_stories") || childSection.equalsIgnoreCase("vertical_list_stories"))
						{
							list.add(path.get("node[" + i + "].data[" + j + "].data_node[" + k + "].headline").toString());
						}
					}
				}
			}
		}
		for (String str : list) { 
			System.out.print(str + "\n"); 
		} 	
	}
	static int num=1;
	static XSSFWorkbook wb ;
	static String FILE_NAME    = "ExecutionReport.xlsx"; 
	static XSSFSheet sheet;
	static InputStream inp;
	@Then("^Compare the stories of Mobile Web and Mobile App in Urdu language$")
	public void compare_the_stories_of_Mobile_Web_and_Mobile_App_in_Urdu_language() throws Throwable {
		
		ApplicationFuncs.writeDataIntoExcel(mobileWebList, list, Stepdefinitions_Hindi.languageName,sectionName);

	}

	@When("^Gets all \"([^\"]*)\" list in Urdu language$")
	public void gets_all_list_in_Urdu_language(String section) throws Throwable {

		sectionName=section;

		if(section.equalsIgnoreCase("تصاویر"))
			section="سپرہٹ گیلری";
		else if(section.equalsIgnoreCase("ویڈیو"))
			section="ویڈیوز";
		else if(section.equalsIgnoreCase("قومی منظر"))
			section="قومی منظر ";
		else if(section.equalsIgnoreCase("تصاویر"))
			section="سپرہٹ گیلری";
		else if(section.equalsIgnoreCase("عالمی منظر"))
			section="عالمی منظر ";
		else if(section.equalsIgnoreCase("اسپورٹس"))
			section="اسپورٹس ";


		KeywordUtil.delay(5000);
		KeywordUtil.scroll();
		KeywordUtil.scroll();
		KeywordUtil.waitForPageToLoad();
		KeywordUtil.scrollingToElementofAPage(By.id("ob_holder"));
		if(section.equals("معیشت")) {
			List<WebElement> list =KeywordUtil.getListElements(By.xpath("//a[text()='"+section+"']/ancestor::div[@class='story-repeat-div']//img"));
			for(int i=2; i<=6;i++) {
				KeywordUtil.waitForVisible(By.xpath("(//a[text()='"+section+"']/ancestor::div[@class='story-repeat-div']//img)["+i+"]"));
				String title = KeywordUtil.getAttribute("title", By.xpath("(//a[text()='"+section+"']/ancestor::div[@class='story-repeat-div']//img)["+i+"]"));
				boolean status = KeywordUtil.isWebElementVisible(By.xpath("(//a[text()='"+section+"']/ancestor::div[@class='story-repeat-div']//img)["+i+"]"));
				if(status) {
					mobileWebList.add(title);
				}
			}

		}else {
			List<WebElement> list =KeywordUtil.getListElements(By.xpath("//a[text()='"+section+"']/ancestor::div[contains(@class,'globalhd')]/following-sibling::*//img"));
			for(int i=1; i<=5;i++) {
				KeywordUtil.waitForVisible(By.xpath("(//a[text()='"+section+"']/ancestor::div[contains(@class,'globalhd')]/following-sibling::*//img)["+i+"]"));
				String title = KeywordUtil.getAttribute("title", By.xpath("(//a[text()='"+section+"']/ancestor::div[contains(@class,'globalhd')]/following-sibling::*//img)["+i+"]"));
				boolean status = KeywordUtil.isWebElementVisible(By.xpath("(//a[text()='"+section+"']/ancestor::div[contains(@class,'globalhd')]/following-sibling::*//img)["+i+"]"));
				if(status) {
					mobileWebList.add(title);
				}
			}
		}
		for(String str:mobileWebList){
			System.out.println(str+"\n");
		}
		System.out.println("---------------------------------------------------");
		//GlobalUtil.getDriver().close();
	}

	@When("^User hits News(\\d+) mobile app Urdu language api for \"([^\"]*)\" section$")
	public void user_hits_News_mobile_app_Urdu_language_api_for_section(int arg1, String section) throws Throwable {


		RestAssured.baseURI = ConfigReader.getValue("News18_EndPoint");
		int pageNumber=1;

		if(section.equalsIgnoreCase("قومی منظر") || section.equalsIgnoreCase("جموں و کشمیر"))
		{
			pageNumber =2;
		}
		else if(section.equalsIgnoreCase("انٹرٹینمنٹ") || section.equalsIgnoreCase("تصاویر"))
		{
			pageNumber =3;
		}
		else if(section.equalsIgnoreCase("عالمی منظر") || section.equalsIgnoreCase("اسپورٹس"))
		{
			pageNumber =4;
		}
		else if(section.equalsIgnoreCase("تعلیم و روزگار") || section.equalsIgnoreCase("ویڈیو"))
		{
			pageNumber =5;
		}
		else if(section.equalsIgnoreCase("معیشت") || section.equalsIgnoreCase("ویڈیو"))
		{
			pageNumber =6;
		}

		KeywordUtil.delay(2000);
		Response response = RestAssured.given().when().get("ur/get/URDN18:ur_V1_APP_homefeed/android/"+pageNumber+"/86/");
		JsonPath path = response.jsonPath();
		List<String> l1 = path.get("node");
		for(int i=0;i<l1.size();i++) {
			String storysection = path.get("node[" + i + "].storysection").toString();
			if (storysection.equals(section)) {
				List<String> l2 = path.get("node[" + i + "].data");
				for (int j = 0; j < l2.size(); j++) {
					List<String> l3 = path.get("node[" + i + "].data[" + j + "].data_node");
					for (int k = 0; k < l3.size(); k++) {
						String childSection = path.get("node[" + i + "].data[" + j + "].data_node.child_layout_type["+k+"]").toString();
						if(childSection.equalsIgnoreCase("list_top_stories") || childSection.equalsIgnoreCase("medium_horizontal_stories") || childSection.equalsIgnoreCase("vertical_list_stories")||childSection.equalsIgnoreCase("sports_top_stories"))
						{
							list.add(path.get("node[" + i + "].data[" + j + "].data_node[" + k + "].headline").toString());
						}
					}
				}
			}
		}
		for (String str : list) { 
			System.out.print(str + "\n"); 
		}  

	}

	@When("^Gets all sections list in Urdu language$")
	public void gets_all_sections_list_in_Urdu_language() throws Throwable {
		KeywordUtil.delay(5000);
		KeywordUtil.waitForPageToLoad();
		KeywordUtil.scroll();
		KeywordUtil.delay(2000);
		KeywordUtil.waitForVisible(Urdu_Homepage.sectionNames);
		List<WebElement> count = KeywordUtil.getListElements(Urdu_Homepage.sectionNames);


		for(int i=1;i<=count.size();i++)
		{
			KeywordUtil.mouseOver(By.xpath("(//div[contains(@class,'globalhd')]//h2/a)["+i+"]"));
			String title = KeywordUtil.getElementText(By.xpath("(//div[contains(@class,'globalhd')]//h2/a)["+i+"]")).toLowerCase();
			Boolean status = KeywordUtil.isWebElementVisible(By.xpath("(//div[contains(@class,'globalhd')]//h2/a)["+i+"]"));
			if (status) {
				mobileWebList.add(title);
			}
		}
		List<WebElement> list = KeywordUtil.getListElements(By.xpath("//div[contains(@class,'globalhd')]/following-sibling::h2/a"));
		for(int i=1;i<=list.size();i++)
		{
			KeywordUtil.mouseOver(By.xpath("(//div[contains(@class,'globalhd')]/following-sibling::h2/a)["+i+"]"));
			String title = KeywordUtil.getElementText(By.xpath("(//div[contains(@class,'globalhd')]/following-sibling::h2/a)["+i+"]")).toLowerCase();
			Boolean status = KeywordUtil.isWebElementVisible(By.xpath("(//div[contains(@class,'globalhd')]/following-sibling::h2/a)["+i+"]"));
			if (status) {
				mobileWebList.add(title);
			}
		}
		for (String str : mobileWebList) { 
			System.out.print(str + "\n"); 
		} 
		//GlobalUtil.getDriver().close();


	}

	ArrayList<String> mobileappList=new ArrayList<String>();
	@When("^User hits News(\\d+) mobile app Urdu api for getting sections$")
	public void user_hits_News_mobile_app_Urdu_api_for_getting_sections(int arg1) throws Throwable {

		Response response;
		String storysection;
		RestAssured.baseURI = ConfigReader.getValue("News18_EndPoint");
		for(int i=2;i<=6;i++)
		{
			response = RestAssured.given().when().get("ur/get/URDN18:ur_V1_APP_homefeed/android/"+i+"/86/");
			JsonPath path = response.jsonPath();
			List<String> l1=path.get("node");
			for (int j = 0; j < l1.size(); j++) {
				storysection = path.get("node[" + j + "].data[0].headline").toString().toLowerCase();
				if(!storysection.equals(""))
					mobileappList.add(storysection);
			}
		}
		for (String str : mobileappList) { 
			System.out.print(str + "\n"); 
		} 
	}

	@Then("^Compare the sections in Mobile Web and Mobile App for Urdu language$")
	public void compare_the_sections_in_Mobile_Web_and_Mobile_App_for_Urdu_language() throws Throwable {

		Collection<String> coll = new HashSet<>(mobileWebList);
		for(int i=0;i<mobileWebList.size();i++) {
			if(mobileWebList.get(i).equals("سپرہٹ گیلری")) {
				mobileWebList.set(i, "تصاویر");
			}
			else if(mobileWebList.get(i).equals("ویڈیوز")) {
				mobileWebList.set(i, "ویڈیو");
			}
			else if(mobileWebList.get(i).equals("سپرہٹ گیلری")) {
				mobileWebList.set(i, "تصاویر");
			}
		}

		Collection<String> different = ApplicationFuncs.compareAllTheSectionsWithMap(mobileWebList, mobileappList, Stepdefinitions_Hindi.languageName, coll);

		Row row=ApplicationFuncs.sheet.createRow(ApplicationFuncs.num++);
		Cell c=row.getCell(0);
		if(!(c!=null&&c.getCellType()!=Cell.CELL_TYPE_BLANK)) {
			row.createCell(0).setCellValue("Matching Sections in App and Web");
		}
		c=row.getCell(1);
		if(!(c!=null&&c.getCellType()!=Cell.CELL_TYPE_BLANK)) {
			row.createCell(1).setCellValue("سپرہٹ گیلری(Web)  : تصاویر(App), ویڈیوز(Web)  : ویڈیو(App) and سپرہٹ گیلری(Web) : تصاویر(App)");
		}


		FileOutputStream fileOut = new FileOutputStream(ApplicationFuncs.FILE_NAME); 
		ApplicationFuncs.wb.write(fileOut); 
		fileOut.close(); 
		mobileappList.removeAll(mobileappList);
		mobileWebList.removeAll(mobileWebList);
		KeywordUtil.delay(3000);
		if(different.size()==0)
		{
			Reporter.addStepLog(" <font color='red'> Sections which are not in Mobile App are <font color='magenta'> "+different+" </font> </font>");
			Assert.fail("Mobile Web has more sections");
		}	   




	}

}
