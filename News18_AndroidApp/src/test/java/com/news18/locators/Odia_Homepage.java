package com.news18.locators;

import org.openqa.selenium.By;

public class Odia_Homepage {
	public static By topStory = By.xpath("//div[@class='mid-bigstory']/div[@class='bigstory']//h2");
	public static By verticleStoryList = By.xpath("//a[text()='ଚ଼ର୍ଚ୍ଚିତ ଖବର']/ancestor::div[contains(@class,'globalhd')]/following-sibling::*//a");
	public static By sectionNames = By.xpath("//div[contains(@class,'globalhd')]//h2/a");
	
}
