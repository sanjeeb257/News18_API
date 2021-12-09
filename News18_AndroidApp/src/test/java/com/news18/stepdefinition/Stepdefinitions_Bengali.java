package com.news18.stepdefinition;

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
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.api.base.ApplicationFuncs;
import com.cucumber.listener.Reporter;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.news18.locators.Bengali_Homepage;
import com.utilities.base.ConfigReader;
import com.utilities.base.GlobalUtil;
import com.utilities.base.KeywordUtil;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Stepdefinitions_Bengali {
	List<String> list = new ArrayList<String>();
	List<String> mobileWebList = new ArrayList<String>();
	String sectionName;
	@When("^Gets all top stories list in Bengali language$")
	public void gets_all_top_stories_list_in_Bengali_language() throws Throwable {
		KeywordUtil.waitForPageToLoad();
		KeywordUtil.waitForVisible(Bengali_Homepage.mainTitle);
		String mainTitle = KeywordUtil.getElementText(Bengali_Homepage.mainTitle);
		mobileWebList.add(mainTitle);
		KeywordUtil.scroll();
		List<WebElement> topNewsList = KeywordUtil.getListElements(Bengali_Homepage.topNews);
		KeywordUtil.scroll();
		for (int i = 1 ; i <= topNewsList.size(); i++) {
			KeywordUtil.delay(2500);
			KeywordUtil.waitForVisible(By.xpath("(//div[@class='newstrendvideo-box']//li//a)["+i+"]"));
			String title = KeywordUtil.getElementText(By.xpath("(//div[@class='newstrendvideo-box']//li//a)["+i+"]"));
			mobileWebList.add(title);
		}				
		for (String str : mobileWebList) { 
			System.out.print(str + "\n"); 
		} 
		System.out.println("---------------------------------------------------");
	}

	@When("^User hits News(\\d+) mobile app Bengali language api$")
	public void user_hits_News_mobile_app_Bengali_language_api(int arg1) throws Throwable {
		Response response;
		sectionName = "Top Stories";
		RestAssured.baseURI = ConfigReader.getValue("News18_EndPoint");
		response = RestAssured.given().when().get("bn/get/BENN18:bn_v1_app_homefeed/android/1/92/");
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
	int num = 1;
	String FILE_NAME = "ExecutionReport.xlsx";
	InputStream inp;
	static Workbook wb;
	Sheet sheet = null;

	@Then("^Compare the stories of Mobile Web and Mobile App in Bengali language$")
	public void compare_the_stories_of_Mobile_Web_and_Mobile_App_in_Bengali_language() throws Throwable {
		
		ApplicationFuncs.writeDataIntoExcel(mobileWebList, list, Stepdefinitions_Hindi.languageName,sectionName);		

	}
	@When("^Gets all \"([^\"]*)\" list in Bengali language$")
	public void gets_all_list_in_Bengali_language(String section) throws Throwable {
		sectionName=section;
		KeywordUtil.waitForPageToLoad();
		KeywordUtil.scroll();
		KeywordUtil.delay(2000);
		KeywordUtil.scroll();
		String id = null;
		if(section.equalsIgnoreCase("স্থানীয়"))
			id= "clkbuttonstates";
		else if(section.equalsIgnoreCase("ছবি"))
			id= "clkbuttonphotogallery";
		else if(section.equalsIgnoreCase("খেলা"))
			id= "clkbuttonsports";
		else if(section.equalsIgnoreCase("বিনোদন"))
			id= "clkbuttonentertainment";
		else if(section.equalsIgnoreCase("লাইফস্টাইল"))
			id= "clkbuttonlife-style";
		else if(section.equalsIgnoreCase("প্রযুক্তি"))
			id= "clkbuttontechnology";
		else if(section.equalsIgnoreCase("ব্যবসা-বাণিজ্য"))
			id= "clkbuttonbusiness";
		else if(section.equalsIgnoreCase("ক্রাইম"))
			id= "clkbuttoncrime";
		else if(section.equalsIgnoreCase("বিদেশ"))
			id= "clkbuttoninternational";
		else if(section.equalsIgnoreCase("ফিচার"))
			id= "clkbuttonfeatures";
		else if(section.equalsIgnoreCase("পাঁচমিশালি"))
			id= "clkbuttonoff-beat";
		else if(section.equalsIgnoreCase("ভিডিও"))
			id= "clkbuttonvideos";
		else if(section.equalsIgnoreCase("শো"))
			id= "clkbuttonshows";

		KeywordUtil.delay(5000);
		KeywordUtil.scroll();
		KeywordUtil.scroll();
		KeywordUtil.waitForPageToLoad();
		KeywordUtil.waitForVisible(By.xpath("//div[@id='"+id+"']//a"));
		KeywordUtil.scroll();
		KeywordUtil.mouseOver(By.xpath("//div[@id='"+id+"']//a"));
		if (section.equalsIgnoreCase("ছবি")||section.equalsIgnoreCase("ভিডিও")) {
			List<WebElement> list = KeywordUtil.getListElements(By.xpath("//div[@id='"+id+"']//a"));
			for(int i=1;i<=list.size();i++)
			{
				KeywordUtil.waitForVisible(By.xpath("(//div[@id='"+id+"']//a)["+i+"]"));

				String  title = KeywordUtil.getAttribute("innerText", By.xpath("(//div[@id='"+id+"']//a)["+i+"]"));
				boolean status = KeywordUtil.isWebElementVisible(By.xpath("(//div[@id='"+id+"']//a)["+i+"]"));
				if (status) {
					mobileWebList.add(title);
				}
			}
		}
		else {
			List<WebElement> list = KeywordUtil.getListElements(By.xpath("//div[@id='"+id+"']//a"));
			for(int i=2;i<=list.size();i++)
			{
				KeywordUtil.waitForVisible(By.xpath("(//div[@id='"+id+"']//a)["+i+"]"));
				String  title = KeywordUtil.getAttribute("innerText", By.xpath("(//div[@id='"+id+"']//a)["+i+"]"));
				boolean status = KeywordUtil.isWebElementVisible(By.xpath("(//div[@id='"+id+"']//a)["+i+"]"));
				if (status) {
					mobileWebList.add(title);
				}
			}
		}

		for (String str : mobileWebList) { 
			System.out.print(str + "\n"); 
		} 
		System.out.println("---------------------------------------------------");
	}
	@When("^User hits News(\\d+) mobile app Bengali language api for \"([^\"]*)\" section$")
	public void user_hits_News_mobile_app_Bengali_language_api_for_section(int arg1, String section) throws Throwable {
		sectionName=section;
		RestAssured.baseURI = ConfigReader.getValue("News18_EndPoint");
		sectionName = section;
		int pageNumber=1;
		if(section.equalsIgnoreCase("স্থানীয়"))
			pageNumber=2;
		else if(section.equalsIgnoreCase("ছবি")||section.equalsIgnoreCase("খেলা"))
			pageNumber=3;
		else if(section.equalsIgnoreCase("বিনোদন")||section.equalsIgnoreCase("লাইফস্টাইল"))
			pageNumber=4;
		else if(section.equalsIgnoreCase("প্রযুক্তি")||section.equalsIgnoreCase("ব্যবসা-বাণিজ্য"))
			pageNumber=5;
		else if(section.equalsIgnoreCase("ক্রাইম")||section.equalsIgnoreCase("বিদেশ"))
			pageNumber=6;
		else if(section.equalsIgnoreCase("ফিচার")||section.equalsIgnoreCase("পাঁচমিশালি"))
			pageNumber=7;
		else if(section.equalsIgnoreCase("ভিডিও")||section.equalsIgnoreCase("শো"))
			pageNumber=8;

		KeywordUtil.delay(2000);
		Response response = RestAssured.given().when().get("bn/get/BENN18:bn_v1_app_homefeed/android/"+pageNumber+"/86/");
		JsonPath path = response.jsonPath();
		List<String> l1 = path.get("node");
		if(section.equalsIgnoreCase("ছবি")||section.equalsIgnoreCase("ভিডিও")) {
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

	@When("^Gets all sections list in Bengali language$")
	public void gets_all_sections_list_in_Bengali_language() throws Throwable {
		KeywordUtil.delay(5000);
		KeywordUtil.waitForPageToLoad();
		KeywordUtil.scroll();
		KeywordUtil.delay(2000);
		KeywordUtil.waitForVisible(Bengali_Homepage.sectionList);
		List<WebElement> count = KeywordUtil.getListElements(Bengali_Homepage.sectionList);

		for(int i=1;i<=count.size();i++)
		{
			KeywordUtil.mouseOver(By.xpath("(//div[@class='cat_url_cont']//div[contains(@class,'globalhd')]//a)["+i+"]"));
			String title = KeywordUtil.getElementText(By.xpath("(//div[@class='cat_url_cont']//div[contains(@class,'globalhd')]//a)["+i+"]")).toLowerCase();
			Boolean status = KeywordUtil.isWebElementVisible(By.xpath("(//div[@class='cat_url_cont']//div[contains(@class,'globalhd')]//a)["+i+"]"));
			if (status) {
				mobileWebList.add(title);
			}
		}
		for (String str : mobileWebList) { 
			System.out.print(str + "\n"); 
		} 
		
	}
	ArrayList<String> mobileappList=new ArrayList<String>();
	@When("^User hits News(\\d+) mobile app Bengali api for getting sections$")
	public void user_hits_News_mobile_app_Bengali_api_for_getting_sections(int arg1) throws Throwable {
		Response response;
		String storysection;
		RestAssured.baseURI = ConfigReader.getValue("News18_EndPoint");
		for(int i=2;i<=8;i++)
		{
			response = RestAssured.given().when().get("bn/get/BENN18:bn_v1_app_homefeed/android/"+i+"/86/");
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

	@Then("^Compare the sections in Mobile Web and Mobile App for Bengali language$")
	public void compare_the_sections_in_Mobile_Web_and_Mobile_App_for_Bengali_language() throws Throwable {

		Collection<String> coll = new HashSet<>(mobileWebList);
		for(int i=0;i<mobileWebList.size();i++) {
			if(mobileWebList.get(i).equals("ব্যবসা")) {
				mobileWebList.set(i, "ব্যবসা-বাণিজ্য");
			}
		}

		Collection<String> different=ApplicationFuncs.compareAllTheSectionsWithMap(mobileWebList, mobileappList, Stepdefinitions_Hindi.languageName,coll);
		Row	row=ApplicationFuncs.sheet.createRow(ApplicationFuncs.num++);
		Cell c=row.getCell(0);
		if(!(c!=null&&c.getCellType()!=Cell.CELL_TYPE_BLANK)) {
			row.createCell(0).setCellValue("Matching Sections in App and Web");
		}
		c=row.getCell(1);
		if(!(c!=null&&c.getCellType()!=Cell.CELL_TYPE_BLANK)) {
			row.createCell(1).setCellValue("ব্যবসা(Web)  : ব্যবসা-বাণিজ্য(App)");
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
