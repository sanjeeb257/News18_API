package com.api.base;

import static com.jayway.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

import com.cucumber.listener.Reporter;
import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.utilities.base.ConfigReader;
import com.utilities.base.KeywordUtil;

public class ApplicationFuncs {


	public static String baseURI = ConfigReader.getValue("Location_EndPoint");

	public String jsonObjectString = null;
	public static Response response = null;
	public static RequestSpecification request=null;
	static JsonNode jsonObject=null;


	public String reqPost(String payload, String params) {
		RestAssured.baseURI = baseURI  + params;
		request = given().body(payload).when().contentType(ContentType.JSON);
		response = request.post("");
		return response.getBody().prettyPrint();
	}
	public String reqPost(String payload, String resource, String queryParamKey, String queryParamValue) {
		RestAssured.baseURI = baseURI  ;
		request = given().body(payload).queryParam(queryParamKey, queryParamValue).when().contentType(ContentType.JSON);
		response = request.post(resource);
		return response.getBody().prettyPrint();
	}
	public String reqPut(String payload, String params) {
		RestAssured.baseURI = baseURI ;
		request = given().header("Content-Type", ContentType.JSON).body(payload);
		response = request.when().put(params);
		return response.getBody().prettyPrint();
	}
	public String reqGet( String resource, String queryParamKey1, String queryParamValue1,String queryParamKey2, String queryParamValue2) {
		System.out.println("Host is " + baseURI  + resource);
		RestAssured.baseURI = baseURI  ;
		request = given().queryParam(queryParamKey1, queryParamValue1).queryParam(queryParamKey2, queryParamValue2).when().contentType(ContentType.JSON);
		response = request.get(resource);
		return response.getBody().prettyPrint();
	}
	public String reqDelete(String payload, String params) {
		RestAssured.baseURI = baseURI ;
		request = given().header("Content-Type", ContentType.JSON).body(payload);
		response = request.when().delete(params);
		return response.getBody().prettyPrint();
	}
	public static int statusCodeFromResponse(int expectedCode)
	{
		int statusCode = response.getStatusCode();
		if(statusCode==expectedCode)
			Reporter.addStepLog(" <font color='green'> Response code for the Request is  <font color='magenta'> "+statusCode+" </font> </font>");
		else
			Reporter.addStepLog(" <font color='red'> Response code for the Request is  <font color='magenta'> "+statusCode+" </font> </font>");
		return statusCode;
	}

