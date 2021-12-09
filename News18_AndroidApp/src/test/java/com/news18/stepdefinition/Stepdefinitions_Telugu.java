package com.news18.stepdefinition;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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

import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Stepdefinitions_Telugu {

	List<String> list = new ArrayList<String>();
	List<String> mobileWebList = new ArrayList<String>();
	String sectionName;

	@When("^Gets all top stories list in Telugu language$")
	public void gets_all_top_stories_list_in_Telugu_language() throws Throwable {
		KeywordUtil.waitForPageToLoad();
		List<WebElement> topNewsList = KeywordUtil.getListElements(Kannada_Homepage.topStories);
		KeywordUtil.scroll();
		for (int i = 2; i < topNewsList.size(); i++) {
			KeywordUtil.delay(2500);
			String title = KeywordUtil.getAttribute("title",
					By.xpath("//div[@id='homePAgeSlider']//li[" + i + "]//img"));
			mobileWebList.add(title);
		}
		List<WebElement> bottomSlider = KeywordUtil.getListElements(By.xpath("//ul[@id='bottomSlider']/li//img"));
		for (int j = 1; j <= bottomSlider.size(); j++) {
			String title = KeywordUtil.getAttribute("title", By.xpath("(//ul[@id='bottomSlider']/li//img)[" + j + "]"));
			mobileWebList.add(title);
		}
		for (String str : mobileWebList) {
			System.out.print(str + "\n");
		}
		System.out.println("---------------------------------------------------");
		//GlobalUtil.getDriver().close();
	}

	@When("^User hits News(\\d+) mobile app Telugu language api$")
	public void user_hits_News_mobile_app_Telugu_language_api(int arg1) throws Throwable {
		Response response;
		sectionName = "Top Stories";
		RestAssured.baseURI = ConfigReader.getValue("News18_EndPoint");
		response = RestAssured.given().when().get("tl/get/TELUGUN18:tl_v1_app_homefeed/android/1/92/");
		JsonPath path = response.jsonPath();
		List<String> storysectionList =path.get("node");
		for (int i = 0; i <storysectionList.size(); i++) {
			String storysection = path.get("node[" + i + "].dimension").toString();
			if (storysection.equalsIgnoreCase("top_stories")) {
				List<String> l2 = path.get("node[" + i + "].data");
				for (int j = 0; j < l2.size(); j++) {
					List<String> l3 = path.get("node[" + i + "].data[" + j + "].data_node");
					for (int k = 0; k < l3.size(); k++) {
						String childSection = path
								.get("node[" + i + "].data[" + j + "].data_node.child_layout_type[" + k + "]")
								.toString();
						if (childSection.equalsIgnoreCase("top_stories")
								|| childSection.equalsIgnoreCase("medium_horizontal_stories")
								|| childSection.equalsIgnoreCase("vertical_list_stories")) {
							list.add(path.get("node[" + i + "].data[" + j + "].data_node[" + k + "].headline")
									.toString());
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

	@Then("^Compare the stories of Mobile Web and Mobile App in Telugu language$")
	public void compare_the_stories_of_Mobile_Web_and_Mobile_App_in_Telugu_language() throws Throwable {
		ApplicationFuncs.writeDataIntoExcel(mobileWebList, list, Stepdefinitions_Hindi.languageName,sectionName);

	}
	
	@When("^Gets all \"([^\"]*)\" list in Telugu language$")
	public void gets_all_list_in_Telugu_language(String section){
		sectionName=section;
		KeywordUtil.waitForPageToLoad();
		KeywordUtil.scroll();
		KeywordUtil.delay(2000);
		KeywordUtil.scroll();
		String id = null;
		if(section.equalsIgnoreCase("అగ్ర కథనాలు"))
			id= "topkhabrein";
		else if(section.equalsIgnoreCase("తెలంగాణ"))
			id= "nation";
		else if(section.equalsIgnoreCase("ఆంధ్రప్రదేశ్"))
			id= "andhra-pradesh";
		else if(section.equalsIgnoreCase("సినిమా"))
			id= "movies";
		else if(section.equalsIgnoreCase("రాజకీయం"))
			id= "politics";
		else if(section.equalsIgnoreCase("క్రైమ్"))
			id= "crime";
		else if(section.equalsIgnoreCase("జాతీయం"))
			id= "national";
		else if(section.equalsIgnoreCase("క్రీడలు"))
			id= "sports";
		else if(section.equalsIgnoreCase("బిజినెస్"))
			id= "business";
		else if(section.equalsIgnoreCase("జాబ్స్"))
			id= "jobs";
		else if(section.equalsIgnoreCase("టెక్నాలజి"))
			id= "technology";
		else if(section.equalsIgnoreCase("లైఫ్ స్టైల్"))
			id= "life-style";
		else if(section.equalsIgnoreCase("అంతర్జాతీయం"))
			id= "international";
		else if(section.equalsIgnoreCase("ఫోటోలు"))
			id= "photogallery";
		else if(section.equalsIgnoreCase("వీడియోలు"))
			id= "videos";

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
	
	@When("^User hits News(\\d+) mobile app Telugu language api for \"([^\"]*)\" section$")
	public void user_hits_News_mobile_app_Telugu_language_api_for_section(int arg1, String section){
		sectionName=section;
		RestAssured.baseURI = ConfigReader.getValue("News18_EndPoint");
		sectionName = section;
		int pageNumber=2;
		if(section.equalsIgnoreCase("అగ్ర కథనాలు"))
			pageNumber=2;
		else if(section.equalsIgnoreCase("తెలంగాణ"))
			pageNumber=3;
		else if(section.equalsIgnoreCase("ఆంధ్రప్రదేశ్"))
			pageNumber=4;
		else if(section.equalsIgnoreCase("సినిమా"))
			pageNumber=5;
		else if(section.equalsIgnoreCase("రాజకీయం"))
			pageNumber=6;
		else if(section.equalsIgnoreCase("క్రైమ్"))
			pageNumber=7;
		else if(section.equalsIgnoreCase("జాతీయం"))
			pageNumber=8;
		else if(section.equalsIgnoreCase("క్రీడలు"))
			pageNumber=9;
		else if(section.equalsIgnoreCase("బిజినెస్"))
			pageNumber=10;
		else if(section.equalsIgnoreCase("జాబ్స్"))
			pageNumber=11;
		else if(section.equalsIgnoreCase("టెక్నాలజి"))
			pageNumber=12;
		else if(section.equalsIgnoreCase("లైఫ్ స్టైల్"))
			pageNumber=13;
		else if(section.equalsIgnoreCase("అంతర్జాతీయం"))
			pageNumber=14;
		else if(section.equalsIgnoreCase("ఫోటోలు"))
			pageNumber=15;
		else if(section.equalsIgnoreCase("వీడియోలు"))
			pageNumber=16;
		KeywordUtil.delay(2000);
		Response response = RestAssured.given().when().get("tl/get/TELUGUN18:tl_v1_app_homefeed/android/"+pageNumber+"/86/");
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
	@When("^Gets all sections list in Telugu language$")
	public void gets_all_sections_list_in_Telugu_language(){
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
	@When("^User hits News(\\d+) mobile app Telugu api for getting sections$")
	public void user_hits_News_mobile_app_Telugu_api_for_getting_sections(int arg1){
		Response response;
		String storysection;
		RestAssured.baseURI = ConfigReader.getValue("News18_EndPoint");
		for(int i=2;i<=16;i++)
		{
			response = RestAssured.given().when().get("tl/get/TELUGUN18:tl_v1_app_homefeed/android/"+i+"/86/");
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
	
	@Then("^Compare the sections in Mobile Web and Mobile App for Telugu language$")
	public void compare_the_sections_in_Mobile_Web_and_Mobile_App_for_Telugu_language() throws IOException{
		Collection<String> coll = new HashSet<>(mobileWebList);
		for(int i=0;i<mobileWebList.size();i++) {
			if(mobileWebList.get(i).equals("జాబ్స్ & ఎడ్యుకేషన్")) {
				mobileWebList.set(i, "జాబ్స్");
			}
			else if(mobileWebList.get(i).equals("ప్రపంచం")) {
				mobileWebList.set(i, "అంతర్జాతీయం");
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
			row.createCell(1).setCellValue("జాబ్స్ & ఎడ్యుకేషన్(Web)  : జాబ్స్(App)  and ప్రపంచం(Web) : అంతర్జాతీయం(App)");
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

