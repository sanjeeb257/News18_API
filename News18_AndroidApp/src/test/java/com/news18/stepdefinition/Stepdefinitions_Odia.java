package com.news18.stepdefinition;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.api.base.ApplicationFuncs;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.news18.locators.Odia_Homepage;
import com.utilities.base.ConfigReader;
import com.utilities.base.GlobalUtil;
import com.utilities.base.KeywordUtil;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Stepdefinitions_Odia {
	List<String> list = new ArrayList<String>();
	List<String> mobileWebList = new ArrayList<String>();
	String sectionName;
	@When("^Gets all top stories list in Odia language$")
	public void gets_all_top_stories_list_in_Odia_language() throws Throwable {
		KeywordUtil.waitForVisible(Odia_Homepage.topStory);
		String title = KeywordUtil.getElementText(Odia_Homepage.topStory);
		mobileWebList.add(title);
		KeywordUtil.scroll();
		List<WebElement> list =  KeywordUtil.getListElements(Odia_Homepage.verticleStoryList);
		for(int i=1;i<=list.size();i++) {

			KeywordUtil.waitForVisible(By.xpath("(//a[text()='ଚ଼ର୍ଚ୍ଚିତ ଖବର']/ancestor::div[contains(@class,'globalhd')]/following-sibling::*//a)["+i+"]"));
			title = KeywordUtil.getAttribute("title", By.xpath("(//a[text()='ଚ଼ର୍ଚ୍ଚିତ ଖବର']/ancestor::div[contains(@class,'globalhd')]/following-sibling::*//a)["+i+"]"));
			boolean status = KeywordUtil.isWebElementVisible(By.xpath("(//a[text()='ଚ଼ର୍ଚ୍ଚିତ ଖବର']/ancestor::div[contains(@class,'globalhd')]/following-sibling::*//a)["+i+"]"));
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

	@When("^User hits News(\\d+) mobile app Odia language api$")
	public void user_hits_News_mobile_app_Odia_language_api(int arg1) throws Throwable {

		Response response;
		sectionName = "Top Stories";
		RestAssured.baseURI = ConfigReader.getValue("News18_EndPoint");
		response = RestAssured.given().when().get("od/get/ODIA-N18-od_V1_APP_homefeed/android/1/92/");
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

							if(list.size()>=4)
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
	@Then("^Compare the stories of Mobile Web and Mobile App in Odia language$")
	public void compare_the_stories_of_Mobile_Web_and_Mobile_App_in_Odia_language() throws Throwable {

		ApplicationFuncs.writeDataIntoExcel(mobileWebList, list, Stepdefinitions_Hindi.languageName,sectionName);

	}
	@When("^Gets all \"([^\"]*)\" list in Odia language$")
	public void gets_all_list_in_Odia_language(String section) throws Throwable {
		sectionName=section;
		KeywordUtil.delay(5000);
		KeywordUtil.scroll();
		KeywordUtil.scroll();
		KeywordUtil.waitForPageToLoad();
		KeywordUtil.scrollingToElementofAPage(By.id("ob_holder"));
		List<WebElement> list =KeywordUtil.getListElements(By.xpath("//a[text()='"+section+"']/ancestor::div[contains(@class,'globalhd')]/following-sibling::*//a"));
		if(section.equalsIgnoreCase("ଫଟୋ ଗ୍ୟାଲେରୀ") || section.equalsIgnoreCase("ଭିଡିଓ")) {
			for(int i=1; i<=list.size();i++) {
				KeywordUtil.waitForVisible(By.xpath("(//a[text()='"+section+"']/ancestor::div[contains(@class,'globalhd')]/following-sibling::*//a)["+i+"]"));
				String title = KeywordUtil.getAttribute("title", By.xpath("(//a[text()='"+section+"']/ancestor::div[contains(@class,'globalhd')]/following-sibling::*//a)["+i+"]"));
				boolean status = KeywordUtil.isWebElementVisible(By.xpath("(//a[text()='"+section+"']/ancestor::div[contains(@class,'globalhd')]/following-sibling::*//a)["+i+"]"));
				if(status) {
					mobileWebList.add(title);
				}
			}
		}else {
			for(int i=2; i<=list.size();i++) {
				KeywordUtil.waitForVisible(By.xpath("(//a[text()='"+section+"']/ancestor::div[contains(@class,'globalhd')]/following-sibling::*//a)["+i+"]"));
				String title = KeywordUtil.getAttribute("title", By.xpath("(//a[text()='"+section+"']/ancestor::div[contains(@class,'globalhd')]/following-sibling::*//a)["+i+"]"));
				boolean status = KeywordUtil.isWebElementVisible(By.xpath("(//a[text()='"+section+"']/ancestor::div[contains(@class,'globalhd')]/following-sibling::*//a)["+i+"]"));
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
	@When("^User hits News(\\d+) mobile app Odia language api for \"([^\"]*)\" section$")
	public void user_hits_News_mobile_app_Odia_language_api_for_section(int arg1, String section) throws Throwable {


		RestAssured.baseURI = ConfigReader.getValue("News18_EndPoint");
		int pageNumber=1;

		if(section.equalsIgnoreCase("ନ୍ୟୁଜ୍") || section.equalsIgnoreCase("ଟ୍ରେଣ୍ଡିଙ୍ଗ୍‌"))
		{
			pageNumber =2;
		}
		else if(section.equalsIgnoreCase("ଫଟୋ ଗ୍ୟାଲେରୀ") || section.equalsIgnoreCase("ଭିଡିଓ"))
		{
			pageNumber =3;
		}

		KeywordUtil.delay(2000);
		Response response = RestAssured.given().when().get("od/get/ODIA-N18-od_V1_APP_homefeed/android/"+pageNumber+"/85/");
		JsonPath path = response.jsonPath();
		List<String> l1 = path.get("node");
		if(section.equalsIgnoreCase("ଫଟୋ ଗ୍ୟାଲେରୀ")||section.equalsIgnoreCase("ଭିଡିଓ")) {
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
								if(list.size()>=3)
									break;
								else
									list.add(path.get("node[" + i + "].data[" + j + "].data_node[" + k + "].headline").toString());
							}
						}
					}
				}
			}
		}else {
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
								if(list.size()>=4)
									break;
								else
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
	@When("^Gets all sections list in Odia language$")
	public void gets_all_sections_list_in_Odia_language() throws Throwable {
		KeywordUtil.delay(5000);
		KeywordUtil.waitForPageToLoad();
		KeywordUtil.scroll();
		KeywordUtil.delay(2000);
		KeywordUtil.waitForVisible(Odia_Homepage.sectionNames);
		List<WebElement> count = KeywordUtil.getListElements(Odia_Homepage.sectionNames);

		for(int i=1;i<=count.size();i++)
		{
			KeywordUtil.mouseOver(By.xpath("(//div[contains(@class,'globalhd')]//h2/a)["+i+"]"));
			String title = KeywordUtil.getElementText(By.xpath("(//div[contains(@class,'globalhd')]//h2/a)["+i+"]")).toLowerCase();
			Boolean status = KeywordUtil.isWebElementVisible(By.xpath("(//div[contains(@class,'globalhd')]//h2/a)["+i+"]"));
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
	@When("^User hits News(\\d+) mobile app Odia api for getting sections$")
	public void user_hits_News_mobile_app_Odia_api_for_getting_sections(int arg1) throws Throwable {
		Response response;
		String storysection;
		RestAssured.baseURI = ConfigReader.getValue("News18_EndPoint");
		for(int i=2;i<4;i++)
		{
			response = RestAssured.given().when().get("od/get/ODIA-N18-od_V1_APP_homefeed/android/"+i+"/85/");
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

	@Then("^Compare the sections in Mobile Web and Mobile App for Odia language$")
	public void compare_the_sections_in_Mobile_Web_and_Mobile_App_for_Odia_language() throws Throwable {

		ApplicationFuncs.compareAllTheSectionsWithoutMap(mobileWebList, mobileappList, Stepdefinitions_Hindi.languageName);

	}
}
