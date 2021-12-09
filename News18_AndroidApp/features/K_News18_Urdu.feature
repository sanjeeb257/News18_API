@News18API
Feature: Urdu : Validate Stories in all Sections by comparing Mobile Web and Mobile App
   
      @Urdu
    Scenario: Validate existing Top stories for Both Mobile Web and Mobile App 
    
    Given User Launches the Mobile Web browser 
	When User navigates to News18 mobile web "Urdu" home page
	Then Gets all top stories list in Urdu language
	And User hits News18 mobile app Urdu language api
	Then Compare the stories of Mobile Web and Mobile App in Urdu language
	
	  @Urdu
    Scenario Outline: Validate existing Top stories for Both Mobile Web and Mobile App 

	Given User navigates to News18 mobile web "Urdu" home page
	When Gets all "<Section>" list in Urdu language
	And User hits News18 mobile app Urdu language api for "<Section>" section
	Then Compare the stories of Mobile Web and Mobile App in Urdu language
	
	Examples:
	|Section|
	|قومی منظر|
	|جموں و کشمیر|
	|انٹرٹینمنٹ|
	|تصاویر|
	|عالمی منظر|
	|اسپورٹس|
	|تعلیم و روزگار|
	|معیشت|
	
	
	 @Urdu
	Scenario: Validate the sections present in Both Mobile Web and Mobile App 
	Given User navigates to News18 mobile web "Urdu" home page
	When Gets all sections list in Urdu language
	And User hits News18 mobile app Urdu api for getting sections
	Then Compare the sections in Mobile Web and Mobile App for Urdu language