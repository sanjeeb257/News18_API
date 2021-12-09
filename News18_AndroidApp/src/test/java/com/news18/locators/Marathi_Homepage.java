package com.news18.locators;

import org.openqa.selenium.By;

public class Marathi_Homepage {
	public static By mainTitle = By.xpath("//div[@class='bigstory']//h2/a[2]");
	public static By topNewsList = By.xpath("//ul[@id='bottomSlider']/li//h2/a");
	public static By manoranjan = By.xpath("//h2/a[text()='मनोरंजन']");
	public static By manoTitle = By.xpath("//div[@id='clkbuttonentertainment']//h2/a");
	public static By monoList = By.xpath("//div[@id='clkbuttonentertainment']//li/a");
	public static By mahaMainTitle = By.xpath("//div[@class='bigstory']//h2/a[2]");
	public static By mahaNewsList = By.xpath("//div[@id='clkbuttonentertainment']//li/a");
	public static By sportsText = By.xpath("//h2/a[text()='स्पोर्ट्स']");
	public static By sportsMainTitle = By.xpath("//div[@id='clkbuttonsport']/div[@class='bigstory']/h2/a");
	public static By sportsNewsList = By.xpath("//div[@id='clkbuttonsport']/ul[@class='global-storylist']/li/a");
	public static By topStories = By.xpath("//ul[@id='newstrendvideo']/li");
	

}

