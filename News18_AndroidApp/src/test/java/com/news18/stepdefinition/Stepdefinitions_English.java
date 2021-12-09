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
import com.news18.locators.English_Homepage;
import com.utilities.base.ConfigReader;
import com.utilities.base.DriverUtil;
import com.utilities.base.GlobalUtil;
import com.utilities.base.KeywordUtil;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


public class Stepdefinitions_English {

	List<String> list = new ArrayList<String>();
	List<String> mobileWebList = new ArrayList<String>();
	ArrayList<String> mobileappList=new ArrayList<String>();
	String sectionName;
	@Given("^User Launches the Mobile Web browser$")
	public void User_Launches_the_Mobile_Web_browser() {
		GlobalUtil.setDriver(DriverUtil.getChromeBrowser("CHROME"));
	}
	@When("^Gets all top stories list$")
	public void gets_all_top_stories_list() throws Throwable {

		sectionName = "Top Stories";
		KeywordUtil.waitForVisible(English_Homepage.titleHeader);
		List<WebElement> homepageNewsList = KeywordUtil.getListElements(English_Homepage.homepageNews);

		String title = KeywordUtil.getElementText(English_Homepage.mainTitle);
		mobileWebList.add(title);

		List<String> homeNews = new ArrayList<String>();
		for (int k = 1; k <= homepageNewsList.size(); k++) {

			KeywordUtil.waitForVisible(By.xpath("//div[@class='mstory-thumb-wrap']//div[@class='mstory-row']["+k+"]/div[@class='text']//p"));
			title = KeywordUtil.getElementText(By.xpath("//div[@class='mstory-thumb-wrap']//div[@class='mstory-row']["+k+"]/div[@class='text']//p"));
			boolean status = KeywordUtil.isWebElementVisible(By.xpath("//div[@class='mstory-thumb-wrap']//div[@class='mstory-row']["+k+"]/div[@class='text']//p"));
			if (status) {
				homeNews.add(title);
			}
		}
		List<String> homeList = new ArrayList<String>();
		for (int j = 1; j<=3; j++) {

			KeywordUtil.waitForVisible(By.xpath("//div[@class='mtopstory-wrap']//li["+j+"]/a"));
			title = KeywordUtil.getAttribute("title", By.xpath("//div[@class='mtopstory-wrap']//li["+j+"]/a"));
			Boolean l2SectionList = KeywordUtil.isWebElementVisible(By.xpath("//div[@class='mtopstory-wrap']//li["+j+"]/a"));
			if (l2SectionList) {
				homeList.add(title);
			}
		}
		mobileWebList.addAll(homeNews);
		mobileWebList.addAll(homeList);

		for (String str : mobileWebList) { 
			System.out.print(str + "\n"); 
		} 
		System.out.println("---------------------------------------------------");
		//GlobalUtil.getDriver().close();
	}

