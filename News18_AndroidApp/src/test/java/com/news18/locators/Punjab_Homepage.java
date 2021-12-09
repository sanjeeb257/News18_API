package com.news18.locators;

import org.openqa.selenium.By;

public class Punjab_Homepage {
	public static By topStory = By.xpath("//div[@class='bigstory']/div[@class='bigstory']/h2/a");
	public static By verticleStoryList = By.xpath("//a[text()='Latest News']/ancestor::div[contains(@class,'globalhd')]/following-sibling::*//a");

}
