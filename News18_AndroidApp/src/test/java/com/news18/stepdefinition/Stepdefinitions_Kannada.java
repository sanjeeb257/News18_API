package com.news18.stepdefinition;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import com.news18.locators.Hindi_Homepage;
import com.news18.locators.Kannada_Homepage;
import com.news18.locators.Marathi_Homepage;
import com.utilities.base.ConfigReader;
import com.utilities.base.GlobalUtil;
import com.utilities.base.KeywordUtil;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Stepdefinitions_Kannada {
	List<String> mobileWebList = new ArrayList<String>();
	List<String> list = new ArrayList<String>();
	String sectionName;
	@When("^Gets all top stories list in Kannada language$")
	public void gets_all_top_stories_list_in_Kannada_language() throws Throwable {

		KeywordUtil.waitForPageToLoad();
		KeywordUtil.delay(2000);
		KeywordUtil.scroll();
		List<WebElement> topNewsList = KeywordUtil.getListElements(Kannada_Homepage.topStories);
		for(int i=2;i<topNewsList.size();i++) {
			KeywordUtil.delay(2500);
			String title = KeywordUtil.getAttribute("title",By.xpath("//div[@id='homePAgeSlider']//li["+i+"]//img"));
			mobileWebList.add(title);
		}
		List<WebElement> bottomSlider = KeywordUtil.getListElements(By.xpath("//ul[@id='bottomSlider']/li//img"));
		for(int j=1; j<=bottomSlider.size();j++) {
			String title = KeywordUtil.getAttribute("title",By.xpath("(//ul[@id='bottomSlider']/li//img)["+j+"]"));
			mobileWebList.add(title);
		}
		for(String str:mobileWebList) {
			System.out.println(str + "\n");
		}
		System.out.println("---------------------------------------------------");
		//GlobalUtil.getDriver().close();

	}

	@When("^User hits News(\\d+) mobile app Kannada language api$")
	public void user_hits_News_mobile_app_Kannada_language_api(int arg1) throws Throwable {

		Response response;
		sectionName = "Top Stories";
		RestAssured.baseURI = ConfigReader.getValue("News18_EndPoint");
		response = RestAssured.given().when().get("kn/get/KANNADAN18:kn_v1_app_homefeed/android/1/92/");
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
	@Then("^Compare the stories of Mobile Web and Mobile App in Kannada language$")
	public void compare_the_stories_of_Mobile_Web_and_Mobile_App_in_Kannada_language() throws Throwable {
		
		ApplicationFuncs.writeDataIntoExcel(mobileWebList, list, Stepdefinitions_Hindi.languageName,sectionName);
	}

	@When("^Gets all \"([^\"]*)\" list in Kannada language$")
	public void gets_all_list_in_Kannada_language(String section) throws Throwable {

		sectionName=section;
		KeywordUtil.waitForPageToLoad();
		KeywordUtil.scroll();
		KeywordUtil.delay(2000);
		KeywordUtil.scroll();
		String id = null;
		if(section.equalsIgnoreCase("ರಾಜ್ಯ"))
			id= "state";
		else if(section.equalsIgnoreCase("ವಿಡಿಯೋ"))
			id= "videos";
		else if(section.equalsIgnoreCase("ಕ್ರೀಡೆ"))
			id= "sports";
		else if(section.equalsIgnoreCase("ಸಿನಿಮಾ"))
			id= "entertainment";
		else if(section.equalsIgnoreCase("ಫೋಟೋ"))
			id= "photogallery";
		else if(section.equalsIgnoreCase("ಲೈಫ್ ಸ್ಟೈಲ್"))
			id= "lifestyle";
		else if(section.equalsIgnoreCase("ದೇಶ-ವಿದೇಶ"))
			id= "national-international";
		else if(section.equalsIgnoreCase("ಮೊಬೈಲ್- ಟೆಕ್"))
			id= "tech";
		
		KeywordUtil.delay(5000);
		KeywordUtil.scroll();
		KeywordUtil.scroll();
		KeywordUtil.waitForPageToLoad();
		KeywordUtil.waitForVisible(By.xpath("//ul[@id='swpstory_"+id+"']/li//img"));
		KeywordUtil.scroll();
		KeywordUtil.mouseOver(By.xpath("//ul[@id='swpstory_"+id+"']/li//img"));
		List<WebElement> list = KeywordUtil.getListElements(By.xpath("//ul[@id='swpstory_"+id+"']/li//img"));
		for(int i=1;i<=list.size();i++)
		{
			KeywordUtil.waitForVisible(By.xpath("//ul[@id='swpstory_"+id+"']/li["+i+"]//img"));

			String  title = KeywordUtil.getAttribute("title", By.xpath("//ul[@id='swpstory_"+id+"']/li["+i+"]//img"));
			boolean status = KeywordUtil.isWebElementVisible(By.xpath("//ul[@id='swpstory_"+id+"']/li["+i+"]//img"));
			if (status) {
				mobileWebList.add(title);
			}
			//			}
		}

		for (String str : mobileWebList) { 
			System.out.print(str + "\n"); 
		} 
		System.out.println("---------------------------------------------------");
		//GlobalUtil.getDriver().close();


	}

	@When("^User hits News(\\d+) mobile app Kannada language api for \"([^\"]*)\" section$")
	public void user_hits_News_mobile_app_Kannada_language_api_for_section(int arg1, String section) throws Throwable {


		RestAssured.baseURI = ConfigReader.getValue("News18_EndPoint");
		sectionName = section;
		int pageNumber=1;
		if(section.equalsIgnoreCase("ರಾಜ್ಯ"))
			pageNumber=2;
		else if(section.equalsIgnoreCase("ವಿಡಿಯೋ")||section.equalsIgnoreCase("ಕ್ರೀಡೆ"))
			pageNumber=3;
		else if(section.equalsIgnoreCase("ಸಿನಿಮಾ")||section.equalsIgnoreCase("ಫೋಟೋ"))
			pageNumber=4;
		else if(section.equalsIgnoreCase("ಲೈಫ್ ಸ್ಟೈಲ್")||section.equalsIgnoreCase("ದೇಶ-ವಿದೇಶ"))
			pageNumber=5;
		else if(section.equalsIgnoreCase("ಮೊಬೈಲ್- ಟೆಕ್"))
			pageNumber=6;
		KeywordUtil.delay(2000);

		Response response;
		response = RestAssured.given().when().get("kn/get/KANNADAN18:kn_v1_app_homefeed/android/"+pageNumber+"/86/");
		JsonPath path = response.jsonPath();
		List<String> l1=path.get("node");
		for (int i = 0; i < l1.size(); i++) {
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


	@When("^Gets all sections list in Kannada language$")
	public void gets_all_sections_list_in_Kannada_language() throws Throwable {
		
		KeywordUtil.delay(5000);
		KeywordUtil.waitForPageToLoad();
		KeywordUtil.scroll();
		KeywordUtil.delay(2000);
		KeywordUtil.waitForVisible(Kannada_Homepage.sectionNames);
		List<WebElement> count = KeywordUtil.getListElements(Kannada_Homepage.sectionNames);

		for(int i=1;i<=count.size();i++)
		{
			KeywordUtil.mouseOver(By.xpath("(//section[contains(@class,'bgclr')]//h2[contains(@class,'hd')]/a)["+i+"]"));
			String title = KeywordUtil.getElementText(By.xpath("(//section[contains(@class,'bgclr')]//h2[contains(@class,'hd')]/a)["+i+"]")).toLowerCase();
			Boolean status = KeywordUtil.isWebElementVisible(By.xpath("(//section[contains(@class,'bgclr')]//h2[contains(@class,'hd')]/a)["+i+"]"));
			if (status) {
				if(title.equalsIgnoreCase("ವೀಡಿಯೊಗಳು"))
					title = "ವಿಡಿಯೋ";
				else if(title.equalsIgnoreCase("ಫೋಟೊ ಗ್ಯಾಲರಿ"))
					title = "ಫೋಟೋ";
				mobileWebList.add(title);
			}
		}
		for (String str : mobileWebList) { 
			System.out.print(str + "\n"); 
		} 
		//GlobalUtil.getDriver().close();
	}

	ArrayList<String> mobileappList=new ArrayList<String>();
	@When("^User hits News(\\d+) mobile app Kannada api for getting sections$")
	public void user_hits_News_mobile_app_Kannada_api_for_getting_sections(int arg1) throws Throwable {
		Response response;
		String storysection;
		RestAssured.baseURI = ConfigReader.getValue("News18_EndPoint");
		for(int i=2;i<=6;i++)
		{
			response = RestAssured.given().when().get("kn/get/KANNADAN18:kn_v1_app_homefeed/android/"+i+"/86/");
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

	@Then("^Compare the sections in Mobile Web and Mobile App for Kannada language$")
	public void compare_the_sections_in_Mobile_Web_and_Mobile_App_for_Kannada_language() throws Throwable {
		
		Collection<String> coll = new HashSet<>(mobileWebList);
		for(int i=0;i<mobileWebList.size();i++) {
			if(mobileWebList.get(i).equals("ಟೆಕ್")) {
				mobileWebList.set(i, "ಮೊಬೈಲ್- ಟೆಕ್");
			}
			if(mobileWebList.get(i).equals("ಮನರಂಜನೆ")) {
				mobileWebList.set(i, "ಸಿನಿಮಾ");
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
			row.createCell(1).setCellValue("ಟೆಕ್(Web):ಮೊಬೈಲ್- ಟೆಕ್(App)   and  ಮನರಂಜನೆ (Web):ಸಿನಿಮಾ(App)");
		}


		FileOutputStream fileOut = new FileOutputStream(ApplicationFuncs.FILE_NAME); 
		ApplicationFuncs.wb.write(fileOut); 
		fileOut.close(); 
		mobileappList.removeAll(mobileappList);
		mobileWebList.removeAll(mobileWebList);
		KeywordUtil.delay(3000);
		if(different.size()!=0)
		{
			Reporter.addStepLog(" <font color='red'> Sections which are not in Mobile App are <font color='magenta'> "+different+" </font> </font>");
			Assert.fail("Mobile Web has more sections");
		}	   
	}
}
