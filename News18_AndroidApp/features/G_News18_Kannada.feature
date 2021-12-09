@News18API
Feature: Kannada : Validate Stories in all Sections by comparing Mobile Web and Mobile App
   
      @Kannada
    Scenario: Validate existing Top stories for Both Mobile Web and Mobile App 
    
    Given User Launches the Mobile Web browser
	When User navigates to News18 mobile web "Kannada" home page
	Then Gets all top stories list in Kannada language
	And User hits News18 mobile app Kannada language api
	Then Compare the stories of Mobile Web and Mobile App in Kannada language
	
	  @Kannada
    Scenario Outline: Validate existing Top stories for Both Mobile Web and Mobile App 

	Given User navigates to News18 mobile web "Kannada" home page
	When Gets all "<Section>" list in Kannada language
	And User hits News18 mobile app Kannada language api for "<Section>" section
	Then Compare the stories of Mobile Web and Mobile App in Kannada language
	
	Examples:
	|Section|
	|  ರಾಜ್ಯ     |
	| ವಿಡಿಯೋ |
	|  ಕ್ರೀಡೆ      |
	|  ಸಿನಿಮಾ |
	| ಫೋಟೋ |
	|ಲೈಫ್ ಸ್ಟೈಲ್|
	|ದೇಶ-ವಿದೇಶ|
	|ಮೊಬೈಲ್- ಟೆಕ್|

		@Kannada
	Scenario: Validate the sections present in Both Mobile Web and Mobile App 
	Given User navigates to News18 mobile web "Kannada" home page
	When Gets all sections list in Kannada language
	And User hits News18 mobile app Kannada api for getting sections
	Then Compare the sections in Mobile Web and Mobile App for Kannada language