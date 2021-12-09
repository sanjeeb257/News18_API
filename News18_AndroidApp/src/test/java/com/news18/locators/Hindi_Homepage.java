package com.news18.locators;

import org.openqa.selenium.By;

public class Hindi_Homepage {

	public static By mainTitle = By.xpath("//div[@class='bigstory']//h2");
	public static By topstoryList = By.xpath("//div[@class='newstrendvideo-box']//li//a");
	public static By sections = By.xpath("//div[contains(@class,'globalhd') or @class='livetv-hdr']/h2");
	public static By swamdan = By.xpath("//div[@class='swdnam']");
	public static By cartoon = By.xpath("//div[@class='cartoonwrap']//a");	
	public static By raasiBhavishya = By.xpath("//h2/a[text()='राशिभविष्य']") ;
}

