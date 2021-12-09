package com.news18.locators;

import org.openqa.selenium.By;

public class English_Homepage {
	public static By homepageNews = By.xpath("//div[@class='mstory-thumb-wrap']//div[@class='mstory-row']");
	public static By newsList = By.xpath("//div[@class='mtopstory-wrap']//li");
	public static By mainTitle = By.xpath("//h1[@class='mlead-head']/a");
	public static By titleHeader = By.xpath("//h1");
	public static By hotAndTrendingCount = By.xpath("//div[@class='hntranding']//ul/li");
	public static By videos = By.xpath("//h2//span[text()='Videos']");
	public static By videoRightButton = By.xpath("//div[contains(@class,'video-gallery-slider')]//a[@class='right-arrow r1 act']");
	public static By photoGalleries = By.xpath("//span[text()='Photogalleries']");
	public static By photoGalleriesRightArrow = By.xpath("//div[contains(@class,'photo-gallery-slider')]//a[@class='right-arrow r1 act']");
	public static By sections = By.xpath("//div[contains(@class,'hntranding') or contains(@class,'vspacer30')]//h2/span");
}
