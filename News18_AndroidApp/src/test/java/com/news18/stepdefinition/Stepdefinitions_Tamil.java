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
import com.news18.locators.Kannada_Homepage;
import com.utilities.base.ConfigReader;
import com.utilities.base.GlobalUtil;
import com.utilities.base.KeywordUtil;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Stepdefinitions_Tamil {

	List<String> list = new ArrayList<String>();
	List<String> mobileWebList = new ArrayList<String>();
	String sectionName;

	@When("^Gets all top stories list in Tamil language$")
	public void gets_all_top_stories_list_in_Tamil_language() throws Throwable {
		KeywordUtil.waitForPageToLoad();
		List<WebElement> topNewsList = KeywordUtil.getListElements(Kannada_Homepage.topStories);
		KeywordUtil.scroll();
		for (int i = 2; i < topNewsList.size(); i++) {
			KeywordUtil.delay(2500);
			KeywordUtil.waitForVisible(By.xpath("//div[@id='homePAgeSlider']//li["+i+"]//img"));
			String title = KeywordUtil.getAttribute("title", By.xpath("//div[@id='homePAgeSlider']//li["+i+"]//img"));
			mobileWebList.add(title);
		}
		List<WebElement> bottomSlider = KeywordUtil.getListElements(By.xpath("//ul[@id='bottomSlider']/li//img"));
		for(int j=1; j<=bottomSlider.size();j++) {
			KeywordUtil.waitForInVisibile(By.xpath("//ul[@id='bottomSlider']/li["+j+"]//img"));
			String title = KeywordUtil.getAttribute("title", By.xpath("(//ul[@id='bottomSlider']/li//img)["+j+"]"));
			mobileWebList.add(title);		
		}
		for (String str : mobileWebList) { 
			System.out.print(str + "\n"); 
		} 
		System.out.println("---------------------------------------------------");
		//GlobalUtil.getDriver().close();


	}

	@When("^User hits News(\\d+) mobile app Tamil language api$")
	public void user_hits_News_mobile_app_Tamil_language_api(int arg1) throws Throwable {


		Response response;
		sectionName = "Top Stories";
		RestAssured.baseURI = ConfigReader.getValue("News18_EndPoint");
		response = RestAssured.given().when().get("tm/get/TAMILN18:tm_V1_APP_homefeed/android/1/92/");
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
	@Then("^Compare the stories of Mobile Web and Mobile App in Tamil language$")
	public void compare_the_stories_of_Mobile_Web_and_Mobile_App_in_Tamil_language() throws Throwable {

		ApplicationFuncs.writeDataIntoExcel(mobileWebList, list, Stepdefinitions_Hindi.languageName,sectionName);

	}
	@When("^Gets all \"([^\"]*)\" list in Tamil language$")
	public void gets_all_list_in_Gujarati_language(String section) throws InterruptedException{
		sectionName=section;
		KeywordUtil.waitForPageToLoad();
		KeywordUtil.scroll();
		KeywordUtil.delay(2000);
		KeywordUtil.scroll();
		String id = null;
		if(section.equalsIgnoreCase("நியூஸ்18 ஸ்பெஷல்"))
			id= "topkhabrein";
		else if(section.equalsIgnoreCase("தமிழ்நாடு"))
			id= "nation";
		else if(section.equalsIgnoreCase("குற்றம்"))
			id= "tag_topic";
		else if(section.equalsIgnoreCase("இந்தியா"))
			id= "national";
		else if(section.equalsIgnoreCase("உலகம்"))
			id= "international";
		else if(section.equalsIgnoreCase("வணிகம்"))
			id= "business";
		else if(section.equalsIgnoreCase("பொழுதுபோக்கு"))
			id= "entertainment";
		else if(section.equalsIgnoreCase("விளையாட்டு"))
			id= "sports";
		else if(section.equalsIgnoreCase("லைஃப்ஸ்டைல்"))
			id= "lifestyle";
		else if(section.equalsIgnoreCase("ஆட்டோமொபைல்ஸ்"))
			id= "automobile";
		else if(section.equalsIgnoreCase("தொழில்நுட்பம்"))
			id= "technology";
		else if(section.equalsIgnoreCase("கல்வி"))
			id= "education";
		else if(section.equalsIgnoreCase("வேலைவாய்ப்பு"))
			id= "employment";
		else if(section.equalsIgnoreCase("சிறப்புக் கட்டுரைகள்"))
			id= "special-articles";
		else if(section.equalsIgnoreCase("புகைப்படங்கள்"))
			id= "photogallery";
		else if(section.equalsIgnoreCase("காணொளி"))
			id= "videos";

		KeywordUtil.delay(5000);
		KeywordUtil.scroll();
		KeywordUtil.scroll();
		KeywordUtil.waitForPageToLoad();
		KeywordUtil.scrollingToElementofAPage(By.id("ob_holder"));
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

	@When("^User hits News(\\d+) mobile app Tamil language api for \"([^\"]*)\" section$")
	public void user_hits_News_mobile_app_Gujarati_language_api_for_section(int arg1, String section){

		sectionName=section;
		RestAssured.baseURI = ConfigReader.getValue("News18_EndPoint");
		sectionName = section;
		int pageNumber=1;
		if(section.equalsIgnoreCase("நியூஸ்18 ஸ்பெஷல்"))
			pageNumber= 2;
		else if(section.equalsIgnoreCase("தமிழ்நாடு"))
			pageNumber= 3;
		else if(section.equalsIgnoreCase("குற்றம்"))
			pageNumber= 4;
		else if(section.equalsIgnoreCase("இந்தியா"))
			pageNumber= 5;
		else if(section.equalsIgnoreCase("உலகம்"))
			pageNumber= 6;
		else if(section.equalsIgnoreCase("வணிகம்"))
			pageNumber= 7;
		else if(section.equalsIgnoreCase("பொழுதுபோக்கு"))
			pageNumber= 8;
		else if(section.equalsIgnoreCase("விளையாட்டு"))
			pageNumber= 9;
		else if(section.equalsIgnoreCase("லைஃப்ஸ்டைல்"))
			pageNumber= 10;
		else if(section.equalsIgnoreCase("ஆட்டோமொபைல்ஸ்"))
			pageNumber= 11;
		else if(section.equalsIgnoreCase("தொழில்நுட்பம்"))
			pageNumber= 12;
		else if(section.equalsIgnoreCase("கல்வி"))
			pageNumber= 13;
		else if(section.equalsIgnoreCase("வேலைவாய்ப்பு"))
			pageNumber= 14;
		else if(section.equalsIgnoreCase("சிறப்புக் கட்டுரைகள்"))
			pageNumber= 15;
		else if(section.equalsIgnoreCase("புகைப்படங்கள்"))
			pageNumber= 16;
		else if(section.equalsIgnoreCase("காணொளி"))
			pageNumber= 17;
		KeywordUtil.delay(2000);
		Response response = RestAssured.given().when().get("tm/get/TAMILN18:tm_V1_APP_homefeed/android/"+pageNumber+"/85/");
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
	@When("^Gets all sections list in Tamil language$")
	public void gets_all_sections_list_in_Tamil_language() throws Throwable {

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
				mobileWebList.add(title);
			}
		}
		for (String str : mobileWebList) { 
			System.out.print(str + "\n"); 
		} 
		//GlobalUtil.getDriver().close();

	}

	ArrayList<String> mobileappList=new ArrayList<String>();
	@When("^User hits News(\\d+) mobile app Tamil api for getting sections$")
	public void user_hits_News_mobile_app_Tamil_api_for_getting_sections(int arg1) throws Throwable {

		Response response;
		String storysection;
		RestAssured.baseURI = ConfigReader.getValue("News18_EndPoint");
		for(int i=2;i<=17;i++)
		{
			response = RestAssured.given().when().get("tm/get/TAMILN18:tm_V1_APP_homefeed/android/"+i+"/85/");
			JsonPath path = response.jsonPath();
			List<String> l1=path.get("node");
			for (int j = 0; j < l1.size(); j++) {
				storysection = path.get("node[" + j + "].data[0].headline").toString().toLowerCase();
				if(!storysection.equals(""))
					mobileappList.add(storysection);
			}
		}
		//		}
		for (String str : mobileappList) { 
			System.out.print(str + "\n"); 
		} 

	}

	@Then("^Compare the sections in Mobile Web and Mobile App for Tamil language$")
	public void compare_the_sections_in_Mobile_Web_and_Mobile_App_for_Tamil_language() throws Throwable {

		Collection<String> coll = new HashSet<>(mobileWebList);
		for(int i=0;i<mobileWebList.size();i++) {
			if(mobileWebList.get(i).equals("குற்ற நிகழ்வுகள்")) {
				mobileWebList.set(i, "குற்றம்");
			}
			else if(mobileWebList.get(i).equals("புகைப்படம்")) {
				mobileWebList.set(i, "புகைப்படங்கள்");
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
			row.createCell(1).setCellValue("குற்ற நிகழ்வுகள்(Web)  : குற்றம்(App) and புகைப்படம்(Web)  : புகைப்படங்கள்(App)");
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
