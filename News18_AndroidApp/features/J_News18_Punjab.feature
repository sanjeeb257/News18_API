@News18API
Feature: Punjab : Validate Stories in all Sections by comparing Mobile Web and Mobile App
   
      @Punjab
    Scenario: Validate existing Top stories for Both Mobile Web and Mobile App 
    
    Given User Launches the Mobile Web browser 
	When User navigates to News18 mobile web "Punjab" home page
	Then Gets all top stories list in Punjab language
	And User hits News18 mobile app Punjab language api
	Then Compare the stories of Mobile Web and Mobile App in Punjab language
	
	  @Punjab
    Scenario Outline: Validate existing Top stories for Both Mobile Web and Mobile App 

	Given User navigates to News18 mobile web "Punjab" home page
	When Gets all "<Section>" list in Punjab language
	And User hits News18 mobile app Punjab language api for "<Section>" section
	Then Compare the stories of Mobile Web and Mobile App in Punjab language
	
	Examples:
	|Section|
	|ਪੰਜਾਬ|
	|ਰਾਸ਼ਟਰੀ|
	|ਅੰਤਰਰਾਸ਼ਟਰੀ|
	|APP_LIFESTYLE|
	|ਮਨੋਰੰਜਨ|
	|ਖੇਡਾਂ|
	|ਫੋਟੋ|
	|ਵੀਡੀਓ|

	
	
	 @Punjab
	Scenario: Validate the sections present in Both Mobile Web and Mobile App 
	Given User navigates to News18 mobile web "Punjab" home page
	When Gets all sections list in Punjab language
	And User hits News18 mobile app Punjab api for getting sections
	Then Compare the sections in Mobile Web and Mobile App for Punjab language