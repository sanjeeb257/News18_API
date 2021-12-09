package com.news18.stepdefinition;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
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
import com.news18.locators.Punjab_Homepage;
import com.utilities.base.ConfigReader;
import com.utilities.base.KeywordUtil;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Stepdefinitions_Punjab {
	List<String> mobileWebList = new ArrayList<String>();
	String sectionName;
	List<String> list = new ArrayList<String>();
	@When("^Gets all top stories list in Punjab language$")
	public void gets_all_top_stories_list_in_Punjab_language() throws Throwable {

		KeywordUtil.waitForVisible(Punjab_Homepage.topStory);
		String title = KeywordUtil.getElementText(Punjab_Homepage.topStory);
		mobileWebList.add(title);
		KeywordUtil.scroll();
		List<WebElement> list =  KeywordUtil.getListElements(Punjab_Homepage.verticleStoryList);
		for(int i=1;i<=list.size();i++) {

			KeywordUtil.waitForVisible(By.xpath("(//a[text()='Latest News']/ancestor::div[contains(@class,'globalhd')]/following-sibling::*//a)["+i+"]"));
			title = KeywordUtil.getAttribute("title", By.xpath("(//a[text()='Latest News']/ancestor::div[contains(@class,'globalhd')]/following-sibling::*//a)["+i+"]"));
			boolean status = KeywordUtil.isWebElementVisible(By.xpath("(//a[text()='Latest News']/ancestor::div[contains(@class,'globalhd')]/following-sibling::*//a)["+i+"]"));
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

	@When("^User hits News(\\d+) mobile app Punjab language api$")
	public void user_hits_News_mobile_app_Punjab_language_api(int arg1) throws Throwable {


		Response response;
		sectionName = "Top Stories";
		RestAssured.baseURI = ConfigReader.getValue("News18_EndPoint");
		response = RestAssured.given().when().get("pn/get/PUNJABIN18:pn_V1_APP_homefeed/android/1/92/");
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
							if(list.size()>=5)
								break;
							else 
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
	@Then("^Compare the stories of Mobile Web and Mobile App in Punjab language$")
	public void compare_the_stories_of_Mobile_Web_and_Mobile_App_in_Punjab_language() throws Throwable {


		ApplicationFuncs.writeDataIntoExcel(mobileWebList, list, Stepdefinitions_Hindi.languageName,sectionName);

	}

	@When("^Gets all \"([^\"]*)\" list in Punjab language$")
	public void gets_all_list_in_Punjab_language(String section) throws Throwable {

		sectionName=section;
		String id = null;
		if(section.equalsIgnoreCase("ਪੰਜਾਬ"))
			id= "Punjab";
		else if(section.equalsIgnoreCase("ਰਾਸ਼ਟਰੀ"))
			id= "National";
		else if(section.equalsIgnoreCase("ਅੰਤਰਰਾਸ਼ਟਰੀ"))
			id= "World";
		else if(section.equalsIgnoreCase("APP_LIFESTYLE"))
			id= "Life";
		else if(section.equalsIgnoreCase("ਮਨੋਰੰਜਨ"))
			id= "Films";
		else if(section.equalsIgnoreCase("ਖੇਡਾਂ"))
			id= "Sports";
		else if(section.equalsIgnoreCase("ਫੋਟੋ"))
			id= "Photos";
		else if(section.equalsIgnoreCase("ਵੀਡੀਓ"))
			id= "Videos";
		KeywordUtil.delay(5000);
		KeywordUtil.scroll();
		KeywordUtil.scroll();
		KeywordUtil.waitForPageToLoad();
		KeywordUtil.scrollingToElementofAPage(By.id("ob_holder"));

		if(section.equalsIgnoreCase("ਫੋਟੋ")||section.equalsIgnoreCase("ਵੀਡੀਓ")) {
			List<WebElement> list =KeywordUtil.getListElements(By.xpath("//a[text()='"+id+"']/ancestor::div[contains(@class,'globalhd')]/following-sibling::*//li//h3"));
			for(int i=1; i<=list.size();i++) {
				KeywordUtil.waitForVisible(By.xpath("//a[text()='"+id+"']/ancestor::div[contains(@class,'globalhd')]/following-sibling::*//li["+i+"]//h3"));
				String title = KeywordUtil.getElementText(By.xpath("//a[text()='"+id+"']/ancestor::div[contains(@class,'globalhd')]/following-sibling::*//li["+i+"]//h3"));
				boolean status = KeywordUtil.isWebElementVisible(By.xpath("//a[text()='"+id+"']/ancestor::div[contains(@class,'globalhd')]/following-sibling::*//li["+i+"]//h3"));
				if(status) {
					mobileWebList.add(title);
				}
			}

		}
		//KeywordUtil.delay(8000);
		else {
			List<WebElement> list =KeywordUtil.getListElements(By.xpath("//a[text()='"+id+"']/ancestor::div[contains(@class,'globalhd')]/following-sibling::*//a"));
			for(int j=2; j<list.size();j++) {
				KeywordUtil.waitForVisible(By.xpath("(//a[text()='"+id+"']/ancestor::div[contains(@class,'globalhd')]/following-sibling::*//a)["+j+"]"));
				String title = KeywordUtil.getElementText(By.xpath("(//a[text()='"+id+"']/ancestor::div[contains(@class,'globalhd')]/following-sibling::*//a)["+j+"]"));
				boolean status = KeywordUtil.isWebElementVisible(By.xpath("(//a[text()='"+id+"']/ancestor::div[contains(@class,'globalhd')]/following-sibling::*//a)["+j+"]"));
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

	@When("^User hits News(\\d+) mobile app Punjab language api for \"([^\"]*)\" section$")
	public void user_hits_News_mobile_app_Punjab_language_api_for_section(int arg1, String section) throws Throwable {

		RestAssured.baseURI = ConfigReader.getValue("News18_EndPoint");
		sectionName = section;
		int pageNumber=1;
		if(section.equalsIgnoreCase("ਪੰਜਾਬ"))
			pageNumber=2;
		else if(section.equalsIgnoreCase("ਰਾਸ਼ਟਰੀ"))
			pageNumber=3;
		else if(section.equalsIgnoreCase("ਅੰਤਰਰਾਸ਼ਟਰੀ")||section.equalsIgnoreCase("APP_LIFESTYLE"))
			pageNumber=4;
		else if(section.equalsIgnoreCase("ਮਨੋਰੰਜਨ")||section.equalsIgnoreCase("ਖੇਡਾਂ"))
			pageNumber=5;
		else if(section.equalsIgnoreCase("ਫੋਟੋ"))
			pageNumber=6;
		else if(section.equalsIgnoreCase("ਵੀਡੀਓ"))
			pageNumber=7;
		KeywordUtil.delay(2000);

		Response response;
		response = RestAssured.given().when().get("pn/get/PUNJABIN18:pn_v1_app_homefeed/android/"+pageNumber+"/86/");
		JsonPath path = response.jsonPath();
		List<String> l1=path.get("node");
		if(section.equalsIgnoreCase("ਫੋਟੋ")||section.equalsIgnoreCase("ਵੀਡੀਓ")) {
			for (int i = 0; i < l1.size(); i++) {
				String storysection = path.get("node[" + i + "].storysection").toString();
				if (storysection.equals(section)) {
					List<String> l2 = path.get("node[" + i + "].data");
					for (int j = 0; j < l2.size(); j++) {
						List<String> l3 = path.get("node[" + i + "].data[" + j + "].data_node");
						for (int k = 0; k < l3.size(); k++) {
							String childSection = path.get("node[" + i + "].data[" + j + "].data_node.child_layout_type["+k+"]").toString();
							if(childSection.equalsIgnoreCase("list_top_stories") || childSection.equalsIgnoreCase("medium_horizontal_stories") || childSection.equalsIgnoreCase("vertical_list_stories")||childSection.equalsIgnoreCase("sports_top_stories")||childSection.equalsIgnoreCase("media_grid_stories"))
							{
								if(list.size()>=3)
									break;
								else
									list.add(path.get("node[" + i + "].data[" + j + "].data_node[" + k + "].headline").toString());
							}
						}
					}
				}
			}
		}
		else {
			for (int i = 0; i < l1.size(); i++) {
				String storysection = path.get("node[" + i + "].storysection").toString();
				if (storysection.equals(section)) {
					List<String> l2 = path.get("node[" + i + "].data");
					for (int j = 0; j < l2.size(); j++) {
						List<String> l3 = path.get("node[" + i + "].data[" + j + "].data_node");
						for (int k = 0; k < l3.size(); k++) {
							String childSection = path.get("node[" + i + "].data[" + j + "].data_node.child_layout_type["+k+"]").toString();
							if(childSection.equalsIgnoreCase("list_top_stories") || childSection.equalsIgnoreCase("medium_horizontal_stories") || childSection.equalsIgnoreCase("vertical_list_stories")||childSection.equalsIgnoreCase("sports_top_stories")||childSection.equalsIgnoreCase("media_grid_stories"))
							{
								list.add(path.get("node[" + i + "].data[" + j + "].data_node[" + k + "].headline").toString());
							}
						}
					}
				}
			}
		}
		for (String str : list) { 
			System.out.print(str + "\n"); 
		}   


	}

	@When("^Gets all sections list in Punjab language$")
	public void gets_all_sections_list_in_Punjab_language() throws Throwable {
		KeywordUtil.waitForPageToLoad();
		KeywordUtil.scroll();
		KeywordUtil.scrollingToElementofAPage(By.id("ob_holder"));
		KeywordUtil.delay(2000);
		KeywordUtil.waitForVisible(Hindi_Homepage.sections);
		List<WebElement> count = KeywordUtil.getListElements(Hindi_Homepage.sections);

		for(int i=3;i<=count.size();i++)
		{
			KeywordUtil.mouseOver(By.xpath("(//div[contains(@class,'globalhd')]/h2)["+i+"]"));
			String title = KeywordUtil.getElementText(By.xpath("(//div[contains(@class,'globalhd')]/h2)["+i+"]")).toLowerCase();
			boolean status = KeywordUtil.isWebElementVisible(By.xpath("(//div[contains(@class,'globalhd')]/h2)["+i+"]"));
			if (status) {
				mobileWebList.add(title);
			}
		}
		for (String str : mobileWebList) { 
			System.out.print(str + "\n"); 
		} 

	}

	ArrayList<String> mobileappList=new ArrayList<String>();
	@When("^User hits News(\\d+) mobile app Punjab api for getting sections$")
	public void user_hits_News_mobile_app_Punjab_api_for_getting_sections(int arg1) throws Throwable {
		Response response;
		String storysection;
		RestAssured.baseURI = ConfigReader.getValue("News18_EndPoint");
		for(int i=2;i<8;i++)
		{
			response = RestAssured.given().when().get("pn/get/PUNJABIN18:pn_v1_app_homefeed/android/"+i+"/86/");
			JsonPath path = response.jsonPath();
			 
			List<String> l1=path.get("node");
			for (int j = 0; j < l1.size(); j++) {
				storysection = path.get("node[" + j + "].storysection").toString();
				if(storysection.equalsIgnoreCase("ਵੀਡੀਓ")||storysection.equalsIgnoreCase("ਫੋਟੋ")) {
					storysection = path.get("node[" + j + "].dimension").toString();
					mobileappList.add(storysection);
				}
				else if(!storysection.equalsIgnoreCase("banner_rect")) {
					String multiDimention = path.get("node["+j+"].data[0].multi_dimension").toString().toLowerCase();
					multiDimention=multiDimention.replace("pn_", "");
					if(!multiDimention.equals(""))
						mobileappList.add(multiDimention);
				}
			}
		}
		for (String str : mobileappList) { 
			System.out.print(str + "\n"); 
		} 
	}

	@Then("^Compare the sections in Mobile Web and Mobile App for Punjab language$")
	public void compare_the_sections_in_Mobile_Web_and_Mobile_App_for_Punjab_language() throws Throwable {

		Collection<String> coll = new HashSet<>(mobileWebList);
		for(int i=0;i<mobileWebList.size();i++) {
			if(mobileWebList.get(i).equals("world")) {
				mobileWebList.set(i, "international");
			}
			else if(mobileWebList.get(i).equals("life")) {
				mobileWebList.set(i, "lifestyle");
			}
			else if(mobileWebList.get(i).equals("films")) {
				mobileWebList.set(i, "entertainment");
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
			row.createCell(1).setCellValue("world(Web)  : international(App)  and life(Web) : lifestyle(App)");
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
