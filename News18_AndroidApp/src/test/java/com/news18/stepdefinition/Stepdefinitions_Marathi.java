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
import com.news18.locators.Hindi_Homepage;
import com.news18.locators.Marathi_Homepage;
import com.utilities.base.ConfigReader;
import com.utilities.base.GlobalUtil;
import com.utilities.base.KeywordUtil;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Stepdefinitions_Marathi extends ApplicationFuncs{
	List<String> mobileWebList = new ArrayList<String>();
	List<String> list = new ArrayList<String>();
	String sectionName;

	@When("^Gets all top stories list in Marathi language$")
	public void gets_all_top_stories_list_in_Marathi_language() throws Throwable {
		KeywordUtil.waitForPageToLoad();
		KeywordUtil.scroll();
		KeywordUtil.delay(2000);
		KeywordUtil.scroll();
		KeywordUtil.waitForVisible(Marathi_Homepage.mainTitle);
		String title = KeywordUtil.getElementText(Marathi_Homepage.mainTitle);
		mobileWebList.add(title);
		KeywordUtil.scroll();
		List<WebElement> topNewsList = KeywordUtil.getListElements(Marathi_Homepage.topNewsList);
		for(int i=1;i<=topNewsList.size()+1;i++) {
			if(i!=3) {
				KeywordUtil.waitForVisible(By.xpath("//ul[@id='bottomSlider']/li["+i+"]//h2/a"));
				title = KeywordUtil.getElementText(By.xpath("//ul[@id='bottomSlider']/li["+i+"]//h2/a"));
				boolean status = KeywordUtil.isWebElementVisible(By.xpath("//ul[@id='bottomSlider']/li["+i+"]//h2/a"));
				if(status) {
					mobileWebList.add(title);
				}
			}
		}
		for(String str:mobileWebList) {
			System.out.println(str + "\n");
		}
		System.out.println("---------------------------------------------------");
		//GlobalUtil.getDriver().close();

	}

	@When("^User hits News(\\d+) mobile app Marathi language api$")
	public void user_hits_News_mobile_app_Marathi_language_api(int arg1) throws Throwable {

		Response response;
		sectionName = "Top Stories";
		RestAssured.baseURI = ConfigReader.getValue("News18_EndPoint");
		response = RestAssured.given().when().get("lk/get/LOKMAT:lk_v1_app_homefeed/android/1/92/");
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
						if(childSection.equalsIgnoreCase("top_stories") || childSection.equalsIgnoreCase("medium_horizontal_stories"))
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
	@Then("^Compare the stories of Mobile Web and Mobile App in Marathi language$")
	public void compare_the_stories_of_Mobile_Web_and_Mobile_App_in_Marathi_language() throws Throwable {

		ApplicationFuncs.writeDataIntoExcel(mobileWebList, list, Stepdefinitions_Hindi.languageName,sectionName);

	}
	@When("^Gets all \"([^\"]*)\" list in Marathi language$")
	public void gets_all_list_in_Marathi_language(String section) throws Throwable {
		sectionName=section;
		KeywordUtil.waitForPageToLoad();
		KeywordUtil.scroll();
		KeywordUtil.delay(2000);
		KeywordUtil.scroll();
		String id = null;
		if(section.equalsIgnoreCase("मनोरंजन"))
			id= "entertainment";
		else if(section.equalsIgnoreCase("महाराष्ट्र"))
			id="maharashtra";
		else if(section.equalsIgnoreCase("स्पोर्टस"))
			id= "sport";
		else if(section.equalsIgnoreCase("मनी"))
			id= "money";
		else if(section.equalsIgnoreCase("टेक्नोलाॅजी"))
			id= "technology";
		else if(section.equalsIgnoreCase("लाईफस्टाईल"))
			id= "lifestyle";
		else if(section.equalsIgnoreCase("व्हिडिओ"))
			id= "videos";
		else if(section.equalsIgnoreCase("फोटो"))
			id= "photogallery";
		KeywordUtil.delay(5000);
		KeywordUtil.scroll();
		KeywordUtil.scroll();
		KeywordUtil.waitForPageToLoad();
		KeywordUtil.waitForVisible(By.xpath("//div[@id='clkbutton"+id+"']//a"));
		KeywordUtil.scroll();
		KeywordUtil.mouseOver(By.xpath("//div[@id='clkbutton"+id+"']//a"));
		List<WebElement> videos = KeywordUtil.getListElements(By.xpath("//div[@id='clkbutton"+id+"']//a"));
		if(section.equalsIgnoreCase("व्हिडिओ")||section.equalsIgnoreCase("फोटो")) {
			for(int i=1;i<=videos.size();i++)
			{
				KeywordUtil.waitForVisible(By.xpath("(//div[@id='clkbutton"+id+"']//a)["+i+"]"));

				String  title = KeywordUtil.getText(By.xpath("(//div[@id='clkbutton"+id+"']//a)["+i+"]"));
				boolean status = KeywordUtil.isWebElementVisible(By.xpath("(//div[@id='clkbutton"+id+"']//a)["+i+"]"));
				if (status) {
					mobileWebList.add(title);
				}
			}
		}
		else {
			for(int i=1;i<=videos.size()-2;i++)
			{
				KeywordUtil.waitForVisible(By.xpath("(//div[@id='clkbutton"+id+"']//a)["+(i+1)+"]"));

				String  title = KeywordUtil.getText(By.xpath("(//div[@id='clkbutton"+id+"']//a)["+(i+1)+"]"));
				boolean status = KeywordUtil.isWebElementVisible(By.xpath("(//div[@id='clkbutton"+id+"']//a)["+(i+1)+"]"));
				if (status) {
					mobileWebList.add(title);
				}
			}
		}

		for (String str : mobileWebList) { 
			System.out.print(str + "\n"); 
		} 
		System.out.println("---------------------------------------------------");
		//GlobalUtil.getDriver().close();
	}

	@When("^User hits News(\\d+) mobile app Marathi language api for \"([^\"]*)\" section$")
	public void user_hits_News_mobile_app_Marathi_language_api_for_section(int arg1, String section) throws Throwable {

		RestAssured.baseURI = ConfigReader.getValue("News18_EndPoint");
		sectionName = section;
		int pageNumber=1;
		if(section.equalsIgnoreCase("मनोरंजन"))
			pageNumber=4;
		else if(section.equalsIgnoreCase("महाराष्ट्र"))
			pageNumber=5;
		else if(section.equalsIgnoreCase("स्पोर्टस"))
			pageNumber=6;
		else if(section.equalsIgnoreCase("मनी"))
			pageNumber=7;
		else if(section.equalsIgnoreCase("टेक्नोलाॅजी"))
			pageNumber=8;
		else if(section.equalsIgnoreCase("लाईफस्टाईल"))
			pageNumber=9;
		else if(section.equalsIgnoreCase("व्हिडिओ"))
			pageNumber=10;
		else if(section.equalsIgnoreCase("फोटो"))
			pageNumber=11;
		KeywordUtil.delay(2000);

		Response response;
		response = RestAssured.given().when().get("lk/get/LOKMAT:lk_v1_app_homefeed/android/"+pageNumber+"/86/");
		JsonPath path = response.jsonPath();
		List<String> l1=path.get("node");

		if(section.equals("व्हिडिओ")||section.equals("फोटो"))
		{
			for (int i = 0; i < l1.size(); i++) {
				String storysection = path.get("node[" + i + "].storysection").toString();
				if (storysection.equals(section)) {
					List<String> l2 = path.get("node[" + i + "].data");
					for (int j = 0; j < l2.size(); j++) {
						List<String> l3 = path.get("node[" + i + "].data[" + j + "].data_node");
						for (int k = 0; k < l3.size(); k++) {
							String childSection = path.get("node[" + i + "].data[" + j + "].data_node.child_layout_type["+k+"]").toString();
							if(childSection.equalsIgnoreCase("list_top_stories") || childSection.equalsIgnoreCase("medium_horizontal_stories") || childSection.equalsIgnoreCase("vertical_list_stories"))
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
							if(childSection.equalsIgnoreCase("list_top_stories") || childSection.equalsIgnoreCase("medium_horizontal_stories") || childSection.equalsIgnoreCase("vertical_list_stories")||childSection.equalsIgnoreCase("sports_top_stories"))
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

	@When("^Gets all sections list in Marathi language$")
	public void gets_all_sections_list_in_Marathi_language() throws Throwable {
		KeywordUtil.delay(5000);
		KeywordUtil.waitForPageToLoad();
		KeywordUtil.scroll();
		KeywordUtil.scrollingToElementofAPage(By.id("ob_holder"));
		KeywordUtil.delay(2000);
		KeywordUtil.waitForVisible(Hindi_Homepage.sections);
		List<WebElement> count = KeywordUtil.getListElements(Hindi_Homepage.sections);

		for(int i=1;i<=count.size();i++)
		{
			KeywordUtil.mouseOver(By.xpath("(//div[contains(@class,'globalhd') or @class='livetv-hdr']/h2)["+i+"]"));
			String title = KeywordUtil.getElementText(By.xpath("(//div[contains(@class,'globalhd') or @class='livetv-hdr']/h2)["+i+"]")).toLowerCase();
			Boolean status = KeywordUtil.isWebElementVisible(By.xpath("(//div[contains(@class,'globalhd') or @class='livetv-hdr']/h2)["+i+"]"));
			if (status) {
				if(title.equalsIgnoreCase("स्पोर्ट्स"))
					title = "स्पोर्टस";
				else if(title.equalsIgnoreCase("लाइफस्टाइल"))
					title = "लाईफस्टाईल";
				else if(title.equalsIgnoreCase("व्हिडीओ"))
					title = "व्हिडिओ";
				else if(title.equalsIgnoreCase("फोटो गॅलरी"))
					title = "फोटो";
				mobileWebList.add(title);
			}
		}
		String title = KeywordUtil.getElementText(Marathi_Homepage.topStories);
		Boolean status = KeywordUtil.isWebElementVisible(Marathi_Homepage.topStories);
		if (status) 
			mobileWebList.add(title);
		for (String str : mobileWebList) { 
			System.out.print(str + "\n"); 
		} 
		//GlobalUtil.getDriver().close();
	}

	ArrayList<String> mobileappList=new ArrayList<String>();
	@When("^User hits News(\\d+) mobile app Marathi api for getting sections$")
	public void user_hits_News_mobile_app_Marathi_api_for_getting_sections(int arg1) throws Throwable {

		Response response;
		String storysection;
		RestAssured.baseURI = ConfigReader.getValue("News18_EndPoint");
		for(int i=2;i<=11;i++)
		{
			response = RestAssured.given().when().get("lk/get/LOKMAT:lk_v1_app_homefeed/android/"+i+"/86/");
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

	@Then("^Compare the sections in Mobile Web and Mobile App for Marathi language$")
	public void compare_the_sections_in_Mobile_Web_and_Mobile_App_for_Marathi_language() throws Throwable {
		
		ApplicationFuncs.compareAllTheSectionsWithoutMap(mobileWebList, mobileappList, Stepdefinitions_Hindi.languageName);

	}
}
