package com.news18.locators;

import org.openqa.selenium.By;

public class Kannada_Homepage {
	public static By topStories = By.xpath("//div[@id='homePAgeSlider']//li//img");
	public static By bottomslider = By.xpath("//ul[@id='bottomSlider']/li//img");
	public static By sectionNames = By.xpath("//section[contains(@class,'bgclr')]//h2[contains(@class,'hd')]/a");

}

