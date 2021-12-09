@News18API
Feature: English : Validate Stories in all Sections by comparing Mobile Web and Mobile App
    
      @English
    Scenario: Validate existing Top stories for Both Mobile Web and Mobile App 

	Given User Launches the Mobile Web browser
	When User navigates to News18 mobile web "English" home page
	Then Gets all top stories list
	And User hits News18 mobile app api
	Then Compare the stories of Mobile Web and Mobile App
	
	@English
	Scenario Outline: Validate existing stories for sections in Both Mobile Web and Mobile App 
	Given User Launches the Mobile Web browser
	Given User navigates to News18 mobile web "English" home page
	When Gets top 5 stories of "<Section>" list
	And User hits News18 mobile app apifor "<Section>"
	Then Compare the stories of Mobile Web and Mobile App
	
	Examples:
	|Section|
  |HOT & Trending|
  |Videos|
  |India|
	|Photos|
	|Cricket|
	|Technology|
	|World|

	@English
	Scenario: Validate the sections present in Both Mobile Web and Mobile App 
  Given User Launches the Mobile Web browser
	Given User navigates to News18 mobile web "English" home page
	When Gets all sections list
	And User hits News18 mobile app api for getting sections
	Then Compare the sections in Mobile Web and Mobile App