	public static String getDataFromJsonResponse(String requiredData) {
		try {
			jsonObject = ValidationUtils.getJsonNode(response.asString());
			requiredData = jsonObject.findValue(requiredData).asText();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return requiredData;
	}

	static String requiredData=null;

	public static String getDataFromJsonResponse(String required, String res) {
		try {
			jsonObject = ValidationUtils.getJsonNode(response.asString());
			requiredData = jsonObject.findValue(required).asText();
			if(requiredData.equals(res))
				Reporter.addStepLog(" <font color='green'> Response for the "+"<font color='magenta'>"+required+"</font> is <font color='magenta'> "+res+"</font> </font>");	
			else
				Reporter.addStepLog(" <font color='red'> Response for the "+"<font color='magenta'>"+required+"</font> is <font color='magenta'> "+res+"</font> </font>");	
		} catch (IOException e) {
			e.printStackTrace();
		}
		return requiredData;
	}
	
	public static int num=1;
	public static XSSFWorkbook wb ;
	public	static String FILE_NAME    = "ExecutionReport.xlsx"; 
	public static XSSFSheet sheet;
	static InputStream inp;
	public static void writeDataIntoExcel(List<String> mobileWebList, List<String> list, String language,String sectionName) throws IOException {
		int m=0;
		inp = new FileInputStream(FILE_NAME);
		if(sectionName.equalsIgnoreCase("Top Stories")) {
			wb=new XSSFWorkbook(inp);
			sheet = wb.createSheet(language);
		}
		else
		{
			wb=new XSSFWorkbook(inp);
			for(int i=0; i<wb.getNumberOfSheets();i++) {
				if(wb.getSheetName(i).equalsIgnoreCase(language)) {
					sheet=wb.getSheetAt(i);
				}
			}
		}
		if(sectionName.equalsIgnoreCase("Top Stories"))
		{

			Row	row = sheet.createRow(0);
			Cell c = row.getCell(0);
			if (!(c != null && c.getCellType() != Cell.CELL_TYPE_BLANK)) {
				row.createCell(0).setCellValue("Section Name"); 
			}
			c = row.getCell(1);
			if (!(c != null && c.getCellType() != Cell.CELL_TYPE_BLANK)) {
				row.createCell(1).setCellValue("Article Number"); 
			}
			c = row.getCell(2);
			if (!(c != null && c.getCellType() != Cell.CELL_TYPE_BLANK)) {
				row.createCell(2).setCellValue("Mobile Web"); 
			}
			c = row.getCell(3);
			if (!(c != null && c.getCellType() != Cell.CELL_TYPE_BLANK)) {
				row.createCell(3).setCellValue("Mobile App"); 
			}
			c = row.getCell(4);
			if (!(c != null && c.getCellType() != Cell.CELL_TYPE_BLANK)) {
				row.createCell(4).setCellValue("Result"); 
			}
			num=sheet.getLastRowNum()+1;
		}
		else {
			num=sheet.getLastRowNum()+2;
		}
		for(int i=0;i<list.size();i++)
		{
			Row	row = sheet.createRow(num++);

			if((list.get(i).replace(" ", "").contains(mobileWebList.get(i).replace(" ", ""))) || (mobileWebList.get(i).replace(" ", "").contains(list.get(i).replace(" ", ""))) || (list.get(i).replace(" ", "").equalsIgnoreCase(mobileWebList.get(i).replace(" ", ""))))
			{
				Reporter.addStepLog(" <font color='green'> <font color='magenta'> "+(i+1)+" </font> article is same for both Mobile web and Mobile App :  <font color='magenta'> "+list.get(i)+" </font> </font>");
				Cell c = row.getCell(0);
				if (!(c != null && c.getCellType() != Cell.CELL_TYPE_BLANK)) {
					row.createCell(0).setCellValue(sectionName); 
				}
				c = row.getCell(1);
				if (!(c != null && c.getCellType() != Cell.CELL_TYPE_BLANK)) {
					row.createCell(1).setCellValue(i+1); 
				}
				c = row.getCell(2);
				if (!(c != null && c.getCellType() != Cell.CELL_TYPE_BLANK)) {
					row.createCell(2).setCellValue(mobileWebList.get(i)); 
				}
				c = row.getCell(4);
				if (!(c != null && c.getCellType() != Cell.CELL_TYPE_BLANK)) {
					row.createCell(4).setCellValue("PASS"); 
				}
			}
			else
			{
				Reporter.addStepLog(" <font color='red'> <font color='magenta'> "+(i+1)+" </font> article in Mobile web has: <font color='magenta'> "+mobileWebList.get(i)+" </font> and Mobile App has: <font color='magenta'> "+list.get(i)+" </font> </font> " );
				m++;

				Cell c = row.getCell(0);
				if (!(c != null && c.getCellType() != Cell.CELL_TYPE_BLANK)) {
					row.createCell(0).setCellValue(sectionName); 
				}
				c = row.getCell(1);
				if (!(c != null && c.getCellType() != Cell.CELL_TYPE_BLANK)) {
					row.createCell(1).setCellValue(i+1); 
				}
				c = row.getCell(2);
				if (!(c != null && c.getCellType() != Cell.CELL_TYPE_BLANK)) {
					row.createCell(2).setCellValue(mobileWebList.get(i)); 
				}
				c = row.getCell(3);
				if (!(c != null && c.getCellType() != Cell.CELL_TYPE_BLANK)) {
					row.createCell(3).setCellValue(list.get(i)); 
				}
				c = row.getCell(4);
				if (!(c != null && c.getCellType() != Cell.CELL_TYPE_BLANK)) {
					row.createCell(4).setCellValue("FAIL"); 
				}
			}
		}
		FileOutputStream fileOut = new FileOutputStream(FILE_NAME); 
		wb.write(fileOut); 
		fileOut.close(); 
		fileOut.flush();
		list.removeAll(list);
		mobileWebList.removeAll(mobileWebList);
		KeywordUtil.delay(3000);
		if(m>0)
		{
			Assert.fail("Got "+m+" mismatch");
		}
	}

	public static void compareAllTheSectionsWithoutMap(Collection< String> mobileWebList, Collection<String> mobileappList, String language) throws IOException {

		Collection<String> similar = new HashSet<String>( mobileWebList );
		Collection<String> different = new HashSet<String>();
		Collection<String> appList = new HashSet<String>(mobileappList);
		different.addAll( mobileWebList );
		similar.retainAll( mobileappList );
		different.removeAll( similar );
		appList.removeAll(similar);
		System.out.printf("MobileWebList:%s%nMobileAppList:%s%nExtraSectionsInWeb:%s%nExtraSectionsInApp:%s%n",mobileWebList,mobileappList,different,appList);

		for(int i=0; i<wb.getNumberOfSheets();i++) {
			if(wb.getSheetName(i).equalsIgnoreCase(language)) {
				sheet=wb.getSheetAt(i);
			}
		}

		StringBuilder strbul = new StringBuilder();
		for(String str:mobileWebList) {
			strbul.append(str);
			strbul.append(",");
		}
		String mobWebList = strbul.toString();

		StringBuilder strbul1 = new StringBuilder();
		for(String str:mobileappList) {
			strbul1.append(str);
			strbul1.append(",");
		}
		String mobAppList = strbul1.toString();

		StringBuilder strbul2 = new StringBuilder();
		for(String str:different) {
			strbul2.append(str);
			strbul2.append(",");
		}
		String extraSectionsInWeb = strbul2.toString();

		StringBuilder strbul3 = new StringBuilder();
		for(String str:appList) {
			strbul3.append(str);
			strbul3.append(",");
		}
		String extraSectionsInApp = strbul3.toString();

		num=sheet.getLastRowNum()+2;
		Row	row = sheet.createRow(num++);
		Cell c = row.getCell(0);
		if (!(c != null && c.getCellType() != Cell.CELL_TYPE_BLANK)) {
			row.createCell(0).setCellValue("MobileWebList");
		}
		c = row.getCell(1);
		if (!(c != null && c.getCellType() != Cell.CELL_TYPE_BLANK)) {
			if(mobWebList.isEmpty()) {
				row.createCell(1).setCellValue("No Sections");
			}else {
				row.createCell(1).setCellValue(mobWebList);
			}
		}


		row = sheet.createRow(num++);
		c = row.getCell(0);
		if (!(c != null && c.getCellType() != Cell.CELL_TYPE_BLANK)) {
			row.createCell(0).setCellValue("MobileAppList");
		}
		c = row.getCell(1);
		if (!(c != null && c.getCellType() != Cell.CELL_TYPE_BLANK)) {
			if(mobAppList.isEmpty()) {
				row.createCell(1).setCellValue("No Sections");
			}else {
				row.createCell(1).setCellValue(mobAppList);
			}
		}


		row = sheet.createRow(num++);
		c = row.getCell(0);
		if (!(c != null && c.getCellType() != Cell.CELL_TYPE_BLANK)) {
			row.createCell(0).setCellValue("ExtraSectionsInWeb");
		}
		c = row.getCell(1);
		if (!(c != null && c.getCellType() != Cell.CELL_TYPE_BLANK)) {
			if(extraSectionsInWeb.isEmpty()) {
				row.createCell(1).setCellValue("No Extra Sections");
			}else {
				row.createCell(1).setCellValue(extraSectionsInWeb);
			}
		}


		row = sheet.createRow(num++);
		c = row.getCell(0);
		if (!(c != null && c.getCellType() != Cell.CELL_TYPE_BLANK)) {
			row.createCell(0).setCellValue("ExtraSectionsInApp");
		}
		c = row.getCell(1);
		if (!(c != null && c.getCellType() != Cell.CELL_TYPE_BLANK)) {

			if(extraSectionsInApp.isEmpty()) {
				row.createCell(1).setCellValue("No Extra Section");
			}else {
				row.createCell(1).setCellValue(extraSectionsInApp);
			}
		}

		FileOutputStream fileOut = new FileOutputStream(FILE_NAME); 
		wb.write(fileOut); 
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

	public static Collection<String> compareAllTheSectionsWithMap(Collection<String> mobileWebList, Collection<String> mobileappList,String language, Collection<String> coll) {

		Collection<String> similar = new HashSet<String>( mobileWebList );
		Collection<String> different = new HashSet<String>();
		different.addAll( mobileWebList );
		Collection<String> appList = new HashSet<String>(mobileappList);
		similar.retainAll( mobileappList );
		different.removeAll( similar );
		appList.removeAll(similar);
		System.out.printf("MobileWebList:%s%nMobileAppList:%s%nExtraSectionsInWeb:%s%nExtraSectionsInApp:%s%n",coll,mobileappList,different,appList);

		for(int i=0; i<wb.getNumberOfSheets();i++) {
			if(wb.getSheetName(i).equalsIgnoreCase(language)) {
				sheet=wb.getSheetAt(i);
			}
		}

		StringBuilder strbul = new StringBuilder();
		for(String str:coll) {
			strbul.append(str);
			strbul.append(",");
		}
		String mobWebList = strbul.toString();

		StringBuilder strbul1 = new StringBuilder();
		for(String str:mobileappList) {
			strbul1.append(str);
			strbul1.append(",");
		}
		String mobAppList = strbul1.toString();

		StringBuilder strbul2 = new StringBuilder();
		for(String str:different) {
			strbul2.append(str);
			strbul2.append(",");
		}
		String extraSectionsInWeb = strbul2.toString();

		StringBuilder strbul3 = new StringBuilder();
		for(String str:appList) {
			strbul3.append(str);
			strbul3.append(",");
		}
		String extraSectionsInApp = strbul3.toString();

		num=sheet.getLastRowNum()+2;
		Row	row = sheet.createRow(num++);
		Cell c = row.getCell(0);
		if (!(c != null && c.getCellType() != Cell.CELL_TYPE_BLANK)) {
			row.createCell(0).setCellValue("MobileWebList");
		}
		c = row.getCell(1);
		if (!(c != null && c.getCellType() != Cell.CELL_TYPE_BLANK)) {
			if(mobWebList.isEmpty()) {
				row.createCell(1).setCellValue("No Sections");
			}else {
				row.createCell(1).setCellValue(mobWebList);
			}
		}


		row = sheet.createRow(num++);
		c = row.getCell(0);
		if (!(c != null && c.getCellType() != Cell.CELL_TYPE_BLANK)) {
			row.createCell(0).setCellValue("MobileAppList");
		}
		c = row.getCell(1);
		if (!(c != null && c.getCellType() != Cell.CELL_TYPE_BLANK)) {
			if(mobAppList.isEmpty()) {
				row.createCell(1).setCellValue("No Sections");
			}else {
				row.createCell(1).setCellValue(mobAppList);
			}
		}


		row = sheet.createRow(num++);
		c = row.getCell(0);
		if (!(c != null && c.getCellType() != Cell.CELL_TYPE_BLANK)) {
			row.createCell(0).setCellValue("ExtraSectionsInWeb");
		}
		c = row.getCell(1);
		if (!(c != null && c.getCellType() != Cell.CELL_TYPE_BLANK)) {
			if(extraSectionsInWeb.isEmpty()) {
				row.createCell(1).setCellValue("No Extra Sections");
			}else {
				row.createCell(1).setCellValue(extraSectionsInWeb);
			}
		}


		row = sheet.createRow(num++);
		c = row.getCell(0);
		if (!(c != null && c.getCellType() != Cell.CELL_TYPE_BLANK)) {
			row.createCell(0).setCellValue("ExtraSectionsInApp");
		}
		c = row.getCell(1);
		if (!(c != null && c.getCellType() != Cell.CELL_TYPE_BLANK)) {

			if(extraSectionsInApp.isEmpty()) {
				row.createCell(1).setCellValue("No Extra Section");
			}else {
				row.createCell(1).setCellValue(extraSectionsInApp);
			}
		}

		return different;
	}
}