	@When("^User hits News(\\d+) mobile app api$")
	public void user_hits_News_mobile_app_api(int arg1) throws Throwable {

		RestAssured.baseURI = ConfigReader.getValue("News18_EndPoint");

		Response response = RestAssured.given().when().get("en/get/news18:en_v1_app_homefeed/android/1/92/");

		JsonPath path = response.jsonPath();
		List<String> storysectionList =path.get("node");
		for (int i = 0; i <storysectionList.size(); i++) {
			String storysection = path.get("node[" + i + "].storysection").toString();
			if (storysection.equalsIgnoreCase("top stories")) {
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
	@Then("^Compare the stories of Mobile Web and Mobile App$")
	public void compare_the_stories_of_Mobile_Web_and_Mobile_App() throws Throwable {

		ApplicationFuncs.writeDataIntoExcel(mobileWebList, list, Stepdefinitions_Hindi.languageName,sectionName);

	}

	@When("^Gets top (\\d+) stories of \"([^\"]*)\" list$")
	public void gets_top_stories_of_list(int count, String section) throws Throwable {
		sectionName = section;
		if(section.equalsIgnoreCase("HOT & Trending"))
		{
			KeywordUtil.waitForVisible(English_Homepage.hotAndTrendingCount);
			List<WebElement> hotTrendingCount = KeywordUtil.getListElements(English_Homepage.hotAndTrendingCount);
			for(int i=1;i<=count+1;i++)
			{	
				if(i!=2)
				{
					KeywordUtil.waitForVisible(By.xpath("//div[@class='hntranding']//ul/li["+i+"]/a[3]"));
					String title = KeywordUtil.getAttribute("title", By.xpath("//div[@class='hntranding']//ul/li["+i+"]/a[3]"));
					boolean status = KeywordUtil.isWebElementVisible(By.xpath("//div[@class='hntranding']//ul/li["+i+"]/a[3]"));
					if (status) {
						mobileWebList.add(title);
					}
				}
			}
		}
		else if(section.equalsIgnoreCase("Videos"))
		{
			KeywordUtil.waitForPageToLoad();
			KeywordUtil.delay(2000);
			KeywordUtil.scroll();
			KeywordUtil.waitForVisible(English_Homepage.videos);
			KeywordUtil.mouseOver(English_Homepage.videos);
			
			for(int i=1;i<=count;i++)
			{	
				KeywordUtil.delay(2000);
				if(i>2)
				{
					KeywordUtil.waitForVisible(English_Homepage.videoRightButton);
					KeywordUtil.clickJS(English_Homepage.videoRightButton);
					KeywordUtil.delay(1000);
				}
				KeywordUtil.waitForVisible(By.xpath("//span[text()='Videos']/../following-sibling::div//li[not(@class='glide__slide--clone')]["+i+"]/a[2]"));
				String title = KeywordUtil.getElementText(By.xpath("//span[text()='Videos']/../following-sibling::div//li[not(@class='glide__slide--clone')]["+i+"]/a[2]"));
				Boolean status = KeywordUtil.isWebElementVisible(By.xpath("//span[text()='Videos']/../following-sibling::div//li[not(@class='glide__slide--clone')]["+i+"]/a[2]"));
				if (status) {
					mobileWebList.add(title);
				}
			}			
		}
		else if(section.equalsIgnoreCase("Photos"))
		{
			if(section.equalsIgnoreCase("Photos"))
				section = "Photogalleries";
			KeywordUtil.scroll();
			KeywordUtil.waitForVisible(English_Homepage.photoGalleries);
			KeywordUtil.mouseOver(English_Homepage.photoGalleries);
			
			for(int i=1;i<=count;i++)
			{	
				
				KeywordUtil.delay(2000);
				if(i>2)
				{
					KeywordUtil.waitForVisible(English_Homepage.photoGalleriesRightArrow);
					KeywordUtil.clickJS(English_Homepage.photoGalleriesRightArrow);
					KeywordUtil.delay(1000);
				}
				KeywordUtil.waitForVisible(By.xpath("//span[text()='Photogalleries']/../following-sibling::div//li[not(@class='glide__slide--clone')]["+i+"]/a[2]"));
				String title = KeywordUtil.getElementText(By.xpath("//span[text()='Photogalleries']/../following-sibling::div//li[not(@class='glide__slide--clone')]["+i+"]/a[2]"));
				Boolean status = KeywordUtil.isWebElementVisible(By.xpath("//span[text()='Photogalleries']/../following-sibling::div//li[not(@class='glide__slide--clone')]["+i+"]/a[2]"));
				if (status) {
					mobileWebList.add(title);
				}
			}			
		}
		else if(section.equalsIgnoreCase("India") || section.equalsIgnoreCase("Cricket") || section.equalsIgnoreCase("Technology") || section.equalsIgnoreCase("World"))
		{
			if(section.equalsIgnoreCase("Technology"))
				section = "Tech";
			else if (section.equalsIgnoreCase("Cricket"))
				section = "Cricketnext";
			KeywordUtil.waitForVisible(By.xpath("//h2/span[text()='" + section + "']"));
			KeywordUtil.mouseOver(By.xpath("//h2/span[text()='" + section + "']"));
			for(int i=1;i<=count;i++)
			{	
				KeywordUtil.waitForVisible(By.xpath("//span[text()='" + section + "']/parent::h2/following-sibling::ul/li[" + i + "]/a"));
				String title = KeywordUtil.getElementText(By.xpath("//span[text()='" + section + "']/parent::h2/following-sibling::ul/li[" + i + "]/a"));
				Boolean status = KeywordUtil.isWebElementVisible(By.xpath("//span[text()='" + section + "']/parent::h2/following-sibling::ul/li[" + i + "]/a"));
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


	@When("^User hits News(\\d+) mobile app apifor \"([^\"]*)\"$")
	public void user_hits_News_mobile_app_apifor(int arg1, String section) throws Throwable {

		RestAssured.baseURI = ConfigReader.getValue("News18_EndPoint");
		int pageNumber=1;

		if(section.equalsIgnoreCase("HOT & Trending") || section.equalsIgnoreCase("Videos"))
		{
			pageNumber =2;
		}
		else if(section.equalsIgnoreCase("India") || section.equalsIgnoreCase("Photos"))
		{
			pageNumber =3;
		}
		else if(section.equalsIgnoreCase("Cricket") || section.equalsIgnoreCase("Technology"))
		{
			pageNumber =4;
		}
		else if(section.equalsIgnoreCase("World"))
		{
			pageNumber =5;
		}
		Response response = RestAssured.given().when().get("en/get/news18:en_v1_app_homefeed/android/"+pageNumber+"/92/");
		JsonPath path = response.jsonPath();
		List<String> l1=path.get("node");
		System.out.println(l1.size());
		for (int i = 0; i < l1.size(); i++) {
			String storysection = path.get("node[" + i + "].data[0].headline").toString();

			if (storysection.equalsIgnoreCase(section)) {

				List<String> l2 = path.get("node[" + i + "].data");

				for (int j = 0; j < l2.size(); j++) {
					List<String> l3 = path.get("node[" + i + "].data[" + j + "].data_node");

					for (int k = 0; k < l3.size(); k++) {
						String childSection = path.get("node[" + i + "].data[" + j + "].data_node.child_layout_type["+k+"]").toString();

						if(childSection.equalsIgnoreCase("list_top_stories") || childSection.equalsIgnoreCase("medium_horizontal_stories") || childSection.equalsIgnoreCase("vertical_list_stories"))
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

	@When("^Gets all sections list$")
	public void gets_all_sections_list() throws Throwable {

		KeywordUtil.waitForVisible(English_Homepage.sections);
		List<WebElement> count = KeywordUtil.getListElements(English_Homepage.sections);

		for(int i=1;i<=count.size();i++)
		{
			KeywordUtil.mouseOver(By.xpath("(//div[contains(@class,'hntranding') or contains(@class,'vspacer30')]//h2/span)["+i+"]"));
			String title = KeywordUtil.getElementText(By.xpath("(//div[contains(@class,'hntranding') or contains(@class,'vspacer30')]//h2/span)["+i+"]")).toLowerCase().replace("from our", "from our shows");
			Boolean status = KeywordUtil.isWebElementVisible(By.xpath("(//div[contains(@class,'hntranding') or contains(@class,'vspacer30')]//h2/span)["+i+"]"));
			if (status) {
				if(title.equalsIgnoreCase("tech"))
					title = "technology";
				else if (title.equalsIgnoreCase("cricketnext"))
					title = "cricket";
				else if (title.equalsIgnoreCase("photogalleries"))
					title = "photos";
				mobileWebList.add(title);
			}
		}
		for (String str : mobileWebList) { 
			System.out.print(str + "\n"); 
		} 
		//GlobalUtil.getDriver().close();
	}
	
	@When("^User hits News(\\d+) mobile app api for getting sections$")
	public void user_hits_News_mobile_app_api_for_getting_sections(int arg1) throws Throwable {
		Response response;
		String storysection;
		RestAssured.baseURI = ConfigReader.getValue("News18_EndPoint");
		for(int i=2;i<=4;i++)
		{
			response = RestAssured.given().when().get("en/get/news18:en_v1_app_homefeed/android/"+i+"/92/");
			JsonPath path = response.jsonPath();

			List<String> l1=path.get("node");
			System.out.println(l1);
			for (int j = 0; j < l1.size(); j++) {
				storysection = path.get("node[" + j + "].data[0].headline").toString().toLowerCase();
				System.out.println(storysection);
				if(!storysection.equals(""))
					mobileappList.add(storysection);
			}

		}
		for (String str : mobileappList) { 
			System.out.print(str + "\n"); 
		} 
	}

	@Then("^Compare the sections in Mobile Web and Mobile App$")
	public void compare_the_sections_in_Mobile_Web_and_Mobile_App() throws Throwable {

		ApplicationFuncs.compareAllTheSectionsWithoutMap(mobileWebList, mobileappList, Stepdefinitions_Hindi.languageName);

	}

}