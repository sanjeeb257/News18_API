package com.news18.locators;

import org.openqa.selenium.By;

public class Urdu_Homepage {
	public static By topStory = By.xpath("//div[@class='mid-bigstory']//div[@class='bigstory']//h2");
	public static By storyList = By.xpath("//a[text()='تازہ خبریں']/ancestor::div[contains(@class,'globalhd')]/following-sibling::*//a");
	public static By sectionNames = By.xpath("//div[contains(@class,'globalhd')]//h2/a");

}
