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
import com.utilities.base.ConfigReader;
import com.utilities.base.DriverUtil;
import com.utilities.base.GlobalUtil;
import com.utilities.base.KeywordUtil;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Stepdefinitions_Hindi {

	List<String> list = new ArrayList<String>();
	List<String> mobileWebList = new ArrayList<String>();
	String sectionName;
	public static String languageName;
	@Given("^User navigates to News(\\d+) mobile web \"([^\"]*)\" home page$")
	public String user_navigates_to_News_mobile_web_home_page(int arg1, String language) throws Throwable {
		languageName=language;
		//GlobalUtil.setDriver(DriverUtil.getChromeBrowser("CHROME"));
		if(language.equalsIgnoreCase("Hindi")) 
			KeywordUtil.navigateToUrl("https://hindi.news18.com/");
		else if(language.equalsIgnoreCase("English")) 
			KeywordUtil.navigateToUrl("https://www.news18.com/");
		else if(language.equalsIgnoreCase("Marathi"))
			KeywordUtil.navigateToUrl("https://lokmat.news18.com/");
		else if(language.equalsIgnoreCase("Kannada"))
			KeywordUtil.navigateToUrl("https://kannada.news18.com/");
		else if(language.equalsIgnoreCase("Gujarati"))
			KeywordUtil.navigateToUrl("https://gujarati.news18.com/");
		else if(language.equalsIgnoreCase("Malayalam"))
			KeywordUtil.navigateToUrl("https://malayalam.news18.com/");
		else if(language.equalsIgnoreCase("Bengali"))
			KeywordUtil.navigateToUrl("https://bengali.news18.com/");
		else if(language.equalsIgnoreCase("Odia"))
			KeywordUtil.navigateToUrl("https://odia.news18.com/");
		else if(language.equalsIgnoreCase("Punjab"))
			KeywordUtil.navigateToUrl("https://punjab.news18.com/");
		else if(language.equalsIgnoreCase("Urdu"))
			KeywordUtil.navigateToUrl("https://urdu.news18.com/");
		else if(language.equalsIgnoreCase("Tamil"))
			KeywordUtil.navigateToUrl("https://tamil.news18.com/");
		else if(language.equalsIgnoreCase("Assam"))
			KeywordUtil.navigateToUrl("https://assam.news18.com/");
		else if(language.equalsIgnoreCase("Telugu"))
			KeywordUtil.navigateToUrl("https://telugu.news18.com/");
		return languageName;

	}
	@When("^Gets all top stories list in Hindi language$")
	public void gets_all_top_stories_list_in_Hindi_language() throws Throwable {

		KeywordUtil.waitForVisible(Hindi_Homepage.mainTitle);
		String title = KeywordUtil.getElementText(Hindi_Homepage.mainTitle);
		mobileWebList.add(title);
		KeywordUtil.scroll();
		List<WebElement> list = KeywordUtil.getListElements(Hindi_Homepage.topstoryList);
		for (int k = 1; k <list.size(); k++) {
			KeywordUtil.waitForVisible(By.xpath("(//div[@class='newstrendvideo-box']//li//a)["+k+"]"));
			title = KeywordUtil.getElementText(By.xpath("(//div[@class='newstrendvideo-box']//li//a)["+k+"]"));
			boolean status = KeywordUtil.isWebElementVisible(By.xpath("(//div[@class='newstrendvideo-box']//li//a)["+k+"]"));
			if (status) {
				mobileWebList.add(title);
			}
		}

		for (String str : mobileWebList) { 
			System.out.print(str + "\n"); 
		} 
		System.out.println("---------------------------------------------------");
		//GlobalUtil.getDriver().close();
	}

	@When("^User hits News(\\d+) mobile app Hindi language api$")
	public void user_hits_News_mobile_app_Hindi_language_api(int arg1) throws Throwable {
		Response response;
		sectionName = "Top Stories";
		RestAssured.baseURI = ConfigReader.getValue("News18_EndPoint");
		response = RestAssured.given().when().get("hi/get/HINNEWS18:hi_v1_app_homefeed/android/1/92/");
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
	@Then("^Compare the stories of Mobile Web and Mobile App in Hindi language$")
	public void compare_the_stories_of_Mobile_Web_and_Mobile_App_in_Hindi_language() throws Throwable {

		ApplicationFuncs.writeDataIntoExcel(mobileWebList, list, languageName,sectionName);		

	}

	@When("^Gets all \"([^\"]*)\" list in Hindi language$")
	public void gets_all_list_in_Hindi_language(String section) throws Throwable {

		String id = null;
		if(section.equalsIgnoreCase("वीडियो"))
			id= "videos";
		else if(section.equalsIgnoreCase("लाइफ"))
			id="lifestyle";
		else if(section.equalsIgnoreCase("मोबाइल-टेक"))
			id="tech";
		else if(section.equalsIgnoreCase("मनी"))
			id="business";
		else if(section.equalsIgnoreCase("लाइफ"))
			id="lifestyle";
		else if(section.equalsIgnoreCase("क्रिकेट"))
			id="sports";
		else if(section.equalsIgnoreCase("क्राइम"))
			id="crime";
		else if(section.equalsIgnoreCase("करियर & जॉब्स"))
			id="career";
		else if(section.equalsIgnoreCase("नॉलेज"))
			id="knowledge";
		else if(section.equalsIgnoreCase("दुनिया"))
			id="world";
		else if(section.equalsIgnoreCase("मनोरंजन"))
			id="bollywood";
		else if(section.equalsIgnoreCase("फोटो"))
			id="photogallery";

		KeywordUtil.delay(5000);
		KeywordUtil.scroll();
		KeywordUtil.scroll();
		KeywordUtil.waitForPageToLoad();
		KeywordUtil.delay(8000);
		KeywordUtil.scrollingToElementofAPage(Hindi_Homepage.raasiBhavishya);
		KeywordUtil.waitForPageToLoad();
		KeywordUtil.waitForVisible(By.xpath("//div[@id='clkbutton"+id+"']//picture/img"));
		KeywordUtil.scroll();
		KeywordUtil.mouseOver(By.xpath("//div[@id='clkbutton"+id+"']//picture/img"));
		List<WebElement> videos = KeywordUtil.getListElements(By.xpath("//div[@id='clkbutton"+id+"']//picture/img"));
		for(int i=1;i<=videos.size();i++)
		{
			KeywordUtil.waitForVisible(By.xpath("(//div[@id='clkbutton"+id+"']//picture/img)["+i+"]"));

			String  title = KeywordUtil.getAttribute("title", By.xpath("(//div[@id='clkbutton"+id+"']//picture/img)["+i+"]"));
			boolean status = KeywordUtil.isWebElementVisible(By.xpath("(//div[@id='clkbutton"+id+"']//picture/img)["+i+"]"));
			if (status) {
				mobileWebList.add(title);
			}
		}

		for (String str : mobileWebList) { 
			System.out.print(str + "\n"); 
		} 
		System.out.println("---------------------------------------------------");
	}

	@When("^User hits News(\\d+) mobile app Hindi language api for \"([^\"]*)\" section$")
	public void user_hits_News_mobile_app_Hindi_language_api_for_section(int arg1, String section) throws Throwable {
		RestAssured.baseURI = ConfigReader.getValue("News18_EndPoint");
		sectionName = section;
		int pageNumber=1;
		if(section.equalsIgnoreCase("वीडियो"))
			pageNumber=2;
		else if(section.equalsIgnoreCase("लाइफ"))
		{
			section = "लाइफ़";
			pageNumber=4;
		}
		else if(section.equalsIgnoreCase("मोबाइल-टेक"))
			pageNumber=4;
		else if(section.equalsIgnoreCase("मनी") || section.equalsIgnoreCase("क्रिकेट"))
			pageNumber=5;
		else if(section.equalsIgnoreCase("क्राइम") || section.equalsIgnoreCase("करियर & जॉब्स"))
			pageNumber=6;
		else if(section.equalsIgnoreCase("नॉलेज") || section.equalsIgnoreCase("दुनिया"))
			pageNumber=7;
		else if(section.equalsIgnoreCase("मनोरंजन")||section.equalsIgnoreCase("फोटो"))
			pageNumber=3;

		KeywordUtil.delay(2000);

		Response response;
		response = RestAssured.given().when().get("hi/get/HINNEWS18:hi_v1_app_homefeed/android/"+pageNumber+"/86/");
		JsonPath path = response.jsonPath();
		List<String> l1=path.get("node");
		if(section.equals("वीडियो")||section.equals("फोटो"))
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
		else if(section.equals("नॉलेज"))
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
		else
		{
			for (int i = 0; i < l1.size(); i++) {
				String storysection = path.get("node[" + i + "].storysection").toString();
				if (storysection.equals(section)) {
					List<String> l2 = path.get("node[" + i + "].data");
					for (int j = 0; j < l2.size(); j++) {
						List<String> l3 = path.get("node[" + i + "].data[" + j + "].data_node");
						for (int k = 0; k < l3.size(); k++) {
							String childSection = path.get("node[" + i + "].data[" + j + "].data_node.child_layout_type["+k+"]").toString();
							if(childSection.equalsIgnoreCase("list_top_stories") || childSection.equalsIgnoreCase("medium_horizontal_stories") || childSection.equalsIgnoreCase("vertical_list_stories") || childSection.equalsIgnoreCase("sports_top_stories"))
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

	@When("^Gets all sections list in Hindi language$")
	public void gets_all_sections_list_in_Hindi_language() throws Throwable {

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
				if(title.equalsIgnoreCase("करियर"))
					title = "करियर & जॉब्स";

				mobileWebList.add(title);
			}
		}
		String title = KeywordUtil.getElementText(Hindi_Homepage.swamdan);
		Boolean status = KeywordUtil.isWebElementVisible(Hindi_Homepage.swamdan);
		if (status) 
			mobileWebList.add(title);

		title = KeywordUtil.getElementText(Hindi_Homepage.cartoon);
		status = KeywordUtil.isWebElementVisible(Hindi_Homepage.cartoon);
		if (status) 
			mobileWebList.add(title);

		for (String str : mobileWebList) { 
			System.out.print(str + "\n"); 
		} 
		//GlobalUtil.getDriver().close();
	}
	ArrayList<String> mobileappList=new ArrayList<String>();
	@When("^User hits News(\\d+) mobile app Hindi api for getting sections$")
	public void user_hits_News_mobile_app_Hindi_api_for_getting_sections(int arg1) throws Throwable {
		Response response;
		String storysection;
		RestAssured.baseURI = ConfigReader.getValue("News18_EndPoint");
		for(int i=1;i<=7;i++)
		{
			if(i==1)
			{
				response = RestAssured.given().when().get("hi/get/HINNEWS18:hi_v1_app_homefeed/android/"+i+"/86/");
				JsonPath path = response.jsonPath();
				for (int m = 0; m <= 6; m++) {
					storysection = path.get("node[" + m + "].dimension").toString();
					if (storysection.equalsIgnoreCase("top_stories")) {
						List<String> l2 = path.get("node[" + m + "].data");
						for (int j = 0; j < l2.size(); j++) {
							List<String> l3 = path.get("node[" + m + "].data[" + j + "].data_node");
							for (int k = 0; k < l3.size(); k++) {
								String childSection = path.get("node[" + m + "].data[" + j + "].data_node.child_layout_type["+k+"]").toString();
								if(childSection.equalsIgnoreCase("livetv"))
								{
									mobileappList.add("लाइव टीवी");
								}
							}
						}
					}
				}
			}
			else
			{
				response = RestAssured.given().when().get("hi/get/HINNEWS18:hi_v1_app_homefeed/android/"+i+"/86/");
				JsonPath path = response.jsonPath();
				List<String> l1=path.get("node");
				for (int j = 0; j < l1.size(); j++) {
					storysection = path.get("node[" + j + "].data[0].headline").toString().toLowerCase();
					if(!storysection.equals(""))
						mobileappList.add(storysection);
				}
			}
		}
		for (String str : mobileappList) { 
			System.out.print(str + "\n"); 
		} 
	}

	@Then("^Compare the sections in Mobile Web and Mobile App for Hindi language$")
	public void compare_the_sections_in_Mobile_Web_and_Mobile_App_for_Hindi_language() throws Throwable {
		Collection<String> coll = new HashSet<>(mobileWebList);
		for(int i=0;i<mobileWebList.size();i++) {
			if(mobileWebList.get(i).equals("खेल")) {
				mobileWebList.set(i, "क्रिकेट");		
			}
			if(mobileWebList.get(i).equals("बॉलीवुड")) {
				mobileWebList.set(i, "मनोरंजन");		
			}
			if(mobileWebList.get(i).equals("सुपरहिट गैलरी")) {
				mobileWebList.set(i, "फोटो");		
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
			row.createCell(1).setCellValue("खेल(Web):क्रिकेट(App) , बॉलीवुड(Web) :मनोरंजन(App)   and  सुपरहिट गैलरी(Web) :फोटो(App)");
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
